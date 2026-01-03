package com.example.aoc.year2025.day2;

import com.example.aoc.Utils;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day2 {

    public static BigInteger sumInvalid(List<Pair<BigInteger, BigInteger>> ranges,
                                        Function<BigInteger, Boolean> validityFunction) {
        BigInteger sum = BigInteger.valueOf(0);
        for(Pair<BigInteger, BigInteger> range: ranges) {
            BigInteger low = range.getKey();
            BigInteger high = range.getValue();
            while (low.compareTo(high) <= 0) {
                if (validityFunction.apply(low))
                    sum = sum.add(low);
                low = low.add(BigInteger.valueOf(1));
            }
        }
        return sum;
    }

    private static boolean isTwiceRepeated(BigInteger value) {
        String string = value.toString();
        char[] digits = string.toCharArray();
        int medianIndex = digits.length/2;
        return string.substring(0, medianIndex).equals(string.substring(medianIndex));
    }

    private static boolean isAtLeastTwiceRepeated(BigInteger value) {
        String string = value.toString();
        char[] digits = string.toCharArray();
        for (int i=1; i< digits.length; i++) {
            String pattern = string.substring(0, i);
            if (string.split(pattern).length == 0)
                return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        String input = Utils.readFile(new File("src/main/java/com/example/aoc/year2025/day2/input.txt"));
        List<String> ranges = Arrays.stream(input.split(",")).toList();
        List<Pair<BigInteger, BigInteger>> rangesValues = ranges.stream()
                .map(range -> range.split("-"))
                .map(split -> new Pair<>(BigInteger.valueOf(Long.parseLong(split[0])),
                        BigInteger.valueOf(Long.parseLong(split[1]))))
                .toList();
        System.out.println(sumInvalid(rangesValues, Day2::isTwiceRepeated));
        System.out.println(sumInvalid(rangesValues, Day2::isAtLeastTwiceRepeated));
    }
}
