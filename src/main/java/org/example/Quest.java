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

    public void buildQuest() {
        int count = 0;
        game.logAndPrint("P" + sponsor.getId() + " Will Begin the Stage Building Process");
        stageValues.add(0);
        List<Card> currStageCards = new ArrayList<>();
        int currStageValue = 0;
        while (count < stages) {
            try {
                sponsor.showHand();
                game.logAndPrint("Previous Stage Value: " + stageValues.getLast());
                game.logAndPrint("Current Stage Value: " + currStageValue);
                game.logAndPrint("Current Cards: " + cardBuildString(currStageCards));
                game.logAndPrint("Select the position of the card to include in Stage #" + (count + 1) + " or enter Quit to stop building stage");
                String answer = game.getNextCommandOrInput();
                if (answer.equalsIgnoreCase("Quit")) {
                    stageValues.add(currStageValue);
                    currStageCards.clear();
                    currStageValue = 0;
                    count++;
                    game.clearScreen();
                    continue;
                }
                int pos = Integer.parseInt(answer);
                Card stageCard = sponsor.getHand().get(pos - 1);
                stageCards.add(stageCard);
                currStageCards.add(stageCard);
                currStageValue += stageCard.getValue();
                sponsor.getHand().remove(pos - 1);

            } catch (NumberFormatException err) {
                game.logAndPrint("Not a valid integer. Please try again");
            } catch (IndexOutOfBoundsException err) {
                game.logAndPrint("Not a valid position. Please try again");
            }

        }
        game.logAndPrint("All stages are built");
        for (int i = 1; i < stageValues.size(); i++) {
            game.logAndPrint("Stage #" + i + " Value: " + stageValues.get(i));
        }
        game.logAndPrint("Press [ENTER] to complete stage changes");
        game.getNextCommandOrInput();
        game.clearScreen();
    }

    public String cardBuildString(List<Card> build) {
        String ret = "";
        for(int i=0;i<build.size();i++){
            ret += build.get(i).getName() + " ";
        }
        return ret;
    }
}