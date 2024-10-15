package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Deck adventureDeck;
    private Deck eventDeck;
    private List<Player> players;
    private Player currPlayer;
    private int counter;
    private List<String> commands;

    public Game() {
        adventureDeck = new Deck();
        eventDeck = new Deck();
        this.players = new ArrayList<>();
        this.currPlayer = null;
        this.counter = 0;
        for(int i=0;i<4;i++){
            this.players.add(new Player(i+1));
        }
        setupAdventureDeck();
        setupEventDeck();
    }

    public void setupAdventureDeck() {
        addCardsToDeck(adventureDeck, "F5", Card.CardType.FOE, 5, 8);  // 8 Foe 5 cards
        addCardsToDeck(adventureDeck, "F10", Card.CardType.FOE, 10, 7);  // 7 Foe 10 cards
        addCardsToDeck(adventureDeck, "F15", Card.CardType.FOE, 15, 8);  // 8 Foe 15 cards
        addCardsToDeck(adventureDeck, "F20", Card.CardType.FOE, 20, 7);  // 7 Foe 20 cards
        addCardsToDeck(adventureDeck, "F25", Card.CardType.FOE, 25, 7);  // 7 Foe 25 cards
        addCardsToDeck(adventureDeck, "F30", Card.CardType.FOE, 30, 4);  // 4 Foe 30 cards
        addCardsToDeck(adventureDeck, "F35", Card.CardType.FOE, 35, 4);  // 4 Foe 35 cards
        addCardsToDeck(adventureDeck, "F40", Card.CardType.FOE, 40, 2);  // 2 Foe 40 cards
        addCardsToDeck(adventureDeck, "F50", Card.CardType.FOE, 50, 2);  // 2 Foe 50 cards
        addCardsToDeck(adventureDeck, "F70", Card.CardType.FOE, 70, 1);  // 1 Foe 70 card

        addCardsToDeck(adventureDeck, "D", Card.CardType.WEAPON, 5, 6);  // 6 Dagger cards
        addCardsToDeck(adventureDeck, "H", Card.CardType.WEAPON, 10, 12);  // 12 Horse cards
        addCardsToDeck(adventureDeck, "S", Card.CardType.WEAPON, 10, 16);  // 16 Sword cards
        addCardsToDeck(adventureDeck, "B", Card.CardType.WEAPON, 15, 8);  // 8 Battle-Axe cards
        addCardsToDeck(adventureDeck, "L", Card.CardType.WEAPON, 20, 6);  // 6 Lance cards
        addCardsToDeck(adventureDeck, "E", Card.CardType.WEAPON, 30, 2);  // 2 Excalibur cards
    }

    public void setupEventDeck() {
        addCardsToDeck(eventDeck, "Q2", Card.CardType.QUEST, 2, 3);  // 8 Foe 5 cards
        addCardsToDeck(eventDeck, "Q3", Card.CardType.QUEST, 3, 4);  // 8 Foe 5 cards
        addCardsToDeck(eventDeck, "Q4", Card.CardType.QUEST, 4, 3);  // 8 Foe 5 cards
        addCardsToDeck(eventDeck, "Q5", Card.CardType.QUEST, 5, 2);  // 8 Foe 5 cards

        addCardsToDeck(eventDeck, "Plague", Card.CardType.EVENT, 0, 1);
        addCardsToDeck(eventDeck, "Queens Favor", Card.CardType.EVENT, 0, 2);  // 12 Horse cards
        addCardsToDeck(eventDeck, "Prosperity", Card.CardType.EVENT, 0, 2);  // 16 Sword cards
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public Player getNextPlayer(int turnCounter) {
        int turn = (turnCounter % players.size()) + 1;
        return this.getPlayer(turn);
    }

    public void setCommands(List<String> commands) {

    }

    public void shuffleAdventureDeck() {
        adventureDeck.shuffle();
    }

    public void shuffleEventDeck() {
        eventDeck.shuffle();
    }

    public Deck getAdventureDeck() {
        return adventureDeck;
    }

    public Deck getEventDeck() {
        return eventDeck;
    }

    private void addCardsToDeck(Deck deck, String cardName, Card.CardType type, int value, int count) {
        for (int i = 0; i < count; i++) {
            deck.addCard(new Card(type, cardName, value));
        }
    }

    public Player getPlayer(int id){
        if(id >= 0 && id<=players.size()){
            return this.players.get(id-1);
        }
        return null;
    }

    public void dealAdventureCards(int amount, int id){
        for(int i=0;i<amount;i++){
            this.getPlayer(id).addCard(adventureDeck.dealCard());
        }
    }

    public void discardAdventureCard(Card card){
        adventureDeck.discardCard(card);
    }

    public void dealInitialAdventureCards(){

    }

    public boolean checkTrim(Player player){
        return false;
    }
}
