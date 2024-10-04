package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;

import java.util.ArrayList;
import java.util.Random;

public class VoidReaper extends Player {
    private Random random = new Random();
    private Animation shakeAnimation;
    private ImageView yourImage;

    public VoidReaper(Context context, ImageView yourImage) {
        super(context, yourImage, "Void Reaper", R.drawable.character_void_reaper, "left", 150,
                new int[]{R.drawable.character_void_reaper_2},
                new int[]{R.drawable.background_void_1},
                60000, 580, 0, 0,
                new String[]{"Slash", "The Void", "Dimension Shift", "Reverse Time", "Forward Time"},
                new int[]{0, 7, 3, 5, 5}, new int[]{0, 0, 0, 0, 0});

        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    /**
     * the way he receive a hit, when his health drop 0, he will go to instant rage and increase his hp to 35k
     * and the opponent will receive a 9000 hit and can not be dodge
     */
    @Override
    public void receiveHit(Player hitter, Player target) {
        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());
        yourImage.startAnimation(shakeAnimation);

        receiveStatus(target, "Rage", 10);
        if (!hasStatus(target, "Rage", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(target, "Rage", 50));
            if (getHealth() <= 0) {
                setHealth(35700);
                setAttack(9000);
                hitter.setDodge(0);

                hitter.receiveHit(hitter, target);

                hitter.setDodge(hitter.getMaxDodge());
                setAttack(getMaxAttack());
                getStatusValue().set(index, getStatusValue().get(index) - 50);
            }
        }
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

    //attack that will ignore defense
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.setDefense(0);
        target.receiveHit(hitter, target);
        target.setDefense(target.getMaxDefense());
    }

    //prevent enemy from using all skills
    private void skill1(Player hitter, Player target) {
        for (int i = 1; i <= target.getMaxSkillCooldowns().length - 1; i++) {
            target.getSkillCooldowns()[i] += 5;
        }

        setAttack(getAttack() + 6500);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //enemy cannot dodge all incoming attacks
    private void skill2(Player hitter, Player target) {
        target.setDodge(0);
        setAttack(getAttack() + 5000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
        target.setDodge(target.getMaxDodge());
    }

    //high burst
    private void skill3(Player hitter, Player target) {
        setAttack(getAttack() + 8870);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //heal and increase damage
    private void skill4(Player hitter, Player target) {
        setHealth(getHealth() + 10000);
        setAttack(getAttack() + 11000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }
}
