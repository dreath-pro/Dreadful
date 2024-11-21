package com.example.dreadful.characters;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.logics.NumberComma;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Jimhardcore extends Player {
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private MonsterDatabase monsterDatabase;
    private int ironBody = 0;
    private int muscleCharge = 0;

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
        this.monsterDatabase = new MonsterDatabase(context);
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                events.add(getName() + " grunts in mild pain, barely fazed.");

                dialogues.add("Heh, that tickles... I barely felt that!");
                dialogues.add("Is that all you've got? Muahahahah!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case 1:
                events.add(getName() + " stumbles back slightly but shakes it off with a grin.");

                dialogues.add("Nice try... but it’ll take more than that to bring me down!");
                dialogues.add("I’m just getting started! Muahahahah!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case 2:
                events.add(getName() + " stumbles from the impact, his face twisting in pain, but he stands tall.");

                dialogues.add("Grrr… that was a good hit! But you’ll have to do better!");
                dialogues.add("I’m far from finished! Muahahahah!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case 3:
                events.add(getName() + " collapses briefly to one knee, blood dripping from his mouth, but he laughs through the pain.");

                dialogues.add("Hah… you really think you’ve got me? I’ll show you what real strength is!");
                dialogues.add("This only makes me stronger… Muahahahah!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
        }
    }

    public void defeatReward() {
        if (!monsterDatabase.doesSelectedDataExist(getName())) {
            monsterDatabase.addMonster(getName());
        }
    }

    /**
     * the way he receive a hit, when his health drop 0, he will go to instant rage and increase his hp to 35k
     * and the opponent will receive a hit base on the rage value and can not be dodge and penetrates defense
     */
    public void receiveHit(Player hitter, Player target) {
        String result = receiveHitLogic(hitter, target);
        switch (result) {
            case "DODGE":
                events.add(hitter.getName() + " swings or launches an attack, but " + getName() + " narrowly dodges, moving with surprising speed for his massive frame.");

                dialogues.add("Too slow, " + hitter.getName() + "! You'll have to do better than that!");
                dialogues.add("I’m faster than I look, and you’re just getting warmed up! Muahahahah!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                break;
            case "BLOCKED":
                events.add(getName() + " strikes, but " + getName() + " braces himself and absorbs the impact with his iron gauntlet.");

                dialogues.add("Is that all you've got? My gauntlet’s made of stronger stuff!");
                dialogues.add("Nice try, but it'll take more than that to break me! Muahahahah!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
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

        ironBody--;
        if (ironBody <= 0) {
            ironBody = 0;

            setAttack(getMaxAttack());
            setDefense(getMaxDefense());
        }

        muscleCharge--;
        if (muscleCharge <= 0) {
            muscleCharge = 0;

            setAttack(getMaxAttack());
            setDefense(getMaxDefense());
        }
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

                if (prompt.isTherePopup()) {
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

                if (prompt.isTherePopup()) {
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

                if (prompt.isTherePopup()) {
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

                if (prompt.isTherePopup()) {
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

                if (prompt.isTherePopup()) {
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

                if (prompt.isTherePopup()) {
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

    //increase the damage and defense
    private void skill3(Player hitter, Player target) {
        setAttack(getAttack() + 580);
        setDefense(getDefense() + 180);

        ironBody = 15;
    }

    //multiply the current damage and defense by 2
    private void skill4(Player hitter, Player target) {
        setAttack(getAttack() * 2);
        setDefense(getDefense() * 2);

        muscleCharge = 15;
    }

    //pukes onto the enemy's face with a decent damage and two damage over time
    private void skill5(Player hitter, Player target) {
        setAttack(getAttack() + 370);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        target.getDamageOverTime().add(280);
        target.getDamageOverTimeValue().add(6);

        target.getDamageOverTime().add(30);
        target.getDamageOverTimeValue().add(21);
    }
}
