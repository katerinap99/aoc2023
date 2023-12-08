package com.example.aoc.day8;

public class Node {
    private String label;
    private String left;
    private String right;

    public Node(String label, String left, String right) {
        this.label = label;
        this.left = left;
        this.right = right;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
