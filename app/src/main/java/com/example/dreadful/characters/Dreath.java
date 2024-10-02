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

    public Dreath() {
        super("Dreath", "character_dreath", "left", "average", null,
                88070, 2580, 880, 0,
                new String[]{"Butcher", "Amputate", "Doom", "Hand Gun Piercing", "Carnage"});
    }

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

    public void useRandomAttack(Character hitter, Character target) {
        int skillIndex = random.nextInt(getSkillNames().length + 1);

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
    }

    @Override
    public void basicAttack(Character hitter, Character target) {
        //attack that will ignore defense

        target.setDefense(0);
        target.receiveHit(hitter, target);
        target.setDefense(target.getMaxDefense());
    }

    private void skill1(Character hitter, Character target) {
        setAttack(getAttack() + 8000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    private void skill2(Character hitter, Character target) {
        setAttack(getAttack() + 8000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    private void skill3(Character hitter, Character target) {
        setAttack(getAttack() + 8000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    private void skill4(Character hitter, Character target) {
        setAttack(getAttack() + 8000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }
}
