package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class DreadProphet extends Player {
    private Random random = new Random();
    private Prompt prompt;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public DreadProphet(Context context, ImageView yourImage, TestActivity testActivity) {
        super(context, yourImage, "Dread Prophet", R.drawable.character_dread_prophet, "left", 210,
                null, null,
                120000, 2888, 0, 0,
                new String[]{"Dark Bolt", "Sixfold Judgement", "Reverse Prayer", "Sinful Retribution", "Spectral Choir"},
                new int[]{0, 4, 7, 4, 6}, new int[]{0, 0, 0, 0, 0});

        this.prompt = new Prompt(testActivity);
        this.prompt = testActivity.getPrompt();
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                events.add("The " + getName() + " doth not so much as waver, yet his eyes lock onto thee as though weighing thy soul. Shadows creep at the edges of the cathedral, the faint whispers of the damned stirring as they mark thy sin.");
                events.add("The Prophet’s skeletal hands remain steady, and he doth barely acknowledge thy strike. But a chill setteth in the air, as though the Abyss itself hath taken note of the sinner in its midst.");

                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Thou hast barely grazed mine bones, yet the stain of sin doth cling to thee.");
                dialogues.add("A sinner thou art, for to strike me is to strike at the very heart of the Abyss.");
                dialogues.add("Thy hand is weak, yet it doth reek of transgression.");
                dialogues.add("Thy feeble attempt is but a child's folly. Thou art marked by thy trespass.");
                dialogues.add("A scratch, no more. Yet the sin thou hast wrought shall linger.");
                dialogues.add("Such weakness... and yet thou hast sinned nonetheless.");
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
            case 1:
                events.add("The Prophet grimaceth as thy blow maketh contact. His bones groan, but he doth remain firm. The weight of his judgment seems to pull the very light from the room, shadows dancing more wildly as though they hunger for thy soul.");
                events.add("A dark fog swirls around the " + getName() + " as the hit doth send a rattle through his form. His cold, empty gaze locketh upon thee, and the burden of guilt groweth heavier with every passing moment.");
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Thou striketh me in vain. Thy sin grows, and thy soul withers.");
                dialogues.add("Every blow thou landeth is an affront to the Abyss. Thou hast chosen thy path to damnation.");
                dialogues.add("Beware, for every strike condemns thee further, sinner.");
                dialogues.add("The Abyss seeth thy sin. None shall escape its grasp!");
                dialogues.add("Thou strikest in ignorance, hastening thine eternal doom!");
                dialogues.add("Thy sinful hand doth tremble before me, yet still thou seeketh to wound!");
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
            case 2:
                events.add("The force of thy strike cracketh his skeletal frame, causing fragments of bone to scatter across the floor. The Prophet staggers slightly, but his hatred for thee doth only grow. The very air turns oppressive, as if the Abyss itself hath begun to claim thee.");
                events.add("The blow sends a visible tremor through the " + getName() + ", his ancient bones splintering under the weight of thy strike. The shadows around him deepen, growing more violent, and the sensation of dread fills the air like a smothering fog.");
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Thou hast wounded me, sinner, but thou hast also sealed thy doom!");
                dialogues.add("Stronger thou art, yet greater is thy sin! Thou art beyond redemption!");
                dialogues.add("A grievous sin hast thou committed! Thy soul shall rot in the Abyss for this!");
                dialogues.add("Every strike doth bind thee to the Abyss. Thy soul is forfeit!");
                dialogues.add("Foul sinner, thou shalt know the full wrath of the Abyss for this offense!");
                dialogues.add("Thy power is great, but thy sin is greater still! The Abyss awaiteth thee!");
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
            case 3:
                events.add("Thy blow shattereth his form, breaking bones and tearing the shadows that cling to him. Yet the " + getName() + " doth not fall; his eyes blaze with fury unbound. The very earth trembleth, and the cathedral seemeth to shift as if the Abyss itself doth hunger for thy soul.");
                events.add("The Prophet staggers, his form unraveling under the might of thy attack. Darkness spills from the cracks in his bones, and an unearthly wail echoes through the cathedral. The shadows writhe as if alive, closing in upon thee, eager to claim thee for thy sins.");
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Blasphemer! Thou hast committed a sin most grave, and for it, thou shalt be devoured whole!");
                dialogues.add("Thou hast sinned beyond measure! The Abyss opens its maw to swallow thee whole!");
                dialogues.add("Thou art damned! The weight of thy sin shall drag thee to the Abyss, and none shall mourn thy fall!");
                dialogues.add("Thou fool! Thou hast brought ruin upon thyself. Thy soul is marked for the Abyss!");
                dialogues.add("A sin most grievous! None shall escape the dark, and thou art its next feast!");
                dialogues.add("The Abyss calls for thee, sinner! Thou hast earned thy place in its depths!");
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
        }
    }

    /**
     * everytime an attacker hits dread prophet, they will be mark with sin, and every attack it will add
     * 10 mark of sin
     */
    public void receiveHit(Player hitter, Player target) {
        String result = receiveHitLogic(hitter, target);
        switch (result) {
            case "DODGE":
            case "BLOCKED":
            case "":

                break;
            default:
                damageExpression(prompt.measureDamage(Integer.parseInt(result)));
                break;
        }
        receiveStatus(hitter, "Mark of Sin", 10);
    }

    /**
     * the way he is receiving damage is different from other characters
     * the damage overtime received from the opponent will be convert into his own heal over time
     * the heal overtime he receive will be useless and be ignore, only damage over time is his way of healing
     */
    public void receiveTimeEffect(Player hitter, Player target) {
        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for (int i = 0; i <= getDamageOverTime().size() - 1; i++) {
            if (getDamageOverTimeValue().get(i) > 0) {
                setHealth(getHealth() + getDamageOverTime().get(i));
                getDamageOverTimeValue().set(i, getDamageOverTimeValue().get(i) - 1);

                tempDot.add(getDamageOverTime().get(i));
                tempDotValue.add(getDamageOverTimeValue().get(i));
            }
        }

        setDamageOverTime(tempDot);
        setDamageOverTimeValue(tempDotValue);

        runTimeHeal();
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

    //this attack is undodgeable but can be reduce by opponent's defense
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(target.getMaxDodge());
    }

    //simple burst attack
    private void skill1(Player hitter, Player target) {
        setAttack(getAttack() + 8450);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());
    }

    //enemy healing will be reverse and convert to damage
    private void skill2(Player hitter, Player target) {
        int allHeal = 0;
        for (int i = 0; i <= target.getHealOverTime().size() - 1; i++) {
            allHeal += target.getHealOverTime().get(i);
        }

        target.getHealOverTime().clear();
        target.getHealOverTimeValue().clear();

        target.getDamageOverTime().add(allHeal);
        target.getDamageOverTime().add(450);
        target.getDamageOverTimeValue().add(15);
        target.getDamageOverTimeValue().add(15);
    }

    //retribution for sinner hitter/attacker, base on the value of the "mark of sin"
    //reduce 50% of attacker current health
    private void skill3(Player hitter, Player target) {
        target.receiveHit(hitter, target);

        if (!hasStatus(target, "Mark of Sin", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(target, "Mark of Sin", 50));
            int maxHealth = target.getHealth();
            int percentage = target.getStatusValue().get(index);
            int damage = (maxHealth * percentage) / 100;

            setAttack(damage);
            target.receiveHit(hitter, target);
            setAttack(getMaxAttack());
        }
    }

    //heal over time for a short turn, base on the value of the "mark of sin"
    private void skill4(Player hitter, Player target) {
        if (!hasStatus(target, "Mark of Sin", 50).isEmpty()) {
            int index = Integer.parseInt(hasStatus(target, "Mark of Sin", 50));
            int maxHealth = 2100;
            int percentage = target.getStatusValue().get(index);
            int heal = (maxHealth * percentage) / 100;

            getDamageOverTime().add(heal);
            getDamageOverTimeValue().add(9);
        }else
        {
            getDamageOverTime().add(1800);
            getDamageOverTimeValue().add(9);
        }
    }
}
