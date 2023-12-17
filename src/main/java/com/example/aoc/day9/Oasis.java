package com.example.aoc.day9;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Oasis {
    private List<Long> steps(List<Long> numbers) {
        return IntStream.range(1, numbers.size())
                .mapToObj(i -> numbers.get(i) - numbers.get(i-1))
                .collect(Collectors.toList());
    }

    private Long findNext(List<Long> numbers) {
        return numbers.stream().distinct().count() == 1
                ? numbers.get(0)
                : numbers.get(numbers.size() - 1) + findNext(steps(numbers));
    }

    public long calculate(boolean reverse, List<List<Long>> input) {
        return input.stream()
                .map(ArrayList::new)
                .peek(reverse ? Collections::reverse : __ -> {})
                .mapToLong(this::findNext)
                .sum();
    }

    public static void main(String[] args) throws IOException {
        List<List<Long>> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day9/input.txt"))
                .stream()
                .map(s -> Arrays.stream(s.split(" ")).map(Long::parseLong).toList())
                .toList();
        Oasis oasis = new Oasis();
        System.out.println(oasis.calculate(false, input));
        System.out.println(oasis.calculate(true, input));
    }
}
