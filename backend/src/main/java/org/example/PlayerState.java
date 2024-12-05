package org.example;
import java.util.*;

public class PlayerState {
    private int id;
    private int shields;
    private List<String> hand;
    private boolean declinedToParticipate;

    public PlayerState(Player player) {
        this.id = player.getId();
        this.shields = player.getSheilds();
        this.hand = new ArrayList<>();
        for (Card card : player.getHand()) {
            hand.add(card.getName());
        }
        this.declinedToParticipate = player.getDeclinedToParticipate();

    }

    public PlayerState() {}

    public int getId() {
        return id;
    }

    public int getShields() {
        return shields;
    }

    public List<String> getHand() {
        return hand;
    }

    public boolean getDeclinedToParticipate(){
        return declinedToParticipate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public void setHand(List<String> hand) {
        this.hand = hand;
    }

    public void setDeclinedToParticipate(boolean declinedToParticipate){
        this.declinedToParticipate = declinedToParticipate;
    }

    

}