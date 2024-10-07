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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.adapters.ViewSkill;
import com.example.dreadful.adapters.ViewStatus;
import com.example.dreadful.logics.GameMechanics;
import com.example.dreadful.models.Player;
import com.example.dreadful.logics.SetupCharacter;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //person with spiky armor covered with dark liquid, hd detailed cartoon, dark fantasy theme, white background, facing right, standing full view, red, black, dark-gray, crimson-red
    //long hair man riding a armored horse with long sword, hd detailed 2d cartoon, horror theme, white background, facing right, standing full view, red, black, gray, white, dark-red

    //mossy old ruins building, landscape, hd detailed 2d cartoon, horror theme, blue, unfocused
    //altar of cathedral, landscape, hd detailed 2d cartoon, horror theme, blue, unfocused, red, crimson-red, black

    //two swords clashing, simple icon, digital art, dark fantasy theme, vibrant shading, white background, red, crimson-red, black, dark-red
    //immobilize, minimalist icon, horror theme, vibrant shading, red, crimson-red, dark-red, black

    private ConstraintLayout backgroundImage;
    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Button backButton, startButton;
    private ImageView promptButton;
    private TextView promptView;
    private TextView yourStunText, enemyStunText;
    private LinearLayout yourPlayerLayout, enemyPlayerLayout;

    private Player yourPlayer, enemyPlayer;
    private GameMechanics gameMechanics;
    private SetupCharacter setupCharacter;
    private Random random = new Random();
    private int[] backgroundList = {R.drawable.background_cathedral, R.drawable.background_ruins};
    private int selectedBackground = 0;

    private void initViews() {
        yourName = findViewById(R.id.yourName);
        enemyName = findViewById(R.id.enemyName);

        yourHealth = findViewById(R.id.yourHealth);
        enemyHealth = findViewById(R.id.enemyHealth);

        yourHealthText = findViewById(R.id.yourHealthText);
        enemyHealthText = findViewById(R.id.enemyHealthText);

        yourImage = findViewById(R.id.yourImage);
        enemyImage = findViewById(R.id.enemyImage);

        yourPlayerLayout = findViewById(R.id.yourPlayerLayout);
        enemyPlayerLayout = findViewById(R.id.enemyPlayerLayout);

        backgroundImage = findViewById(R.id.main);

        backButton = findViewById(R.id.backButton);
        startButton = findViewById(R.id.startButton);
        promptButton = findViewById(R.id.promptButton);
        promptView = findViewById(R.id.promptView);

        yourStunText = findViewById(R.id.yourStunText);
        enemyStunText = findViewById(R.id.enemyStunText);
    }

    private void startConfiguration() {
        promptView.setText("");
        selectedBackground = random.nextInt(backgroundList.length);
        backgroundImage.setBackgroundResource(backgroundList[selectedBackground]);

        setupCharacter = new SetupCharacter(this,
                yourName, yourHealth, yourHealthText, yourImage,
                enemyName, enemyHealth, enemyHealthText, enemyImage,
                yourPlayer, enemyPlayer, backgroundImage, yourStunText, enemyStunText, backgroundList, selectedBackground);

        setupCharacter.initializeYourViews();
        setupCharacter.initializeEnemyViews();
        yourPlayer = setupCharacter.returnYourCharacter();
        enemyPlayer = setupCharacter.returnEnemyCharacter();

        gameMechanics = new GameMechanics(this, yourHealth, yourHealthText, enemyHealth,
                enemyHealthText, yourPlayer, enemyPlayer, promptView, yourStunText, enemyStunText);
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

        yourPlayerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCharacterDetails(yourPlayer);
            }
        });

        enemyPlayerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCharacterDetails(enemyPlayer);
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

    private void showCharacterDetails(Player player) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_character_details);

        TextView playerName = dialog.findViewById(R.id.playerName);
        ImageView playerImage = dialog.findViewById(R.id.playerImage);
        RecyclerView statusList = dialog.findViewById(R.id.statusList);
        RecyclerView skillList = dialog.findViewById(R.id.skillList);

        playerName.setText(player.getName());
        playerImage.setImageResource(player.getImage());

        if (player.getImageDirection().equals("left")) {
            playerImage.setScaleX(-1);
        }

        LinearLayoutManager statusLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        statusList.setLayoutManager(statusLayoutManager);
        ViewStatus viewStatus = new ViewStatus(this, player);
        statusList.setAdapter(viewStatus);

        LinearLayoutManager skillLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        skillList.setLayoutManager(skillLayoutManager);
        ViewSkill viewSkill = new ViewSkill(this, player);
        skillList.setAdapter(viewSkill);

        dialog.show();
    }
}