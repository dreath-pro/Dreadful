package com.example.dreadful.activities;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.dreadful.adapters.ViewPrompt;
import com.example.dreadful.adapters.ViewSkill;
import com.example.dreadful.adapters.ViewStatus;
import com.example.dreadful.logics.GameMechanics;
import com.example.dreadful.logics.SetupCharacter;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class TestActivity extends AppCompatActivity {
    private ConstraintLayout backgroundImage;
    private TextView yourName, enemyName;
    private ProgressBar yourHealth, enemyHealth;
    private TextView yourHealthText, enemyHealthText;
    private ImageView yourImage, enemyImage;
    private Button backButton, startButton, resetButton;
    private ImageView promptButton;
    private TextView promptView;
    private TextView yourStunText, enemyStunText;
    private LinearLayout yourPlayerLayout, enemyPlayerLayout;

    private Player yourPlayer, enemyPlayer;
    private GameMechanics gameMechanics;
    private SetupCharacter setupCharacter;
    private Random random = new Random();
    private int[] backgroundList = {R.drawable.background_cathedral, R.drawable.background_dark_forest,
            R.drawable.background_graveyard, R.drawable.background_cave};
    private int selectedBackground = 0;
    private ViewStatus viewStatus;
    private ViewSkill viewSkill;
    private ViewPrompt viewPrompt;

    private Prompt prompt;
    private Dialog battleLogsDialog;

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

        backgroundImage = findViewById(R.id.test);

        backButton = findViewById(R.id.backButton);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        promptButton = findViewById(R.id.promptButton);
        promptView = findViewById(R.id.promptView);

        yourStunText = findViewById(R.id.yourStunText);
        enemyStunText = findViewById(R.id.enemyStunText);
    }

    private void startConfiguration(boolean newViews) {
        promptView.setText("");

        prompt = new Prompt(this);
        prompt.addEventMessage("Fate has led them to this pivotal moment of encounter.");

        if (newViews) {
            selectedBackground = random.nextInt(backgroundList.length);
        }
        backgroundImage.setBackgroundResource(backgroundList[selectedBackground]);

        if (newViews) {
            setupCharacter = new SetupCharacter(this,
                    yourName, yourHealth, yourHealthText, yourImage,
                    enemyName, enemyHealth, enemyHealthText, enemyImage,
                    yourPlayer, enemyPlayer, backgroundImage, yourStunText,
                    enemyStunText, backgroundList, selectedBackground,
                    yourHealth, enemyHealth, prompt);
        }

        setupCharacter.initializeYourViews(newViews);
        setupCharacter.initializeEnemyViews(newViews);
        yourPlayer = setupCharacter.returnYourCharacter();
        enemyPlayer = setupCharacter.returnEnemyCharacter();

        gameMechanics = new GameMechanics(this, yourHealth, yourHealthText, enemyHealth,
                enemyHealthText, yourPlayer, enemyPlayer, promptView, yourStunText, enemyStunText, startButton);
    }

    private void invisibleButtons(Boolean invisible) {
        if (invisible) {
            backButton.setVisibility(View.GONE);
            resetButton.setVisibility(View.GONE);
        } else {
            backButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        initViews();
        startConfiguration(true);

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
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConfiguration(true);
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
                    startConfiguration(false);
                }
            }
        });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (battleLogsDialog != null && battleLogsDialog.isShowing()) {
                    battleLogsDialog.dismiss();
                }
                showBattleLogs();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.test), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameMechanics.stopBattleLoop(); // Ensure cleanup when activity is destroyed
    }

    public void addPrompt(Prompt prompt)
    {
        this.prompt = prompt;
    }

    private void showBattleLogs() {
        battleLogsDialog = new Dialog(this);
        battleLogsDialog.setContentView(R.layout.dialog_battle_log);

        RecyclerView promptList = battleLogsDialog.findViewById(R.id.promptList);

        LinearLayoutManager statusLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        promptList.setLayoutManager(statusLayoutManager);
        viewPrompt = new ViewPrompt(this, prompt);
        promptList.setAdapter(viewPrompt);

        battleLogsDialog.show();
    }

    private void showCharacterDetails(Player player) {
        Dialog characterDialog = new Dialog(this);
        characterDialog.setContentView(R.layout.dialog_character_details);

        TextView playerName = characterDialog.findViewById(R.id.playerName);
        ImageView playerImage = characterDialog.findViewById(R.id.playerImage);
        RecyclerView statusList = characterDialog.findViewById(R.id.statusList);
        RecyclerView skillList = characterDialog.findViewById(R.id.skillList);

        playerName.setText(player.getName());
        playerImage.setImageResource(player.getImage());

        if (player.getImageDirection().equals("left")) {
            playerImage.setScaleX(-1);
        }

        LinearLayoutManager statusLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        statusList.setLayoutManager(statusLayoutManager);
        viewStatus = new ViewStatus(this, player);
        statusList.setAdapter(viewStatus);

        LinearLayoutManager skillLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        skillList.setLayoutManager(skillLayoutManager);
        viewSkill = new ViewSkill(this, player);
        skillList.setAdapter(viewSkill);

        characterDialog.show();
    }
}
