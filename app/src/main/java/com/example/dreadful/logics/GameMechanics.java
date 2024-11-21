package com.example.dreadful.logics;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreadful.models.Monster;

import java.util.ArrayList;
import java.util.Random;

public class GameMechanics {
    private Context context;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private TextView yourStunText, enemyStunText;
    private Monster yourMonster, enemyMonster;
    private TextView promptView;
    private Button startButton;

    private Handler turnDelay = new Handler();
    private Handler hitDelay = new Handler();
    private Random random = new Random();

    private NumberComma numberComma = new NumberComma();
    private boolean isBattle;

    public GameMechanics(Context context, ProgressBar yourHealth, TextView yourHealthText, ProgressBar enemyHealth,
                         TextView enemyHealthText, Monster yourMonster, Monster enemyMonster, TextView promptView,
                         TextView yourStunText, TextView enemyStunText, Button startButton, boolean isBattle) {

        this.context = context;

        this.yourHealth = yourHealth;
        this.yourHealthText = yourHealthText;

        this.enemyHealth = enemyHealth;
        this.enemyHealthText = enemyHealthText;

        this.yourMonster = yourMonster;
        this.enemyMonster = enemyMonster;
        this.promptView = promptView;

        this.startButton = startButton;

        this.yourStunText = yourStunText;
        this.enemyStunText = enemyStunText;
        this.isBattle = isBattle;
    }

    private void receiveTimeEffect() {
        if (yourMonster.getHealth() > 0) {
            yourMonster.receiveTimeEffect(enemyMonster, yourMonster);
        }

        if (enemyMonster.getHealth() > 0) {
            enemyMonster.receiveTimeEffect(yourMonster, enemyMonster);
        }

        updateHealthBars();
    }

    public void battleLoop() {
        turnDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (yourMonster.getHealth() > 0 && enemyMonster.getHealth() > 0) {
                    int attacker = random.nextInt(2);

                    ArrayList<Monster> monsters = new ArrayList<>();
                    monsters.add(yourMonster);
                    monsters.add(enemyMonster);

                    while (monsters.get(attacker).getStun() > 0) {
                        attacker = random.nextInt(2);
                    }

                    if (attacker == 0) {
                        promptView.setText(yourMonster.getName() + " makes a move!");
                    } else {
                        promptView.setText(enemyMonster.getName() + " makes a move!");
                    }

                    receiveTimeEffect();
                    hitDelay(attacker);
                }

                if (yourMonster.getHealth() <= 0 && enemyMonster.getHealth() <= 0) {
                    Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show();
                    startButton.performClick();
                } else if (yourMonster.getHealth() <= 0) {
                    Toast.makeText(context, enemyMonster.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
                    startButton.performClick();
                } else if (enemyMonster.getHealth() <= 0) {
                    Toast.makeText(context, yourMonster.getName() + " is victorious!", Toast.LENGTH_SHORT).show();

                    if (isBattle) {
                        enemyMonster.defeatReward();
                    }

                    startButton.performClick();
                }
            }
        }, 1800);
    }

    private void hitDelay(int attacker) {
        hitDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (attacker == 0) {
                    promptView.setText(yourMonster.getName() + " uses \"" + yourMonster.useRandomAttack(yourMonster, enemyMonster) + "\"");
                } else {
                    promptView.setText(enemyMonster.getName() + " uses \"" + enemyMonster.useRandomAttack(enemyMonster, yourMonster) + "\"");
                }
                yourMonster.setStun(yourMonster.getStun() - 1);
                enemyMonster.setStun(enemyMonster.getStun() - 1);
                updateHealthBars();

                receiveTimeEffect();
                battleLoop();
            }
        }, 3000);
    }

    public void stopBattleLoop() {
        turnDelay.removeCallbacksAndMessages(null);
        hitDelay.removeCallbacksAndMessages(null);
    }

    private void updateHealthBars() {
        yourHealthText.setText(numberComma.convertComma(yourMonster.getHealth()));
        enemyHealthText.setText(numberComma.convertComma(enemyMonster.getHealth()));
        yourHealth.setProgress(yourMonster.getHealth());
        enemyHealth.setProgress(enemyMonster.getHealth());
        yourStunText.setText(String.valueOf(yourMonster.getStun()));
        enemyStunText.setText(String.valueOf(enemyMonster.getStun()));
    }
}
