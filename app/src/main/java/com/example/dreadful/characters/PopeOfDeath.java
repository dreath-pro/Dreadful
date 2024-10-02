package com.example.dreadful.characters;

import android.util.Log;

import com.example.dreadful.R;
import com.example.dreadful.models.Character;

import java.util.ArrayList;
import java.util.Random;

public class PopeOfDeath extends Character {
    private Random random = new Random();
    private int imageView = R.drawable.character_pope_of_death;
    private int[] transformationView = {};
    private int[] dimension = {};

    public PopeOfDeath() {
        super("Pope of Death", "character_pope_of_death", "left", "titan", null,
                120000, 2888, 0, 0,
                new String[]{"Dark Bolt", "Spectral Judgement", "Reverse Prayer"},
                new int[]{0, 4, 6}, new int[]{0, 0, 0});
    }

    /**
     * the way he is receiving damage is different from other characters
     * the damage overtime received from the opponent will be convert into his own heal over time
     * the heal overtime he receive will be useless and be ignore, only damage over time is his way of healing
     */
    @Override
    public void receiveTimeHp(Character hitter, Character target) {
        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for (int i = 0; i <= getDamageOverTime().size() - 1; i++) {
            if (getDamageOverTimeValue().get(i) > 0) {
                setHealth(getHealth() + getDamageOverTime().get(i));
                getDamageOverTimeValue().set(i, getDamageOverTimeValue().get(i) - 1);

                tempDot.add(getDamageOverTime().get(i));
                tempDotValue.add(getDamageOverTimeValue().get(i));
            }
        }

        setDamageOverTime(tempDot);
        setDamageOverTimeValue(tempDotValue);
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

    //this attack is undodgeable but can be reduce by opponent's defense
    @Override
    public void basicAttack(Character hitter, Character target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
    }

    //simple burst attack
    private void skill1(Character hitter, Character target) {
        setAttack(getAttack() + 8450);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //coming soon, enemy healing will be reverse and convert to damage
    private void skill2(Character hitter, Character target) {
        getDamageOverTime().add(3500);
        getDamageOverTimeValue().add(9);
    }
}
