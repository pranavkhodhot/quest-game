package org.example;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int stages;
    private Player sponsor;
    private List<Integer> stageValues;
    private List<Card> stageCards;
    private Game game;

    public Quest(int stages, Player sponsor, Game game) {
        this.stages = stages;
        this.sponsor = sponsor;
        this.game = game;
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

    public String cardBuildString(List<Card> build) {
        String ret = "";
        for(int i=0;i<build.size();i++){
            ret += build.get(i).getName() + " ";
        }
        return ret;
    }
}