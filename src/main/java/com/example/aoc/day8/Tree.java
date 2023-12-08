package com.example.aoc.day8;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Tree {
    private Map<String, Node> nodesMap;
    private List<String> traversalDirections;

    public void generateTree(List<String> input) {
        this.traversalDirections = Arrays.stream(input.get(0).split("")).toList();
        input.remove(0);
        this.nodesMap = input.stream()
                .map(this::generateNode)
                .collect(Collectors.toMap(Node::getLabel, Function.identity()));
    }

    private Node generateNode(String token) {
        List<String> parts = Arrays.stream(token.split("[=(),\\s+]"))
                .filter(a -> !a.equals("")).toList();
        return new Node(parts.get(0), parts.get(1), parts.get(2));
    }

    public int traverse(Node node, int iteration) {
        while (!node.getLabel().equals("ZZZ")) {
            iteration++;
            String direction = getDirection(iteration);
            node = nodesMap.get(direction.equals("L") ? node.getLeft() : node.getRight());
        }
        return iteration;
    }

    private String getDirection(int iterations) {
        return Math.floorMod(iterations, this.traversalDirections.size()) == 0 ?
                this.traversalDirections.get(this.traversalDirections.size() - 1) :
                this.traversalDirections.get(Math.floorMod(iterations, this.traversalDirections.size()) - 1);
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day8/input.txt")).stream()
                .filter(s -> !s.equals(""))
                .collect(Collectors.toList());
        Tree tree = new Tree();
        tree.generateTree(input);
        System.out.println(tree.traverse(tree.nodesMap.get("AAA"), 0));
    }
}