package com.example.aoc.day21;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StepCounter {
    private char[][] map;
    private Position start;

    private List<Position> externals;
    public void parse(List<String> input) {
        map = new char[input.size()][];
        for (int i=0; i< input.size(); i++) {
            map[i] = input.get(i).toCharArray();
            if (input.get(i).contains("S"))
                start = new Position(i, input.get(i).indexOf('S'));
        }
    }

    private record Position(int x, int y){}

    private Set<Position> move(Set<Position> positions, int maxX, int maxY) {
        Set<Position> next = new HashSet<>();
        for (Position position : positions) {
            int x = position.x;
            int y = position.y;
        if (x < maxX - 1 && map[x + 1][y] != '#')
            next.add(new Position(x + 1, y));
        if (x >= 1 && map[x - 1][y] != '#')
            next.add(new Position(x - 1, y));
        if (y < maxY - 1 && map[x][y + 1] != '#')
            next.add(new Position(x, y + 1));
        if (y >= 1 && map[x][y - 1] != '#')
            next.add(new Position(x, y - 1));
    }
        return next;
    }

    private int getIndex(int value, int reference,  int iteration, int max) {
        return ((value-reference)/iteration) % (max-reference-1);
    }

    private Set<Position> moveInfinitely(Set<Position> positions, int maxX, int maxY, int step) {
        Set<Position> next = new HashSet<>();
        for (Position position : positions) {
            int iteration = step/(maxX+1-start.x);
            int x = position.x;
            int y = position.y;
        if (x < maxX - 1 && map[x + 1 ][y] != '#')
            next.add(new Position(x+1, y));
        else if ( x==maxX-1 && map[0][y] != '#')
            next.add(new Position(0, y));
        if ((x >= 1 && map[x - 1][y] != '#'))
            next.add(new Position(x - 1, y));
        else if ( x==0 && map[maxX-1][y] != '#')
            next.add(new Position(maxX-1, y));
        if ((y < maxY - 1 && map[x][y+1] != '#'))
            next.add(new Position(x, y+1));
        else if ( (y==maxY-1 && map[x][0] != '#'))
            next.add(new Position(x, 0));
        if ((y >= 1 && map[x][y-1] != '#'))
            next.add(new Position(x, y-1));
        else if ( y==0 && map[x][maxY-1] != '#')
                next.add(new Position(x, maxY-1));
        }
        return next;
    }

    public int countSteps() {
        Set<Position> possiblePositions = new HashSet<>();
        possiblePositions.add(start);
        int count = 1;
           while (count <= 10) {
            possiblePositions = moveInfinitely(possiblePositions, map.length , map[0].length, count);
            count++;
        }
        return possiblePositions.size();
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day21/input.txt"));
        StepCounter stepCounter = new StepCounter();
        stepCounter.parse(input);
        System.out.println(stepCounter.countSteps());
    }
}
