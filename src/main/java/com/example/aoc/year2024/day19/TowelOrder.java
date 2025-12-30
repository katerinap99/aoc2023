package com.example.aoc.year2024.day19;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TowelOrder {
    private List<String> availablePatterns;
    private List<String> towelDesigns;
    private int maxPatternLength;

    public TowelOrder() {
        availablePatterns = new ArrayList<>();
        towelDesigns = new ArrayList<>();
    }

    public boolean isValid(String towelDesign, int currentIndex, int offset, boolean backwardsCheck) {
        if (currentIndex == 0)
            return true;
        if (currentIndex < 0 || offset > maxPatternLength)
            return false;
        if ((backwardsCheck && availablePatterns.contains(towelDesign.substring(currentIndex, currentIndex+offset)))
        || (!backwardsCheck && availablePatterns.contains(towelDesign.substring(currentIndex-offset, currentIndex)))) {
            offset =1;
            return isValid(towelDesign, currentIndex-1, offset, true);
        }
        else {
            offset++;
            return
                    currentIndex-offset >= 0 && isValid(towelDesign, currentIndex, offset, true) ||
                    currentIndex+offset<= towelDesign.length() && isValid(towelDesign, currentIndex, offset, false) ;
        }
    }

    private void parse(List<String> input) {
        availablePatterns = Arrays.stream(input.get(0).split(","))
                .map(s -> {
                    s = s.stripLeading();
                    return s;
                }).toList();
        towelDesigns = input.subList(2, input.size());
        maxPatternLength = availablePatterns.stream()
                .map(String::length)
                .max(Integer::compare)
                .orElse(0);
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/year2024/day19/input.txt"));
        TowelOrder towelOrder = new TowelOrder();
        towelOrder.parse(input);
        System.out.println(towelOrder.towelDesigns
                .stream()
                .filter(towelDesign -> towelOrder.isValid(towelDesign, towelDesign.length()-1, 1, true))
                .count());
    }
}
