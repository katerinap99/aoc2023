package com.example.aoc.day7;

import com.example.aoc.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Hand {
    private final String cards;
    private int bid;
    private int weight;

    public Hand(String cards, int bid) {
        this.cards = cards;
        this.bid = bid;
    }



    public int getWeight() {
        return weight;
    }

    public String getCards() {
        return cards;
    }

    public int getBid() {
        return bid;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public static class HandComparator implements Comparator<Hand> {

        private final Map<String, Integer> cardStrength;

        public HandComparator(Map<String, Integer> cardStrength) {
            this.cardStrength = cardStrength;
        }

        public int compareCards(String card1, String card2){
            return Integer.compare(Optional.ofNullable(cardStrength.get(card1)).orElseGet(() -> Integer.parseInt(card1)),
                    Optional.ofNullable(cardStrength.get(card2)).orElseGet(() -> Integer.parseInt(card2)));
        }

        @Override
        public int compare(Hand hand1, Hand hand2) {
            int weightComparison = Integer.compare(hand1.getWeight(), hand2.getWeight());
            if (weightComparison!=0)
                return weightComparison;
            List<String> hand1Cards = Utils.toCharStream(hand1.getCards()).map(String::valueOf).toList();
            List<String> hand2Cards = Utils.toCharStream(hand2.getCards()).map(String::valueOf).toList();
            for(int i =0; i <hand1Cards.size(); i++) {
                int cardStrengthComparison = compareCards(hand1Cards.get(i), hand2Cards.get(i));
                if (cardStrengthComparison != 0)
                    return cardStrengthComparison;
            }
            return 0;
        }
    }
}
