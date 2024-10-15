package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;
    private List<Card> discard;


    public Deck() {
        this.deck = new ArrayList<>();
        this.discard = new ArrayList<>();
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public List<Card> getDeckCards(){
        return this.deck;
    }

    public List<Card> getDiscardCards(){
        return this.discard;
    }

    public Card getDeckCard(int i){
        if(i >= 0 && i < this.getDeckSize()){
            return deck.get(i);
        }
        return null;
    }

    public void shuffle() {
        List<Card> originalDeck = new ArrayList<>(deck);
        for(int i=0;i<3;i++){
            Collections.shuffle(deck);
        }
        while (originalDeck.equals(deck)) {
            Collections.shuffle(deck);
        }
    }

    public int getDeckSize() {
        return deck.size();
    }

    public int getDiscardSize() {
        return discard.size();
    }

    public void printDeck() {
        for(int i=0;i<deck.size();i++){
            System.out.println(this.getDeckCard(i).toString());
        }
    }

    public Card dealCard(){
        if(!deck.isEmpty()){
            return deck.removeLast();
        } else {
            deck = new ArrayList<>(discard);
            discard.clear();
            shuffle();
            return deck.removeLast();
        }
    }

    public void discardCard(Card card){
        discard.add(card);
    }
}