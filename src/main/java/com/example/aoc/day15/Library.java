package com.example.aoc.day15;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Library {
    private final List<Box> boxes;
    private List<Step> steps;

    public Library(){
        this.boxes = IntStream.range(0, 256)
                .boxed()
                .map(Box::new)
                .toList();
        this.steps = new ArrayList<>();
    }
    private int hash(String s) {
        List<Integer> asciiValues = Utils.toCharStream(s).map(c -> (int) c).toList();
        int totalValue = 0;
        for(int value: asciiValues) {
            totalValue+=value;
            totalValue = totalValue*17;
            totalValue = totalValue % 256;
        }
        return totalValue;
    }
    private int getHashesSum(String s) {
        List<String> steps =  Arrays.stream(s.split(",")).toList();
        List<Integer> stepHashes = new ArrayList<>();
        for(String step: steps){
            stepHashes.add(hash(step));
        }
        return stepHashes.stream().reduce(Integer::sum).orElse(0);
    }

    private void parseSteps(String input) {
        this.steps =  Arrays.stream(input.split(","))
                .map(step -> {
                    String[] parts = step.split("[=-]");
                    return parts.length==1 ? new Step(parts[0], '-', 0) :
                    new Step(parts[0], '=', Integer.parseInt(parts[1]));
                })
                .toList();
    }

    private void placeLenses(){
        this.steps.forEach(step -> {
            int hash = hash(step.chars);
            if (step.operator.equals('='))
                this.boxes.get(hash).addLens(step.chars, step.focalLength);
            else
                this.boxes.get(hash).removeLens(step.chars);
        });
    }

    public int getTotalFocusingPower(){
        return this.boxes.stream()
                .map(Box::getFocusingPower)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private record Step(String chars, Character operator, int focalLength){};
    public static void main(String[] args) throws IOException {
        Library library = new Library();
        String input = Utils.readFile(new File("src/main/java/com/example/aoc/day15/input.txt"));
        System.out.println(library.getHashesSum(input));
        //part 2
        library.parseSteps(input);
        library.placeLenses();
        System.out.println(library.getTotalFocusingPower());
    }
}
