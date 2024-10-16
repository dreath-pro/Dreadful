package com.example.dreadful.models;

import com.example.dreadful.activities.TestActivity;

import java.util.ArrayList;
import java.util.Random;

public class Prompt {
    private ArrayList<String> eventMessage;
    private ArrayList<String> dialogueMessage;
    private TestActivity testActivity;
    private Random random;

    private ArrayList<Integer> selectedMessage;  // 0 = event, 1 = dialogue

    public Prompt(TestActivity testActivity) {
        this.eventMessage = new ArrayList<>();
        this.dialogueMessage = new ArrayList<>();
        this.testActivity = testActivity;
        this.random = new Random();

        this.selectedMessage = new ArrayList<>();
    }

    public void selectRandomEvent(ArrayList<String> dialogues) {
        addEventMessage(dialogues.get(random.nextInt(dialogues.size())));
        testActivity.setPrompt(this);
    }

    public void selectRandomDialogue(Player selectedPlayer, ArrayList<String> dialogues, boolean isRandomPopup) {
        if (isRandomPopup) {
            if (random.nextInt(2) == 0) {
                addDialogueMessage(selectedPlayer, dialogues.get(random.nextInt(dialogues.size())));
                testActivity.setPrompt(this);
            }
        } else {
            addDialogueMessage(selectedPlayer, dialogues.get(random.nextInt(dialogues.size())));
            testActivity.setPrompt(this);
        }
    }

    public int getTotalMessages() {
        return eventMessage.size() + dialogueMessage.size();
    }

    public ArrayList<String> getEventMessage() {
        return eventMessage;
    }

    private void addEventMessage(String eventMessage) {
        getEventMessage().add(eventMessage);
        selectedMessage.add(0);
    }

    public ArrayList<String> getDialogueMessage() {
        return dialogueMessage;
    }

    private void addDialogueMessage(Player selectedPlayer, String dialogueMessage) {
        getDialogueMessage().add(selectedPlayer.getName() + ": " + dialogueMessage);
        selectedMessage.add(1);
    }

    public ArrayList<Integer> getSelectedMessage() {
        return selectedMessage;
    }
}
