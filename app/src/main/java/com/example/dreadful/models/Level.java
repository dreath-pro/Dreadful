package com.example.dreadful.models;

import java.util.ArrayList;

public class Level {
    private ArrayList<String> levelName;
    private ArrayList<String> warningMessage;

    public Level() {
        levelName = new ArrayList<>();
        warningMessage = new ArrayList<>();

        levelName.add("Vessel");
        levelName.add("Predator");
        levelName.add("Apex");
        levelName.add("Abyssal");
        levelName.add("Nexus");
    }

    public int getLevelCount() {
        return levelName.size();
    }

    public String getLevelName(int level) {
        return levelName.get(level);
    }

    public String getWarningMessage(int level) {
        String result = "";
        switch (level) {
            case 0:
                result = "A dangerous " + getLevelName(level) + "-level aberrant has been spotted. Proceed with caution.";
                break;
            case 1:
                result = "An aggressive " + getLevelName(level) + "-level aberrant is approaching. Proceed with caution.";
                break;
            case 2:
                result = "A powerful and dangerous " + getLevelName(level) + "-level aberrant is nearby. Stay alert.";
                break;
            case 3:
                result = "This " + getLevelName(level) + "-level aberrant from the depths of hell is in sight. This is a critical threat. Proceed with extreme caution.";
                break;
            case 4:
                result = "The end of the world is near. A " + getLevelName(level) + "-level aberrant is right in front of you. Evacuate immediately!";
                break;
        }

        return result;
    }
}
