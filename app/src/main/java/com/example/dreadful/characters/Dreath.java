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
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public Dreath(Context context, ImageView yourImage, TestActivity testActivity) {
        super(context, yourImage, "Dreath", R.drawable.character_dreath, "left", 150,
                null, null,
                88070, 2580, 880, 0,
                new String[]{"Butcher", "Dismember", "Ruthless Torture", "Brutal Gut", "Evisceration"},
                new int[]{0, 7, 3, 5, 5}, new int[]{0, 0, 0, 0, 0});

        this.testActivity = testActivity;
        this.prompt = new Prompt(testActivity);
        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    /**
     * the way he receive a hit, when his health drop 0, he will go to instant rage and increase his hp to 35k
     * and the opponent will receive a hit base on the rage value and can not be dodge and penetrates defense
     */
    public void receiveHit(Player hitter, Player target) {
        String result = receiveHitLogic(hitter, target);
        switch (result) {
            case "DODGE":

                break;
            case "BLOCKED":
                events.add("But " + getName() + " blocks the attack with ease.");
                events.add("Unfortunately, " + getName() + " is immune to that attack.");
                events.add("The attack has no effect on " + getName() + ".");
                events.add("That hit tickles " + getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' " : "'s ") + "hard armor.");
                prompt = testActivity.getPrompt();
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("You’ll need a stronger attack to get past me!");
                prompt = testActivity.getPrompt();
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
            default:
                if (Integer.parseInt(result) >= 1 && Integer.parseInt(result) <= 5) {

                } else if (Integer.parseInt(result) >= 6 && Integer.parseInt(result) <= 10) {

                } else if (Integer.parseInt(result) >= 11 && Integer.parseInt(result) <= 20) {

                } else if (Integer.parseInt(result) >= 21 && Integer.parseInt(result) <= 30) {

                } else if (Integer.parseInt(result) >= 31 && Integer.parseInt(result) <= 40) {

                } else if (Integer.parseInt(result) >= 41 && Integer.parseInt(result) <= 50) {

                } else if (Integer.parseInt(result) >= 51 && Integer.parseInt(result) <= 60) {

                } else if (Integer.parseInt(result) >= 61 && Integer.parseInt(result) <= 70) {

                } else if (Integer.parseInt(result) >= 71 && Integer.parseInt(result) <= 80) {

                } else if (Integer.parseInt(result) >= 81 && Integer.parseInt(result) <= 90) {

                } else if (Integer.parseInt(result) >= 91 && Integer.parseInt(result) <= 100) {

                }
                break;
        }

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

                dialogues.add("I am fear itself, unleashed upon you!");
                dialogues.add("Fear does not die... and neither do I!");
                dialogues.add("I am the fear that never fades!");
                dialogues.add("I am fear!");
                dialogues.add("You fear death? Fear me more.");
                dialogues.add("Fear me, for I am the last thing you will ever see.");
                dialogues.add("Fear is eternal, and now... so am I.");
                prompt = testActivity.getPrompt();
                prompt.selectRandomDialogue(this, dialogues, false);
                dialogues.clear();
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

                events.add(getName() + " uses his sharp sword to hack " + target.getName());
                events.add(getName() + " swings his deadly sword.");
                events.add(getName() + " attacks " + target.getName() + " with malice.");
                events.add(getName() + " quick slashes his sword.");
                prompt = testActivity.getPrompt();
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Your fate was sealed the moment you crossed me!");
                prompt = testActivity.getPrompt();
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();
                break;
            case 1:
                skill1(hitter, target);

                events.add(getName() + " tries to brutally dismember " + target.getName() + " alive.");
                events.add(getName() + " tries to chop " + target.getName() + " to pieces.");
                events.add(getName() + " does a violent amputation on " + target.getName() + ".");
                events.add(getName() + " ruthlessly destroying " + target.getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' body parts." : "'s body parts."));
                prompt = testActivity.getPrompt();
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("With every strike, I carve your fate!");
                prompt = testActivity.getPrompt();
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();
                break;
            case 2:
                skill2(hitter, target);

                events.add(getName() + " hacks his opponent with pure rage");
                events.add(getName() + " stabbing " + target.getName() + " multiple times.");
                events.add(getName() + " ruthlessly torturing " + target.getName() + ".");
                prompt = testActivity.getPrompt();
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Prepare to meet your end!");
                prompt = testActivity.getPrompt();
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();
                break;
            case 3:
                skill3(hitter, target);

                events.add(getName() + " tries to gut " + target.getName() + ".");
                events.add(getName() + " thirsty bloody attacks " + target.getName() + " from the insides.");
                events.add(getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' " : "'s ") + "guts and gore attack.");
                events.add(getName() + " tries to disembowel " + target.getName() + ".");
                prompt = testActivity.getPrompt();
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Prepare to meet your end!");
                dialogues.add("Prepare for annihilation!");
                dialogues.add("Let my blade guide you to oblivion!");
                prompt = testActivity.getPrompt();
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();
                break;
            case 4:
                skill4(hitter, target);

                events.add(getName() + " tries to attack " + target.getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' " : "'s ") + "organs and recovers his lost blood.");
                events.add(getName() + " eviscerated " + target.getName() + " and devours internal organs to recover");
                events.add(getName() + " tries to gut " + target.getName() + " with his sharp sword.");
                events.add(getName() + " does a gory attack on " + target.getName() + (target.getName().charAt(target.getName().length() - 1) == 's' ? "' " : "'s ") + " intestines.");
                prompt = testActivity.getPrompt();
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Prepare to meet your end!");
                dialogues.add("Prepare for annihilation!");
                dialogues.add("Let my blade guide you to oblivion!");
                prompt = testActivity.getPrompt();
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();
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
