package com.example.aoc.day14;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Day14 {

    private static Character[][] parseInput(List<String> input) {
        int columns = Utils.toCharStream(input.get(0)).toList().size();
        Character [][] map = new Character[input.size()][columns];
        for (int i = 0; i < input.size(); i++) {
            List<Character> characters = Utils.toCharStream(input.get(i)).toList();
            for (int j = 0; j < characters.size(); j++) {
                map[i][j] = characters.get(j);
            }
        }
        return map;
    }


    private static void tiltNorth(Character[][] characters) {
        for (int i=1; i< characters.length; i++) {
            for (int j = 0; j < characters[i].length; j++) {
                if (characters[i][j] == 'O') {
                    int currentRow = i;
                    while (currentRow > 0 && characters[currentRow-1][j] == '.') {
                        currentRow--;
                    }
                    if (currentRow != i) {
                        characters[i][j] = '.';
                        characters[currentRow][j] = 'O';
                    }
                }
            }
        }
    }

    private static void tiltSouth(Character[][] characters) {
        for (int i=characters.length-2; i>=0; i--) {
            for (int j = 0; j < characters[i].length; j++) {
                if (characters[i][j] == 'O') {
                    int currentRow = i;
                    while (currentRow < characters.length-1 && characters[currentRow+1][j] == '.') {
                        currentRow++;
                    }
                    if (currentRow != i) {
                        characters[i][j] = '.';
                        characters[currentRow][j] = 'O';
                    }
                }
            }
        }
    }

    private static void tiltEast(Character[][] characters) {
        for (int i=0; i< characters.length; i++) {
            for (int j = characters[i].length-2; j >=0 ; j--) {
                if (characters[i][j] == 'O') {
                    int currentColumn = j;
                    while (currentColumn < characters[0].length-1 && characters[i][currentColumn+1] == '.') {
                        currentColumn++;
                    }
                    if (currentColumn != j) {
                        characters[i][j] = '.';
                        characters[i][currentColumn] = 'O';
                    }
                }
            }
        }
    }

    private static void tiltWest(Character[][] characters) {
        for (int i=0; i< characters.length; i++) {
            for (int j =1; j < characters[i].length ; j++) {
                if (characters[i][j] == 'O') {
                    int currentColumn = j;
                    while (currentColumn > 0 && characters[i][currentColumn-1] == '.') {
                        currentColumn--;
                    }
                    if (currentColumn != j) {
                        characters[i][j] = '.';
                        characters[i][currentColumn] = 'O';
                    }
                }
            }
        }
    }

    private static int calculateSum(Character[][] characters) {
        int sum = 0;
        for (int i=0; i< characters.length; i++) {
            for (int j=0; j<characters[i].length; j++){
                if (characters[i][j] == 'O')
                    sum+=(characters.length-i);
            }
        }
        return sum;
    }

    private static void circularTilt(Character[][] characters){
        tiltNorth(characters);
        tiltWest(characters);
        tiltSouth(characters);
        tiltEast(characters);
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day14/input.txt"));
        Character[][] characters = parseInput(input);
        List<Integer> states= new ArrayList<>();
        //part1
        tiltNorth(characters);
        System.out.println(calculateSum(characters));
        // part2
        characters = parseInput(input);
        int finalIteration = 1000000000-1;
        int iteration=0;
        while(iteration<=finalIteration){
            circularTilt(characters);
            int stateHash = Arrays.deepHashCode(characters);
            if (states.contains(stateHash)){
                int patternSize = iteration-states.indexOf(stateHash);
                 finalIteration = iteration + ((finalIteration-iteration) % patternSize);
            }
            else {
                states.add(stateHash);
            }
            iteration++;
        }
        System.out.println(calculateSum(characters));
    }
}
