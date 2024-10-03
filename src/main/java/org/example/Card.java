package org.example;

public class Card {
    public enum CardType {
        FOE, WEAPON, QUEST, EVENT
    }

    private CardType type;
    private String name;
    private int value;

    public Card(CardType type, String name, int value) {

    }


    public Card(CardType type, String name) {

    }

    public CardType getType() {
        return null;
    }

    public int getValue() {
        return 0;
    }

    public String getName() {
        return null;
    }

}
