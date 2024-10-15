package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.Random;

public class KumoNingyo extends Player {
    private Random random = new Random();
    private Animation shakeAnimation;
    private ImageView yourImage;
    private ProgressBar yourHealthBar;
    private Prompt prompt;
    private int poison = 10;
    private int creepyStalkerTime = 0;
    private int limbTwitch = 6, maxLimbTwitch = 6;

    public KumoNingyo(Context context, ImageView yourImage, ProgressBar yourHealthBar, Prompt prompt) {
        super(context, yourImage, "Kumo Ningyō", R.drawable.character_kumo_ningyo, "left", 210,
                null, null,
                5800, 180, 0, 20,
                new String[]{"Doku Kizu", "Shinobi Ashi Keri", "Tsukurogami", "Kakure Kage", "Ito no Tami"},
                new int[]{0, 3, 3, 3, 6}, new int[]{0, 0, 0, 0, 0}, prompt);

        this.yourImage = yourImage;
        this.yourHealthBar = yourHealthBar;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        this.prompt = prompt;
    }

    /**
     * everytime an attacker hit, kumo ningyo will be mark with lost limb buff and healing effects
     * will increase significantly base on the lost limbs buff
     */
    public void receiveHit(Player hitter, Player target) {
        receiveHitLogic(hitter, target);
        receiveStatus(target, "Lost Limbs", 1);
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        limbTwitch--;
        if (limbTwitch <= 0) {
            limbTwitch = maxLimbTwitch;

            if (!hasStatus(target, "Lost Limbs", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(target, "Lost Limbs", 1));
                bypassSetHealth(getHealth() + (1000 * getStatusValue().get(index)));

                if(getHealth() > getMaxHealth())
                {
                    bypassSetMaxHealth(getHealth());
                    yourHealthBar.setMax(getMaxHealth());
                }

                poison *= getStatusValue().get(index);
            }else
            {
                bypassSetHealth(getHealth() + 1000);

                if(getHealth() > getMaxHealth())
                {
                    bypassSetMaxHealth(getHealth());
                    yourHealthBar.setMax(getMaxHealth());
                }

                poison += 10;
            }
        }

        creepyStalkerTime--;
        if (creepyStalkerTime <= 0) {
            setDodge(getMaxDodge());
            creepyStalkerTime = 0;

            if (!hasStatus(target, "Creepy Stalker", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(target, "Creepy Stalker", 1));
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

    //every attack has poison effect that last 10 turns
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
        target.getDamageOverTime().add(poison);
        target.getDamageOverTimeValue().add(30);
    }

    //uses base damage 3 times and same poison effect
    private void skill1(Player hitter, Player target) {
        setAttack(getAttack() * 3);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        target.getDamageOverTime().add(poison * 3);
        target.getDamageOverTimeValue().add(30 * 3);
    }

    //recovers 800 health and multiply by the value of the lost limbs buff and bypasses the max health rewriting it
    //also removes the lost limbs buff, while also increasing the poison effect permanently
    private void skill2(Player hitter, Player target) {
        if (!hasStatus(hitter, "Lost Limbs", 1).isEmpty()) {
            int index = Integer.parseInt(hasStatus(hitter, "Lost Limbs", 1));
            bypassSetHealth(getHealth() + (1000 * getStatusValue().get(index)));

            if(getHealth() > getMaxHealth())
            {
                bypassSetMaxHealth(getHealth());
                yourHealthBar.setMax(getMaxHealth());
            }

            poison *= getStatusValue().get(index);

            getStatus().remove(index);
            getStatusValue().remove(index);
        }else
        {
            bypassSetHealth(getHealth() + 1000);

            if(getHealth() > getMaxHealth())
            {
                bypassSetMaxHealth(getHealth());
                yourHealthBar.setMax(getMaxHealth());
            }

            poison += 10;
        }
    }

    //burst attack with poison and add 60% dodge for the hitter's next attack with the same poison effect
    private void skill3(Player hitter, Player target) {
        hitter.setAttack(4500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        target.getDamageOverTime().add(poison);
        target.getDamageOverTimeValue().add(30);

        creepyStalkerTime = 5;
        receiveStatus(hitter, "Creepy Stalker", 1);
        setDodge(getDodge() + 60);
    }

    //stuns the target
    private void skill4(Player hitter, Player target)
    {
        target.setStun(4);
    }
}
