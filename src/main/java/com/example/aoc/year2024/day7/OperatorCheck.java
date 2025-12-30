package com.example.aoc.year2024.day7;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OperatorCheck {
    private final List<Equation> equations;

    private static class Node {
        long value;
        Node left;
        Node right;

        public Node(long value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    private static class NonBinaryNode {
        long value;
        NonBinaryNode left;
        NonBinaryNode right;
        NonBinaryNode third;

        public NonBinaryNode(long value) {
            this.value = value;
            left = null;
            right = null;
            third = null;
        }
    }

    private record Equation(long result, List<Integer> operands) {
    }

    public OperatorCheck() {
        this.equations = new ArrayList<>();
    }

    public void parse(List<String> input) {
        input
                .forEach(line -> {
                    String[] parts = line.split(":");
                    long result = Long.parseLong(parts[0]);
                    String[] partsClean = Arrays.copyOfRange(parts[1].split(" "), 1, parts[1].split(" ").length);
                    List<Integer> operands = Arrays.stream(partsClean)
                            .map(Integer::parseInt)
                            .toList();
                    this.equations.add(new Equation(result, operands));
                });
    }

    private boolean isValid(Equation equation) {
        Node root = new Node(equation.operands.get(0));
        List<Node> leaves = List.of(root);
        for (int i = 1; i < equation.operands.size(); i++) {
            ArrayList<Node> newLeaves = new ArrayList<>();
            int index = i;
            leaves.forEach(leaf -> {
                leaf.left = new Node(leaf.value + equation.operands.get(index));
                leaf.right = new Node(leaf.value * equation.operands.get(index));
                newLeaves.add(leaf.left);
                newLeaves.add(leaf.right);
            });
            leaves = newLeaves;
        }
        return leaves.stream().anyMatch(leaf -> leaf.value == equation.result);
    }

    private boolean isValidWithConcat(Equation equation) {
        NonBinaryNode root = new NonBinaryNode(equation.operands.get(0));
        List<NonBinaryNode> leaves = List.of(root);
        for (int i = 1; i < equation.operands.size(); i++) {
            ArrayList<NonBinaryNode> newLeaves = new ArrayList<>();
            int index = i;
            leaves.forEach(leaf -> {
                leaf.left = new NonBinaryNode(leaf.value + equation.operands.get(index));
                leaf.right = new NonBinaryNode(leaf.value * equation.operands.get(index));
                leaf.third = new NonBinaryNode(
                        Long.parseLong(String.valueOf(leaf.value).concat(String.valueOf(equation.operands.get(index)))));
                newLeaves.add(leaf.left);
                newLeaves.add(leaf.right);
                newLeaves.add(leaf.third);
            });
            leaves = newLeaves;
        }
        return leaves.stream().anyMatch(leaf -> leaf.value == equation.result);
    }

    private long sumValidEquations() {
        return equations.stream()
                .filter(this::isValid)
                .map(equation -> equation.result)
                .reduce(Long::sum)
                .orElse(0L);
    }

    private long sumValidEquationsWithConcat() {
        return equations.stream()
                .filter(this::isValidWithConcat)
                .map(equation -> equation.result)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/year2024/day7/input.txt"));
        OperatorCheck operatorCheck = new OperatorCheck();
        operatorCheck.parse(input);
        System.out.println(operatorCheck.sumValidEquations());
        System.out.println(operatorCheck.sumValidEquationsWithConcat());
    }
}
