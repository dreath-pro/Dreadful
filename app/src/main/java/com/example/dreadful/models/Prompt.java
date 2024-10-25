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

    public Prompt() {

    }

    public Prompt(TestActivity testActivity) {
        this.eventMessage = new ArrayList<>();
        this.dialogueMessage = new ArrayList<>();
        this.testActivity = testActivity;
        this.random = new Random();

        this.selectedMessage = new ArrayList<>();
    }

    public String getApostrophe(String word) {
        if (word.charAt(word.length() - 1) == 's') {
            return "'";
        } else {
            return "'s";
        }
    }

    public int measureDamage(int damagePercentage) {
        if (damagePercentage >= 0 && damagePercentage <= 5) {
            return 0;
        } else if (damagePercentage >= 6 && damagePercentage <= 10) {
            return 0;
        } else if (damagePercentage >= 11 && damagePercentage <= 20) {
            return 0;
        } else if (damagePercentage >= 21 && damagePercentage <= 30) {
            return 1;
        } else if (damagePercentage >= 31 && damagePercentage <= 40) {
            return 1;
        } else if (damagePercentage >= 41 && damagePercentage <= 50) {
            return 1;
        } else if (damagePercentage >= 51 && damagePercentage <= 60) {
            return 2;
        } else if (damagePercentage >= 61 && damagePercentage <= 70) {
            return 2;
        } else if (damagePercentage >= 71 && damagePercentage <= 80) {
            return 2;
        } else if (damagePercentage >= 81 && damagePercentage <= 90) {
            return 3;
        } else if (damagePercentage >= 91 && damagePercentage <= 100) {
            return 3;
        } else if (damagePercentage >= 101) {
            return 3;
        }

        return 4;
    }

    public void selectRandomEvent(ArrayList<String> dialogues) {
        addEventMessage(dialogues.get(random.nextInt(dialogues.size())));
    }

    public void selectRandomDialogue(Player selectedPlayer, ArrayList<String> dialogues, boolean isRandomPopup) {
        if (isRandomPopup) {
            if (random.nextInt(2) == 0) {
                addDialogueMessage(selectedPlayer, dialogues.get(random.nextInt(dialogues.size())));
            }
        } else {
            addDialogueMessage(selectedPlayer, dialogues.get(random.nextInt(dialogues.size())));
        }
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
