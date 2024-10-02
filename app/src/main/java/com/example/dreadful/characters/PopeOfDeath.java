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
                new String[]{"Dark Bolt", "Sixfold Judgement", "Reverse Prayer", "Sinful Retribution", "Spectral Choir"},
                new int[]{0, 4, 7, 4, 6}, new int[]{0, 0, 0, 0, 0});
    }

    /**
     * everytime an attacker hits pope of death, they will be mark with sin, and every attack it will add
     * 10 mark of sin
     */
    @Override
    public void receiveHit(Character hitter, Character target) {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return;

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());

        boolean withSin = false;
        int sinIndex = 0;
        for (int i = 0; i <= hitter.getDebuffs().size() - 1; i++) {
            if (hitter.getDebuffs().get(i).equals("Mark of Sin")) {
                withSin = true;
                sinIndex = i;
            }
        }

        if(!withSin)
        {
            hitter.getDebuffs().add("Mark of Sin");
            hitter.getDebuffsValue().add(10);
        }else
        {
            hitter.getDebuffsValue().set(sinIndex, hitter.getDebuffsValue().get(sinIndex) + 10);
        }
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
            case 3:
                skill3(hitter, target);
                break;
            case 4:
                skill4(hitter, target);
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

    //enemy healing will be reverse and convert to damage
    private void skill2(Character hitter, Character target) {
        int allHeal = 0;
        for(int i = 0; i <= target.getHealOverTime().size() - 1; i++)
        {
            allHeal += target.getHealOverTime().get(i);
        }

        target.getDamageOverTime().add(allHeal);
        target.getDamageOverTimeValue().add(15);
    }

    //retribution for sinner hitter/attacker, base on the value of the "mark of sin"
    //reduce 50% of attacker current health
    private void skill3(Character hitter, Character target)
    {
        target.receiveHit(hitter, target);

        for(int i = 0; i <= target.getDebuffs().size() - 1; i++)
        {
            if(target.getDebuffs().get(i).equals("Mark of Sin") && target.getDebuffsValue().get(i) >= 50)
            {
                int maxHealth = target.getHealth();
                int percentage = target.getDebuffsValue().get(i);
                int damage = (maxHealth * percentage) / 100;

                setAttack(damage);
                target.receiveHit(hitter, target);
                setAttack(getMaxAttack());
            }
        }
    }

    //heal over time for a short turn, base on the value of the "mark of sin"
    private void skill4(Character hitter, Character target)
    {
        for(int i = 0; i <= target.getDebuffs().size() - 1; i++)
        {
            if(target.getDebuffs().get(i).equals("Mark of Sin") && target.getDebuffsValue().get(i) >= 50)
            {
                int maxHealth = 1200;
                int percentage = target.getDebuffsValue().get(i);
                int heal = (maxHealth * percentage) / 100;

                getDamageOverTime().add(heal);
                getDamageOverTimeValue().add(9);
            }
        }
    }
}
