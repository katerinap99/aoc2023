package com.example.aoc.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tree {
    Day19.Node root;

    String label;

    public Tree( String label) {
        this.root = null;
        this.label = label;
    }

    private Day19.Node addRecursive(Day19.Node current, Day19.Step currentStep, Map<String, Day19.Workflow> subtrees, List<Day19.Step> steps) {
        if (current == null) {
            if (currentStep.getCondition().equals(""))
                return new Day19.Node(currentStep.destination(), null, null);
            steps = subtrees.get(currentStep.destination()).steps();
            current = new Day19.Node(steps.get(0).getCondition(), null, null);
        }
            current.left = addRecursive(current.left, steps.get(0), subtrees, steps);
            current.right = addRecursive(current.right, steps.get(1), subtrees, steps.subList(1, steps.size()));
        return current;
    }

    public void add(Day19.Node root, Map<String, Day19.Workflow> subtrees) {
        this.root = addRecursive(root, subtrees.get("in").steps().get(0), subtrees, subtrees.get("in").steps());
    }

    public void traversePreOrder(Day19.Node node) {
        if (node != null) {
            System.out.print(" " + node.condition);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }
}
