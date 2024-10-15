package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private List<Card> hand;
    private int sheilds;

    public Player(int id){
        this.id = id;
        this.hand = new ArrayList<Card>();
        this.sheilds = 0;
    }

    public List<Card> getHand() {
        return null;
    }

    public void addCard(Card card) {

    }
}
