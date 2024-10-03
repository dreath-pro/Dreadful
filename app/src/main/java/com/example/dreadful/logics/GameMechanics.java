package com.example.dreadful.logics;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreadful.models.Character;

import java.util.Random;

public class GameMechanics {
    private Context context;
    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Character yourCharacter, enemyCharacter;
    private TextView promptView;

    private Handler turnDelay = new Handler();
    private Handler hitDelay = new Handler();
    private Random random = new Random();

    public GameMechanics(Context context, TextView yourName, ProgressBar yourHealth, TextView yourHealthText, ImageView yourImage,
                         TextView enemyName, ProgressBar enemyHealth, TextView enemyHealthText, ImageView enemyImage,
                         Character yourCharacter, Character enemyCharacter, TextView promptView) {

        this.context = context;
        this.yourName = yourName;
        this.yourHealth = yourHealth;
        this.yourHealthText = yourHealthText;
        this.yourImage = yourImage;

        this.enemyName = enemyName;
        this.enemyHealth = enemyHealth;
        this.enemyHealthText = enemyHealthText;
        this.enemyImage = enemyImage;

        this.yourCharacter = yourCharacter;
        this.enemyCharacter = enemyCharacter;
        this.promptView = promptView;
    }

    private void receiveTimeHp() {
        yourCharacter.receiveTimeHp(yourCharacter, enemyCharacter);
        enemyCharacter.receiveTimeHp(enemyCharacter, yourCharacter);
        updateHealthBars();
    }

    public void battleLoop() {
        turnDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (yourCharacter.getHealth() > 0 && enemyCharacter.getHealth() > 0) {
                    int attacker = random.nextInt(2);

                    if(attacker == 0)
                    {
                        promptView.setText(yourCharacter.getName() + " makes a move!");
                    }else
                    {
                        promptView.setText(enemyCharacter.getName() + " makes a move!");
                    }

                    receiveTimeHp();
                    hitDelay(attacker);
                }

                if (yourCharacter.getHealth() <= 0 && enemyCharacter.getHealth() <= 0) {
                    Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show();
                } else if (yourCharacter.getHealth() <= 0) {
                    Toast.makeText(context, enemyCharacter.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
                } else if (enemyCharacter.getHealth() <= 0) {
                    Toast.makeText(context, yourCharacter.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1800);
    }

    private void hitDelay(int attacker) {
        hitDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (attacker == 0) {
                    promptView.setText(yourCharacter.getName() + " uses \"" + yourCharacter.useRandomAttack(yourCharacter, enemyCharacter) + "\"");
                } else {
                    promptView.setText(enemyCharacter.getName() + " uses \"" + enemyCharacter.useRandomAttack(enemyCharacter, yourCharacter) + "\"");
                }
                updateHealthBars();

                receiveTimeHp();
                battleLoop();
            }
        }, 3000);
    }

    public void stopBattleLoop() {
        turnDelay.removeCallbacksAndMessages(null);
        hitDelay.removeCallbacksAndMessages(null);
    }

    private void updateHealthBars() {
        yourHealthText.setText(String.valueOf(yourCharacter.getHealth()));
        enemyHealthText.setText(String.valueOf(enemyCharacter.getHealth()));
        yourHealth.setProgress(yourCharacter.getHealth());
        enemyHealth.setProgress(enemyCharacter.getHealth());
    }
}
