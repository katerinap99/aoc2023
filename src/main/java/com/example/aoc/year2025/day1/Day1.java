package com.example.aoc.year2025.day1;

import com.example.aoc.Utils;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    private List<Rotation> rotationSteps = new ArrayList<>();

    public record Rotation(
            String direction,
            int offset
    ) {
    }

    public int countDestinationZeros(int currentOffset) {
        int count = 0;
        int index = 0;
        while (index < rotationSteps.size()) {
            Rotation rotation = rotationSteps.get(index);
            if (rotation.direction().equals("L"))
                currentOffset = turnLeft(currentOffset, rotation.offset);
            else
                currentOffset = turnRight(currentOffset, rotation.offset);
            if (currentOffset == 0)
                count++;
            index++;
        }
        return count;
    }

    public int countPassingZeros(int currentOffset) {
        int count = 0;
        int index = 0;
        while (index < rotationSteps.size()) {
            Rotation rotation = rotationSteps.get(index);
            Pair<Integer, Integer> rotationResult;
            if (rotation.direction().equals("L")) {
                rotationResult = turnLeftCountingCircles(currentOffset, rotation.offset);

            } else {
                rotationResult = turnRightCountingCircles(currentOffset, rotation.offset);
            }
            currentOffset = rotationResult.getKey();
            count += rotationResult.getValue();
            if (currentOffset == 0)
                count++;
            index++;
        }
        return count;
    }

    public int turnLeft(int currentOffset, int offset) {
        int actualOffset = offset % 100;
        if (actualOffset <= currentOffset)
            return currentOffset - actualOffset;
        else
            return 100 - (actualOffset - currentOffset);
    }

    public Pair<Integer, Integer> turnLeftCountingCircles(int currentOffset, int offset) {
        int circles = offset / 100;
        int actualOffset = offset % 100;
        if (actualOffset <= currentOffset)
            return new Pair<>(currentOffset - actualOffset, circles);
        else
            return new Pair<>(100 - (actualOffset - currentOffset),
                    countCircles(circles, currentOffset, 100 - (actualOffset - currentOffset)));
    }

    public int turnRight(int currentOffset, int offset) {
        int actualOffset = offset % 100;
        if (actualOffset + currentOffset < 100)
            return actualOffset + currentOffset;
        else
            return Math.abs(100 - (actualOffset + currentOffset));
    }

    public Pair<Integer, Integer> turnRightCountingCircles(int currentOffset, int offset) {
        int circles = offset / 100;
        int actualOffset = offset % 100;
        if (actualOffset + currentOffset < 100)
            return new Pair<>(actualOffset + currentOffset, circles);
        else
            return new Pair<>(Math.abs(100 - (actualOffset + currentOffset)),
                    countCircles(circles, currentOffset, Math.abs(100 - (actualOffset + currentOffset))));
    }

    public int countCircles(int circles, int currentPosition, int nextPosition) {
        return currentPosition == 0 || nextPosition == 0 ? circles : circles + 1;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/year2025/day1/input.txt"));
        Day1 day1 = new Day1();
        for (String step : input) {
            day1.rotationSteps.add(new Rotation(step.substring(0, 1), Integer.parseInt(step.substring(1))));
        }
        System.out.println(day1.countDestinationZeros(50));
        System.out.println(day1.countPassingZeros(50));
    }
}
