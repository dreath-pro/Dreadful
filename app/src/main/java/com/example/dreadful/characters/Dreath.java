package com.example.dreadful.characters;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.adapters.ViewPrompt;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Dreath extends Player {
    private Random random = new Random();
    private Animation shakeAnimation;
    private ImageView yourImage;
    private Prompt prompt;
    private TestActivity testActivity;

    public Dreath(Context context, ImageView yourImage, TestActivity testActivity) {
        super(context, yourImage, "Dreath", R.drawable.character_dreath, "left", 150,
                null, null,
                88070, 2580, 880, 0,
                new String[]{"Butcher", "Dismember", "Ruthless Torture", "Brutal Gut", "Evisceration"},
                new int[]{0, 7, 3, 5, 5}, new int[]{0, 0, 0, 0, 0});

        this.testActivity = testActivity;
        this.prompt = new Prompt();
        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    /**
     * the way he receive a hit, when his health drop 0, he will go to instant rage and increase his hp to 35k
     * and the opponent will receive a hit base on the rage value and can not be dodge and penetrates defense
     */
    public void receiveHit(Player hitter, Player target) {
        receiveHitLogic(hitter, target);

        receiveStatus(target, "Rage", 10);
        if (!hasStatus(target, "Rage", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(target, "Rage", 50));
            if (getHealth() <= 0) {
                setHealth(56780);

                int damage = (hitter.getHealth() * target.getStatusValue().get(index)) / 100;
                setAttack(damage);

                hitter.setDefense(0);
                hitter.setDodge(0);

                hitter.receiveHit(hitter, target);

                hitter.setDodge(hitter.getMaxDodge());
                hitter.setDefense(hitter.getMaxDefense());
                setAttack(getMaxAttack());
                getStatusValue().set(index, getStatusValue().get(index) - 50);

                String[] dialogues = {
                        "I am fear itself, unleashed upon you!",
                        "Fear does not die... and neither do I!",
                        "I am the fear that never fades!",
                        "I am fear!",
                        "You fear death? Fear me more.",
                        "Fear me, for I am the last thing you will ever see.",
                        "Fear is eternal, and now... so am I."};
                prompt = testActivity.getPrompt();
                prompt.addDialogueMessage(this, dialogues[random.nextInt(dialogues.length)]);
                testActivity.setPrompt(prompt);
            }
        }
    }

    /**
     * Override parent class' receiveTimeHp method so that his own unique receiveHit will still have effect
     * even if its direct attack
     */
    public void receiveTimeEffect(Player hitter, Player target) {
        runTimeHeal();
        runTimeDamage();

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
        String[] dialogues = {
                getName() + " uses his sharp sword to hack " + target.getName(),
                getName() + " swings his deadly sword.",
                getName() + " attacks " + target.getName() + " with malice.",
                getName() + " quick slashes his sword."};
        prompt = testActivity.getPrompt();
        prompt.addEventMessage(dialogues[random.nextInt(dialogues.length)]);
        testActivity.setPrompt(prompt);

        target.setDefense(0);
        target.receiveHit(hitter, target);
        target.setDefense(target.getMaxDefense());
    }

    //prevent enemy from using all skills
    private void skill1(Player hitter, Player target) {
        String[] dialogues = {
                getName() + " tries to brutally dismember " + target.getName() + " alive.",
                getName() + " tries to chop " + target.getName() + " to pieces.",
                getName() + " does a violent amputation on " + target.getName() + ".",
                getName() + " ruthlessly destroying " + target.getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' body parts." : "'s body parts.")};
        prompt = testActivity.getPrompt();
        prompt.addEventMessage(dialogues[random.nextInt(dialogues.length)]);
        testActivity.setPrompt(prompt);

        for (int i = 1; i <= target.getMaxSkillCooldowns().length - 1; i++) {
            target.getSkillCooldowns()[i] += 5;
        }

        setAttack(getAttack() + 6500);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //enemy cannot dodge all incoming attacks
    private void skill2(Player hitter, Player target) {
        String[] dialogues = {
                getName() + " hacks his opponent with pure rage",
                getName() + " stabbing " + target.getName() + " multiple times.",
                getName() + " ruthlessly torturing " + target.getName() + "."};
        prompt = testActivity.getPrompt();
        prompt.addEventMessage(dialogues[random.nextInt(dialogues.length)]);
        testActivity.setPrompt(prompt);

        target.setDodge(0);
        setAttack(getAttack() + 5000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
        target.setDodge(target.getMaxDodge());
    }

    //high burst
    private void skill3(Player hitter, Player target) {
        String[] dialogues = {
                getName() + " tries to gut " + target.getName() + ".",
                getName() + " thirsty bloody attacks " + target.getName() + " from the insides.",
                getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' " : "'s ") + "guts and gore attack.",
                getName() + " tries to disembowel " + target.getName() + "."};
        prompt = testActivity.getPrompt();
        prompt.addEventMessage(dialogues[random.nextInt(dialogues.length)]);
        testActivity.setPrompt(prompt);

        setAttack(getAttack() + 8870);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //heal and increase damage
    private void skill4(Player hitter, Player target) {
        String[] dialogues = {
                getName() + " tries to attack " + target.getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' " : "'s ") + "organs and recovers his lost blood.",
                getName() + " eviscerated " + target.getName() + " and devours internal organs to recover",
                getName() + " tries to gut " + target.getName() + " with his sharp sword.",
                getName() + " does a gory attack on " + target.getName() + target.getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' " : "'s ") + " intestines."};
        prompt = testActivity.getPrompt();
        prompt.addEventMessage(dialogues[random.nextInt(dialogues.length)]);
        testActivity.setPrompt(prompt);

        setHealth(getHealth() + 10000);
        setAttack(getAttack() + 11000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }
}
