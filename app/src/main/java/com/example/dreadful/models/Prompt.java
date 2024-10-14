package com.example.dreadful.models;

import java.util.ArrayList;

public class Prompt {
    private ArrayList<String> eventMessage;
    private ArrayList<String> dialogueMessage;

    private ArrayList<Integer> selectedMessage;  // 0 = event, 1 = dialogue

    public Prompt() {
        eventMessage = new ArrayList<>();
        dialogueMessage = new ArrayList<>();

        selectedMessage = new ArrayList<>();
    }

    public int getTotalMessages() {
        return eventMessage.size() + dialogueMessage.size();
    }

    public ArrayList<String> getEventMessage() {
        return eventMessage;
    }

    public void addEventMessage(String eventMessage) {
        getEventMessage().add(eventMessage);
        selectedMessage.add(0);
    }

    public ArrayList<String> getDialogueMessage() {
        return dialogueMessage;
    }

    public void addDialogueMessage(Player selectedPlayer, String dialogueMessage) {
        getDialogueMessage().add(selectedPlayer.getName() + ": " + dialogueMessage);
        selectedMessage.add(1);
    }

    public ArrayList<Integer> getSelectedMessage() {
        return selectedMessage;
    }
}
