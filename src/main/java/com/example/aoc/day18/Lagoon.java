package com.example.aoc.day18;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Lagoon {
    private record Step(String direction, int depth, String rgb){}
    private record Trench(int x, int y){};
    private List<Step> parseInput(List<String> input) {
        return input.stream()
                .map(line -> {
                    String[] parts = line.split(" ");
                    return new Step(parts[0], Integer.parseInt(parts[1]), parts[2].substring(1, parts[2].length()-1));
                })
                .collect(Collectors.toList());
    }

    private List<Trench> dig(List<Step> steps) {
        List<Trench> trenches = new ArrayList<>();
        int currentRow = 0;
        int currentColumn = 0;
        for (Step step: steps) {
            int offset = 0;
            while (offset < step.depth) {
                switch (step.direction) {
                    case "R" -> {
                        currentColumn++;
                        trenches.add(new Trench(currentRow, currentColumn));
                    }
                    case "L" -> {
                        currentColumn--;
                        trenches.add(new Trench(currentRow, currentColumn));
                    }
                    case "U" -> {
                        currentRow--;
                        trenches.add(new Trench(currentRow, currentColumn));
                    }
                    case "D" -> {
                        currentRow++;
                        trenches.add(new Trench(currentRow, currentColumn));
                    }
                    default -> offset++;
                }
                offset++;
            }
        }
        return trenches;
    }

    private int getDistanceFromNext(Trench trench, List<Trench> trenches) {
       return trenches.stream()
                .filter(t -> t.x == trench.x && t.y > trench.y)
                .min(Comparator.comparingInt(Trench::y))
                .map(value -> value.y - trench.y)
                .orElse(0);
    }

    private boolean isInternal(Set<Trench> trenches, int x, int y) {
        List<Integer> vertical = trenches.stream().filter(trench -> trench.y == y).map(Trench::x).toList();
        int southest = Collections.max(vertical);
        int northest = Collections.min(vertical);
        return x > northest && x < southest && trenches.stream().anyMatch(trench -> trench.x==x-1 && trench.y==y);
    }

    private Set<Trench> digInterior(List<Trench> trenches){
        Set<Trench> trenchSet = new HashSet<>(trenches);
        trenches.sort(Comparator.comparingInt(Trench::y));
        for (Trench trench: trenches){
            int distance = getDistanceFromNext(trench, trenches);
            if (Math.abs(distance) >= 2) {
                int step = 0;
                while(step<distance) {
                    step++;
                    if (isInternal(trenchSet, trench.x, trench.y+step))
                        trenchSet.add(new Trench(trench.x, trench.y+step));
                }
            }
        }
        return trenchSet;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day18/input.txt"));
        Lagoon lagoon = new Lagoon();
        List<Step> steps = lagoon.parseInput(input);
        List<Trench> trenches = lagoon.dig(steps);
        Set<Trench> trenches1 = lagoon.digInterior(trenches);
        System.out.println("");
    }
}
