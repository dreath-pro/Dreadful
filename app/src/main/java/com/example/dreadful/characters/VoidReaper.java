package com.example.dreadful.characters;

import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.logics.ResizeImage;
import com.example.dreadful.models.Player;

import java.util.ArrayList;
import java.util.Random;

public class VoidReaper extends Player {
    private Random random = new Random();
    private Animation shakeAnimation;
    private ImageView yourImage;
    private ConstraintLayout backgroundImage;
    private int[] backgroundList;
    private int selectedBackground;
    private ResizeImage resizeImage;
    private int voidTime = 0;

    public VoidReaper(Context context, ImageView yourImage, ConstraintLayout backgroundImage, int[] backgroundList, int selectedBackground) {
        super(context, yourImage, "Void Reaper", R.drawable.character_void_reaper, "left", 150,
                new int[]{R.drawable.character_void_reaper_2},
                new int[]{R.drawable.background_void_1},
                60000, 1800, 500, 15,
                new String[]{"Chrono Reap", "The Void", "Dimension Shift"},
                new int[]{0, 5, 5}, new int[]{0, 0, 0});

        this.resizeImage = new ResizeImage(context);
        this.yourImage = yourImage;
        this.backgroundImage = backgroundImage;
        this.backgroundList = backgroundList;
        this.selectedBackground = selectedBackground;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    /**
     * if the attacker hits, the void reaper will be mark with time passed status by 1 and it will stack with
     * each hits
     */
    @Override
    public void receiveHit(Player hitter, Player target) {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return;

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());
        yourImage.startAnimation(shakeAnimation);

        receiveStatus(target, "Time Passed", 1);
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        voidTime--;
        if (voidTime <= 0) {
            backgroundImage.setBackgroundResource(backgroundList[selectedBackground]);
            yourImage.setImageResource(getImage());
            resizeImage.scale(yourImage, getSize());
            voidTime = 0;

            if (!hasStatus(target, "Endless Void", 20).isEmpty()) {
                int index = Integer.parseInt(hasStatus(target, "Endless Void", 20));
                getStatusValue().remove(index);
                getStatus().remove(index);
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

    private void reduceCoolDown(Player hitter, Player target) {
        if (!hasStatus(hitter, "The Void", 1).isEmpty()) {
            for (int i = 1; i <= getMaxSkillCooldowns().length - 1; i++) {
                getSkillCooldowns()[i] -= 1;
                if (getSkillCooldowns()[i] <= 0) {
                    getSkillCooldowns()[i] = 0;
                }
            }
        }

        if (!hasStatus(target, "Endless Void", 20).isEmpty()) {
            for (int i = 1; i <= getMaxSkillCooldowns().length - 1; i++) {
                getSkillCooldowns()[i] = 0;
            }
        }
    }

    //if there is the void mark, the void reaper reduces his cooldown with each attack he deals
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
        reduceCoolDown(hitter, target);
    }

    //stuns the enemy and burst them and applies "the void" status to the target
    private void skill1(Player hitter, Player target) {
        hitter.setAttack(8500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());
        receiveStatus(target, "The Void", 1);
        target.setStun(3);
    }

    //the enemy will be teleported to a different dimension and void reaper will transform, after that
    //he will burst the target and resets all skill cooldown every attack
    private void skill2(Player hitter, Player target) {
        backgroundImage.setBackgroundResource(hitter.getDimension()[random.nextInt(hitter.getDimension().length)]);
        yourImage.setImageResource(hitter.getTransformation()[0]);
        resizeImage.scale(yourImage, 185);
        voidTime = 9;

        hitter.setAttack(12500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        receiveStatus(hitter, "Endless Void", 20);
        reduceCoolDown(hitter, target);
    }
}
