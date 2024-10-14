package com.example.dreadful.logics;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.models.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameMechanics {
    private Context context;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private TextView yourStunText, enemyStunText;
    private Player yourPlayer, enemyPlayer;
    private TextView promptView;
    private Button startButton;

    private Handler turnDelay = new Handler();
    private Handler hitDelay = new Handler();
    private Random random = new Random();

    public GameMechanics(Context context, ProgressBar yourHealth, TextView yourHealthText, ProgressBar enemyHealth,
                         TextView enemyHealthText, Player yourPlayer, Player enemyPlayer, TextView promptView,
                         TextView yourStunText, TextView enemyStunText, Button startButton) {

        this.context = context;

        this.yourHealth = yourHealth;
        this.yourHealthText = yourHealthText;

        this.enemyHealth = enemyHealth;
        this.enemyHealthText = enemyHealthText;

        this.yourPlayer = yourPlayer;
        this.enemyPlayer = enemyPlayer;
        this.promptView = promptView;

        this.startButton = startButton;

        this.yourStunText = yourStunText;
        this.enemyStunText = enemyStunText;
    }

    private void receiveTimeEffect() {
        if (yourPlayer.getHealth() > 0) {
            yourPlayer.receiveTimeEffect(enemyPlayer, yourPlayer);
        }

        if (enemyPlayer.getHealth() > 0) {
            enemyPlayer.receiveTimeEffect(yourPlayer, enemyPlayer);
        }

        updateHealthBars();
    }

    public void battleLoop() {
        turnDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (yourPlayer.getHealth() > 0 && enemyPlayer.getHealth() > 0) {
                    int attacker = random.nextInt(2);

                    ArrayList<Player> players = new ArrayList<>();
                    players.add(yourPlayer);
                    players.add(enemyPlayer);

                    while (players.get(attacker).getStun() > 0) {
                        attacker = random.nextInt(2);
                    }

                    if (attacker == 0) {
                        promptView.setText(yourPlayer.getName() + " makes a move!");
                    } else {
                        promptView.setText(enemyPlayer.getName() + " makes a move!");
                    }

                    receiveTimeEffect();
                    hitDelay(attacker);
                }

                if (yourPlayer.getHealth() <= 0 && enemyPlayer.getHealth() <= 0) {
                    Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show();
                    startButton.performClick();
                } else if (yourPlayer.getHealth() <= 0) {
                    Toast.makeText(context, enemyPlayer.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
                    startButton.performClick();
                } else if (enemyPlayer.getHealth() <= 0) {
                    Toast.makeText(context, yourPlayer.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
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
                    promptView.setText(yourPlayer.getName() + " uses \"" + yourPlayer.useRandomAttack(yourPlayer, enemyPlayer) + "\"");
                } else {
                    promptView.setText(enemyPlayer.getName() + " uses \"" + enemyPlayer.useRandomAttack(enemyPlayer, yourPlayer) + "\"");
                }
                yourPlayer.setStun(yourPlayer.getStun() - 1);
                enemyPlayer.setStun(enemyPlayer.getStun() - 1);
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
        yourHealthText.setText(String.valueOf(yourPlayer.getHealth()));
        enemyHealthText.setText(String.valueOf(enemyPlayer.getHealth()));
        yourHealth.setProgress(yourPlayer.getHealth());
        enemyHealth.setProgress(enemyPlayer.getHealth());
        yourStunText.setText(String.valueOf(yourPlayer.getStun()));
        enemyStunText.setText(String.valueOf(enemyPlayer.getStun()));
    }
}
