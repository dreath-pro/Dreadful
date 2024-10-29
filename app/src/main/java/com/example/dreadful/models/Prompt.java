package com.example.dreadful.models;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;

import java.util.ArrayList;
import java.util.Random;

public class Prompt {
    private MutableLiveData<ArrayList<String>> battleMessage;
    private ArrayList<Integer> messageColor;
    private TestActivity testActivity;
    private Random random;

    public Prompt() {

    }

    public Prompt(TestActivity testActivity) {
        this.battleMessage = new MutableLiveData<>();
        this.testActivity = testActivity;
        this.random = new Random();
        this.messageColor = new ArrayList<>();
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

    public boolean isTherePopup() {
        if (random.nextInt(2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void selectRandomMessage(Player selectedPlayer, ArrayList<String> messages, boolean isDialog) {
        ArrayList<String> newBattleMessage = getBattleMessage().getValue();
        if (newBattleMessage == null) {
            newBattleMessage = new ArrayList<>();
        }
        if (isDialog) {
            newBattleMessage.add(selectedPlayer.getName() + ": " + messages.get(random.nextInt(messages.size())));
        } else {
            newBattleMessage.add(messages.get(random.nextInt(messages.size())));
        }

        addBattleMessage(newBattleMessage);
    }

    public LiveData<ArrayList<String>> getBattleMessage() {
        return battleMessage;
    }

    public void addBattleMessage(ArrayList<String> battleMessage) {
        this.battleMessage.postValue(battleMessage);
    }

    public ArrayList<Integer> getMessageColor() {
        return messageColor;
    }

    public void setMessageColor(ArrayList<Integer> messageColor) {
        this.messageColor = messageColor;
    }
}
