package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;

import java.util.Random;

public class KumoNingyo extends Player {
    private Random random = new Random();
    private int imageView = R.drawable.character_kumo_ningyo;
    private int[] transformationView = {};
    private int[] dimension = {};

    private Animation shakeAnimation;
    private ImageView yourImage;

    public KumoNingyo(Context context, ImageView yourImage) {
        super(context, yourImage, "Kumo Ningyō", "character_kumo_ningyo", "left", 210, null,
                20000, 180, 0, 20,
                new String[]{"Doku Kizu", "Shinobi Ashi Keri", "Tsukurogami", "Kakure Kage"},
                new int[]{0, 3, 3, 3}, new int[]{0, 0, 0, 0});

        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    /**
     * everytime an attacker hit, kumo ningyo will be mark with lost limb buff and healing effects
     * will increase significantly base on the lost limbs buff
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

        receiveStatus(target, "Lost Limbs", 1);
        setDodge(getMaxDodge());
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
        target.getDamageOverTime().add(10);
        target.getDamageOverTimeValue().add(30);
    }

    //uses base damage 3 times and same poison effect
    private void skill1(Player hitter, Player target) {
        setAttack(getAttack() * 3);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        target.getDamageOverTime().add(10 * 3);
        target.getDamageOverTimeValue().add(30 * 3);
    }

    //recovers 1000 health and multiply by the value of the lost limbs buff
    private void skill2(Player hitter, Player target) {
        if(!hasStatus(hitter, "Lost Limbs", 1).isEmpty())
        {
            int index = Integer.parseInt(hasStatus(hitter, "Lost Limbs", 1));
            setHealth(getHealth() + (1000 * getStatusValue().get(index)));
        }
    }

    //simple basic attack and add 60% dodge for the hitter's next attack
    private void skill3(Player hitter, Player target) {
        target.receiveHit(hitter, target);
        target.getDamageOverTime().add(10);
        target.getDamageOverTimeValue().add(30);

        setDodge(getDodge() + 60);
    }
}
