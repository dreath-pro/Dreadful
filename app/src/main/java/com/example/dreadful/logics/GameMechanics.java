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

    private Handler battleDelay = new Handler();
    private Random random = new Random();

    public GameMechanics(Context context, TextView yourName, ProgressBar yourHealth, TextView yourHealthText, ImageView yourImage,
                         TextView enemyName, ProgressBar enemyHealth, TextView enemyHealthText, ImageView enemyImage,
                         Character yourCharacter, Character enemyCharacter) {

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
    }

    public void battleLoop() {
        battleDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (yourCharacter.getHealth() <= 0 && enemyCharacter.getHealth() <= 0) {
                    Toast.makeText(context, "Draw", Toast.LENGTH_SHORT).show();
                } else if (yourCharacter.getHealth() <= 0) {
                    Toast.makeText(context, enemyCharacter.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
                } else if (enemyCharacter.getHealth() <= 0) {
                    Toast.makeText(context, yourCharacter.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
                } else {
                    int attacker = random.nextInt(2);
                    if (attacker == 0) {
                        yourCharacter.useRandomAttack(yourCharacter, enemyCharacter);
                    } else {
                        enemyCharacter.useRandomAttack(enemyCharacter, yourCharacter);
                    }

                    updateHealthBars();
                    battleLoop();
                }
            }
        }, 1000);
    }

    public void stopBattleLoop() {
        battleDelay.removeCallbacksAndMessages(null);
    }

    public void updateHealthBars() {
        yourHealthText.setText(String.valueOf(yourCharacter.getHealth()));
        enemyHealthText.setText(String.valueOf(enemyCharacter.getHealth()));
        yourHealth.setProgress(yourCharacter.getHealth());
        enemyHealth.setProgress(enemyCharacter.getHealth());
    }
}
