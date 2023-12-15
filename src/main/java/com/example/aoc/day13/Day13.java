package com.example.aoc.day13;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {
    public static List<Notes> parseNotes(String input) {
        return Arrays.stream(input.split("\n\n"))
                .map(Notes::new)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        String input = Utils.readFile(new File("src/main/java/com/example/aoc/day13/input.txt"));
        List<Notes> notes = parseNotes(input);
        notes.forEach(Notes::findReflections);
        notes.forEach(Notes::replace);
        System.out.println(notes.stream()
                .map(note -> note.getSymmetryOrientation().equals("horizontal") ? note.getReflectionLine()*100 : note.getReflectionLine())
                .reduce(Integer::sum));
    }
}


