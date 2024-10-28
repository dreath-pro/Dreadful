package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class DreadProphet extends Player {
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public DreadProphet(Context context, ImageView yourImage, Prompt prompt) {
        super(context, yourImage, "Dread Prophet", R.drawable.character_dread_prophet, "left", 210,
                null, null, 120000, 2888, 0, 0);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Dark Bolt");
        skillNames.add("Sixfold Judgement");
        skillNames.add("Reverse Prayer");
        skillNames.add("Sinful Retribution");
        skillNames.add("Spectral Choir");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(4);
        maxSkillCooldowns.add(7);
        maxSkillCooldowns.add(4);
        maxSkillCooldowns.add(6);
        updateMaxSkillCooldowns(maxSkillCooldowns);

        ArrayList<Integer> skillCooldowns = new ArrayList<>();
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        updateSkillCooldowns(skillCooldowns);

        this.context = context;
        this.prompt = prompt;
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
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Thou hast barely grazed mine bones, yet the stain of sin doth cling to thee.");
                dialogues.add("A sinner thou art, for to strike me is to strike at the very heart of the Abyss.");
                dialogues.add("Thy hand is weak, yet it doth reek of transgression.");
                dialogues.add("Thy feeble attempt is but a child's folly. Thou art marked by thy trespass.");
                dialogues.add("A scratch, no more. Yet the sin thou hast wrought shall linger.");
                dialogues.add("Such weakness... and yet thou hast sinned nonetheless.");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
            case 1:
                events.add("The Prophet grimaceth as thy blow maketh contact. His bones groan, but he doth remain firm. The weight of his judgment seems to pull the very light from the room, shadows dancing more wildly as though they hunger for thy soul.");
                events.add("A dark fog swirls around the " + getName() + " as the hit doth send a rattle through his form. His cold, empty gaze locketh upon thee, and the burden of guilt groweth heavier with every passing moment.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Thou striketh me in vain. Thy sin grows, and thy soul withers.");
                dialogues.add("Every blow thou landeth is an affront to the Abyss. Thou hast chosen thy path to damnation.");
                dialogues.add("Beware, for every strike condemns thee further, sinner.");
                dialogues.add("The Abyss seeth thy sin. None shall escape its grasp!");
                dialogues.add("Thou strikest in ignorance, hastening thine eternal doom!");
                dialogues.add("Thy sinful hand doth tremble before me, yet still thou seeketh to wound!");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
            case 2:
                events.add("The force of thy strike cracketh his skeletal frame, causing fragments of bone to scatter across the floor. The Prophet staggers slightly, but his hatred for thee doth only grow. The very air turns oppressive, as if the Abyss itself hath begun to claim thee.");
                events.add("The blow sends a visible tremor through the " + getName() + ", his ancient bones splintering under the weight of thy strike. The shadows around him deepen, growing more violent, and the sensation of dread fills the air like a smothering fog.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Thou hast wounded me, sinner, but thou hast also sealed thy doom!");
                dialogues.add("Stronger thou art, yet greater is thy sin! Thou art beyond redemption!");
                dialogues.add("A grievous sin hast thou committed! Thy soul shall rot in the Abyss for this!");
                dialogues.add("Every strike doth bind thee to the Abyss. Thy soul is forfeit!");
                dialogues.add("Foul sinner, thou shalt know the full wrath of the Abyss for this offense!");
                dialogues.add("Thy power is great, but thy sin is greater still! The Abyss awaiteth thee!");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
            case 3:
                events.add("Thy blow shattereth his form, breaking bones and tearing the shadows that cling to him. Yet the " + getName() + " doth not fall; his eyes blaze with fury unbound. The very earth trembleth, and the cathedral seemeth to shift as if the Abyss itself doth hunger for thy soul.");
                events.add("The Prophet staggers, his form unraveling under the might of thy attack. Darkness spills from the cracks in his bones, and an unearthly wail echoes through the cathedral. The shadows writhe as if alive, closing in upon thee, eager to claim thee for thy sins.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Blasphemer! Thou hast committed a sin most grave, and for it, thou shalt be devoured whole!");
                dialogues.add("Thou hast sinned beyond measure! The Abyss opens its maw to swallow thee whole!");
                dialogues.add("Thou art damned! The weight of thy sin shall drag thee to the Abyss, and none shall mourn thy fall!");
                dialogues.add("Thou fool! Thou hast brought ruin upon thyself. Thy soul is marked for the Abyss!");
                dialogues.add("A sin most grievous! None shall escape the dark, and thou art its next feast!");
                dialogues.add("The Abyss calls for thee, sinner! Thou hast earned thy place in its depths!");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
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
                events.add("The " + getName() + " raiseth his skeletal hand, and between his fingers, a bolt of pure darkness doth streak toward " + target.getName() + ". The attack cannot be dodged, its path certain and unyielding. Though defenses may lessen its sting, it feels the cold embrace of the Abyss biting deep into their form.");
                events.add("A tendril of shadow doth lash forth from the " + getName() + prompt.getApostrophe(getName()) + " outstretched arm, crackling with dark energy. The bolt flies straight, swift and sure, unhindered by " + target.getName() + prompt.getApostrophe(target.getName()) + " attempts to evade. The force of the Abyss itself bears down upon " + target.getName() + ", its touch inescapable, though dampened by their formidable defense.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Run if thou must, but the shadows shall always find thee, " + target.getName() + ".");
                dialogues.add("Feel the weight of the Abyss, for no dodge shall save thee from thy fate!");
                dialogues.add("Mine dark bolt dost seek thee, for none may escape judgment eternal.");
                dialogues.add("Futile are thy escape, for the Abyss giveth no quarter to thee.");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                basicAttack(hitter, target);
                break;
            case 1:
                events.add("The " + getName() + " lifts his skeletal arms to the heavens, channeling the malevolence of the Abyss. Shadows twist and converge around him as six dark bolts form, each crackling with sinister energy. In a blinding flash, the bolts are unleashed, striking " + target.getName() + " with the weight of accumulated sins, each impact a chilling reminder of their transgressions.");
                events.add("With a voice that echoes like thunder, the " + getName() + " intones the words of the Abyss. Six dark tendrils erupt from his outstretched hands, each one a swift harbinger of doom. The air fills with a suffocating darkness as they dart toward " + target.getName() + ", each strike resonating with the fury of judgment, leaving a trail of despair in their wake.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Behold the reckoning! " + skillName + " shall seal thy fate!");
                dialogues.add("Thy sins multiply, and now they shall return to thee in force!");
                dialogues.add("I call upon the depths of despair! Six strikes shall rend thee!");
                dialogues.add("The Abyss hath deemed thee worthy of judgment; prepare for thy end!");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                skill1(hitter, target);
                break;
            case 2:
                events.add("The " + getName() + " raises his skeletal arms to the heavens, chanting a dark incantation that twists the very essence of prayer. As light begins to shimmer around " + target.getName() + ", the glow shifts, turning into a sinister dark aura that wraps around them. The energy that once sought to mend now gnaws at their vitality, converting hope into an unyielding torment.");
                events.add("With a voice resonating from the depths of the Abyss, the " + getName() + " invokes the power of " + skillName + ". Shadows swirl around him, forming a sinister vortex as the healing energies intended for " + target.getName() + " are siphoned away, transforming into a creeping malaise. Each heartbeat brings renewed agony, as what was meant to restore now drags them deeper into despair.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Thy feeble attempts to heal shall serve only to strengthen my wrath!");
                dialogues.add("In the Abyss, the prayers of the unworthy turn to curses!");
                dialogues.add("Let thy lifeblood flow as I twist thy hopes into despair!");
                dialogues.add("What thou deemest healing, I shall transform into thy undoing!");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                skill2(hitter, target);
                break;
            case 3:
                events.add("The " + getName() + prompt.getApostrophe(getName()) + " hollow gaze fixeth upon thee, a chilling smile spreading across his bony visage. As he raises a skeletal hand, a dark sigil materializeth upon thy flesh, the Mark of Sin glimmering ominously. In that moment, a sense of dread envelopeth thee, and the Prophet’s voice echoes, promising that thy sins shall return with unrelenting force.");
                events.add("With a low, rumbling chant, the " + getName() + " conjureth the dark energies of " + skillName + ". The very air crackles with foreboding as the mark upon thee pulsates with an eerie light. A jolt of pain courses through thy veins, and with each heartbeat, the weight of thy transgressions presseth down, stealing thy vitality and turning it against thee, binding thee to the judgment of the Abyss.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("For every strike thou landest, a mark of sin doth grow—now feel its wrath!");
                dialogues.add("Thou thinkest to harm me? Know this: thy sins shall return to thee tenfold!");
                dialogues.add("Thy blood shall pay the price for thy transgressions! " + skillName + " is at hand!");
                dialogues.add("The Abyss doth not forget; every wound thou causeth doth lead to thy own doom!");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                skill3(hitter, target);
                break;
            case 4:
                events.add("With a skeletal hand raised high, the " + getName() + " calleth forth the lost souls of the Abyss. Their voices, twisted in eternal worship, rise as one in a harrowing chant, singing praises to their dark shepherd. The air thickens with the weight of their hymn, as the Mark of Sin upon the enemy shineth bright. The deeper their transgressions, the stronger the choir's fervor, healing the Prophet with each mournful verse.");
                events.add("The very walls of the Abyssal Cathedral tremble as the " + skillName + " raiseth their voices in dark worship. Bound in torment, the souls of the Abyss sing forth a hymn of reverence, their voices full of anguish and praise for the " + getName() + ". With each note, the Mark of Sin doth burn brighter upon the foe, and the Prophet's form is bathed in the unholy light of their worship, his wounds mended by their undying devotion.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Hark! The souls of the damned doth singeth mine praise, their torment healeth mine flesh!");
                dialogues.add("Thee shalt hear their woeful song; for their suffering is mine salvation!");
                dialogues.add("In agony they sing, and through their sin, I am made whole!");
                dialogues.add("Thee canst not silence the hymn of the Abyss, for it doth grant me life everlasting!");
                if (prompt.selectRandomDialogue(this, dialogues, true)) {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                skill4(hitter, target);
                break;
        }

        newSkillCooldowns = getMaxSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        ArrayList<Integer> newMaxSkillCooldowns = getMaxSkillCooldowns().getValue();
        if (newMaxSkillCooldowns == null) {
            newMaxSkillCooldowns = new ArrayList<>();
        }

        for (int i = 0; i <= newMaxSkillCooldowns.size() - 1; i++) {
            if (newSkillCooldowns.get(i) > 0) {
                newSkillCooldowns.set(i, newSkillCooldowns.get(i) - 1);
                if (newSkillCooldowns.get(i) <= 0) {
                    newSkillCooldowns.set(i, 0);
                }

                updateSkillCooldowns(newSkillCooldowns);
            }
        }

        newSkillCooldowns = getMaxSkillCooldowns().getValue();
        newMaxSkillCooldowns = getMaxSkillCooldowns().getValue();

        newSkillCooldowns.set(skillIndex, newMaxSkillCooldowns.get(skillIndex));
        updateSkillCooldowns(newSkillCooldowns);

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
    //reduce percentage of attacker current health base on the value of mark of sin
    private void skill3(Player hitter, Player target) {
        target.receiveHit(hitter, target);

        if (!hasStatus(target, "Mark of Sin", 50).isEmpty()) {
            ArrayList<Integer> newStatusValue = target.getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            int index = Integer.parseInt(hasStatus(target, "Mark of Sin", 50));
            int maxHealth = target.getHealth();
            int percentage = newStatusValue.get(index);
            int damage = (maxHealth * percentage) / 100;

            setAttack(damage);
            target.receiveHit(hitter, target);
            setAttack(getMaxAttack());
        }
    }

    //heal over time for a short turn, base on the value of the "mark of sin"
    private void skill4(Player hitter, Player target) {
        if (!hasStatus(target, "Mark of Sin", 50).isEmpty()) {
            ArrayList<Integer> newStatusValue = target.getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            int index = Integer.parseInt(hasStatus(target, "Mark of Sin", 50));
            int maxHealth = 2100;
            int percentage = newStatusValue.get(index);
            int heal = (maxHealth * percentage) / 100;

            getDamageOverTime().add(heal);
            getDamageOverTimeValue().add(9);
        } else {
            getDamageOverTime().add(1800);
            getDamageOverTimeValue().add(9);
        }
    }
}
