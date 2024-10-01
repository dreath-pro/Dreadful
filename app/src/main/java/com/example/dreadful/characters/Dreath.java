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
                88070, 2580, 880, 0);
    }

    @Override
    public void receiveHit(Character hitter, Character target)
    {
        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());

        if(getHealth() <= 0)
        {
            setHealth(getMaxHealth());

            int maxHealth = hitter.getHealth();
            int percentage = 50;
            int damage = (maxHealth * percentage) / 100;

            setAttack(damage);
            hitter.receiveHit(hitter, target);
            setAttack(getMaxAttack());
        }
    }

    @Override
    public void basicAttack(Character hitter, Character target) {
        target.setDefense(0);
        target.receiveHit(hitter, target);
        target.setDefense(target.getMaxDefense());
    }
}
