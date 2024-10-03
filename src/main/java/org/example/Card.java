package org.example;

public class Card {
    public enum CardType {
        FOE, WEAPON, QUEST, EVENT
    }

    private final CardType type;
    private final String name;
    private int value;

    //Constructor for cards
    public Card(CardType type, String name, int value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        if(this.value == 0){
            return name;
        }
        return this.name + " : " + this.value;
    }

}
