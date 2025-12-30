package com.example.aoc.day5;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Garden {
    public List<Long> seeds;
    public record MapRule(
            String type,
            long destStart,
            long sourceStart,
            long range ){

        public long calculate(long input) {
            return destStart + (input-sourceStart);
        }
    }

    private static List<Long> getSeedIds(String token) {
        return Arrays.stream(token.split(": ")[1].split(" "))
            .toList()
            .stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
    }

    private List<List<MapRule>> parseMaps(String input) {
        List<String> tokens = new java.util.ArrayList<>(Arrays.stream(input.split("\n\n")).toList());
        seeds = getSeedIds(tokens.get(0));
        tokens.remove(0);
        return tokens.stream()
            .map(this::getMapInfo)
            .collect(Collectors.toList());
    }

    private List<MapRule> getMapInfo(String token) {
        List<String> mapInfo = new ArrayList<>(Arrays.stream(token.split("\n")).toList());
        String name = mapInfo.get(0);
        mapInfo.remove(mapInfo.get(0));
        return mapInfo.stream()
            .map(map -> {
                List<Long> mapDetails = Arrays.stream(map.split(" "))
                    .map(Long::parseLong).toList();
            return new MapRule(name, mapDetails.get(0), mapDetails.get(1), mapDetails.get(2));
                    }
                )
            .collect(Collectors.toList());
    }


    public static long getLocation(Long seed, List<List<MapRule>> maps) {
        long result = seed;
        for (List<MapRule> mapRules : maps) {
            long temp = result;
            result = mapRules.stream()
                    .filter(rule -> temp >= rule.sourceStart && temp < rule.sourceStart+rule.range)
                    .findAny()
                    .map(r -> r.calculate(temp))
                    .orElse(temp);
        }
        return result;
    }

    public long getMinLocation(List<List<MapRule>> maps) {
        long min = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i+=2){
            for(long j = seeds.get(i); j< seeds.get(i) + seeds.get(i+1); j++){
                long location = getLocation(j, maps);
                if (location<min)
                    min = location;
            }
        }
        return min;
    }


    public static void main(String[] args) throws IOException {
        String input = Utils.readFile(new File("src/main/java/com/example/aoc/day5/input.txt"));
        Garden garden = new Garden();
        List<List<MapRule>> maps = garden.parseMaps(input);
        System.out.println(garden.seeds.stream().mapToLong(seed -> getLocation(seed, maps)).min());
        System.out.println(garden.getMinLocation(maps));
    }
}