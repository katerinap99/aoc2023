package com.example.aoc.day4;

import com.example.aoc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Map<Integer, Integer> cardCopies = new HashMap<>();
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
        cardCopies.put(card.getId(), 1);
    }
    public void calculateCopies() {
        for(int i = 0; i< cards.size(); i++) {
            int matchingNumbersCount = cards.get(i).getMatchingNumbers().size();
            for (int j = 0; j< cardCopies.get(i+1); j++){
                for(int k = cards.get(i).getId()+1; k <= cards.get(i).getId() + matchingNumbersCount; k++) {
                    cardCopies.put(k, (cardCopies.get(k))+1);
                }
            }
        }
    }

    public int getCopiesSum() {
        calculateCopies();
        return cardCopies.values()
                .stream()
                .reduce(Integer::sum)
                .orElse(cards.size());
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Utils.readFileToList(new File("src/main/java/com/example/aoc/day4/input.txt"));
        Game game = new Game();
        System.out.println(input.stream()
                .map(Card::separateNumbers)
                .map(Card::calculatePoints)
                .reduce(0, Integer::sum));
        input.stream()
                .map(Card::separateNumbers)
                .forEach(game::addCard);
        System.out.println(game.getCopiesSum());

    }
}
