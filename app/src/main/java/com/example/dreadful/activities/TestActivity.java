package com.example.dreadful.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dreadful.R;
import com.example.dreadful.adapters.ViewPrompt;
import com.example.dreadful.adapters.ViewSkill;
import com.example.dreadful.adapters.ViewStatus;
import com.example.dreadful.logics.GameMechanics;
import com.example.dreadful.logics.MainMechanics;
import com.example.dreadful.logics.SetupCharacter;
import com.example.dreadful.logics.SlowSmoothScroller;
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

        backgroundImage = findViewById(R.id.test);

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
        setContentView(R.layout.activity_test);

        initViews();
        mainMechanics = new MainMechanics(this, backgroundImage, yourName, enemyName,
                yourHealth, enemyHealth, yourHealthText, enemyHealthText, yourImage, enemyImage,
                backButton, startButton, resetButton, promptButton, promptView, yourStunText,
                enemyStunText, yourPlayerLayout, enemyPlayerLayout);
        mainMechanics.startConfiguration(true);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        yourPlayerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMechanics.showCharacterDetails(yourPlayer, TestActivity.this);
            }
        });

        enemyPlayerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMechanics.showCharacterDetails(enemyPlayer, TestActivity.this);
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
                mainMechanics.startConfiguration(true);
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
                mainMechanics.battleLogValidation(TestActivity.this);
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
}
