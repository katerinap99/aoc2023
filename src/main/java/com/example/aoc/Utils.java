package com.example.aoc;

import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Utils {
    public static <T>  int getMaxFrequency(List<T> list, Predicate<T> predicate){
        return list.stream().filter(predicate).map(c -> Collections.frequency(list, c)).max(Integer::compareTo).orElse(0);
    }

    public static <T>  int getMaxFrequency(List<T> list){
        return list.stream().map(c -> Collections.frequency(list, c)).max(Integer::compareTo).orElse(0);
    }

    public static <T> int getFrequency(List<T> list, T element){
        return Collections.frequency(list, element);
    }

    public static List<String> readFileToList(File file) throws IOException {
        return Files.readAllLines(file.toPath(), Charset.defaultCharset());
    }

    public static String readFile(File file) throws IOException {
        return Files.readString(file.toPath(), Charset.defaultCharset());
    }

    public static Stream<Character> toCharStream(String s){
        return s.chars().mapToObj(c -> (char) c);
    }


}
