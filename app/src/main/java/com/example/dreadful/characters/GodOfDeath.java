package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class GodOfDeath extends Player {
    private Random random = new Random();
    private Prompt prompt;
    private int timeBeforeDeath = 0;
    private boolean isClockOn = false;

    public GodOfDeath(Context context, ImageView yourImage, TestActivity testActivity) {
        super(context, yourImage, "God of Death", R.drawable.character_god_of_death, "right", 210,
                null, null,
                500000, 500000, 500000, 50,
                new String[]{"Decay Touch", "Pray For The Living", "Time Before Death", "Afterlife"},
                new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0});

        this.prompt = new Prompt(testActivity);
    }

    public void receiveHit(Player hitter, Player target) {
        receiveHitLogic(hitter, target);
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        if (isClockOn) {
            timeBeforeDeath--;

            if (!hasStatus(hitter, "Time Before Death", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(hitter, "Time Before Death", 1));
                hitter.getStatusValue().set(index, hitter.getStatusValue().get(index) - 1);
            }

            if (timeBeforeDeath <= 0) {
                hitter.setHealth(0);
                timeBeforeDeath = 0;

                int index = Integer.parseInt(hasStatus(hitter, "Time Before Death", 0));
                hitter.getStatus().remove(index);
                hitter.getStatusValue().remove(index);
            }
        }
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;
        int skillIndex = random.nextInt(getSkillNames().length);

        while (getSkillCooldowns()[skillIndex] > 0) {
            skillIndex = random.nextInt(getSkillNames().length);
        }

        if (getSkillCooldowns()[1] > 0) {
            getSkillCooldowns()[1] = 0;
        }
        if (getSkillCooldowns()[2] > 0) {
            getSkillCooldowns()[2] = 0;
        }
        if (getSkillCooldowns()[3] > 0) {
            getSkillCooldowns()[3] = 0;
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

    //temporary simple attack
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(getMaxDodge());
    }

    //temporary simple attack
    private void skill1(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(getMaxDodge());
    }

    //give the target time before death ranges from 6 to 10
    private void skill2(Player hitter, Player target) {
        isClockOn = true;
        timeBeforeDeath = random.nextInt(4) + 6;

        target.receiveStatus(target, "Time Before Death", timeBeforeDeath);
    }

    //temporary simple attack
    private void skill3(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(getMaxDodge());
    }
}
