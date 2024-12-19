package com.example.dreadful.logics;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dreadful.R;
import com.example.dreadful.characters.DreadProphet;
import com.example.dreadful.characters.Flamethrower;
import com.example.dreadful.models.Map;
import com.example.dreadful.models.Monster;

import java.util.ArrayList;

public class MapListGetter {
    private ArrayList<Map> maps = new ArrayList<>();
    private Context context;

    public MapListGetter(Context context) {
        this.context = context;

        maps.add(new Map("0001-FAC", "Facility", 1, R.drawable.map_facility, 0, null));
        maps.add(new Map("0002-GRV", "Shadowgrove", 1, R.drawable.map_shadowgrove, 0, null));
        maps.add(new Map("0003-BDS", "Badlands", 1, R.drawable.map_badland, 0, null));

        String shadowgroveName = maps.get(1).getName().toLowerCase(); // Get Shadowgrove name
        maps.add(new Map("0004-GHT", "Ghost Town", 0, R.drawable.map_ghost_town, 0, "Discover all the " + shadowgroveName + " monsters"));

        String ghostTownName = maps.get(3).getName().toLowerCase(); // Get Ghost Town name
        Monster monster = new DreadProphet(context);
        maps.add(new Map("0005-ABY", "Abyss", 0, R.drawable.map_abyss, 0, "Defeat the strongest aberrant " + monster.getName() +" in " + ghostTownName));

        String abyssName = maps.get(4).getName().toLowerCase(); // Get Abyss name
        maps.add(new Map("0006-CLS", "Celestial", 0, R.drawable.map_celestial, 0, "Destroy the final aberrant in " + abyssName));
    }

    public boolean isIdUnique() {
        StringBuilder matchingIds = new StringBuilder();
        boolean isUnique = true;

        for (int i = 0; i <= maps.size() - 1; i++) {
            for (int j = i + 1; j <= maps.size() - 1; j++) {
                if (maps.get(i).getUniqueId().equals(maps.get(j).getUniqueId())) {
                    isUnique = false;
                    matchingIds.append("\n").append(maps.get(i).getName()).append(" and ").append(maps.get(j).getName()).append(": ").append(maps.get(i).getUniqueId()).append("\n");
                }
            }
        }

        if (!isUnique) {
            Toast.makeText(context, "Non unique IDs:" + matchingIds, Toast.LENGTH_SHORT).show();
            Log.d("Non unique IDs:", matchingIds.toString());
        }else
        {
            Toast.makeText(context, "Everything is unique", Toast.LENGTH_SHORT).show();
        }

        return isUnique;
    }

    public ArrayList<Map> getMapList() {
        return maps;
    }
}
