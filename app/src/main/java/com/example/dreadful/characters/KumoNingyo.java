package com.example.dreadful.characters;

import com.example.dreadful.R;
import com.example.dreadful.models.Character;

import java.util.ArrayList;
import java.util.Random;

public class KumoNingyo extends Character {
    private Random random = new Random();
    private int imageView = R.drawable.character_kumo_ningyo;
    private int[] transformationView = {};
    private int[] dimension = {};

    public KumoNingyo() {
        super("Kumo Ningyō", "character_kumo_ningyo", "left", "titan", null,
                20000, 180, 0, 20,
                new String[]{"Doku Kizu", "Shinobi Ashi Keri", "Tsukurogami", "Kakure Kage"},
                new int[]{0, 3, 3, 3}, new int[]{0, 0, 0, 0});
    }

    /**
     * everytime an attacker hit, kumo ningyo will be mark with lost limb buff and healing effects
     * will increase significantly base on the lost limbs buff
     */
    @Override
    public void receiveHit(Character hitter, Character target) {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return;

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());

        receiveBuffDebuff(target, "Lost Limbs", 1);
        setDodge(getMaxDodge());
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

    //every attack has poison effect that last 10 turns
    @Override
    public void basicAttack(Character hitter, Character target) {
        target.receiveHit(hitter, target);
        target.getDamageOverTime().add(10);
        target.getDamageOverTimeValue().add(30);
    }

    //uses base damage 3 times and same poison effect
    private void skill1(Character hitter, Character target) {
        setAttack(getAttack() * 3);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        target.getDamageOverTime().add(10 * 3);
        target.getDamageOverTimeValue().add(30 * 3);
    }

    //recovers 1000 health and multiply by the value of the lost limbs buff
    private void skill2(Character hitter, Character target) {
        if(!hasBuffDebuff(hitter, "Lost Limbs", 1).isEmpty())
        {
            int index = Integer.parseInt(hasBuffDebuff(hitter, "Lost Limbs", 1));
            setHealth(getHealth() + (1000 * getBuffDebuffValue().get(index)));
        }
    }

    //simple basic attack and add 60% dodge for the hitter's next attack
    private void skill3(Character hitter, Character target) {
        target.receiveHit(hitter, target);
        target.getDamageOverTime().add(10);
        target.getDamageOverTimeValue().add(30);

        setDodge(getDodge() + 60);
    }
}
