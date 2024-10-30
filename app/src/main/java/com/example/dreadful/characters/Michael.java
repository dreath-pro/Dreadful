package com.example.dreadful.characters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Michael extends Player{
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private Animation shakeAnimation;
    private ImageView yourImage, enemyImage;
    private int shieldPercentage = 20, maxShieldPercentage = 20;
    private int enemyCurrentHealth;
    private int petrification = 0;
    private int shield = 0;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public Michael(Context context, ImageView yourImage, Prompt prompt, ImageView enemyImage) {
        super(context, yourImage, "Michael", R.drawable.character_michael, "left", 210,
                null, null, 20500, 1800, 800, 0);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Purgatory Slash");
        skillNames.add("Cleansing Wave");
        skillNames.add("Resonance of Truth");
        skillNames.add("Sanctuary Shield");
        skillNames.add("Fate's Binding");
        skillNames.add("Fallen Mourn");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(3);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(7);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(4);
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
        this.yourImage = yourImage;
        this.enemyImage = enemyImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
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

    /**
     * the way he receive a hit, when his health drop 0, he will go to instant rage and increase his hp to 35k
     * and the opponent will receive a hit base on the rage value and can not be dodge and penetrates defense
     */
    public void receiveHit(Player hitter, Player target) {
        hitter.setAttack(hitter.getAttack() * (int) (1 - (double) shieldPercentage));

        if (hitter.getAttack() <= getDefense()) {
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

        } else {
            hitter.setAttack(hitter.getAttack() - getDefense());

            double damagePercentage = (double) hitter.getAttack() / getHealth() * 100;
            damageExpression((int) damagePercentage);

            setHealth(getHealth() - hitter.getAttack());
            yourImage.startAnimation(shakeAnimation);
        }

        hitter.setAttack(hitter.getMaxAttack());
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        runTimeHeal();

        shield--;
        if(shield <= 0)
        {
            shield = 0;
            shieldPercentage = maxShieldPercentage;
        }

        petrification--;
        if(petrification <= 0)
        {
            petrification = 0;

            enemyImage.setColorFilter(null);
            hitter.setHealth(enemyCurrentHealth);
            enemyCurrentHealth = hitter.getHealth();
            hitter.setDefense(hitter.getMaxDefense());
            hitter.setDodge(hitter.getMaxDodge());
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

    //attack that will ignore dodge
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
    }

    //cleanse all status and damage over time while damaging the enemy
    private void skill1(Player hitter, Player target) {
        ArrayList<String> newStatusList = getStatusList().getValue();
        if (newStatusList == null) {
            newStatusList = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValueList = getStatusValueList().getValue();
        if (newStatusValueList == null) {
            newStatusValueList = new ArrayList<>();
        }

        newStatusList.clear();
        newStatusValueList.clear();

        updateStatusList(newStatusList);
        updateStatusValueList(newStatusValueList);

        getDamageOverTime().clear();
        getDamageOverTimeValue().clear();

        setAttack(getAttack() * 2);
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
        setAttack(getMaxAttack());
    }

    //cleanse all enemy's status and heal over time and also stunning them
    private void skill2(Player hitter, Player target) {
        ArrayList<String> newStatusList = target.getStatusList().getValue();
        if (newStatusList == null) {
            newStatusList = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValueList = target.getStatusValueList().getValue();
        if (newStatusValueList == null) {
            newStatusValueList = new ArrayList<>();
        }

        newStatusList.clear();
        newStatusValueList.clear();

        target.updateStatusList(newStatusList);
        target.updateStatusValueList(newStatusValueList);

        target.getHealOverTime().clear();
        target.getHealOverTimeValue().clear();

        target.setStun(3);
    }

    //provides a temporary shield and damaging enemy at the same time
    private void skill3(Player hitter, Player target) {
        shieldPercentage *= 2;
        shield = 5;

        setAttack(getAttack() + 1230);
        setAttack(getAttack() * 2);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //petrify enemy and reduce their health to ash and their dodge while ignoring their defense
    private void skill4(Player hitter, Player target) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0f);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        enemyImage.setColorFilter(filter);

        enemyCurrentHealth = target.getHealth();
        target.setHealth(4000);
        target.setDefense(0);
        target.setDodge(0);

        petrification = 3;
    }

    //give enemy damage over time and hit them
    private void skill5(Player hitter, Player target)
    {
        target.receiveHit(hitter, target);

        target.getDamageOverTime().add(500);
        target.getDamageOverTimeValue().add(15);
    }
}
