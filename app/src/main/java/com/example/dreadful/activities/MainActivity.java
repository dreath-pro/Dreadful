package com.example.dreadful.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreadful.R;
import com.example.dreadful.characters.Flamethrower;
import com.example.dreadful.databases.MapDatabase;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.models.Map;
import com.example.dreadful.models.Monster;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //person with spiky armor covered with dark liquid, hd detailed, dark cartoon 2d, horror theme, white background, facing right, standing full view, red, black, dark-gray, crimson-red
    //long hair man riding a armored horse with long sword, hd detailed, dark cartoon 2d, horror theme, white background, facing right, standing full view, red, black, gray, white, dark-red

    //forest with plenty of creepy angel mossy stone statue, 2d landscape, hd detailed, dark cartoon 2d, horror theme, blur, unfocused

    //two swords clashing, simple icon, digital art, dark horror theme, vibrant shading, white background, red, crimson-red, black, dark-red
    //immobilize, minimalist icon, dark horror theme, vibrant shading, red, crimson-red, dark-red, black
    //awakened monster triangle warning sign, minimalist icon, dark horror theme, vibrant shading, yellow-orange, black
    private Button classicButton, testButton, wikiButton, quitButton;
    private MonsterDatabase monsterDatabase;
    private MapDatabase mapDatabase;

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
        monsterDatabase = new MonsterDatabase(this);
        mapDatabase = new MapDatabase(this);

        if (!monsterDatabase.doesDataExist()) {
            Monster monster = new Flamethrower(this);
            monsterDatabase.addMonster(monster.getName());
        }

        if (!mapDatabase.doesDataExist()) {
            ArrayList<Map> mapList = new ArrayList<>();
            mapList.add(new Map("Facility", 1, 0, 0, null));
            mapList.add(new Map("Shadowgrove", 1, 0, 0, null));
            mapList.add(new Map("Badlands", 1, 0, 0, null));
            mapList.add(new Map("Ghost Town", 0, 0, 0, null));
            mapList.add(new Map("Abyss", 0, 0, 0, null));
            mapList.add(new Map("Celestial", 0, 0, 0, null));

            for (Map map : mapList) {
                mapDatabase.addMap(map);
            }
        }

        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monsterDatabase.getMonsterCount() <= 1) {
                    Toast.makeText(MainActivity.this, "Hunt at least one monster first!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
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