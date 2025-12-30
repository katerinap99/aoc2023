package com.example.aoc.year2024.day5;

import com.example.aoc.Utils;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PageOrdering {
    private final Set<Pair<Integer, Integer>> rules;
    private final List<int[]> lists;

    public PageOrdering() {
        rules = new HashSet<>();
        lists = new ArrayList<>();
    }

    private void parse(String input) {
        Arrays.stream(input.split("\n"))
                .filter(line -> line.contains("|"))
                .forEach(rule -> {
                    String[] numbers = rule.strip().split("\\|");
                    rules.add(new Pair<>(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
                });
        Arrays.stream(input.split("\n"))
                .filter(line -> line.contains(","))
                .forEach(list -> {
                            lists.add(Arrays.stream(list.strip().split(","))
                                    .mapToInt(Integer::parseInt)
                                    .toArray());
                        }
                );
    }

    public boolean oppositeRuleFound(int a, int b) {
        return rules.stream()
                .anyMatch(rule -> rule.getValue().equals(a) && rule.getKey().equals(b));
    }

    public boolean isCorrect(int[] list) {
        for(int i = 0;i < list.length - 1;i ++) {
            if (oppositeRuleFound(list[i], list[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public void reorder(int[] list, int index) {
        if (oppositeRuleFound(list[index], list[index+1])) {
            if (oppositeRuleFound(list[index-1], list[index+1])) {
                // swap b,c
            }
        }
    }

//    public void fixOrder(int[] list) {
//        for (int i=0; i < list.length-2; i++) {
//            if
//        }
//    }
    public int sumCorrectMedians() {
        return lists.stream()
                .filter(this::isCorrect)
                .map(list-> list[list.length/2])
                .reduce(Integer::sum)
                .orElse(0);
    }

    public int sumCorrectMediansAfterFixing() {
        return lists.stream()
                .filter(list -> !isCorrect(list))
                .map(list-> {
//                    fixOrder(list);
                    return list[list.length/2];
                })
                .reduce(Integer::sum)
                .orElse(0);
    }

    public static void main(String[] args) throws IOException {
        String input = Utils.readFile(new File("src/main/java/com/example/aoc/year2024/day5/input.txt"));
        PageOrdering pageOrdering = new PageOrdering();
        pageOrdering.parse(input);
        System.out.println(pageOrdering.sumCorrectMedians());
        System.out.println(pageOrdering.sumCorrectMediansAfterFixing());
    }
}

