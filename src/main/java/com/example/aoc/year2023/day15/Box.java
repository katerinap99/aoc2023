package com.example.aoc.day15;

import java.util.ArrayList;
import java.util.List;

public class Box {
    int label;
    List<Lens> lenses;

    private static class Lens {
        String label;
        int focalLength;

        public Lens(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }
    }

    public Box(int label) {
        this.label = label;
        this.lenses = new ArrayList<>();
    }

    public void removeLens(String label) {
        this.lenses.removeIf(lens -> lens.label.equals(label));
    }

    public void addLens(String label, int focalLength){
        this.lenses.stream()
                .filter(l -> l.label.equals(label))
                .findFirst()
                .ifPresentOrElse(lens -> lens.focalLength = focalLength, () -> this.lenses.add(new Lens(label, focalLength)));
    }

    public int getFocusingPower() {
        int total = 0;
        for (int i = 0; i< lenses.size(); i++){
            total += (label+1) * (i+1) * lenses.get(i).focalLength;
        }
        return total;
    }
}
