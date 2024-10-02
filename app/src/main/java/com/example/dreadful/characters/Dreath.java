package com.example.dreadful.characters;

import android.util.Log;
import android.widget.Toast;

import com.example.dreadful.R;
import com.example.dreadful.models.Character;

import java.util.ArrayList;
import java.util.Random;

public class Dreath extends Character {
    private Random random = new Random();
    private int imageView = R.drawable.character_dreath;
    private int[] transformationView = {};
    private int[] dimension = {};

    public Dreath() {
        super("Dreath", "character_dreath", "right", "average", null,
                88070, 2580, 880, 0,
                new String[]{"Butcher", "Amputate", "Shock Gore", "Ravage Axe", "Carnage"},
                new int[]{0, 7, 3, 5, 5}, new int[]{0, 0, 0, 0, 0});
    }

    /**
     * the way he receive a hit, when his health drop 0, he will go to instant rage and reset his hp to max
     * and reduce the opponent's current health by 50%
     */
    @Override
    public void receiveHit(Character hitter, Character target) {
        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());

        if (getHealth() <= 0) {
            setHealth(getMaxHealth());

            int maxHealth = hitter.getHealth();
            int percentage = 50;
            int damage = (maxHealth * percentage) / 100;

            setAttack(damage);
            hitter.receiveHit(hitter, target);
            setAttack(getMaxAttack());
        }
    }

    /**
     * Override parent class' receiveTimeHp method so that his own unique receiveHit will still have effect
     * even if its direct attack
     */
    @Override
    public void receiveTimeHp(Character hitter, Character target) {
        ArrayList<Integer> tempHot = new ArrayList<>();
        ArrayList<Integer> tempHotValue = new ArrayList<>();

        for (int i = 0; i <= getHealOverTime().size() - 1; i++) {
            if (getHealOverTimeValue().get(i) > 0) {
                setHealth(getHealth() + getHealOverTime().get(i));
                getHealOverTimeValue().set(i, getHealOverTimeValue().get(i) - 1);

                tempHot.add(getHealOverTime().get(i));
                tempHotValue.add(getHealOverTimeValue().get(i));
            }
        }

        setHealOverTime(tempHot);
        setHealOverTimeValue(tempHotValue);


        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for (int i = 0; i <= getDamageOverTime().size() - 1; i++) {
            if (getDamageOverTimeValue().get(i) > 0) {
                setHealth(getHealth() - getDamageOverTime().get(i));

                if (getHealth() <= 0) {
                    setHealth(getMaxHealth());

                    int maxHealth = hitter.getHealth();
                    int percentage = 50;
                    int damage = (maxHealth * percentage) / 100;

                    setAttack(damage);
                    hitter.receiveHit(hitter, target);
                    setAttack(getMaxAttack());
                }
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

    //attack that will ignore defense
    @Override
    public void basicAttack(Character hitter, Character target) {
        target.setDefense(0);
        target.receiveHit(hitter, target);
        target.setDefense(target.getMaxDefense());
    }

    //prevent enemy from using all skills
    private void skill1(Character hitter, Character target) {
        for (int i = 1; i <= target.getMaxSkillCooldowns().length - 1; i++) {
            target.getSkillCooldowns()[i] += 5;
        }

        setAttack(getAttack() + 6500);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //enemy cannot dodge all incoming attacks
    private void skill2(Character hitter, Character target) {
        target.setDodge(0);
        setAttack(getAttack() + 5000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
        target.setDodge(target.getMaxDodge());
    }

    //high burst
    private void skill3(Character hitter, Character target) {
        setAttack(getAttack() + 8870);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //heal and increase damage
    private void skill4(Character hitter, Character target) {
        setHealth(getHealth() + 10000);
        setAttack(getAttack() + 11000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }
}
