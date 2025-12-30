package com.example.aoc.day13;

import com.example.aoc.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Notes {
    private String symbols;
    private List<String> symbolsRows;
    private String symmetryOrientation;
    private int reflectionLine;

    public Notes(String symbols) {
        this.symbols = symbols;
        this.symmetryOrientation = "vertical";
        this.symbolsRows = Arrays.stream(symbols.split("\n")).collect(Collectors.toList());
    }

    public List<String> getSymbolsRows() {
        return symbolsRows;
    }

    public String getSymmetryOrientation() {
        return symmetryOrientation;
    }

    public int getReflectionLine() {
        return reflectionLine;
    }

    private boolean matchesForAll(int front, int rear) {
        return this.symbolsRows.stream()
                .allMatch(row -> Utils.toCharStream(row).toList()
                        .get(front).equals(Utils.toCharStream(row).toList().get(rear)));
    }

    public void findReflections() {
        findVerticalReflection();
        if (reflectionLine == 0) {
            rotate();
            symmetryOrientation = "horizontal";
            findVerticalReflection();
        }
    }

    public void findVerticalReflection() {
        List<String> rows = this.symbolsRows;
        List<Character> firstRow = Utils.toCharStream(rows.get(0)).toList();
        int rear = firstRow.size()-1;
        int front = firstRow.size() % 2 != 0? 1 : 0;
        int reflectedChars = 0;
        while(front<rear && front < firstRow.size()-1 && rear >0) {
            if (matchesForAll(front, rear)) {
                reflectedChars++;
                rear--;
            }
            else {
                reflectedChars = 0;
            }
            if ((rear-front) % 2 !=0)
                front+=2;
            else
                front++;
        }

        if (reflectedChars>0) {
            reflectionLine = front;
        }
        else {
            front =0;
            rear = firstRow.size()%2!=0 ? firstRow.size()-2 : firstRow.size()-1;
            while(front<rear && front <= firstRow.size()-1 && rear >= 0){
                if (matchesForAll(front, rear)) {
                    reflectedChars++;
                    front++;
                }
                else {
                    reflectedChars = 0;
                }
                if ((rear-front) % 2 !=0 )
                    rear-=2;
                else
                    rear--;
            }
            if (reflectedChars>0)
                reflectionLine = front;
        }
    }

    private Character getCompliment(Character c){
        return c.equals('#') ? '.' : '#';
    }


    private void rotate() {
        List<List<Character>> symbols = this.symbolsRows.stream()
                .map(s -> Utils.toCharStream(s).collect(Collectors.toList())).toList();
        List<String> newRows = new ArrayList<>();
       for (int j=0; j< symbols.get( 0).size(); j++){
           String column = "";
           for (List<Character> row : symbols) {
               column = column.concat(String.valueOf(row.get(j)));
           }
           newRows.add(column);
       }
       this.symbols = String.join("\n", newRows);
       this.symbolsRows = newRows;
    }

    public void replace() {
        List<List<Character>> symbols =this.symbolsRows.stream()
                .map(s -> Utils.toCharStream(s).collect(Collectors.toList())).toList();
        for (int i=0; i< symbols.size(); i++) {
            for (int j=0; j< symbols.get(i).size(); j++) {
                int currentReflectionLine = reflectionLine;
                String newRow = symbols.get(i).subList(0, j).stream().map(Object::toString).collect(Collectors.joining())
                       + getCompliment(symbols.get(i).get(j)).toString()
                       + symbols.get(i).subList(j+1, symbols.get(i).size()).stream().map(Object::toString).collect(Collectors.joining());
                this.symbolsRows.remove(i);
                this.symbolsRows.add(i, newRow);
                findReflections();
                if (reflectionLine!= currentReflectionLine){
                   break;
               }
            }

        }
    }

}
