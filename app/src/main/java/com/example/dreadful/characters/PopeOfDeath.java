package com.example.dreadful.characters;

import android.util.Log;

import com.example.dreadful.R;
import com.example.dreadful.models.Character;

import java.util.Random;

public class PopeOfDeath extends Character {
    private Random random = new Random();
    private int imageView = R.drawable.character_pope_of_death;
    private int[] transformationView = {};

    public PopeOfDeath() {
        super("Pope of Death", "character_pope_of_death", "left", "titan", null,
                120000, 2888, 0, 0,
                new String[]{"Dark Bolt", "Spectral Judgement", "Reverse Prayer"},
                new int[]{0, 4, 6}, new int[]{0, 0, 0});
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
            case 2:
                skill2(hitter, target);
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

    @Override
    public void basicAttack(Character hitter, Character target) {
        //attack that is undodgeable

        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
    }

    private void skill1(Character hitter, Character target) {
        setAttack(getAttack() + 8450);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    private void skill2(Character hitter, Character target) {
        getHealOverTime().add(3500);
        getHealOverTimeValue().add(18);
    }
}
