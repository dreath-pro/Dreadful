package com.example.dreadful.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreadful.R;
import com.example.dreadful.characters.Carnant;
import com.example.dreadful.characters.DreadProphet;
import com.example.dreadful.characters.Dreath;
import com.example.dreadful.characters.Flamethrower;
import com.example.dreadful.characters.GodOfDeath;
import com.example.dreadful.characters.HellKnight;
import com.example.dreadful.characters.Jimhardcore;
import com.example.dreadful.characters.KumoNingyo;
import com.example.dreadful.characters.Michael;
import com.example.dreadful.characters.VoidReaper;
import com.example.dreadful.databases.MapDatabase;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.logics.MapListGetter;
import com.example.dreadful.logics.SetupCharacter;
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
    private ArrayList<Monster> sourceMonsters = new ArrayList<>();
    private SetupCharacter setupCharacter = new SetupCharacter();

    private void initViews() {
        classicButton = findViewById(R.id.classicButton);
        testButton = findViewById(R.id.testButton);
        wikiButton = findViewById(R.id.wikiButton);
        quitButton = findViewById(R.id.quitButton);
    }

    private boolean isMonsterUniqueId(ArrayList<Monster> monsterList) {
        StringBuilder matchingIds = new StringBuilder();
        boolean isUnique = true;

        for (int i = 0; i <= monsterList.size() - 1; i++) {
            for (int j = i + 1; j <= monsterList.size() - 1; j++) {
                if (monsterList.get(i).getUniqueId().equals(monsterList.get(j).getUniqueId())) {
                    isUnique = false;
                    matchingIds.append("\n").append(monsterList.get(i).getName()).append(" and ").append(monsterList.get(j).getName()).append(": ").append(monsterList.get(i).getUniqueId()).append("\n");
                }
            }
        }

        if (!isUnique) {
            Toast.makeText(this, "Non unique IDs:" + matchingIds, Toast.LENGTH_SHORT).show();
            Log.d("Non unique IDs:", matchingIds.toString());
        } else {
            Toast.makeText(this, "Everything is unique", Toast.LENGTH_SHORT).show();
        }

        return isUnique;
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

        sourceMonsters.clear();
        sourceMonsters.addAll(setupCharacter.getMonsterListing());

        if (!monsterDatabase.doesDataExist()) {
            Monster monster = new Flamethrower(this);
            monsterDatabase.addMonster(monster);

            isMonsterUniqueId(sourceMonsters);
        } else {
            ArrayList<Monster> finalMonster = new ArrayList<>();

            //automatically updates the monster names if there is changes
            for (Monster monster : sourceMonsters) {
                monsterDatabase.updateMonster(monster);
            }

            ArrayList<Monster> databaseMonster = new ArrayList<>(monsterDatabase.selectAll());

            // If data exists, check if there's a discrepancy in the number of monster
            if (monsterDatabase.monsterCount() != setupCharacter.getMonsterListing().size()) {
                if (isMonsterUniqueId(setupCharacter.getMonsterListing())) {
                    // Iterate through the updated list of maps
                    for (Monster thisListMonster : setupCharacter.getMonsterListing()) {
                        boolean found = false;

                        // Compare with existing monster in the database
                        for (Monster thisDatabaseMonster : databaseMonster) {

                            // Match monster by their unique identifier
                            if (thisListMonster.getUniqueId().equals(thisDatabaseMonster.getUniqueId())) {
                                found = true;
                                break;
                            }
                        }

                        // If no match is found, it's a new monster; add it to the final list
                        if (!found) {
                            finalMonster.add(thisListMonster);
                        }
                    }

                    // Clear the database and replace it with the updated monster list
                    monsterDatabase.deleteAllMonster();
                    for (Monster monster : finalMonster) {
                        monsterDatabase.addMonster(monster); // Add the new or updated monster to the database
                    }
                }
            }
        }

        // Check if there's no data in the database
        if (!mapDatabase.doesDataExist()) {
            // First-time setup: populate the database with all maps from the source
            ArrayList<Map> mapList = new ArrayList<>(mapListGetter.getMapList());

            if (mapListGetter.isIdUnique()) {
                for (Map map : mapList) {
                    mapDatabase.addMap(map); // Add each map to the database
                }
            }
        } else {
            // Get maps from both the database and the updated source
            ArrayList<Map> listMap = new ArrayList<>(mapListGetter.getMapList());
            ArrayList<Map> finalMap = new ArrayList<>();

            //updates the map name automatically if there is changes
            for (Map map : listMap) {
                mapDatabase.updateMap(map);
            }

            //this is the updated maps earlier and store in the variables to be use
            ArrayList<Map> databaseMap = new ArrayList<>(mapDatabase.selectAll());

            // If data exists, check if there's a discrepancy in the number of maps
            if (mapDatabase.mapCount() != mapListGetter.getMapList().size()) {

                if (mapListGetter.isIdUnique()) {
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
//                if (monsterDatabase.getMonsterCount() <= 2) {
                if (monsterDatabase.getMonsterCount() <= 0) {
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