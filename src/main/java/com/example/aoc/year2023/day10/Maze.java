package com.example.aoc.day10;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Maze {
    private final Map<Position, Pipe> pipes = new HashMap<>();
    private Position start;

    private record Pipe(Position position, Function<Position, Position> move){};
    private record Position(int x, int y){
        @Override
        public int hashCode() {
            return x*10000 + y;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Position) obj).x() == x && ((Position) obj).y() == y ;
        }
    };


    private Map<Character, Function<Position, Function<Position, Position>>> move = Map.of(
            '-', curr -> prev -> prev.y() < curr.y()
                    ? new Position(curr.x(), curr.y() +1)
                    : new Position(curr.x(), curr.y() -1),
            '|', curr -> prev -> prev.x() < curr.x()
                    ? new Position(curr.x()+1, curr.y())
                    : new Position(curr.x()-1, curr.y()),
            'L', curr -> prev -> prev.x() < curr.x()
                    ? new Position(curr.x(), curr.y()+1)
                    : new Position(curr.x()-1, curr.y()),
            'J', curr -> prev -> prev.x() < curr.x()
                    ? new Position(curr.x(), curr.y()-1)
                    : new Position(curr.x()-1, curr.y()),
            '7', curr -> prev -> prev.x() > curr.x()
                    ? new Position(curr.x(), curr.y()-1)
                    : new Position(curr.x()+1, curr.y()),
            'F', curr -> prev -> prev.x() > curr.x()
                    ? new Position(curr.x(), curr.y()+1)
                    : new Position(curr.x()+1, curr.y()),
            '.', curr -> prev -> curr
    );

    private void parse(List<String> input) {
        for (int i=0; i< input.size(); i++) {
            for (int j=0; j< input.get(i).length(); j++){
                Position position = new Position(i, j);
                char c = input.get(i).charAt(j);
                if (c == 'S')
                    start = position;
                else
                    pipes.put(position, new Pipe(position, move.get(c).apply(position)));
            }
        }
    }

    public int getSteps(Position next) {
        Position current = next;
        Position previous = start;
        int steps = 1;
        while(!current.equals(start)){
            Position newPosition = pipes.get(current).move.apply(previous);
            previous = current;
            current = newPosition;
            steps++;
        }
        return steps/2;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day10/input.txt"));
        Maze maze = new Maze();
        maze.parse(input);
        System.out.println(maze.getSteps(new Position(maze.start.x()+1, maze.start.y())));
    }
}
