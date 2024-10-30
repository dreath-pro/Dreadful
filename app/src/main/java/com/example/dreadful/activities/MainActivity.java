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
import com.example.dreadful.adapters.ViewSkill;
import com.example.dreadful.adapters.ViewStatus;
import com.example.dreadful.logics.GameMechanics;
import com.example.dreadful.models.Player;
import com.example.dreadful.logics.SetupCharacter;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //person with spiky armor covered with dark liquid, hd detailed, dark cartoon 2d, horror theme, white background, facing right, standing full view, red, black, dark-gray, crimson-red
    //long hair man riding a armored horse with long sword, hd detailed, dark cartoon 2d, horror theme, white background, facing right, standing full view, red, black, gray, white, dark-red

    //mossy old ruins building, landscape, hd detailed, dark cartoon 2d, horror theme, blur, unfocused
    //altar of cathedral, landscape, hd detailed, dark cartoon 2d, horror theme, blur, unfocused, red, crimson-red, black

    //two swords clashing, simple icon, digital art, dark horror theme, vibrant shading, white background, red, crimson-red, black, dark-red
    //immobilize, minimalist icon, dark horror theme, vibrant shading, red, crimson-red, dark-red, black
    private Button classicButton, testButton, wikiButton, quitButton;

    private void initViews() {
        classicButton = findViewById(R.id.classicButton);
        testButton = findViewById(R.id.testButton);
        wikiButton = findViewById(R.id.wikiButton);
        quitButton = findViewById(R.id.quitButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();

        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        wikiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}