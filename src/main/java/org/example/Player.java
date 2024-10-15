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
        return hand;
    }

    public int getId() {
        return -1;
    }

    public void addCard(Card card) {
        if (card.getCardType() == Card.CardType.FOE) {
            hand.add(0, card);
            sortFoeCards();
        }
        else if (card.getCardType() == Card.CardType.WEAPON) {
            addAdventureCardInOrder(card);
        }
    }

    private void sortFoeCards() {
        hand.sort((card1, card2) -> {
            if (card1.getCardType() == Card.CardType.FOE && card2.getCardType() == Card.CardType.FOE) {
                return Integer.compare(card1.getValue(), card2.getValue());
            }
            return 0;
        });
    }

    private void addAdventureCardInOrder(Card card) {
        int startIndex = 0;
        while (startIndex < hand.size() && hand.get(startIndex).getCardType() == Card.CardType.FOE) {
            startIndex++;
        }

        for (int i = startIndex; i < hand.size(); i++) {
            Card currCard = hand.get(i);
            if (currCard.getValue() == card.getValue() && currCard.getName().equals("H") && card.getName().equals("S")) {
                hand.add(i, card);  // Insert Sword before Horse
                return;
            }

            if (currCard.getValue() > card.getValue()) {
                hand.add(i, card);  // Insert the card here
                return;
            }
        }

        hand.add(card);
    }
}
