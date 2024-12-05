package com.example.dreadful.logics;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dreadful.R;
import com.example.dreadful.models.Map;

import java.util.ArrayList;

public class MapListGetter {
    private ArrayList<Map> map = new ArrayList<>();
    private Context context;

    public MapListGetter(Context context) {
        this.context = context;

        map.add(new Map("0001-FAC", "Facility", 1, R.drawable.map_facility, 0, null));
        map.add(new Map("0002-GRV", "Shadowgrove", 1, R.drawable.map_shadowgrove, 0, null));
        map.add(new Map("0003-BDS", "Badlands", 1, R.drawable.map_badland, 0, null));

        String shadowgroveName = map.get(1).getName().toLowerCase(); // Get Shadowgrove name
        map.add(new Map("0004-GHT", "Ghost Town", 0, R.drawable.map_ghost_town, 0, "Discover all the " + shadowgroveName + " monsters"));

        String ghostTownName = map.get(3).getName().toLowerCase(); // Get Ghost Town name
        map.add(new Map("0005-ABY", "Abyss", 0, R.drawable.map_abyss, 0, "Defeat the strongest aberrant in " + ghostTownName));

        String abyssName = map.get(4).getName().toLowerCase(); // Get Abyss name
        map.add(new Map("0006-CLS", "Celestial", 0, R.drawable.map_celestial, 0, "Destroy the final aberrant in " + abyssName));
    }

    public boolean isIdUnique() {
        StringBuilder matchingIds = new StringBuilder();
        boolean isUnique = true;

        for (int i = 0; i <= map.size(); i++) {
            for (int j = 1; j <= map.size(); j++) {
                if (map.get(i).getUniqueId().equals(map.get(j).getUniqueId())) {
                    isUnique = false;
                    matchingIds.append("\n").append(map.get(i).getName()).append(" and ").append(map.get(j).getName()).append(": ").append(map.get(i).getUniqueId()).append("\n");
                }
            }
        }

        if (!isUnique) {
            Toast.makeText(context, "Non unique IDs:" + matchingIds, Toast.LENGTH_SHORT).show();
            Log.d("Non unique IDs:", matchingIds.toString());
        }

        return isUnique;
    }

    public ArrayList<Map> getMap() {
        return map;
    }
}
