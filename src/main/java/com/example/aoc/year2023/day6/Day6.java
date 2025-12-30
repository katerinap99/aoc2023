package com.example.aoc.day6;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.example.aoc.Utils;
import com.google.common.collect.Streams;

public class Day6 {
    public record Race(
            long time,
            long recordDistance){}

    public static List<Race> parseRaces(List<String> input) {
        List<Long> times = Arrays.stream(input.get(0).split(":\\s+")[1].split("\\s+"))
                .map(Long::parseLong)
                .toList();
        List<Long> distances = Arrays.stream(input.get(1).split(":\\s+")[1].split("\\s+"))
                .map(Long::parseLong)
                .toList();
        return Streams.zip(times.stream(), distances.stream(), Race::new).collect(Collectors.toList());
    }

    public static long waysToWin(Race race) {
        return LongStream.range(0, race.time)
                .boxed()
                .filter(i -> (race.time-i)*i > race.recordDistance)
                .count();
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day6/input.txt"));
        System.out.println(parseRaces(input).stream()
                .map(Day6::waysToWin)
                .reduce((a,b) -> a*b));
    }
}
