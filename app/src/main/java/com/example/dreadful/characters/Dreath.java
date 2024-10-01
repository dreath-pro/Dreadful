package com.example.dreadful.characters;

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
        int temporaryAttack = hitter.getAttack();

        hitter.setAttack(getDefense());
        setHealth(getHealth() - hitter.getAttack());

        hitter.setAttack(temporaryAttack);

        if(getHealth() <= 0)
        {
            setHealth(88070);

            int maxHealth = hitter.getHealth();
            int percentage = 50;
            int newHealth = (maxHealth * percentage) / 100;
            hitter.setHealth(newHealth);
        }
    }

    @Override
    public void basicAttack(Character hitter, Character target) {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= target.getDodge())
            return;

        target.setHealth(target.getHealth() - getAttack());
    }
}
