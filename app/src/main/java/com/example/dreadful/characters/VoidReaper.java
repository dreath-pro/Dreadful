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
    private ResizeImage resizeImage;

    public VoidReaper(Context context, ImageView yourImage, ConstraintLayout backgroundImage) {
        super(context, yourImage, "Void Reaper", R.drawable.character_void_reaper, "left", 150,
                new int[]{R.drawable.character_void_reaper_2},
                new int[]{R.drawable.background_void_1},
                60000, 1800, 500, 15,
                new String[]{"Chrono Reap", "The Void", "Dimension Shift"},
                new int[]{0, 5, 3}, new int[]{0, 0, 0});

        this.resizeImage = new ResizeImage(context);
        this.yourImage = yourImage;
        this.backgroundImage = backgroundImage;
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

    //if there is the void mark, the void reaper reduces his cooldown with each attack he deals
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
        if(!hasStatus(target, "The Void", 1).isEmpty())
        {
            for (int i = 1; i <= hitter.getMaxSkillCooldowns().length - 1; i++) {
                hitter.getSkillCooldowns()[i] -= 1;
                if(hitter.getSkillCooldowns()[i] <= 0)
                {
                    hitter.getSkillCooldowns()[i] = 0;
                }
            }
        }
    }

    //stuns the enemy and burst them
    private void skill1(Player hitter, Player target) {
        hitter.setAttack(8500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());
        receiveStatus(target, "The Void", 1);
        target.setStun(3);
    }

    //the enemy will be teleported to a different dimension and void reaper will transform
    private void skill2(Player hitter, Player target) {
        backgroundImage.setBackgroundResource(hitter.getDimension()[random.nextInt(hitter.getDimension().length)]);
        yourImage.setImageResource(hitter.getTransformation()[0]);
        resizeImage.scale(yourImage, 185);

        hitter.setAttack(12500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        if(!hasStatus(target, "The Void", 1).isEmpty())
        {
            for (int i = 1; i <= hitter.getMaxSkillCooldowns().length - 1; i++) {
                hitter.getSkillCooldowns()[i] -= 1;
                if(hitter.getSkillCooldowns()[i] <= 0)
                {
                    hitter.getSkillCooldowns()[i] = 0;
                }
            }
        }
    }
}
