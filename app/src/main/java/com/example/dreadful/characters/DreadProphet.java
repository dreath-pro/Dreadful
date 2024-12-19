package com.example.dreadful.characters;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.models.Monster;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class DreadProphet extends Monster {
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private MonsterDatabase monsterDatabase;

    public DreadProphet(Context context) {
        super("002-DRED", context, null, "Dread Prophet", R.drawable.character_dread_prophet, "left", 210,
                null, null, 120000, 2888, 0, 0);
    }

    public DreadProphet(Context context, ImageView yourImage, Prompt prompt) {
        super("002-DRED", context, yourImage, "Dread Prophet", R.drawable.character_dread_prophet, "left", 210,
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
        this.monsterDatabase = new MonsterDatabase(context);
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

                dialogues.add("Thou hast barely grazed mine bones, yet the stain of sin doth cling to thee.");
                dialogues.add("A sinner thou art, for to strike me is to strike at the very heart of the Abyss.");
                dialogues.add("Thy hand is weak, yet it doth reek of transgression.");
                dialogues.add("Thy feeble attempt is but a child's folly. Thou art marked by thy trespass.");
                dialogues.add("A scratch, no more. Yet the sin thou hast wrought shall linger.");
                dialogues.add("Such weakness... and yet thou hast sinned nonetheless.");

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
                events.add("The Prophet grimaceth as thy blow maketh contact. His bones groan, but he doth remain firm. The weight of his judgment seems to pull the very light from the room, shadows dancing more wildly as though they hunger for thy soul.");
                events.add("A dark fog swirls around the " + getName() + " as the hit doth send a rattle through his form. His cold, empty gaze locketh upon thee, and the burden of guilt groweth heavier with every passing moment.");

                dialogues.add("Thou striketh me in vain. Thy sin grows, and thy soul withers.");
                dialogues.add("Every blow thou landeth is an affront to the Abyss. Thou hast chosen thy path to damnation.");
                dialogues.add("Beware, for every strike condemns thee further, sinner.");
                dialogues.add("The Abyss seeth thy sin. None shall escape its grasp!");
                dialogues.add("Thou strikest in ignorance, hastening thine eternal doom!");
                dialogues.add("Thy sinful hand doth tremble before me, yet still thou seeketh to wound!");

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
                events.add("The force of thy strike cracketh his skeletal frame, causing fragments of bone to scatter across the floor. The Prophet staggers slightly, but his hatred for thee doth only grow. The very air turns oppressive, as if the Abyss itself hath begun to claim thee.");
                events.add("The blow sends a visible tremor through the " + getName() + ", his ancient bones splintering under the weight of thy strike. The shadows around him deepen, growing more violent, and the sensation of dread fills the air like a smothering fog.");

                dialogues.add("Thou hast wounded me, sinner, but thou hast also sealed thy doom!");
                dialogues.add("Stronger thou art, yet greater is thy sin! Thou art beyond redemption!");
                dialogues.add("A grievous sin hast thou committed! Thy soul shall rot in the Abyss for this!");
                dialogues.add("Every strike doth bind thee to the Abyss. Thy soul is forfeit!");
                dialogues.add("Foul sinner, thou shalt know the full wrath of the Abyss for this offense!");
                dialogues.add("Thy power is great, but thy sin is greater still! The Abyss awaiteth thee!");

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
                events.add("Thy blow shattereth his form, breaking bones and tearing the shadows that cling to him. Yet the " + getName() + " doth not fall; his eyes blaze with fury unbound. The very earth trembleth, and the cathedral seemeth to shift as if the Abyss itself doth hunger for thy soul.");
                events.add("The Prophet staggers, his form unraveling under the might of thy attack. Darkness spills from the cracks in his bones, and an unearthly wail echoes through the cathedral. The shadows writhe as if alive, closing in upon thee, eager to claim thee for thy sins.");

                dialogues.add("Blasphemer! Thou hast committed a sin most grave, and for it, thou shalt be devoured whole!");
                dialogues.add("Thou hast sinned beyond measure! The Abyss opens its maw to swallow thee whole!");
                dialogues.add("Thou art damned! The weight of thy sin shall drag thee to the Abyss, and none shall mourn thy fall!");
                dialogues.add("Thou fool! Thou hast brought ruin upon thyself. Thy soul is marked for the Abyss!");
                dialogues.add("A sin most grievous! None shall escape the dark, and thou art its next feast!");
                dialogues.add("The Abyss calls for thee, sinner! Thou hast earned thy place in its depths!");

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
            monsterDatabase.addMonster(this);
        }
    }

    /**
     * everytime an attacker hits dread prophet, they will be mark with sin, and every attack it will add
     * 10 mark of sin
     */
    public void receiveHit(Monster enemy, Monster you) {
        String result = receiveHitLogic(enemy, you);
        switch (result) {
            case "DODGE":
            case "BLOCKED":
            case "":

                break;
            default:
                damageExpression(prompt.measureDamage(Integer.parseInt(result)));
                break;
        }
        receiveStatus(enemy, "Mark of Sin", 10);
    }

    /**
     * the way he is receiving damage is different from other characters
     * the damage overtime received from the opponent will be convert into his own heal over time
     * the heal overtime he receive will be useless and be ignore, only damage over time is his way of healing
     */
    public void receiveTimeEffect(Monster enemy, Monster you) {
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
                events.add("The " + getName() + " raiseth his skeletal hand, and between his fingers, a bolt of pure darkness doth streak toward " + enemy.getName() + ". The attack cannot be dodged, its path certain and unyielding. Though defenses may lessen its sting, it feels the cold embrace of the Abyss biting deep into their form.");
                events.add("A tendril of shadow doth lash forth from the " + getName() + prompt.getApostrophe(getName()) + " outstretched arm, crackling with dark energy. The bolt flies straight, swift and sure, unhindered by " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " attempts to evade. The force of the Abyss itself bears down upon " + enemy.getName() + ", its touch inescapable, though dampened by their formidable defense.");

                dialogues.add("Run if thou must, but the shadows shall always find thee, " + enemy.getName() + ".");
                dialogues.add("Feel the weight of the Abyss, for no dodge shall save thee from thy fate!");
                dialogues.add("Mine dark bolt dost seek thee, for none may escape judgment eternal.");
                dialogues.add("Futile are thy escape, for the Abyss giveth no quarter to thee.");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                basicAttack(you, enemy);
                break;
            case 1:
                events.add("The " + getName() + " lifts his skeletal arms to the heavens, channeling the malevolence of the Abyss. Shadows twist and converge around him as six dark bolts form, each crackling with sinister energy. In a blinding flash, the bolts are unleashed, striking " + enemy.getName() + " with the weight of accumulated sins, each impact a chilling reminder of their transgressions.");
                events.add("With a voice that echoes like thunder, the " + getName() + " intones the words of the Abyss. Six dark tendrils erupt from his outstretched hands, each one a swift harbinger of doom. The air fills with a suffocating darkness as they dart toward " + enemy.getName() + ", each strike resonating with the fury of judgment, leaving a trail of despair in their wake.");

                dialogues.add("Behold the reckoning! " + skillName + " shall seal thy fate!");
                dialogues.add("Thy sins multiply, and now they shall return to thee in force!");
                dialogues.add("I call upon the depths of despair! Six strikes shall rend thee!");
                dialogues.add("The Abyss hath deemed thee worthy of judgment; prepare for thy end!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill1(you, enemy);
                break;
            case 2:
                events.add("The " + getName() + " raises his skeletal arms to the heavens, chanting a dark incantation that twists the very essence of prayer. As light begins to shimmer around " + enemy.getName() + ", the glow shifts, turning into a sinister dark aura that wraps around them. The energy that once sought to mend now gnaws at their vitality, converting hope into an unyielding torment.");
                events.add("With a voice resonating from the depths of the Abyss, the " + getName() + " invokes the power of " + skillName + ". Shadows swirl around him, forming a sinister vortex as the healing energies intended for " + enemy.getName() + " are siphoned away, transforming into a creeping malaise. Each heartbeat brings renewed agony, as what was meant to restore now drags them deeper into despair.");

                dialogues.add("Thy feeble attempts to heal shall serve only to strengthen my wrath!");
                dialogues.add("In the Abyss, the prayers of the unworthy turn to curses!");
                dialogues.add("Let thy lifeblood flow as I twist thy hopes into despair!");
                dialogues.add("What thou deemest healing, I shall transform into thy undoing!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill2(you, enemy);
                break;
            case 3:
                events.add("The " + getName() + prompt.getApostrophe(getName()) + " hollow gaze fixeth upon thee, a chilling smile spreading across his bony visage. As he raises a skeletal hand, a dark sigil materializeth upon thy flesh, the Mark of Sin glimmering ominously. In that moment, a sense of dread envelopeth thee, and the Prophet’s voice echoes, promising that thy sins shall return with unrelenting force.");
                events.add("With a low, rumbling chant, the " + getName() + " conjureth the dark energies of " + skillName + ". The very air crackles with foreboding as the mark upon thee pulsates with an eerie light. A jolt of pain courses through thy veins, and with each heartbeat, the weight of thy transgressions presseth down, stealing thy vitality and turning it against thee, binding thee to the judgment of the Abyss.");

                dialogues.add("For every strike thou landest, a mark of sin doth grow—now feel its wrath!");
                dialogues.add("Thou thinkest to harm me? Know this: thy sins shall return to thee tenfold!");
                dialogues.add("Thy blood shall pay the price for thy transgressions! " + skillName + " is at hand!");
                dialogues.add("The Abyss doth not forget; every wound thou causeth doth lead to thy own doom!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill3(you, enemy);
                break;
            case 4:
                events.add("With a skeletal hand raised high, the " + getName() + " calleth forth the lost souls of the Abyss. Their voices, twisted in eternal worship, rise as one in a harrowing chant, singing praises to their dark shepherd. The air thickens with the weight of their hymn, as the Mark of Sin upon the enemy shineth bright. The deeper their transgressions, the stronger the choir's fervor, healing the Prophet with each mournful verse.");
                events.add("The very walls of the Abyssal Cathedral tremble as the " + skillName + " raiseth their voices in dark worship. Bound in torment, the souls of the Abyss sing forth a hymn of reverence, their voices full of anguish and praise for the " + getName() + ". With each note, the Mark of Sin doth burn brighter upon the foe, and the Prophet's form is bathed in the unholy light of their worship, his wounds mended by their undying devotion.");

                dialogues.add("Hark! The souls of the damned doth singeth mine praise, their torment healeth mine flesh!");
                dialogues.add("Thee shalt hear their woeful song; for their suffering is mine salvation!");
                dialogues.add("In agony they sing, and through their sin, I am made whole!");
                dialogues.add("Thee canst not silence the hymn of the Abyss, for it doth grant me life everlasting!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
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

    //this attack is undodgeable but can be reduce by opponent's defense
    @Override
    public void basicAttack(Monster you, Monster enemy) {
        enemy.setDodge(0);
        enemy.receiveHit(you, enemy);
        enemy.setDodge(enemy.getMaxDodge());
    }

    //simple burst attack
    private void skill1(Monster you, Monster enemy) {
        setAttack(getAttack() + 8450);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());
    }

    //enemy healing will be reverse and convert to damage
    private void skill2(Monster you, Monster enemy) {
        int allHeal = 0;
        for (int i = 0; i <= enemy.getHealOverTime().size() - 1; i++) {
            allHeal += enemy.getHealOverTime().get(i);
        }

        enemy.getHealOverTime().clear();
        enemy.getHealOverTimeValue().clear();

        enemy.getDamageOverTime().add(allHeal);
        enemy.getDamageOverTime().add(450);
        enemy.getDamageOverTimeValue().add(15);
        enemy.getDamageOverTimeValue().add(15);
    }

    //retribution for sinner hitter/attacker, base on the value of the "mark of sin"
    //reduce percentage of attacker current health base on the value of mark of sin
    private void skill3(Monster you, Monster enemy) {
        enemy.receiveHit(you, enemy);

        if (!hasStatus(enemy, "Mark of Sin", 50).isEmpty()) {
            ArrayList<Integer> newStatusValue = enemy.getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            int index = Integer.parseInt(hasStatus(enemy, "Mark of Sin", 50));
            int maxHealth = enemy.getHealth();
            int percentage = newStatusValue.get(index);
            int damage = (maxHealth * percentage) / 100;

            setAttack(damage);
            enemy.receiveHit(you, enemy);
            setAttack(getMaxAttack());
        }
    }

    //heal over time for a short turn, base on the value of the "mark of sin"
    private void skill4(Monster you, Monster enemy) {
        if (!hasStatus(enemy, "Mark of Sin", 50).isEmpty()) {
            ArrayList<Integer> newStatusValue = enemy.getStatusValueList().getValue();
            if (newStatusValue == null) {
                newStatusValue = new ArrayList<>();
            }

            int index = Integer.parseInt(hasStatus(enemy, "Mark of Sin", 50));
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
