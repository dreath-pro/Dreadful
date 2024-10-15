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
import com.example.dreadful.models.Prompt;

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
    private Prompt prompt;
    private int voidTime = 0;
    private int fatigue = 0;

    public VoidReaper(Context context, ImageView yourImage, ConstraintLayout backgroundImage, int[] backgroundList, int selectedBackground, Prompt prompt) {
        super(context, yourImage, "Void Reaper", R.drawable.character_void_reaper, "left", 150,
                new int[]{R.drawable.character_void_reaper_2},
                new int[]{R.drawable.background_void_1, R.drawable.background_void_2},
                60000, 1800, 500, 15,
                new String[]{"Chrono Reap", "Time Fracture", "Dimension Shift", "Reverse Dash", "Singularity Slash", "Void Fatigue", "Temporal Reset"},
                new int[]{0, 5, 5, 3, 4, 5, 10}, new int[]{0, 0, 0, 0, 0, 0, 0}, prompt);

        this.resizeImage = new ResizeImage(context);
        this.yourImage = yourImage;
        this.backgroundImage = backgroundImage;
        this.backgroundList = backgroundList;
        this.selectedBackground = selectedBackground;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        this.prompt = prompt;
    }

    /**
     * if the attacker hits, the void reaper will be mark with time passed status by 1 and it will stack with
     * each hits
     */
    public void receiveHit(Player hitter, Player target) {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return;

        if (!hasStatus(hitter, "Fatigue", 1).isEmpty()) {
            hitter.setAttack(hitter.getAttack() - 250);
        }

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());

        if (hasStatus(hitter, "Fatigue", 1).isEmpty()) {
            hitter.setAttack(hitter.getMaxAttack());
        }

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

        fatigue--;
        if (fatigue <= 0) {
            fatigue = 0;

            if (!hasStatus(hitter, "Fatigue", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(hitter, "Fatigue", 1));

                hitter.setAttack(hitter.getMaxAttack());
                hitter.setDodge(hitter.getMaxDodge());
                hitter.setDefense(hitter.getMaxDefense());

                hitter.getStatusValue().remove(index);
                hitter.getStatus().remove(index);
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
            case 5:
                skill5(hitter, target);
                break;
            case 6:
                skill6(hitter, target);
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
        backgroundImage.setBackgroundResource(hitter.getDimension()[1]);
        yourImage.setImageResource(hitter.getTransformation()[0]);
        resizeImage.scale(yourImage, 185);
        voidTime = 9;

        hitter.setAttack(8500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());
        receiveStatus(target, "The Void", 1);
        target.setStun(3);
    }

    //the enemy will be teleported to a different dimension and void reaper will transform, after that
    //he will burst the target and resets all skill cooldown every attack
    private void skill2(Player hitter, Player target) {
        backgroundImage.setBackgroundResource(hitter.getDimension()[0]);
        yourImage.setImageResource(hitter.getTransformation()[0]);
        resizeImage.scale(yourImage, 185);
        voidTime = 9;

        hitter.setAttack(12500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        receiveStatus(hitter, "Endless Void", 20);
        reduceCoolDown(hitter, target);
    }

    //heals base on the time passed buff
    private void skill3(Player hitter, Player target) {
        if (!hasStatus(hitter, "Time Passed", 1).isEmpty()) {
            int index = Integer.parseInt(hasStatus(hitter, "Time Passed", 1));
            int healthPortion = (int) (getMaxHealth() * 0.05);

            for (int i = 0; i <= getStatusValue().get(index) - 1; i++) {
                setHealth(getHealth() + healthPortion);
            }

            getStatusValue().remove(index);
            getStatus().remove(index);
        } else {
            setHealth(getHealth() + 750);
        }
    }

    //attack target and deals damage based on the time passed buff
    private void skill4(Player hitter, Player target) {
        if (!hasStatus(hitter, "Time Passed", 1).isEmpty()) {
            int index = Integer.parseInt(hasStatus(hitter, "Time Passed", 1));
            int damagePortion = (int) (target.getMaxHealth() * 0.05);

            for (int i = 0; i <= getStatusValue().get(index) - 1; i++) {
                setAttack((getAttack() + damagePortion));
                target.receiveHit(hitter, target);
                setAttack(getMaxAttack());
            }

            getStatusValue().remove(index);
            getStatus().remove(index);
        } else {
            setAttack(getAttack() + 750);
            target.receiveHit(hitter, target);
            setAttack(getMaxAttack());
        }

        reduceCoolDown(hitter, target);
    }

    //reduces enemies stats for 4 turns and applies "fatigue" status to the target
    private void skill5(Player hitter, Player target) {
        fatigue = 12;

        receiveStatus(target, "Fatigue", 50);
        target.setAttack(target.getAttack() - 350);
        target.setDodge(0);
        target.setDefense(0);

        setAttack(getAttack() + 3500);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        reduceCoolDown(hitter, target);
    }

    //resets all the way back to the beginning of the battle and enemies cannot use skill at the start
    private void skill6(Player hitter, Player target) {
        target.setHealth(target.getMaxHealth());
        target.setAttack(target.getMaxAttack());
        target.setDefense(target.getMaxDefense());
        target.setDodge(target.getMaxDodge());
        target.setStun(0);
        target.setHealOverTime(new ArrayList<>());
        target.setHealOverTimeValue(new ArrayList<>());
        target.setDamageOverTime(new ArrayList<>());
        target.setDamageOverTimeValue(new ArrayList<>());

        for (int i = 1; i <= target.getMaxSkillCooldowns().length - 1; i++) {
            target.getSkillCooldowns()[i] = 5;
        }

        setHealth(getMaxHealth());
        setAttack(getMaxAttack());
        setDefense(getMaxDefense());
        setDodge(getMaxDodge());
        setStun(0);
        setHealOverTime(new ArrayList<>());
        setHealOverTimeValue(new ArrayList<>());
        setDamageOverTime(new ArrayList<>());
        setDamageOverTimeValue(new ArrayList<>());

        for (int i = 1; i < getMaxSkillCooldowns().length - 1; i++) {
            getSkillCooldowns()[i] = 0;
        }
    }
}
