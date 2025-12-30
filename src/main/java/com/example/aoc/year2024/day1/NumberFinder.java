package com.example.aoc.year2024.day1;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFinder {

    private static Pattern pattern;

    private static Matcher matcher;

    private static int findDigits(String s) {
        pattern = Pattern.compile("^[^\\d]*(\\d{1})");
        matcher = pattern.matcher(s);
        String firstDigit = "";
        String lastDigit = "";
        if (matcher.find())
            firstDigit = matcher.group(1);
        StringBuilder stringBuilder = new StringBuilder(s);
        matcher = pattern.matcher(stringBuilder.reverse());
        if(matcher.find())
            lastDigit = matcher.group(1);
        return Integer.parseInt(firstDigit) * 10 + Integer.parseInt(lastDigit) ;
    }

    private static int findDigitsOrWords(String s) {
        pattern = Pattern.compile("(?=(one|1)|(two|2)|(three|3)|(four|4)|(five|5)|(six|6)|(seven|7)|(eight|8)|(nine|9))");
        matcher = pattern.matcher(s);
        List<Integer> digits = new ArrayList<>();
        while(matcher.find()){
            for(int i=1; i<10; i++){
                if(Objects.nonNull(matcher.group(i))){
                    digits.add(i);
                }
            }
        }
        return digits.get(0) * 10 + digits.get(digits.size()-1);
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day1/input.txt"));
        System.out.println(input.stream()
                .map(NumberFinder::findDigits)
                .reduce(Integer::sum));
        // part2
        System.out.println(input.stream()
                .map(NumberFinder::findDigitsOrWords)
                .reduce(Integer::sum));
    }
}

