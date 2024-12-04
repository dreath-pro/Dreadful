package com.example.dreadful.logics;

import com.example.dreadful.R;
import com.example.dreadful.models.Map;

import java.util.ArrayList;

public class MapListGetter {
    private ArrayList<Map> map = new ArrayList<>();

    public MapListGetter() {
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

    public ArrayList<Map> getMap() {
        return map;
    }
}
