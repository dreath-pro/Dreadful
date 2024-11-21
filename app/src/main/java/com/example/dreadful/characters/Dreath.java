package com.example.dreadful.characters;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.logics.NumberComma;
import com.example.dreadful.models.Monster;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Dreath extends Monster {
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
    public void receiveHit(Monster enemy, Monster you) {
        String result = receiveHitLogic(enemy, you);
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

        receiveStatus(you, "Rage", 10);
        if (!hasStatus(you, "Rage", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(you, "Rage", 50));
            ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            ArrayList<Integer> targetStatusValue = you.getStatusValueList().getValue();
            if (targetStatusValue == null) {
                targetStatusValue = new ArrayList<>();
            }

            if (getHealth() <= 0) {
                setHealth(56780);

                int damage = (enemy.getHealth() * targetStatusValue.get(index)) / 100;
                setAttack(damage);

                enemy.setDefense(0);
                enemy.setDodge(0);

                enemy.receiveHit(you, enemy);

                enemy.setDodge(enemy.getMaxDodge());
                enemy.setDefense(enemy.getMaxDefense());
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
    public void receiveTimeEffect(Monster enemy, Monster you) {
        runTimeHeal();
        runTimeDamage();

        if (!hasStatus(you, "Rage", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(you, "Rage", 50));
            ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            if (getHealth() <= 0) {
                setHealth(35700);
                setAttack(9000);
                enemy.setDodge(0);

                enemy.receiveHit(you, enemy);

                enemy.setDodge(enemy.getMaxDodge());
                setAttack(getMaxAttack());
                newStatusValue.set(index, newStatusValue.get(index) - 50);
                updateStatusValueList(newStatusValue);
            }
        }
    }

    public String useRandomAttack(Monster you, Monster enemy) {
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
                events.add(getName() + " uses his sharp sword to hack " + enemy.getName());
                events.add(getName() + " swings his deadly sword.");
                events.add(getName() + " attacks " + enemy.getName() + " with malice.");
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

                basicAttack(you, enemy);
                break;
            case 1:
                events.add(getName() + " tries to brutally dismember " + enemy.getName() + " alive.");
                events.add(getName() + " tries to chop " + enemy.getName() + " to pieces.");
                events.add(getName() + " does a violent amputation on " + enemy.getName() + ".");
                events.add(getName() + " ruthlessly destroying " + enemy.getName() + prompt.getApostrophe(enemy.getName()));

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

                skill1(you, enemy);
                break;
            case 2:
                events.add(getName() + " hacks his opponent with pure rage");
                events.add(getName() + " stabbing " + enemy.getName() + " multiple times.");
                events.add(getName() + " ruthlessly torturing " + enemy.getName() + ".");

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

                skill2(you, enemy);
                break;
            case 3:
                events.add(getName() + " tries to gut " + enemy.getName() + ".");
                events.add(getName() + " thirsty bloody attacks " + enemy.getName() + " from the insides.");
                events.add(getName() + prompt.getApostrophe(getName()) + "guts and gore attack.");
                events.add(getName() + " tries to disembowel " + enemy.getName() + ".");

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

                skill3(you, enemy);
                break;
            case 4:
                events.add(getName() + " tries to attack " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + "organs and recovers his lost blood.");
                events.add(getName() + " eviscerated " + enemy.getName() + " and devours internal organs to recover");
                events.add(getName() + " tries to gut " + enemy.getName() + " with his sharp sword.");
                events.add(getName() + " does a gory attack on " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " intestines.");

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

                skill4(you, enemy);
                break;
        }

        reduceCooldown(skillIndex);
        return skillName;
    }

    //attack that will ignore defense
    @Override
    public void basicAttack(Monster you, Monster enemy) {
        enemy.setDefense(0);
        enemy.receiveHit(you, enemy);
        enemy.setDefense(enemy.getMaxDefense());
    }

    //prevent enemy from using all skills
    private void skill1(Monster you, Monster enemy) {
        ArrayList<Integer> newSkillCooldowns = enemy.getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        for (int i = 1; i <= newSkillCooldowns.size() - 1; i++) {
            newSkillCooldowns.set(i, newSkillCooldowns.get(i) + 5);
            enemy.updateMaxSkillCooldowns(newSkillCooldowns);
        }

        setAttack(getAttack() + 6500);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());
    }

    //enemy cannot dodge all incoming attacks
    private void skill2(Monster you, Monster enemy) {
        enemy.setDodge(0);
        setAttack(getAttack() + 5000);

        enemy.receiveHit(you, enemy);

        setAttack(getMaxAttack());
        enemy.setDodge(enemy.getMaxDodge());
    }

    //high burst
    private void skill3(Monster you, Monster enemy) {
        setAttack(getAttack() + 8870);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());
    }

    //heal and increase damage
    private void skill4(Monster you, Monster enemy) {
        setHealth(getHealth() + 10000);
        setAttack(getAttack() + 11000);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());
    }
}
