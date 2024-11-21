package com.example.dreadful.characters;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.adapters.ViewPrompt;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.logics.NumberComma;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Dreath extends Player {
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private NumberComma numberComma = new NumberComma();
    private MonsterDatabase monsterDatabase;

    public Dreath(Context context, ImageView yourImage, Prompt prompt) {
        super(context, yourImage, "Dreath", R.drawable.character_dreath, "left", 150,
                null, null, 88070, 2580, 880, 0);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Butcher");
        skillNames.add("Dismember");
        skillNames.add("Ruthless Torture");
        skillNames.add("Brutal Gut");
        skillNames.add("Evisceration");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(7);
        maxSkillCooldowns.add(3);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(5);
        updateMaxSkillCooldowns(maxSkillCooldowns);

        ArrayList<Integer> skillCooldowns = new ArrayList<>();
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
                events.add(getName() + " shrugs off the attack, barely feeling its impact.");
                events.add("The hit lands, but " + getName() + " remains unfazed, a mere flicker of annoyance crossing his face.");
                events.add(getName() + " glances at his attacker, unimpressed by the weak strike.");

                dialogues.add("A nuisance at best.");
                dialogues.add("You’ll have to try harder than that.");

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
                events.add("The blow lands solidly, yet " + getName() + prompt.getApostrophe(getName()) + " expression reveals no pain, only a cold determination.");
                events.add("The attack connects, but " + getName() + " stands firm, his resolve unshaken.");

                dialogues.add("Pain is merely a distraction.");
                dialogues.add("Interesting... I expected more.");
                dialogues.add("I felt that, but it won’t change the outcome.");

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
                events.add("The strike forces " + getName() + " back slightly, but he quickly regains his composure, undeterred.");
                events.add(getName() + " is momentarily taken aback by the force of the blow, yet his icy demeanor remains intact.");
                events.add("Though the attack stings, " + getName() + " assesses his opponent with a cold glare, plotting his next move.");

                dialogues.add("That hit had some weight. Still irrelevant.");
                dialogues.add("Impressive, but ultimately inconsequential.");
                dialogues.add("You’ve gained my attention, but not my concern.");
                dialogues.add("A more respectable effort, but futile.");

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
                events.add(getName() + " stumbles from the force of the blow, but even as he regains his footing, his eyes burn with defiance.");
                events.add("Despite the severe hit, " + getName() + prompt.getApostrophe(getName()) + " expression remains stoic; he is far from finished.");
                events.add("The pain is evident, but " + getName() + prompt.getApostrophe(getName()) + " resolve hardens; he will not yield to weakness.");

                dialogues.add("Pain is temporary; this fight is eternal.");
                dialogues.add("I won’t be defeated by a mere scratch.");
                dialogues.add("You’ve done well, but it won’t matter in the end.");
                dialogues.add("You think you’ve won? I’m just getting started.");

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

                break;
            case "BLOCKED":
                events.add("But " + getName() + " blocks the attack with ease.");
                events.add("But " + getName() + " is immune to that attack.");
                events.add("The attack has no effect on " + getName() + ".");
                events.add("That hit tickles " + getName() + prompt.getApostrophe(getName()) + "hard armor.");

                dialogues.add("You’ll need a stronger attack to get past me!");

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

        receiveStatus(target, "Rage", 10);
        if (!hasStatus(target, "Rage", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(target, "Rage", 50));
            ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            ArrayList<Integer> targetStatusValue = target.getStatusValueList().getValue();
            if (targetStatusValue == null) {
                targetStatusValue = new ArrayList<>();
            }

            if (getHealth() <= 0) {
                setHealth(56780);

                int damage = (hitter.getHealth() * targetStatusValue.get(index)) / 100;
                setAttack(damage);

                hitter.setDefense(0);
                hitter.setDodge(0);

                hitter.receiveHit(hitter, target);

                hitter.setDodge(hitter.getMaxDodge());
                hitter.setDefense(hitter.getMaxDefense());
                setAttack(getMaxAttack());
                newStatusValue.set(index, newStatusValue.get(index) - 50);

                updateStatusValueList(newStatusValue);

                dialogues.add("I am fear itself, unleashed upon you!");
                dialogues.add("Fear does not die... and neither do I!");
                dialogues.add("I am the fear that never fades!");
                dialogues.add("I am fear!");
                dialogues.add("You fear death? Fear me more.");
                dialogues.add("Fear me, for I am the last thing you will ever see.");
                dialogues.add("Fear is eternal, and now... so am I.");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.red));
                prompt.selectRandomMessage(this, dialogues, true);
                dialogues.clear();

                events.add("Warning: A global emergency has occurred; please follow local authorities' instructions and stay safe.");
                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.red));
                prompt.selectRandomMessage(this, events, false);
                events.clear();


                events.add(numberComma.convertComma(random.nextInt(900000000) + 150000) + " lives perished across the globe from this aura");
                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.red));
                prompt.selectRandomMessage(this, events, false);
                events.clear();
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
            ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            if (getHealth() <= 0) {
                setHealth(35700);
                setAttack(9000);
                hitter.setDodge(0);

                hitter.receiveHit(hitter, target);

                hitter.setDodge(hitter.getMaxDodge());
                setAttack(getMaxAttack());
                newStatusValue.set(index, newStatusValue.get(index) - 50);
                updateStatusValueList(newStatusValue);
            }
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
