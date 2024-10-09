package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.logics.ResizeImage;
import com.example.dreadful.models.Player;

import java.util.Random;

public class HellKnight extends Player {
    private Random random = new Random();
    private Animation shakeAnimation;
    private ImageView yourImage;
    private ResizeImage resizeImage;
    private ProgressBar yourHealthBar;
    private int form = 0;
    private int enhancedDefense = 0;

    public HellKnight(Context context, ImageView yourImage, ProgressBar yourHealthBar) {
        super(context, yourImage, "Hell Knight", R.drawable.character_hell_knight, "right", 155,
                new int[]{R.drawable.character_hell_knight_2}, null,
                20000, 1000, 1000, 5,
                new String[]{"Burn Slash", "Knight's Breath", "Enhanced Armor", "Dragon Form"},
                new int[]{0, 4, 9, 0}, new int[]{0, 0, 0, 0});

        this.resizeImage = new ResizeImage(context);
        this.yourImage = yourImage;
        this.yourHealthBar = yourHealthBar;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        enhancedDefense--;
        if (enhancedDefense <= 0) {
            enhancedDefense = 0;

            if (!hasStatus(target, "Enhanced Armor", 1).isEmpty()) {
                setDefense(getDefense() - 350);

                int index = Integer.parseInt(hasStatus(target, "Enhanced Armor", 1));
                getStatusValue().remove(index);
                getStatus().remove(index);
            }
        }
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;
        int skillIndex;

        do {
            skillIndex = random.nextInt(getSkillNames().length);
        }while(getSkillCooldowns()[skillIndex] > 0);

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

    //simple basic attack
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
    }

    //burst the target and applies burn that last for 7 turns
    private void skill1(Player hitter, Player target) {
        hitter.setAttack(4000);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        target.getDamageOverTime().add(450);
        target.getDamageOverTimeValue().add(21);
    }

    //increase the defense and applies "enhanced armor" status
    private void skill2(Player hitter, Player target) {
        enhancedDefense = 21;
        setDefense(getDefense() + 350);
        receiveStatus(hitter, "Enhanced Armor", 1);
    }

    //transform into a dragon and increase the max health and heals yourself overtime at the same time
    //that lasts for 10 turns, your max health, defense will increase but your dodge will be reduced
    private void skill3(Player hitter, Player target) {
        form = 1;
        yourImage.setImageResource(hitter.getTransformation()[0]);
        resizeImage.scale(yourImage, 200);

        setDefense(2000);
        setDodge(0);

        double percentageLost = (double) (getMaxHealth() - getHealth()) / getMaxHealth() * 100;

        bypassSetMaxHealth(60000);
        setHealth(getMaxHealth());
        yourHealthBar.setMax(getMaxHealth());

        double reducedNewHealth = getMaxHealth() - (getMaxHealth() * (percentageLost / 100));
        setHealth((int) reducedNewHealth);

        getHealOverTime().add(450);
        getHealOverTimeValue().add(30);
    }
}
