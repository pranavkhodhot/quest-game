package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Deck adventureDeck;
    private Deck eventDeck;
    private List<Player> players;
    private Player currPlayer;
    private int counter;
    private List<String> commands;
    private Scanner input;
    private List<String> outputs;
    private Card drawnCard;
    private Quest currQuest;

    public Game() {
        adventureDeck = new Deck();
        eventDeck = new Deck();
        this.players = new ArrayList<>();
        this.currPlayer = null;
        this.currQuest = null;
        this.commands = new ArrayList<>();
        this.input = new Scanner(System.in);
        this.counter = 0;
        this.outputs = new ArrayList<>();
        this.drawnCard = null;
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

    public String getNextCommandOrInput() {
        if (commands != null && !commands.isEmpty()) {
            return commands.remove(0);
        } else {
            return input.nextLine();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCounter() {
        return counter;
    }

    public void setCurrQuest(Quest currQuest) {
        this.currQuest = currQuest;
    }

    public Quest getCurrQuest() {
        return currQuest;
    }

    public List<String> getCommands() {
        return commands;
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
        this.commands = commands;
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

    public Card dealEventCard(){
        return eventDeck.dealCard();
    }

    public void resetPlayerAsked(){
        for(int i=0;i<players.size();i++){
            players.get(i).setDeclinedToParticipate(false);
        }
    }

    public void logAndPrint(String string){
        outputs.add(string);
        System.out.println(string);
    }

    public void clearScreen() {
        for(int i=0;i<50;i++){
            System.out.println();
        }
    }

    public void discardAdventureCard(Card card){
        adventureDeck.discardCard(card);
    }

    public void dealInitialAdventureCards(){
        for (Player player : players) {
            this.dealAdventureCards(12, player.getId());
        }
    }

    public void setDrawnCard(Card drawnCard) {
        this.drawnCard = drawnCard;
    }


    public boolean checkTrim(Player player){
        if(player.getHand().size() > 12){
            int amount = player.getHand().size()-12;
            System.out.println("You must discard " + amount + " cards");
            while(player.getHand().size() > 12){
                player.showHand();
                try {
                    System.out.println("Select a position to discard");
                    int pos = Integer.parseInt(getNextCommandOrInput());
                    Card discardCard = player.getHand().remove(pos-1);
                    discardAdventureCard(discardCard);
                    System.out.println("Adventure Deck Discard: " + adventureDeck.getDiscardSize());
                } catch (NumberFormatException err) {
                    System.out.println("Not a valid integer. Please try again");
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("Not a valid position. Please try again");
                }
            }
            return true;
        }
        return false;
    }

    public String diccardCardFromPlayer(Player player, String position){
        try {
            int pos = Integer.parseInt(position);
            Card discardCard = player.getHand().remove(pos-1);
            discardAdventureCard(discardCard);
            return "Succesfully discarded";
        } catch (NumberFormatException err) {
            return "Not a valid integer. Please try again";
        } catch (IndexOutOfBoundsException err) {
            return "Not a valid position. Please try again";
        }
    }

    public void discardEventCard(Card card){
        eventDeck.discardCard(card);
    }

    public void processPlayerTurn(Player player) {
        System.out.println("P" + player.getId() + "'s Turn");
        checkTrim(player);
        player.showHand();
        player.showSheilds();


        System.out.println("Press [ENTER] to draw");
        String ignore = getNextCommandOrInput();

        drawnCard = dealEventCard();
        logAndPrint("P" + player.getId() + " Drew " + drawnCard);

        if (drawnCard.getCardType() == Card.CardType.EVENT) {
            handleEventCard();
        } else {
            handleQuestCard();
        }

        for (int i=0;i<players.size();i++) {
            if(checkTrim(players.get(i))){
                clearScreen();
            }
        }

        discardEventCard(drawnCard);
        System.out.println("Press [ENTER] to end your turn");
        ignore = getNextCommandOrInput();
        counter++;
    }

    public String handleEventCard() {
        switch (drawnCard.getName()) {
            case "Plague":
                currPlayer.setSheilds(currPlayer.getSheilds() - 2);
                return "P" + currPlayer.getId() + " has lost 2 shield and now has " + currPlayer.getSheilds() + " shields";
            case "Queens Favor":
                dealAdventureCards(2, currPlayer.getId());
                return "P" + currPlayer.getId() + " has drawn 2 cards";
            case "Prosperity":
                for (Player player : players) {
                    dealAdventureCards(2, player.getId());
                }
                return "All players have drawn 2 cards";
        }
        return "";
    }

    private void handleQuestCard() {
        int questStages = Integer.parseInt(drawnCard.getName().substring(1));
        Player sponsor = findQuestSponsor(questStages, currPlayer);
        if (sponsor == null) {
            System.out.println("No sponsors were found, the quest will be discarded");
        } else {
            currQuest = new Quest(questStages, sponsor, this);
        }
    }

    public String createQuest(int sponsorId, int questStages) {
        if (this.getPlayer(sponsorId) == null) {
            return "No sponsors were found, the quest will be discarded";
        } else {
            currQuest = new Quest(questStages, this.getPlayer(sponsorId), this);
            return "A sponsor has been found!";
        }
    }

    public Player findQuestSponsor(int questStages, Player currentPlayer) {
        for (int i = 0; i < players.size(); i++) {
            int askedPlayerId = ((currentPlayer.getId() - 1 + i) % players.size()) + 1;
            Player askedPlayer = this.getPlayer(askedPlayerId);
            if (askedPlayer.ableToSponsor(questStages)) {
                logAndPrint("Would P" + askedPlayer.getId() + " like to sponsor the quest Q" + questStages + "? Yes/No");
                String response = getNextCommandOrInput();
                if (response.equalsIgnoreCase("yes")) {
                    System.out.println("P" + askedPlayer.getId() + " is sponsoring the quest!");
                    return askedPlayer;
                } else {
                    System.out.println("P" + askedPlayer.getId() + " declined to sponsor the quest.");
                }
            } else {
                System.out.println("P" + askedPlayer.getId() + " is not eligible to sponsor");
            }
        }
        return null;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public Card getDrawnCard() {
        return drawnCard;
    }

    private void announceWinners(List<Player> winners) {
        for (Player winner : winners) {
            logAndPrint("Congratulations to P" + winner.getId() + " for being Knighted!");
        }
    }

    public List<Player> checkWinner(){
        List<Player> winners = new ArrayList<>();
        for(int i=0;i<players.size();i++){
            if(players.get(i).isWinner()){
                winners.add(players.get(i));
            }
        }
        return winners;
    }

    public void startGame(Deck mockEventDeck, Deck mockAdventureDeck){
        this.shuffleAdventureDeck();
        this.shuffleEventDeck();
        dealInitialAdventureCards();
        if(mockEventDeck != null){
            eventDeck = mockEventDeck;
        }
        if(mockAdventureDeck != null){
            adventureDeck = mockAdventureDeck;
        }
        while (true) {
            currPlayer = getNextPlayer(counter);
            processPlayerTurn(currPlayer);
            List<Player> winners = checkWinner();
            if (!winners.isEmpty()) {
                announceWinners(winners);
                break;
            }
            clearScreen();
        }
        System.out.println("The game has reached its conclusion");
    }


}
