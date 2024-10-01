package com.example.dreadful.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreadful.R;
import com.example.dreadful.characters.Dreath;
import com.example.dreadful.characters.PopeOfDeath;
import com.example.dreadful.logics.GameMechanics;
import com.example.dreadful.models.Character;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //armored crocodile golem, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view
    //person with spiky armor covered with dark liquid, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view, red, black, dark-gray, crimson-red

    //experimental
    //inside the cathedral, hd, splash art, minimalist, detailed cartoon, dark fantasy theme, background, red, gray, black, dark-red, crimson-red

    //two swords clashing, simple icon, digital art, dark fantasy theme, vibrant shading, white background, red, crimson-red, black, dark-red

    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Button backButton, startButton;

    private ArrayList<Character> characters = new ArrayList<>();
    private Character yourCharacter, enemyCharacter;
    private Random random = new Random();
    private GameMechanics gameMechanics;

    private void initViews() {
        yourName = findViewById(R.id.yourName);
        enemyName = findViewById(R.id.enemyName);

        yourHealth = findViewById(R.id.yourHealth);
        enemyHealth = findViewById(R.id.enemyHealth);

        yourHealthText = findViewById(R.id.yourHealthText);
        enemyHealthText = findViewById(R.id.enemyHealthText);

        yourImage = findViewById(R.id.yourImage);
        enemyImage = findViewById(R.id.enemyImage);

        backButton = findViewById(R.id.backButton);
        startButton = findViewById(R.id.startButton);
    }

    private void initCharacters() {
        characters.clear();
        characters.add(new Dreath());
        characters.add(new PopeOfDeath());
    }

    private void initializeYourCharacter() {
        yourImage.setScaleX(1);

        initCharacters();
        yourCharacter = characters.get(random.nextInt(characters.size()));

        yourName.setText(yourCharacter.getName());
        yourHealthText.setText(String.valueOf(yourCharacter.getHealth()));
        yourHealth.setMax(yourCharacter.getHealth());
        yourHealth.setProgress(yourCharacter.getHealth());
        yourImage.setImageResource(getResources().getIdentifier(yourCharacter.getImage(), "drawable", getPackageName()));

        if (yourCharacter.getImageDirection().equals("right")) {
            yourImage.setScaleX(-1);
        }

        if (yourCharacter.getSize().equals("average")) {
            ViewGroup.LayoutParams layoutParams = yourImage.getLayoutParams();

            int widthInDp = 150; // Desired width in dp
            int heightInDp = 150; // Desired height in dp

            float scale = getResources().getDisplayMetrics().density;
            int widthInPixels = (int) (widthInDp * scale + 0.5f);
            int heightInPixels = (int) (heightInDp * scale + 0.5f);

            layoutParams.width = widthInPixels;
            layoutParams.height = heightInPixels;

            yourImage.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = yourImage.getLayoutParams();

            int widthInDp = 210; // Desired width in dp
            int heightInDp = 210; // Desired height in dp

            float scale = getResources().getDisplayMetrics().density;
            int widthInPixels = (int) (widthInDp * scale + 0.5f);
            int heightInPixels = (int) (heightInDp * scale + 0.5f);

            layoutParams.width = widthInPixels;
            layoutParams.height = heightInPixels;

            yourImage.setLayoutParams(layoutParams);
        }
    }

    private void initializeEnemyCharacter() {
        enemyImage.setScaleX(1);

        initCharacters();
        enemyCharacter = characters.get(random.nextInt(characters.size()));

        enemyName.setText(enemyCharacter.getName());
        enemyHealthText.setText(String.valueOf(enemyCharacter.getHealth()));
        enemyHealth.setMax(enemyCharacter.getHealth());
        enemyHealth.setProgress(enemyCharacter.getHealth());
        enemyImage.setImageResource(getResources().getIdentifier(enemyCharacter.getImage(), "drawable", getPackageName()));

        if (enemyCharacter.getImageDirection().equals("left")) {
            enemyImage.setScaleX(-1);
        }

        if (enemyCharacter.getSize().equals("average")) {
            ViewGroup.LayoutParams layoutParams = enemyImage.getLayoutParams();

            int widthInDp = 150; // Desired width in dp
            int heightInDp = 150; // Desired height in dp

            float scale = getResources().getDisplayMetrics().density;
            int widthInPixels = (int) (widthInDp * scale + 0.5f);
            int heightInPixels = (int) (heightInDp * scale + 0.5f);

            layoutParams.width = widthInPixels;
            layoutParams.height = heightInPixels;

            enemyImage.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = enemyImage.getLayoutParams();

            int widthInDp = 210; // Desired width in dp
            int heightInDp = 210; // Desired height in dp

            float scale = getResources().getDisplayMetrics().density;
            int widthInPixels = (int) (widthInDp * scale + 0.5f);
            int heightInPixels = (int) (heightInDp * scale + 0.5f);

            layoutParams.width = widthInPixels;
            layoutParams.height = heightInPixels;

            enemyImage.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        initializeYourCharacter();
        initializeEnemyCharacter();
        gameMechanics = new GameMechanics(this,
                yourName, yourHealth, yourHealthText, yourImage,
                enemyName, enemyHealth, enemyHealthText, enemyImage,
                yourCharacter, enemyCharacter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCharacters();
                initializeYourCharacter();
                initializeEnemyCharacter();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getText().toString().equals(getString(R.string.start_button))) {
                    gameMechanics.battleLoop();
                    startButton.setText("Stop");
                } else {
                    gameMechanics.stopBattleLoop();
                    startButton.setText(getString(R.string.start_button));
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

//    private void updateHealthBars() {
//        yourHealthText.setText(String.valueOf(yourCharacter.getHealth()));
//        enemyHealthText.setText(String.valueOf(enemyCharacter.getHealth()));
//        yourHealth.setProgress(yourCharacter.getHealth());
//        enemyHealth.setProgress(enemyCharacter.getHealth());
//    }
//
//    private void battleLoop() {
//        battleDelay.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (yourCharacter.getHealth() <= 0 && enemyCharacter.getHealth() <= 0) {
//                    Toast.makeText(MainActivity.this, "Draw", Toast.LENGTH_SHORT).show();
//                } else if (yourCharacter.getHealth() <= 0) {
//                    Toast.makeText(MainActivity.this, enemyCharacter.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
//                } else if (enemyCharacter.getHealth() <= 0) {
//                    Toast.makeText(MainActivity.this, yourCharacter.getName() + " is victorious!", Toast.LENGTH_SHORT).show();
//                } else {
//                    int attacker = random.nextInt(2);
//                    if (attacker == 0) {
//                        yourCharacter.basicAttack(yourCharacter, enemyCharacter);
//                    } else {
//                        enemyCharacter.basicAttack(enemyCharacter, yourCharacter);
//                    }
//
//                    updateHealthBars();
//                    battleLoop();
//                }
//            }
//        }, 1000);
//    }
}