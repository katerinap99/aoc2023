package com.example.aoc.day7;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardGame {
    private final Map<String, Integer> handTypeWeights = Map.of("five", 7, "four", 6, "fullhouse", 5,
             "three", 4, "twopair", 3, "onepair", 2, "highcard", 1);

    private final Map<Integer, String> distinctCharsPerHandType = Map.of(5, "highcard", 4, "onepair", 1, "five");
    private final Map<String, Integer> cardsStrength = Map.of("T", 11, "J", 12, "Q", 13, "K", 14, "A", 15);
    private final Map<String, Integer> cardsStrengthWithJoker = Map.of("T", 11, "J", 1, "Q", 13, "K", 14, "A", 15);


    public Hand parseHand(String input){
        return new Hand(input.split("\\s+")[0], Integer.parseInt(input.split("\\s+")[1]));
    }

    public String getAmbiguousCase(int maxFrequency, int distinctChars) {
        if (distinctChars == 3) {
            if(maxFrequency == 3)
                return "three";
            else
                return "twopair";
        }
        if (distinctChars == 2) {
            if(maxFrequency == 4)
                return "four";
            else
                return "fullhouse";
        }
        return "";
    }

    public void setHandWeight(Hand hand, Boolean withJoker){
        String cards = hand.getCards();
        List<Character> chars = Utils.toCharStream(cards).toList();
        int distinctChars = (int) chars.stream().distinct().count();
        int maxFrequency = Utils.getMaxFrequency(chars);
        if (withJoker && !cards.equals("JJJJJ") && cards.contains("J")) {
                distinctChars-=1;
                maxFrequency = Utils.getMaxFrequency(chars, c -> !c.equals('J')) + Utils.getFrequency(chars, 'J');
        }
        String handCase =  distinctCharsPerHandType.getOrDefault(distinctChars, getAmbiguousCase(maxFrequency, distinctChars));
        hand.setWeight(handTypeWeights.getOrDefault(handCase, 1));
    }

    public int getWinnings(List<Hand> hands, boolean withJoker){
        hands.forEach(hand -> setHandWeight(hand, withJoker));
        hands.sort(new Hand.HandComparator(withJoker ? this.cardsStrengthWithJoker : this.cardsStrength));
        return IntStream.range(0, hands.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, hands::get))
                .entrySet()
                .stream()
                .peek((entry) -> entry.getValue().setBid(entry.getValue().getBid() * entry.getKey()))
                .map(entry -> entry.getValue().getBid())
                .reduce(Integer::sum).orElse(0);
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day7/input.txt"));
        CardGame game = new CardGame();
        List<Hand> hands = input.stream().map(game::parseHand).collect(Collectors.toList());
        System.out.println(game.getWinnings(hands, true));
    }
}
