package com.example.dreadful.characters;

import android.content.Context;
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
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public Michael(Context context, ImageView yourImage, Prompt prompt) {
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
        maxSkillCooldowns.add(7);
        maxSkillCooldowns.add(3);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(5);
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
                events.add(getName() + " uses his sharp sword to hack " + target.getName());
                events.add(getName() + " swings his deadly sword.");
                events.add(getName() + " attacks " + target.getName() + " with malice.");
                events.add(getName() + " quick slashes his sword.");

                dialogues.add("Your fate was sealed the moment you crossed me!");

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
                events.add(getName() + " tries to brutally dismember " + target.getName() + " alive.");
                events.add(getName() + " tries to chop " + target.getName() + " to pieces.");
                events.add(getName() + " does a violent amputation on " + target.getName() + ".");
                events.add(getName() + " ruthlessly destroying " + target.getName() + prompt.getApostrophe(target.getName()));

                dialogues.add("With every strike, I carve your fate!");

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
                events.add(getName() + " hacks his opponent with pure rage");
                events.add(getName() + " stabbing " + target.getName() + " multiple times.");
                events.add(getName() + " ruthlessly torturing " + target.getName() + ".");

                dialogues.add("Prepare to meet your end!");

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
                events.add(getName() + " tries to gut " + target.getName() + ".");
                events.add(getName() + " thirsty bloody attacks " + target.getName() + " from the insides.");
                events.add(getName() + prompt.getApostrophe(getName()) + "guts and gore attack.");
                events.add(getName() + " tries to disembowel " + target.getName() + ".");

                dialogues.add("Prepare to meet your end!");
                dialogues.add("Prepare for annihilation!");
                dialogues.add("Let my blade guide you to oblivion!");

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
                events.add(getName() + " tries to attack " + target.getName() + prompt.getApostrophe(target.getName()) + "organs and recovers his lost blood.");
                events.add(getName() + " eviscerated " + target.getName() + " and devours internal organs to recover");
                events.add(getName() + " tries to gut " + target.getName() + " with his sharp sword.");
                events.add(getName() + " does a gory attack on " + target.getName() + prompt.getApostrophe(target.getName()) + " intestines.");

                dialogues.add("Prepare to meet your end!");
                dialogues.add("Prepare for annihilation!");
                dialogues.add("Let my blade guide you to oblivion!");

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
        }

        reduceCooldown(skillIndex);
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
        ArrayList<Integer> newSkillCooldowns = target.getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        for (int i = 1; i <= newSkillCooldowns.size() - 1; i++) {
            newSkillCooldowns.set(i, newSkillCooldowns.get(i) + 5);
            target.updateMaxSkillCooldowns(newSkillCooldowns);
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
