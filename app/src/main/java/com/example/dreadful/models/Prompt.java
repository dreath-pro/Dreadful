package com.example.dreadful.models;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;

import java.util.ArrayList;
import java.util.Random;

public class Prompt {
    private MutableLiveData<ArrayList<String>> eventMessage;
    private MutableLiveData<ArrayList<String>> dialogueMessage;
    private ArrayList<Integer> eventColor, dialogueColor;
    private TestActivity testActivity;
    private Random random;

    private ArrayList<Integer> selectedMessage;  // 0 = event, 1 = dialogue

    public Prompt() {

    }

    public Prompt(TestActivity testActivity) {
        this.eventMessage = new MutableLiveData<>();
        this.dialogueMessage = new MutableLiveData<>();
        this.testActivity = testActivity;
        this.random = new Random();
        this.eventColor = new ArrayList<>();
        this.dialogueColor = new ArrayList<>();
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
        ArrayList<String> newEventMessage = getEventMessage().getValue();
        if (newEventMessage == null) {
            newEventMessage = new ArrayList<>();
        }
        newEventMessage.add(dialogues.get(random.nextInt(dialogues.size())));
        addEventMessage(newEventMessage);
    }

    public boolean selectRandomDialogue(Player selectedPlayer, ArrayList<String> dialogues, boolean isRandomPopup) {
        boolean isTherePopup = false;

        if (isRandomPopup) {
            if (random.nextInt(2) == 0) {
                ArrayList<String> newDialogueMessage = getDialogueMessage().getValue();
                if (newDialogueMessage == null) {
                    newDialogueMessage = new ArrayList<>();
                }
                newDialogueMessage.add(selectedPlayer.getName() + ": " + dialogues.get(random.nextInt(dialogues.size())));

                addDialogueMessage(newDialogueMessage);
                isTherePopup = true;
            }
        } else {
            ArrayList<String> newDialogueMessage = getDialogueMessage().getValue();
            if (newDialogueMessage == null) {
                newDialogueMessage = new ArrayList<>();
            }
            newDialogueMessage.add(selectedPlayer.getName() + ": " + dialogues.get(random.nextInt(dialogues.size())));

            addDialogueMessage(newDialogueMessage);
            isTherePopup = true;
        }

        return isTherePopup;
    }

    public int getTotalMessages() {
        ArrayList<String> newEventMessage = getEventMessage().getValue();
        if (newEventMessage == null) {
            newEventMessage = new ArrayList<>();
        }

        ArrayList<String> newDialogueMessage = getDialogueMessage().getValue();
        if (newDialogueMessage == null) {
            newDialogueMessage = new ArrayList<>();
        }

        return newEventMessage.size() + newDialogueMessage.size();
    }

    public LiveData<ArrayList<String>> getEventMessage() {
        return eventMessage;
    }

    public void addEventMessage(ArrayList<String> eventMessage) {
        this.eventMessage.postValue(eventMessage);
        selectedMessage.add(0);
    }

    public LiveData<ArrayList<String>> getDialogueMessage() {
        return dialogueMessage;
    }

    public void addDialogueMessage(ArrayList<String> dialogueMessage) {
        this.dialogueMessage.postValue(dialogueMessage);
        selectedMessage.add(1);
    }

    public ArrayList<Integer> getSelectedMessage() {
        return selectedMessage;
    }

    public ArrayList<Integer> getEventColor() {
        return eventColor;
    }

    public void setEventColor(ArrayList<Integer> eventColor) {
        this.eventColor = eventColor;
    }

    public ArrayList<Integer> getDialogueColor() {
        return dialogueColor;
    }

    public void setDialogueColor(ArrayList<Integer> dialogueColor) {
        this.dialogueColor = dialogueColor;
    }
}
