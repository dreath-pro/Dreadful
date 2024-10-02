package com.example.dreadful.characters;

import android.util.Log;

import com.example.dreadful.R;
import com.example.dreadful.models.Character;

import java.util.Random;

public class PopeOfDeath extends Character{
    private Random random = new Random();
    private int imageView = R.drawable.character_pope_of_death;
    private int[] transformationView = {};

    public PopeOfDeath() {
        super("Pope of Death", "character_pope_of_death", "left", "titan", null,
                120000, 2888, 0, 0,
                new String[]{"Dark Bolt", "Abyssal Hym"});
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
        }
    }

    @Override
    public void basicAttack(Character hitter, Character target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
    }

    private void skill1(Character hitter, Character target) {
        setAttack(getAttack() + 8000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }
}
