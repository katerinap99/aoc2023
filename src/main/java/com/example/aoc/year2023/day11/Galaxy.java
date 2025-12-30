package com.example.aoc.day11;

public class Galaxy {
    private int x;
    private int y;

    public Galaxy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static boolean equals(Galaxy a, Galaxy b) {
        return a.x == b.x && a.y == b.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}