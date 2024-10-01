package com.example.dreadful.characters;

import com.example.dreadful.R;
import com.example.dreadful.models.Character;

import java.util.Random;

public class PopeOfDeath extends Character{
    private Random random = new Random();
    private int imageView = R.drawable.character_pope_of_death;
    private int[] transformationView = {};

    public PopeOfDeath() {
        super("Pope of Death", "character_pope_of_death", "left", "titan", null,
                120000, 2888, 0, 0);
    }

    @Override
    public void basicAttack(Character hitter, Character target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
    }
}
