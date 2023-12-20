package com.example.aoc.day19;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day19 {

    private List<Map<String, Integer>> parts = new ArrayList<>();
    private Map<String, Workflow> workflows =new HashMap<>();

    private Map<String, Long> min = new HashMap<>(Map.of("x", 0L, "m", 0L, "a", 0L, "s", 0L));
    private Map<String, Long> max = new HashMap<>(Map.of("x", 4000L, "m", 4000L, "a", 4000L, "s", 4000L));
    public record Workflow(
            String label,
            List<Step> steps,
            String finalState
    ){}

    public record Step(
            String category,
            String operator,
            Integer operand,
            String destination
    ){}

    public void parseInput(String input) {
        String[] inputParts = input.split("\n\n");
        this.workflows =  Arrays.stream(inputParts[0].split("\n")).map(line -> {
            String[] workflow = line.split("\\{");
            String[] steps = workflow[1].substring(0, workflow[1].length() - 1).split(",");
            String finalState = steps[steps.length - 1];
            steps = Arrays.copyOfRange(steps, 0, steps.length-1);
            List<Step> stepsList = Arrays.stream(steps)
                    .map(step -> {
                        String[] stepParts = step.split(":");
                        return new Step(stepParts[0].substring(0, 1), stepParts[0].substring(1, 2), Integer.parseInt(stepParts[0].substring(2)), stepParts[1]);
                    })
                    .toList();
            return new Workflow(workflow[0], stepsList, finalState);
        }).collect(Collectors.toMap(Workflow::label, Function.identity()));
        this.parts = Arrays.stream(inputParts[1].split("\n"))
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
        for (int i=0; i< workflow.steps.size(); i++){
            String category = workflow.steps.get(i).category;
            String operator = workflow.steps.get(i).operator;
            Integer operand = workflow.steps.get(i).operand;
            String destination = workflow.steps.get(i).destination;
            if (operator.equals("<")){
                if (part.get(category)<operand){
                    if (destination.equals("R") || destination.equals("A"))
                        return destination;
                    else
                        return move(part, workflows.get(destination), dest);
                }
            }
            if (operator.equals(">")){
                if (part.get(category)>operand){
                    if (destination.equals("R") || destination.equals("A"))
                        return destination;
                    else
                        return move(part, workflows.get(destination), dest);
                }
            }
        }

        return move(part, workflows.get(workflow.finalState), workflow.finalState);
    }

    private String move2(Workflow workflow, String dest, Map<String, Long> min, Map<String, Long> max) {
        if (dest.equals("A")) return "A";
        if (dest.equals("R")) return "R";
        for (int i=0; i< workflow.steps.size(); i++) {
            System.out.println(workflow.label);
            String category = workflow.steps.get(i).category;
            String operator = workflow.steps.get(i).operator;
            Integer operand = workflow.steps.get(i).operand;
            String destination = workflow.steps.get(i).destination;
            if (operator.equals("<")){
                if (max.get(category)>operand) {
                    if (destination.equals("R"))
                        return destination;
                    max.put(category, (long) (operand - 1));
                    if(destination.equals("A"))
                        return destination;
                    else
                        return move2(workflows.get(destination), dest, min, max);
                }
            }
            if (operator.equals(">")) {
                if (min.get(category)<operand) {
                    if (destination.equals("R"))
                        return destination;
                    min.put(category, (long) operand+1);
                    if (destination.equals("A"))
                        return destination;
                    else
                        return move2( workflows.get(destination), dest, min, max);
                }
            }
        }
        if(workflow.finalState.equals("A")) {

        }
        return move2(workflows.get(workflow.finalState), workflow.finalState, min, max);
    }

    public int moveAll() {
        int sum =0;
        for (Map<String, Integer> part: parts) {
            if (move(part, workflows.get("in"), "in").equals("A")){
                sum+=part.get("x")+part.get("m")+part.get("a")+part.get("s");
            }
        }
        return sum;
    }

    public long moveAll2() {
        long count = 0;
        for (Workflow workflow: workflows.values()) {
            long combinations = 0;
            if (move2( workflow, workflow.label, min, max).equals("A")){
               combinations = (long) (max.get("x") - min.get("x")) *(max.get("m")-min.get("m"))*(max.get("a")-min.get("a"))*(max.get("s")-min.get("s"));
               count += combinations;
            }
        }
        return count;
    }

    public long getAccepted() {
        return parts.stream().map(part -> move(part, workflows.get("in"), "in")).filter(res-> res.equals("A")).count();
    }

    public static void main(String[] args) throws IOException {
        String input = Utils.readFile(new File("src/main/java/com/example/aoc/day19/input.txt"));
        Day19 day19 = new Day19();
        day19.parseInput(input);
        System.out.println("");
        System.out.println(day19.moveAll());
        System.out.println(day19.moveAll2());
    }
}
