package org.example;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int stages;
    private Player sponsor;
    private List<Integer> stageValues;
    private List<Card> stageCards;
    private List<Player> participants;
    private Game game;

    public Quest(int stages, Player sponsor, Game game) {
        this.stages = stages;
        this.sponsor = sponsor;
        this.game = game;
        this.participants = new ArrayList<>();
        this.stageValues = new ArrayList<>();
        this.stageCards = new ArrayList<>();
    }
    public List<Card> getStageCards() {
        return stageCards;
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

    public List<Player> getParticipantsForQuest(Player sponsor) {
        List<Player> participants = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            if (player.getId() != sponsor.getId() && !player.getDeclinedToParticipate()) {
                game.logAndPrint("Would P" + player.getId() + " like to participate? Yes/No");
                String response = game.getNextCommandOrInput();
                if (response.equalsIgnoreCase("yes")) {
                    participants.add(player);
                    game.logAndPrint("P" + player.getId() + " is participating in the quest!");
                } else {
                    game.logAndPrint("P" + player.getId() + " declined to participate.");
                    player.setDeclinedToParticipate(true);
                }
                game.clearScreen();
            }
        }
        return participants;
    }

    public void buildQuest() {
        int count = 0;
        game.logAndPrint("P" + sponsor.getId() + " Will Begin the Stage Building Process");
        stageValues.add(0);
        List<Card> currStageCards = new ArrayList<>();
        int currStageValue = 0;
        while(count < stages){
            try{
                sponsor.showHand();
                game.logAndPrint("Previous Stage Value: " + stageValues.getLast());
                game.logAndPrint("Current Stage Value: " + currStageValue);
                game.logAndPrint("Current Cards: " + cardBuildString(currStageCards));
                game.logAndPrint("Select the position of the card to include in Stage #" + (count+1) + " or enter Quit to stop building stage");
                String answer = game.getNextCommandOrInput();
                if(answer.equalsIgnoreCase("Quit")){
                    if(currStageCards.isEmpty()){
                        game.logAndPrint("Stage cannot be Empty" );
                    } else if (!validStage(currStageCards)) {
                        game.logAndPrint("Stage cannot have no foe");
                    } else if(currStageValue <= stageValues.getLast() ){
                        game.logAndPrint("Insufficient value for this stage");
                    }
                    else {
                        stageValues.add(currStageValue);
                        currStageCards.clear();
                        currStageValue = 0;
                        count++;
                        game.clearScreen();
                    }
                    continue;
                }
                int pos = Integer.parseInt(answer);
                Card stageCard = sponsor.getHand().get(pos-1);
                if(stageCard.getCardType()== Card.CardType.FOE){
                    if(validStage(currStageCards)){
                        game.logAndPrint("Cannot have more than 1 Foe Card in a stage");
                    } else {
                        stageCards.add(stageCard);
                        currStageCards.add(stageCard);
                        currStageValue += stageCard.getValue();
                        sponsor.getHand().remove(pos-1);

                    }
                } else {
                    if(hasRepeatedCard(currStageCards,stageCard)){
                        game.logAndPrint("Cannot have repeated weapon card in Stage");
                    } else {
                        stageCards.add(stageCard);
                        currStageCards.add(stageCard);
                        currStageValue += stageCard.getValue();
                        sponsor.getHand().remove(pos-1);
                    }
                }
            }
            catch (NumberFormatException err) {
                game.logAndPrint("Not a valid integer. Please try again");
            }
            catch (IndexOutOfBoundsException err) {
                game.logAndPrint("Not a valid position. Please try again");
            }

        }
        game.logAndPrint("All stages are built");
        for(int i=1;i<stageValues.size();i++){
            game.logAndPrint("Stage #" + i + " Value: " + stageValues.get(i));
        }
        game.logAndPrint("Press [ENTER] to complete stage changes");
        game.getNextCommandOrInput();
        game.clearScreen();
    }

    public void handleBeginningStage(Player participant){
        game.dealAdventureCards(1, participant.getId());
        game.checkTrim(participant);
    }

    public List<Card> buildAttack(Player participant) {
        List<Card> attackCards = new ArrayList<>();
        int attackValue = 0;
        while (true) {
            participant.showHand();
            game.logAndPrint("Current Attack: " + cardBuildString(attackCards));
            game.logAndPrint("Current Attack Value: " + attackValue);
            game.logAndPrint("Select the position of the card to include in your attack or type 'Quit' when finished");
            String input = game.getNextCommandOrInput();
            if (input.equalsIgnoreCase("Quit")) {
                if (attackCards.isEmpty()) {
                    game.logAndPrint("Can't have an empty attack.");
                    continue;
                } else {
                    break;
                }
            }
            try {
                int cardPos = Integer.parseInt(input);
                Card chosenCard = participant.getHand().get(cardPos - 1);
                if (hasRepeatedCard(attackCards, chosenCard)) {
                    game.logAndPrint("You cannot use the same card twice");
                } else if (chosenCard.getCardType() == Card.CardType.FOE) {
                    game.logAndPrint("Cannot use FOE card in attack");
                } else {
                    attackCards.add(chosenCard);
                    attackValue += chosenCard.getValue();
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                game.logAndPrint("Invalid input. Please try again.");
            }
        }
        return attackCards;
    }

    public List<Integer> getStageValues() {
        return stageValues;
    }

    public List<Player> startQuest() {
        List<Player> successfulParticipants = new ArrayList<>();
        int currentStage = 1;
        while (currentStage <= stages) {
            participants = getParticipantsForQuest(sponsor);
            if (participants.isEmpty()) {
                game.logAndPrint("No Participants Left");
                break;
            }
            game.logAndPrint("Stage " + currentStage + " begins!");
            for (int i = 0; i < participants.size(); i++) {
                Player currParticipant = participants.get(i);
                game.logAndPrint("P" + currParticipant.getId() + " will build an attack");

                if (!buildAndResolveAttack(currParticipant, currentStage)) {
                    currParticipant.setDeclinedToParticipate(true);
                    participants.remove(i);
                    i--;
                } else if (currentStage == stages) {
                    successfulParticipants.add(currParticipant);
                }
            }
            currentStage++;
        }
        return successfulParticipants;
    }
    public boolean buildAndResolveAttack(Player participant, int currentStage) {
        handleBeginningStage(participant);

        List<Card> attackCards = buildAttack(participant);
        int attackValue = calculateAttackValue(attackCards);

        int stageDifficulty = stageValues.get(currentStage);
        if (attackValue >= stageDifficulty) {
            game.clearScreen();
            game.logAndPrint("P" + participant.getId() + " succeeded in Stage " + currentStage + "!");
            game.logAndPrint("Press [ENTER] to end Attack Phase!");
            game.getNextCommandOrInput();
            game.clearScreen();
            return true;
        } else {
            game.clearScreen();
            game.logAndPrint("P" + participant.getId() + " failed in Stage " + currentStage + " and is eliminated.");
            game.logAndPrint("Press [ENTER] to end Attack Phase!");
            game.getNextCommandOrInput();
            game.clearScreen();
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
}