package com.example.aoc.day19;

import java.util.List;

public class Tree {

    public static class Node {
        String label;
        String category;
        String operator;
        Integer operand;
        String destination;
        Node left;
        Node right;


        public Node(String label, String category, String operator, Integer operand, String destination) {
            this.label = label;
            this.category = category;
            this.operator = operator;
            this.operand = operand;
            this.destination=destination;
            left = null;
            right=null;
        }

        public Node(String label) {
            this.label = label;
        }
    }
    Node root;

    private Node addRecursive(Node current, Node next) {
        if (current == null) {
            return new Node(next.label, next.category, next.operator,
                    next.operand, next.destination);
        }
        current.left = addRecursive(current.left, next);
        current.right = addRecursive(current.right, next);

        return current;
    }

    private void add(Node node) {
        root = addRecursive(root, node);
    }

    public void add(List<Node> nodes){
        for(Node node: nodes){
            add(node);
        }
    }


}
