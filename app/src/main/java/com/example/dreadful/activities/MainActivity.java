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
import com.example.dreadful.logics.MapListGetter;
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
    private MapListGetter mapListGetter;

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
        mapListGetter = new MapListGetter(this);

        if (!monsterDatabase.doesDataExist()) {
            Monster monster = new Flamethrower(this);
            monsterDatabase.addMonster(monster.getName());
        }

        // Check if there's no data in the database
        if (!mapDatabase.doesDataExist()) {

            if (mapListGetter.isIdUnique()) {
                // First-time setup: populate the database with all maps from the source
                ArrayList<Map> mapList = new ArrayList<>(mapListGetter.getMap());
                for (Map map : mapList) {
                    mapDatabase.addMap(map); // Add each map to the database
                }
            }
        } else {

            // If data exists, check if there's a discrepancy in the number of maps
            if (mapDatabase.mapCount() != mapListGetter.getMap().size()) {

                if (mapListGetter.isIdUnique()) {
                    // Get maps from both the database and the updated source
                    ArrayList<Map> databaseMap = new ArrayList<>(mapDatabase.selectAll());
                    ArrayList<Map> listMap = new ArrayList<>(mapListGetter.getMap());
                    ArrayList<Map> finalMap = new ArrayList<>();

                    // Iterate through the updated list of maps
                    for (Map thisListMap : listMap) {
                        boolean found = false;

                        // Compare with existing maps in the database
                        for (Map thisDatabaseMap : databaseMap) {

                            // Match maps by their unique identifier
                            if (thisListMap.getUniqueId().equals(thisDatabaseMap.getUniqueId())) {

                                // Merge attributes: retain database attributes in the updated map
                                Map retainedMap = new Map(thisListMap.getUniqueId(), thisListMap.getName(), thisDatabaseMap.getStatus(), 0, thisDatabaseMap.getExplorePercentage(), null);
                                finalMap.add(retainedMap);  // Add the updated map version
                                found = true;
                                break;
                            }
                        }

                        // If no match is found, it's a new map; add it to the final list
                        if (!found) {
                            finalMap.add(thisListMap);
                        }
                    }

                    // Clear the database and replace it with the updated map list
                    mapDatabase.deleteAllMap();
                    for (Map map : finalMap) {
                        mapDatabase.addMap(map); // Add the new or updated map to the database
                    }
                }
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
                if (monsterDatabase.getMonsterCount() <= 2) {
                    Toast.makeText(MainActivity.this, "Hunt at least two monster first!", Toast.LENGTH_SHORT).show();
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