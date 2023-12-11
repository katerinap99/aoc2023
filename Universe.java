package com.example.aoc.day11;

import com.example.aoc.Utils;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Universe {
    public List<Galaxy> galaxies;
    public List<List<String>> universeMap;

    public Universe(List<String> input) {
        this.galaxies = new ArrayList<>();
        this.universeMap = input.stream()
                .map(row -> Arrays.stream(row.split("")).collect(Collectors.toList()))
                .toList();
    }
    public void locateGalaxies(List<List<String>> universeMap){
        for(int i=0; i < universeMap.size(); i++) {
            for(int j=0; j< universeMap.get(i).size(); j++)
                if (universeMap.get(i).get(j).equals("#"))
                    galaxies.add(new Galaxy(i,j));
        }
    }
    public long getMinDistance(Galaxy a, Galaxy b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY()-b.getY());
    }
    public long getDistancesSum() {
        return (Lists.cartesianProduct(galaxies, galaxies)
                .stream()
                .filter(pair -> !Galaxy.equals(pair.get(0), pair.get(1)))
                .map(item -> getMinDistance(item.get(0), item.get(1)))
                .reduce(Long::sum)
                .orElse(0L)) / 2;
    }
    public void expand(int offset) {
        int columns = universeMap.get(0).size();
        locateGalaxies(universeMap);
        int currentRow = 0;
        for (List<String> rows : universeMap) {
            if (!rows.contains("#")) {
                int temp = currentRow;
                galaxies.stream().filter(galaxy -> galaxy.getX() > temp).forEach(galaxy -> galaxy.setX(galaxy.getX()+offset));
                currentRow += offset;
            }
            currentRow++;
        }

        int currentColumn = 0;
        for(int i=0; i< columns; i++) {
            boolean galaxyFound = false;
            for (List<String> rows : universeMap) {
                if (!Objects.equals(rows.get(i), ".")) {
                    galaxyFound = true;
                    break;
                }
            }
            if (!galaxyFound){
                int temp = currentColumn;
                galaxies.stream().filter(galaxy -> galaxy.getY() > temp).forEach(galaxy -> galaxy.setY(galaxy.getY()+offset));
                currentColumn +=offset;
            }
            currentColumn++;
        }
    }

    public static void main(String[] args) throws IOException {
       List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day11/input.txt"));
        Universe universe = new Universe(input);
        universe.expand(1);
        System.out.println(universe.getDistancesSum());

        universe = new Universe(input);
        universe.expand(999999);
        System.out.println(universe.getDistancesSum());
    }
}
