package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*")
public class GameController {

    private Game game;

    public GameController() {
        resetGame();
    }

    @PostMapping(value = "/start", produces = "application/json", consumes = "application/json")
    public String startGame(@RequestBody Map<String, Object> requestBody) {
        String scenerio = (String) requestBody.get("scenerio");
        resetGame();
        System.out.println(scenerio);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        setUpScenerios(scenerio);
        game.dealInitialAdventureCards();
        return "Game started. Adventure cards dealt to players.";
    }

    @GetMapping(value = "/state")
    public GameState getGameState() {
        if (game == null) {
            throw new IllegalStateException("Game has not been initialized.");
        }
        return new GameState(game);
    }

    @GetMapping("/draw-event-card")
    public String drawEventCard() {
        game.setDrawnCard(game.dealEventCard());
        return game.getDrawnCard().getName();
    }

    @GetMapping("/handle-event-card")
    public String handleEventCard() {
        return game.handleEventCard();
    }

    @GetMapping("/next-player")
    public String nextTurn() {
        game.discardEventCard(game.getDrawnCard());
        game.setCounter(game.getCounter()+1);
        game.setCurrPlayer(game.getNextPlayer(game.getCounter()));
        return game.getDrawnCard().getName();
    }

    @PostMapping(value = "/discard-card", produces = "application/json", consumes = "application/json")
    public String discardCard(@RequestBody Map<String, Object> requestBody) {
        int playerId = (int) requestBody.get("playerId");
        String position = (String) requestBody.get("position");
        Player player = game.getPlayer(playerId);
        return game.diccardCardFromPlayer(player, position);
    }

    @PostMapping(value = "/able-to-sponsor", produces = "application/json", consumes = "application/json")
    public boolean checkIfPlayerCanSponsor(@RequestBody Map<String, Object> requestBody) {
        int playerId = (int) requestBody.get("playerId");
        int numStages = (int) requestBody.get("stages");
        Player player = game.getPlayer(playerId);
        return player.ableToSponsor(numStages);
    }

    @PostMapping(value = "/create-quest", produces = "application/json", consumes = "application/json")
    public String createQuest(@RequestBody Map<String, Object> requestBody) {
        int playerId = (int) requestBody.get("sponsorId");
        int numStages = (int) requestBody.get("stages");
        return game.createQuest(playerId, numStages);
    }

    @PostMapping(value = "/add-card-to-stage", produces = "application/json", consumes = "application/json")
    public String handleAddCardToStage(@RequestBody Map<String, Object> requestBody) {
        String answer = (String) requestBody.get("answer");
        return game.getCurrQuest().addCardToQuestStage(answer);
    }

    @PostMapping(value = "/ask-participant", produces = "application/json", consumes = "application/json")
    public String addOrRemoveParticipant(@RequestBody Map<String, Object> requestBody) {
        String answer = (String) requestBody.get("answer");
        int playerId = (int) requestBody.get("playerId");
        return game.getCurrQuest().getPossibleParticipants(playerId, answer);
    }
    

    @PostMapping(value = "/draw-one", produces = "application/json", consumes = "application/json")
    public String handleBeginningStage(@RequestBody Map<String, Object> requestBody) {
        int playerId = (int) requestBody.get("playerId");
        String cardName = game.getAdventureDeck().getTopCardOfDeck().getName();
        game.dealAdventureCards(1,playerId);
        return cardName;
    }

    @PostMapping(value = "/add-card-to-attack", produces = "application/json", consumes = "application/json")
    public String handleAddCardToAttack(@RequestBody Map<String, Object> requestBody) {
        String answer = (String) requestBody.get("answer");
        int participantId = (int) requestBody.get("participantId");
        Player participant = game.getPlayer(participantId);
        return game.getCurrQuest().addCardToAttack(participant,answer);
    }

    @PostMapping(value = "/resolve-attack", produces = "application/json", consumes = "application/json")
    public String resolveAttack(@RequestBody Map<String, Object> requestBody) {
        int participantId = (int) requestBody.get("participantId");
        Player participant = game.getPlayer(participantId);
        return game.getCurrQuest().handleAttackOutcome(participant);
    }

    @GetMapping("/move-to-next-stage")
    public void nextStage() {
        game.getCurrQuest().setCurrentStage(game.getCurrQuest().getCurrentStage()+1);
    }

    @GetMapping(value = "/finish-quest")
    public void resolveQuest() {
        game.getCurrQuest().finishQuest();
    }

    @PostMapping(value = "/check-winners", produces = "application/json")
    public List<PlayerState> checkWinners() {
        List<Player> winners = game.checkWinner();
        List<PlayerState> winnerStates = new ArrayList<>();

        for (Player winner : winners) {
            winnerStates.add(new PlayerState(winner));
        }
        return winnerStates;
    }

    private void resetGame() {
        game = new Game();
    }

    private void setUpScenerios(String scenerio){
        ArrayList<String> eventCards = new ArrayList<>();
        ArrayList<String> adventureCards = new ArrayList<>();
        game.shuffleAdventureDeck();
        game.shuffleEventDeck();
        switch (scenerio){
            case "A1_scenario":
                Collections.addAll(eventCards,"Q4");    //Rigging event deck cards
                Collections.addAll(adventureCards,"L","F30","S","B","L","L","F10","B","S","F30");           //Rigging adventure deck cards (Quest 1 Drawn Cards)
                Collections.addAll(adventureCards,"E","L","B","H","H","S","D","D","F40","F15","F15","F5");  //Rigging adventure deck cards (Player 4 Hand)
                Collections.addAll(adventureCards,"L","B","H","H","S","S","S","D","F15","F5","F5","F5");    //Rigging adventure deck cards (Player 3 Hand)
                Collections.addAll(adventureCards,"E","B","B","H","H","S","D","F40","F15","F15","F5","F5"); //Rigging adventure deck cards (Player 2 Hand)
                Collections.addAll(adventureCards,"L","B","B","H","H","S","S","D","F15","F15","F5","F5");   //Rigging adventure deck cards (Player 1 Hand)
                for(String card : eventCards){
                    game.getEventDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                for(String card : adventureCards){
                    game.getAdventureDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                break;

            case "2_winner":
                Collections.addAll(eventCards,"Q3","Q4");   //Rigging event deck cards
                Collections.addAll(adventureCards,"L","B","B","S","F30","F25","F20","F20");                             //Rigging adventure deck cards (Sponsor redraw cards)
                Collections.addAll(adventureCards,"F25","F25","F15","F15","D","D");                                     //Rigging adventure deck cards (Quest 2 Drawn Card)
                Collections.addAll(adventureCards,"F30","F25","F25","F20","F20","F20","F20","F15","F15","F10","F5");    //Rigging adventure deck cards (Sponsor redraw cards)
                Collections.addAll(adventureCards,"F20","F15","F15","F30","F30","F10","F10","F40","F5");                //Rigging adventure deck cards (Quest 1 Drawn Cards)
                Collections.addAll(adventureCards,"F50","F70","H","H","S","S","S","B","B","L","L","E");                 //Rigging adventure deck cards (Player 4 Hand)
                Collections.addAll(adventureCards,"F5","F5","F5","F5","D","D","D","H","H","H","H","H");                 //Rigging adventure deck cards (Player 3 Hand)
                Collections.addAll(adventureCards,"F40","F50","H","H","S","S","S","B","B","L","L","E");                 //Rigging adventure deck cards (Player 2 Hand)
                Collections.addAll(adventureCards,"F5","F5","F10","F10","F15","F15","D","H","H","B","B","L");           //Rigging adventure deck cards (Player 1 Hand)

                for(String card : eventCards){
                    game.getEventDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                for(String card : adventureCards){
                    game.getAdventureDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                break;

            case "1_winner":
                Collections.addAll(eventCards,"Q3","Queens Favor","Prosperity","Plague","Q4");                          //Rigging event deck cards
                Collections.addAll(adventureCards,"F35","S","S","S","S","H","H","H");                             //Rigging adventure deck cards (Sponsor redraw cards)
                Collections.addAll(adventureCards,"F50","F40","S","S","F50","H","B");                                   //Rigging adventure deck cards (Quest 2 Drawn Cards)
                Collections.addAll(adventureCards,"F25","F30");                                                         //Player drawn cards for Queens Favor
                Collections.addAll(adventureCards,"D","D","F40","B","S","H","F25","F25");                               //Players drawn cards for Prosperity
                Collections.addAll(adventureCards,"F15","F15","F15","F15","F10","F10","F5","F5");                       //Rigging adventure deck cards (Sponsor redraw cards)
                Collections.addAll(adventureCards,"F20","F10","F5","F20","F10","F5","F25","F5","F15","F20","F10","F5"); //Rigging adventure deck cards (Quest 1 Drawn Cards)
                Collections.addAll(adventureCards,"F25","F30","F70","H","H","S","S","S","B","B","L","L");               //Rigging adventure deck cards (Player 4 Hand)
                Collections.addAll(adventureCards,"F25","F30","H","H","S","S","S","B","B","L","L","E");                 //Rigging adventure deck cards (Player 3 Hand)
                Collections.addAll(adventureCards,"F25","F30","H","H","S","S","S","B","B","L","L","E");                 //Rigging adventure deck cards (Player 2 Hand)
                Collections.addAll(adventureCards,"F5","F5","F10","F10","F15","F15","F20","F20","D","D","D","D");       //Rigging adventure deck cards (Player 1 Hand)

                for(String card : eventCards){
                    game.getEventDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                for(String card : adventureCards){
                    game.getAdventureDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                break;

            case "0_winner":
                Collections.addAll(eventCards,"Q2");
                Collections.addAll(adventureCards,"S","S","S","H","H","H","H","D","D","D","D","F15","F10","F5");                                                     //Rigging adventure deck cards (Sponsor redraw cards)
                Collections.addAll(adventureCards,"F10","F15","F5");                                                    //Rigging adventure deck cards (Quest 1 Drawn Cards)
                Collections.addAll(adventureCards,"F5","F5","F10","F15","F15","F20","F20","F25","F25","F30","F50","E"); //Rigging adventure deck cards (Player 4 Hand)
                Collections.addAll(adventureCards,"F5","F5","F10","F15","F15","F20","F20","F25","F25","F30","F40","L"); //Rigging adventure deck cards (Player 3 Hand)
                Collections.addAll(adventureCards,"F5","F5","F10","F15","F15","F20","F20","F25","F30","F30","F40","E"); //Rigging adventure deck cards (Player 2 Hand)
                Collections.addAll(adventureCards,"F50","F70","D","D","S","S","H","H","B","B","L","L");                 //Rigging adventure deck cards (Player 1 Hand)

                for(String card : eventCards){
                    game.getEventDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                for(String card : adventureCards){
                    game.getAdventureDeck().findAndPlaceCardOnTopOfDeck(card);
                }
                break;

            default:
                System.out.println("Scenerio does not exist");
        }
    }

    
}