package com.example.dreadful.characters;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.logics.NumberComma;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Jimhardcore extends Player{
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private NumberComma numberComma = new NumberComma();

    public Jimhardcore(Context context, ImageView yourImage, Prompt prompt) {
        super(context, yourImage, "Jimhardcore", R.drawable.character_jimhardcore, "right", 150,
                null, null, 8500, 470, 230, 34);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Thunder Knuckle");
        skillNames.add("Electric Whiplash");
        skillNames.add("Gauntlet Strike");
        skillNames.add("Iron Fist");
        skillNames.add("Muscle Charge");
        skillNames.add("Barf Barrage");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(3);
        maxSkillCooldowns.add(3);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(6);
        maxSkillCooldowns.add(3);
        updateMaxSkillCooldowns(maxSkillCooldowns);

        ArrayList<Integer> skillCooldowns = new ArrayList<>();
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        updateSkillCooldowns(skillCooldowns);

        this.prompt = prompt;
        this.context = context;
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case 1:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case 2:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case 3:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
        }
    }

    public void defeatReward() {

    }

    /**
     * the way he receive a hit, when his health drop 0, he will go to instant rage and increase his hp to 35k
     * and the opponent will receive a hit base on the rage value and can not be dodge and penetrates defense
     */
    public void receiveHit(Player hitter, Player target) {
        String result = receiveHitLogic(hitter, target);
        switch (result) {
            case "DODGE":
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case "BLOCKED":
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case "":

                break;
            default:
                damageExpression(prompt.measureDamage(Integer.parseInt(result)));
                break;
        }
    }

    /**
     * Override parent class' receiveTimeHp method so that his own unique receiveHit will still have effect
     * even if its direct attack
     */
    public void receiveTimeEffect(Player hitter, Player target) {
        runTimeHeal();
        runTimeDamage();
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;

        ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        ArrayList<String> newSkillNames = getSkillNames().getValue();
        if (newSkillNames == null) {
            newSkillNames = new ArrayList<>();
        }

        int skillIndex = random.nextInt(newSkillCooldowns.size());

        while (newSkillCooldowns.get(skillIndex) > 0) {
            skillIndex = random.nextInt(newSkillCooldowns.size());
        }

        skillName = newSkillNames.get(skillIndex);
        switch (skillIndex) {
            case 0:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                basicAttack(hitter, target);
                break;
            case 1:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill1(hitter, target);
                break;
            case 2:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill2(hitter, target);
                break;
            case 3:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill3(hitter, target);
                break;
            case 4:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill4(hitter, target);
                break;
            case 5:
                events.add("");

                dialogues.add("");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill5(hitter, target);
                break;
        }

        reduceCooldown(skillIndex);
        return skillName;
    }

    //basic attack that has stun
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
        target.setStun(2);
    }

    //enhance attack with damage over time effect (electric)
    private void skill1(Player hitter, Player target) {
        setAttack(getAttack() + 330);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        target.getDamageOverTime().add(180);
        target.getDamageOverTimeValue().add(9);
    }

    //nothing special just a burst attack
    private void skill2(Player hitter, Player target) {
        setAttack(getAttack() + 1200);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //enemy cannot dodge all incoming attacks
    private void skill3(Player hitter, Player target) {
        target.setDodge(0);
        setAttack(getAttack() + 5000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
        target.setDodge(target.getMaxDodge());
    }

    //heal and increase damage
    private void skill4(Player hitter, Player target) {
        setHealth(getHealth() + 10000);
        setAttack(getAttack() + 11000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    private void skill5(Player hitter, Player target) {
        setHealth(getHealth() + 10000);
        setAttack(getAttack() + 11000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }
}
