package org.example;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int stages;
    private int currentStage;
    private int currStageValue;
    private int currAttackValue;
    private Player sponsor;
    private List<Integer> stageValues;
    private List<Card> stageCards;
    private List<Card> currStageCards;
    private List<Player> participants;
    private List<Player> successfulParticipants;
    private List<Card> attackCards;

    private Game game;

    public Quest(int stages, Player sponsor, Game game) {
        this.stages = stages;
        this.sponsor = sponsor;
        this.game = game;
        this.participants = new ArrayList<>(game.getPlayers());
        this.participants.remove(participants.indexOf(sponsor));
        this.stageValues = new ArrayList<>();
        this.stageCards = new ArrayList<>();
        this.successfulParticipants = new ArrayList<>();
        this.currentStage = 1;
        this.currStageCards = new ArrayList<>();
        this.currStageValue = 0;
        this.stageValues.add(0);
        this.attackCards = new ArrayList<>();
        this.currAttackValue = calculateAttackValue(attackCards);
    }


    public List<Card> getCurrStageCards() {
        return currStageCards;
    }

    public int getCurrStageValue() {
        return currStageValue;
    }

    public List<Player> getSuccessfulParticipants() {
        return successfulParticipants;
    }

    public List<Card> getStageCards() {
        return stageCards;
    }

    public List<Player> getSuccessful() {
        return successfulParticipants;
    }

    public void setParticipants(List<Player> participants) {
        this.participants = participants;
    }

    public Player getSponsor() {
        return sponsor;
    }

    public int getStages() {
        return stages;
    }

    public List<Player> getParticipants() {
        return participants;
    }

    public void moveNextStage(){
        currentStage += 1;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public List<Card> getAttackCards() {
        return attackCards;
    }
    public void setAttackCards(List<Card> attackCards) {
        this.attackCards = attackCards;
    }

    public int getCurrAttackValue() {
        return currAttackValue;
    }

    public void setCurrAttackValue(int currAttackValue) {
        this.currAttackValue = currAttackValue;
    }

    public boolean validStage(List<Card> stage){
        for(int i=0;i< stage.size();i++){
            if(stage.get(i).getName().startsWith("F")){
                return true;
            }
        }
        return false;
    }

    public boolean hasRepeatedCard(List<Card> build, Card card){
        for(int i=0;i< build.size();i++){
            if(build.get(i).getName().equals(card.getName())){
                return true;
            }
        }
        return false;
    }


    public String getPossibleParticipants(int playerId, String answer) {
        Player askedPlayer = game.getPlayer(playerId);
        System.out.println(playerId);
        if(answer.equalsIgnoreCase("yes")){
            return "P" + askedPlayer.getId() + " is participating in the quest!";
        } else{
             askedPlayer.setDeclinedToParticipate(true);
             if(participants.contains(askedPlayer)){
                 participants.remove(participants.indexOf(askedPlayer));
             }
            return "P" + askedPlayer.getId() + " declined to participate.";
        }
    }


    public String addCardToQuestStage(String answer){
        try{
            currStageValue = calculateAttackValue(currStageCards);
            if(answer.equalsIgnoreCase("Quit")){
                if(currStageCards.isEmpty()){
                    return "Stage cannot be Empty" ;
                } else if (!validStage(currStageCards)) {
                    return "Stage cannot have no foe";
                } else if(currStageValue <= stageValues.get(stageValues.size()-1) ){
                    return "Insufficient value for this stage";
                }
                else {
                    stageValues.add(currStageValue);
                    currStageCards.clear();
                    currStageValue = 0;
                    return "Stage has been built";
                }
            }
            int pos = Integer.parseInt(answer);
            Card stageCard = sponsor.getHand().get(pos-1);
            if(stageCard.getCardType()== Card.CardType.FOE){
                if(validStage(currStageCards)){
                    return "Cannot have more than 1 Foe Card in a stage";
                } else {
                    stageCards.add(stageCard);
                    currStageCards.add(stageCard);
                    currStageValue += stageCard.getValue();
                    sponsor.getHand().remove(pos-1);
                    return "Card has been added";
                }
            } else {
                if(hasRepeatedCard(currStageCards,stageCard)){
                    return "Cannot have repeated weapon card in Stage";
                } else {
                    stageCards.add(stageCard);
                    currStageCards.add(stageCard);
                    currStageValue += stageCard.getValue();
                    sponsor.getHand().remove(pos-1);
                    return "Card has been added";
                }
            }
        }
        catch (NumberFormatException err) {
            return "Not a valid integer. Please try again";
        }
        catch (IndexOutOfBoundsException err) {
            return "Not a valid position. Please try again";
        }
    }

    public void handleBeginningStage(Player participant){
        game.dealAdventureCards(1, participant.getId());
        game.checkTrim(participant);
    }

    public String addCardToAttack(Player participant, String input) {
        if (input.equalsIgnoreCase("Quit")) {
            return "Attack has been built";
        }
        try {
            int cardPos = Integer.parseInt(input);
            Card chosenCard = participant.getHand().get(cardPos - 1);
            if (hasRepeatedCard(attackCards, chosenCard)) {
                return "You cannot use the same card twice";
            } else if (chosenCard.getCardType() == Card.CardType.FOE) {
                return "Cannot use FOE card in attack";
            } else {
                attackCards.add(chosenCard);
                participant.getHand().remove(cardPos - 1);
                currAttackValue = calculateAttackValue(attackCards);
                return "Card successfully added";
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return "Invalid input. Please try again.";
        }    
    }

    public List<Integer> getStageValues() {
        return stageValues;
    }

    public String handleAttackOutcome(Player participant) {
        String ret = "P" + participant.getId() + " succeeded in Stage " + currentStage + "!";
        if (!resolveAttack()) {
            participant.setDeclinedToParticipate(true);
            participants.remove(participant);
            ret = "P" + participant.getId() + " failed in Stage " + currentStage + "!";
        } else if (currentStage == stages) {
            successfulParticipants.add(participant);
        } 
        return ret;
    }

    public boolean resolveAttack() {
        int attackValue = calculateAttackValue(attackCards);
        int stageDifficulty = stageValues.get(currentStage);
        for(Card card : attackCards){
            game.discardAdventureCard(card);
        }
        attackCards.clear();
        if (attackValue >= stageDifficulty) {
            return true;
        } else {
            return false;
        }
    }

    public int calculateAttackValue(List<Card> attackCards) {
        int val = 0;
        for(int i=0;i<attackCards.size();i++){
            val += attackCards.get(i).getValue();
        }
        return val;
    }

    public String cardBuildString(List<Card> build) {
        String ret = "";
        for(int i=0;i<build.size();i++){
            ret += build.get(i).getName() + " ";
        }
        return ret;
    }

    public String playersString(List<Player> players) {
        String ret = "";
        for(int i=0;i<players.size();i++){
            ret += "P"+players.get(i).getId() + " ";
        }
        return ret;
    }

    public void finishQuest(List<Player> successfulPlayers) {
        if (successfulPlayers.isEmpty()) {
            game.logAndPrint("The quest has failed as all participants have been eliminated.");
        } else {
            game.logAndPrint("The quest has been completed successfully by " + playersString(successfulPlayers));
            for(int i=0;i<successfulPlayers.size();i++){
                System.out.println("Player " + successfulPlayers.get(i).getId());
                successfulPlayers.get(i).setSheilds(successfulPlayers.get(i).getSheilds()+stages);
            }
        }
        int numStageCards = stageCards.size();
        for(int i=0;i<stageCards.size();i++){
            game.discardAdventureCard(stageCards.remove(0));
            i--;
        }

        game.logAndPrint("Sponsor will now regain Cards, Press [ENTER] to begin card redraw");
        String out = game.getNextCommandOrInput();
        game.dealAdventureCards(numStageCards+stages, sponsor.getId());
        game.checkTrim(sponsor);
    }

    public void finishQuest() {
        for(int i=0;i<successfulParticipants.size();i++){
            successfulParticipants.get(i).setSheilds(successfulParticipants.get(i).getSheilds()+stages);        
        }
        int numStageCards = stageCards.size();
        for(int i=0;i<numStageCards;i++){
            game.discardAdventureCard(stageCards.get(i));
        }
        stageCards.clear();
        game.dealAdventureCards(numStageCards+stages, sponsor.getId());
        game.resetPlayerAsked();
    }
}