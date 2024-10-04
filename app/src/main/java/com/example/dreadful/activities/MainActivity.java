package com.example.dreadful.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreadful.R;
import com.example.dreadful.logics.GameMechanics;
import com.example.dreadful.models.Character;
import com.example.dreadful.utils.SetupCharacter;

public class MainActivity extends AppCompatActivity {
    //armored crocodile golem, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view
    //person with spiky armor covered with dark liquid, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view, red, black, dark-gray, crimson-red
    //humanoid void mutant with no face but only white teeth and with a tentacle back and root hands, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view, violet, dark-violet, blue-violet, dark-blue

    //experimental
    //altar of cathedral background, hd, minimalist cartoon, dark fantasy theme, background, red, gray, black, dark-red, crimson-red

    //two swords clashing, simple icon, digital art, dark fantasy theme, vibrant shading, white background, red, crimson-red, black, dark-red

    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Button backButton, startButton;
    private ImageView promptButton;
    private TextView promptView;
    private LinearLayout yourPlayer, enemyPlayer;

    private Character yourCharacter, enemyCharacter;
    private GameMechanics gameMechanics;
    private SetupCharacter setupCharacter;

    private void initViews() {
        yourName = findViewById(R.id.yourName);
        enemyName = findViewById(R.id.enemyName);

        yourHealth = findViewById(R.id.yourHealth);
        enemyHealth = findViewById(R.id.enemyHealth);

        yourHealthText = findViewById(R.id.yourHealthText);
        enemyHealthText = findViewById(R.id.enemyHealthText);

        yourImage = findViewById(R.id.yourImage);
        enemyImage = findViewById(R.id.enemyImage);

        yourPlayer = findViewById(R.id.yourPlayer);
        enemyPlayer = findViewById(R.id.enemyPlayer);

        backButton = findViewById(R.id.backButton);
        startButton = findViewById(R.id.startButton);
        promptButton = findViewById(R.id.promptButton);
        promptView = findViewById(R.id.promptView);
    }

    private void startConfiguration() {
        promptView.setText("");

        setupCharacter = new SetupCharacter(this,
                yourName, yourHealth, yourHealthText, yourImage,
                enemyName, enemyHealth, enemyHealthText, enemyImage,
                yourCharacter, enemyCharacter);

        setupCharacter.initializeYourViews();
        setupCharacter.initializeEnemyViews();
        yourCharacter = setupCharacter.returnYourCharacter();
        enemyCharacter = setupCharacter.returnEnemyCharacter();

        gameMechanics = new GameMechanics(this,
                yourName, yourHealth, yourHealthText, yourImage,
                enemyName, enemyHealth, enemyHealthText, enemyImage,
                yourCharacter, enemyCharacter, promptView);
    }

    private void invisibleButtons(Boolean invisible) {
        if (invisible) {
            backButton.setVisibility(View.GONE);
        } else {
            backButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        startConfiguration();

        yourPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCharacterDetails(yourCharacter);
            }
        });

        enemyPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCharacterDetails(enemyCharacter);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConfiguration();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getText().toString().equals(getString(R.string.start_button))) {
                    invisibleButtons(true);
                    startButton.setText("Stop");
                    gameMechanics.battleLoop();
                } else {
                    invisibleButtons(false);
                    startButton.setText(getString(R.string.start_button));
                    gameMechanics.stopBattleLoop();
                }
            }
        });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showCharacterDetails(Character player) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_character_details);

        TextView playerName = dialog.findViewById(R.id.playerName);
        ImageView playerImage = dialog.findViewById(R.id.playerImage);

        playerName.setText(player.getName());
        playerImage.setImageResource(getResources().getIdentifier(player.getImage(), "drawable", getPackageName()));

        dialog.show();
    }
}