package org.example;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<PlayerState> players;
    private List<PlayerState> winners;
    private QuestState currentQuest;
    private PlayerState currentPlayer;
    private int adventureDeckSize;
    private int adventureDiscardPileSize;
    private int eventDeckSize;
    private int eventDiscardPileSize;
    private String drawnCard;

    public GameState(Game game) {
        // Populate player states
        this.players = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            players.add(new PlayerState(player));
        }

        this.currentQuest = game.getCurrQuest() != null ? new QuestState(game.getCurrQuest()) : null;
        this.currentPlayer = game.getCurrPlayer() != null ? new PlayerState(game.getCurrPlayer()) : null; 

        this.adventureDeckSize = game.getAdventureDeck().getDeckSize();
        this.adventureDiscardPileSize = game.getAdventureDeck().getDiscardSize();
        this.eventDeckSize = game.getEventDeck().getDeckSize();
        this.eventDiscardPileSize = game.getEventDeck().getDiscardSize();
        this.drawnCard = game.getDrawnCard() != null ? game.getDrawnCard().getName() : null;
        this.winners = new ArrayList<>();
        for (Player player : game.checkWinner()) {
            winners.add(new PlayerState(player));
        }
    }

    public GameState() {} // No-argument constructor for Jackson

    // Getters and setters for all fields
    public List<PlayerState> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerState> players) {
        this.players = players;
    }

    public QuestState getCurrentQuest() {
        return currentQuest;
    }

    public void setCurrentQuest(QuestState currentQuest) {
        this.currentQuest = currentQuest;
    }

    public PlayerState getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerState currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getAdventureDeckSize() {
        return adventureDeckSize;
    }

    public void setAdventureDeckSize(int adventureDeckSize) {
        this.adventureDeckSize = adventureDeckSize;
    }

    public int getAdventureDiscardPileSize() {
        return adventureDiscardPileSize;
    }

    public void setAdventureDiscardPileSize(int adventureDiscardPileSize) {
        this.adventureDiscardPileSize = adventureDiscardPileSize;
    }

    public int getEventDeckSize() {
        return eventDeckSize;
    }

    public void setEventDeckSize(int eventDeckSize) {
        this.eventDeckSize = eventDeckSize;
    }

    public int getEventDiscardPileSize() {
        return eventDiscardPileSize;
    }

    public void setEventDiscardPileSize(int eventDiscardPileSize) {
        this.eventDiscardPileSize = eventDiscardPileSize;
    }

    public List<PlayerState> getWinners() {
        return winners;
    }

    public void setWinners(List<PlayerState> winners) {
        this.winners = winners;
    }

    public String getDrawnCard(){
        return drawnCard;
    }

    public void setDrawnCard(String drawnCard){
        this.drawnCard = drawnCard;
    }
}

