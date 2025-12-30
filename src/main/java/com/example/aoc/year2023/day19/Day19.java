package com.example.aoc.year2023.day19;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day19 {
    private List<Map<String, Integer>> parts = new ArrayList<>();
    private Map<String, Workflow> workflows =new HashMap<>();

    private List<Tree> trees = new ArrayList<>();
    public record Workflow(String label, List<Step> steps){}
    public record Step(String category, String operator, Integer operand, String destination, boolean isFinal) {
        public String getCondition() {
            return isFinal ? "" : category + operator+operand.toString() ;
        }
    }

    public static class Node{
        String condition;
        Node left;
        Node right;

        public Node(String condition, Node left, Node right) {
            this.condition = condition;
            this.left = left;
            this.right = right;
        }
    }

    public void parseInput(String input) {
        String[] inputParts = input.split("\r\n\r\n");
        this.workflows =  Arrays.stream(inputParts[0].split("\r\n")).map(line -> {
            String[] workflow = line.split("\\{");
            String[] steps = workflow[1].substring(0, workflow[1].length() - 1).split(",");
            String finalState = steps[steps.length - 1];
            Step finalStep = new Step(null, null, null, finalState, true);
            steps = Arrays.copyOfRange(steps, 0, steps.length-1);
            List<Step> stepsList = new ArrayList<>(Arrays.stream(steps)
                    .map(step -> {
                        String[] stepParts = step.split(":");
                        return new Step(stepParts[0].substring(0, 1), stepParts[0].substring(1, 2), Integer.parseInt(stepParts[0].substring(2)), stepParts[1], false);
                    })
                    .toList());
            stepsList.add(finalStep);
            return new Workflow(workflow[0], stepsList);
        }).collect(Collectors.toMap(Workflow::label, Function.identity()));
        this.parts = Arrays.stream(inputParts[1].split("\r\n"))
                .map(line -> {
                    line = line.substring(1, line.length()-1);
                    String[] ratings = line.split(",");
                    return Map.of("x", Integer.parseInt(ratings[0].split("=")[1]),
                            "m", Integer.parseInt(ratings[1].split("=")[1]),
                            "a", Integer.parseInt(ratings[2].split("=")[1]),
                            "s",Integer.parseInt(ratings[3].split("=")[1]));
                })
                .collect(Collectors.toList());
    }

    private String move(Map<String, Integer> part, Workflow workflow, String dest) {
        if (dest.equals("A")) return "A";
        if (dest.equals("R")) return "R";
        for (int i=0; i< workflow.steps.size(); i++) {
            String destination = workflow.steps.get(i).destination;
            if (workflow.steps.get(i).isFinal)
                return move(part, workflows.getOrDefault(destination, null), destination);
            String category = workflow.steps.get(i).category;
            String operator = workflow.steps.get(i).operator;
            Integer operand = workflow.steps.get(i).operand;
            if (operator.equals("<") && part.get(category)<operand)
                    return move(part, workflows.getOrDefault(destination, null), destination);
            if (operator.equals(">") && part.get(category)>operand)
                    return move(part, workflows.getOrDefault(destination, null), destination);
        }
        return dest;
    }

    List<List<String>> successfulPaths = new ArrayList<>();
    private void getAllOutcomes(List<Step> workflow, List<String> path) {
        ArrayList<String> current = new ArrayList<>(path);
        workflow.forEach(step -> {
            String destination = step.destination;
            if (destination.equals("R")) {
                return;
            }
            if (!step.isFinal) {
                current.add(step.getCondition());
            }
            if (destination.equals("A")) {
                successfulPaths.add(current);
                return;
            }
            getAllOutcomes(workflows.get(destination).steps, current);

        });

    }

    public int moveAll() {
       return parts.stream()
               .filter(part -> move(part, workflows.get("in"), "in").equals("A"))
               .map(part -> part.get("x") + part.get("m") + part.get("a") + part.get("s"))
               .reduce(Integer::sum)
               .orElse(0);
    }

    public void getAllPaths() {
       getAllOutcomes(workflows.get("in").steps, List.of());
        System.out.println("ok");
    }


    public static void main(String[] args) throws IOException {
        String input = Utils.readFile(new File("src/main/java/com/example/aoc/day19/input.txt"));
        Day19 day19 = new Day19();
        day19.parseInput(input);
        System.out.println(day19.moveAll());
        day19.getAllPaths();
        Tree tree = new Tree("in");
        tree.add(new Node(day19.workflows.get("in").steps.get(0).getCondition(), null, null), day19.workflows);
        tree.traversePreOrder(tree.root);
    }
}
