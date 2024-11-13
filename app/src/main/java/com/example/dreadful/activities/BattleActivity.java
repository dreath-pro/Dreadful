package com.example.dreadful.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreadful.R;
import com.example.dreadful.logics.MainMechanics;

public class BattleActivity extends AppCompatActivity {
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

    private MainMechanics mainMechanics;

    public void initViews() {
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

        backgroundImage = findViewById(R.id.battle);

        backButton = findViewById(R.id.backButton);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        promptButton = findViewById(R.id.promptButton);
        promptView = findViewById(R.id.promptView);

        yourStunText = findViewById(R.id.yourStunText);
        enemyStunText = findViewById(R.id.enemyStunText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_battle);

        initViews();
        mainMechanics = new MainMechanics(this, backgroundImage, yourName, enemyName,
                yourHealth, enemyHealth, yourHealthText, enemyHealthText, yourImage, enemyImage,
                backButton, startButton, resetButton, promptButton, promptView, yourStunText,
                enemyStunText, yourPlayerLayout, enemyPlayerLayout);
        mainMechanics.startConfiguration(true);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(BattleActivity.this, MapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        yourPlayerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMechanics.showCharacterDetails(0, BattleActivity.this);
            }
        });

        enemyPlayerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMechanics.showCharacterDetails(1, BattleActivity.this);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BattleActivity.this, MapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMechanics.editButton();
            }
        });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMechanics.battleLogValidation(BattleActivity.this);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.battle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
