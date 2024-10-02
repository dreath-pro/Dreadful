package com.example.dreadful.characters;

import com.example.dreadful.R;
import com.example.dreadful.models.Character;

import java.util.ArrayList;
import java.util.Random;

public class KumoNingyo extends Character{
    private Random random = new Random();
    private int imageView = R.drawable.character_kumo_ningyo;
    private int[] transformationView = {};
    private int[] dimension = {};

    public KumoNingyo() {
        super("Kumo Ningyō", "character_kumo_ningyo", "left", "titan", null,
                20000, 180, 0, 20,
                new String[]{"Pierce", "Limb Kick"},
                new int[]{0, 3}, new int[]{0, 0});
    }

    public void useRandomAttack(Character hitter, Character target) {
        int skillIndex = random.nextInt(getSkillNames().length);

        while (getSkillCooldowns()[skillIndex] > 0) {
            skillIndex = random.nextInt(getSkillNames().length);
        }

        switch (skillIndex) {
            case 0:
                basicAttack(hitter, target);
                break;
            case 1:
                skill1(hitter, target);
                break;
        }

        for (int i = 0; i <= getMaxSkillCooldowns().length - 1; i++) {
            if (getSkillCooldowns()[i] > 0) {
                getSkillCooldowns()[i]--;
                if (getSkillCooldowns()[i] <= 0) {
                    getSkillCooldowns()[i] = 0;
                }
            }
        }
        getSkillCooldowns()[skillIndex] = getMaxSkillCooldowns()[skillIndex];
    }

    //uses base damage 3 times
    private void skill1(Character hitter, Character target) {
        setAttack(getAttack() * 3);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }
}
