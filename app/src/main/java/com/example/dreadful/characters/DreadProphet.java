package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class DreadProphet extends Player {
    private Random random = new Random();
    private Animation shakeAnimation;
    private ImageView yourImage;
    private Prompt prompt;

    public DreadProphet(Context context, ImageView yourImage, Prompt prompt) {
        super(context, yourImage, "Dread Prophet", R.drawable.character_dread_prophet, "left", 210,
                null, null,
                120000, 2888, 0, 0,
                new String[]{"Dark Bolt", "Sixfold Judgement", "Reverse Prayer", "Sinful Retribution", "Spectral Choir"},
                new int[]{0, 4, 7, 4, 6}, new int[]{0, 0, 0, 0, 0}, prompt);

        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        this.prompt = prompt;
    }

    /**
     * everytime an attacker hits dread prophet, they will be mark with sin, and every attack it will add
     * 10 mark of sin
     */
    public void receiveHit(Player hitter, Player target) {
        receiveHitLogic(hitter, target);
        receiveStatus(hitter, "Mark of Sin", 10);
    }

    /**
     * the way he is receiving damage is different from other characters
     * the damage overtime received from the opponent will be convert into his own heal over time
     * the heal overtime he receive will be useless and be ignore, only damage over time is his way of healing
     */
    public void receiveTimeEffect(Player hitter, Player target) {
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

        runTimeHeal();
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;
        int skillIndex = random.nextInt(getSkillNames().length);

        while (getSkillCooldowns()[skillIndex] > 0) {
            skillIndex = random.nextInt(getSkillNames().length);
        }

        skillName = getSkillNames()[skillIndex];
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
        return skillName;
    }

    //this attack is undodgeable but can be reduce by opponent's defense
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
    }

    //simple burst attack
    private void skill1(Player hitter, Player target) {
        setAttack(getAttack() + 8450);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //enemy healing will be reverse and convert to damage
    private void skill2(Player hitter, Player target) {
        int allHeal = 0;
        for (int i = 0; i <= target.getHealOverTime().size() - 1; i++) {
            allHeal += target.getHealOverTime().get(i);
        }

        target.getHealOverTime().clear();
        target.getHealOverTimeValue().clear();

        target.getDamageOverTime().add(allHeal);
        target.getDamageOverTime().add(450);
        target.getDamageOverTimeValue().add(15);
        target.getDamageOverTimeValue().add(15);
    }

    //retribution for sinner hitter/attacker, base on the value of the "mark of sin"
    //reduce 50% of attacker current health
    private void skill3(Player hitter, Player target) {
        target.receiveHit(hitter, target);

        if (!hasStatus(target, "Mark of Sin", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(target, "Mark of Sin", 50));
            int maxHealth = target.getHealth();
            int percentage = target.getStatusValue().get(index);
            int damage = (maxHealth * percentage) / 100;

            setAttack(damage);
            target.receiveHit(hitter, target);
            setAttack(getMaxAttack());
        }
    }

    //heal over time for a short turn, base on the value of the "mark of sin"
    private void skill4(Player hitter, Player target) {
        if (!hasStatus(target, "Mark of Sin", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(target, "Mark of Sin", 50));
            int maxHealth = 2100;
            int percentage = target.getStatusValue().get(index);
            int heal = (maxHealth * percentage) / 100;

            getDamageOverTime().add(heal);
            getDamageOverTimeValue().add(9);
        }else
        {
            getDamageOverTime().add(1800);
            getDamageOverTimeValue().add(9);
        }
    }
}
