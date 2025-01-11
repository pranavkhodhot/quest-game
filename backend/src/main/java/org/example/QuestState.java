package org.example;

import java.util.ArrayList;
import java.util.List;

public class QuestState {
    private int stages;
    private int currentStage;
    private int currStageValue;
    private int currAttackValue;
    private Player sponsor;
    private List<Integer> stageValues;
    private List<String> stageCards;
    private List<String> currStageCards;
    private List<Player> participants;
    private List<Player> successfulParticipants;
    private List<String> currAttackCards;

    public QuestState(Quest quest) {
        this.stages = quest.getStages();
        this.currentStage = quest.getCurrentStage();
        this.sponsor = quest.getSponsor();
        this.stageCards = new ArrayList<>();
        for (Card card : quest.getStageCards()) {
            stageCards.add(card.getName());
        }
        this.stageValues = quest.getStageValues();
        this.participants = quest.getParticipants();
        this.successfulParticipants = quest.getSuccessful();
        this.currStageCards = new ArrayList<>();
        for (Card card : quest.getCurrStageCards()) {
            currStageCards.add(card.getName());
        }
        this.currStageValue = quest.calculateAttackValue(quest.getCurrStageCards());
        this.currAttackCards = new ArrayList<>();
        for (Card card : quest.getAttackCards()) {
            currAttackCards.add(card.getName());
        }
        this.currAttackValue = quest.calculateAttackValue(quest.getAttackCards());
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public Player getSponsor() {
        return sponsor;
    }

    public void setSponsor(Player sponsor) {
        this.sponsor = sponsor;
    }

    public List<Integer> getStageValues() {
        return stageValues;
    }

    public void setStageValues(List<Integer> stageValues) {
        this.stageValues = stageValues;
    }

    public List<String> getStageCards() {
        return stageCards;
    }

    public void setStageCards(List<String> stageCards) {
        this.stageCards = stageCards;
    }

    public List<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Player> participants) {
        this.participants = participants;
    }

    public List<Player> getSuccessfulParticipants() {
        return successfulParticipants;
    }

    public List<String> getCurrStageCards() {
        return currStageCards;
    }

    public void setCurrStageCards(List<String> currStageCards) {
        this.currStageCards = currStageCards;
    }

    public int getCurrStageValue() {
        return currStageValue;
    }

    public void setCurrStageValue(int currStageValue) {
        this.currStageValue = currStageValue;
    }

    public List<String> getCurrAttackCards() {
        return currAttackCards;
    }

    public void setCurrAttackCards(List<String> currAttackCards) {
        this.currAttackCards = currAttackCards;
    }

    public int getCurrAttackValue() {
        return currAttackValue;
    }

    public void setCurrAttackValue(int currAttackValue) {
        this.currAttackValue = currAttackValue;
    }

    public void setSuccessfulParticipants(List<Player> successfulParticipants) {
        this.successfulParticipants = successfulParticipants;
    }
}
