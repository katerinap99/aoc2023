package com.example.aoc.day4;

import java.util.*;
import java.util.stream.Collectors;

public class Card {
    private int id;
    private List<Integer> winningNumbers;
    private List<Integer> ownNumbers ;

    public Card(int id, List<Integer> winningNumbers, List<Integer> ownNumbers) {
        this.id = id;
        this.winningNumbers = winningNumbers;
        this.ownNumbers = ownNumbers;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public List<Integer> getOwnNumbers() {
        return ownNumbers;
    }

    public static Card separateNumbers(String card) {
        int id = getId(card);
        card = card.substring(10);
        List<String> numbers  = Arrays.stream(card.split("\\|")).toList();
        List<Integer> winningNumbers = Arrays.stream(numbers.get(0).split(" "))
                .filter(num -> !Objects.equals(num, ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> ownNumbers = Arrays.stream(numbers.get(1).substring(1).split(" "))
                .filter(num -> !Objects.equals(num, ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return new Card(id, winningNumbers, ownNumbers);
    }

    public int calculatePoints() {
        return (int) Math.pow(2, getMatchingNumbers().size()-1);
    }

    public List<Integer> getMatchingNumbers() {
        return winningNumbers.stream()
                .filter(num -> ownNumbers.contains(num))
                .toList();
    }

    private static int getId(String card) {
        return Integer.parseInt(card.substring(5,8).stripLeading());
    }

}
