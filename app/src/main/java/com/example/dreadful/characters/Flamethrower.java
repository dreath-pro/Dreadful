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

public class Flamethrower extends Monster {
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private int gas = 3, maxGas = 3;
    private MonsterDatabase monsterDatabase;

    public Flamethrower(Context context) {
        super("004-FLME", context, null, "Flamethrower", R.drawable.character_flamethrower, "right", 140,
                null, null, 3000, 320, 8, 20);
    }

    public Flamethrower(Context context, ImageView yourImage, Prompt prompt) {
        super("004-FLME", context, yourImage, "Flamethrower", R.drawable.character_flamethrower, "right", 140,
                null, null, 3000, 320, 8, 20);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Fire");
        skillNames.add("Burst Flame");
        skillNames.add("Reload");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(3);
        updateMaxSkillCooldowns(maxSkillCooldowns);

        ArrayList<Integer> skillCooldowns = new ArrayList<>();
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
                events.add("The hazmat-clad figure barely flinches, a faint scorch mark glinting on his suit as he press forward with calculated steps.");

                dialogues.add("A scratch won't stop the burn.");
                dialogues.add("Not enough to slow me down.");

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
                events.add("Smoke rises from a puncture in the hazmat suit, the hiss of escaping heat mixing with the roar of flames. " + getName() + " adjusts his grip, methodically tightening his hold on the weapon.");

                dialogues.add("Armor compromised, but irrelevant.");
                dialogues.add("I'll endure. You won’t.");

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
                events.add(getName() + " stumbles slightly, a jagged tear revealing seared fabric underneath. His breathing grows heavy, the rasp of the mask amplifying his resolve. " + getName() + " ignites with a ferocious roar, drowning out all other sounds.");

                dialogues.add("Pain is a fleeting sensation.");
                dialogues.add("This body is expendable; the mission is not.");

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
                events.add("Flames lick at the edges of the hazmat suit as it buckles under the strain. " + getName() + " stands amidst the inferno, a grim silhouette, the trigger pressed down for a desperate last barrage.");

                dialogues.add("Final ignition... make it count.");
                dialogues.add("If I burn, you burn with me.");

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

    public void receiveHit(Monster enemy, Monster you) {
        String result = receiveHitLogic(enemy, you);
        switch (result) {
            case "DODGE":
                events.add(getName() + " shifts his weight effortlessly, the fiery arc of " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " attack carving through the air mere inches from his suit. His unblinking gaze remains fixed on his foe, unreadable behind the mask.");
                events.add("As " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " attack barrels forward, " + getName() + " pivots with an unhurried elegance. A low hiss escapes the respirator, " + getName().toLowerCase() + " already trained on his opponent’s new position.");
                events.add("The ground scorches where " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " strike lands, but " + getName() + " is already elsewhere, his boots leaving faint burn marks on the terrain as he maneuvers with practiced precision.");

                dialogues.add("You’re slower than expected.");
                dialogues.add("This heat must be distracting you.");
                dialogues.add("You won’t land a strike. Not in this lifetime.");

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
                events.add(enemy.getName() + prompt.getApostrophe(enemy.getName()) + " strike crashes against " + getName() + prompt.getApostrophe(getName()) + " flamethrower nozzle, a spark of fire and force erupting from the collision. The hazmat-clad figure doesn’t flinch, his respirator hissing steadily, as though the effort was beneath notice.");
                events.add("The clash of " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " attack and " + getName() + prompt.getApostrophe(getName()) + " reinforced gauntlet sends a shockwave through the air. Unmoved, " + getName() + " locks his stance, the weapon held firm like an extension of his cold, methodical will.");
                events.add(getName() + prompt.getApostrophe(getName()) + " arm absorbs the brunt of the attack, the hazmat suit smoldering but holding strong. Behind the mask, his unseen eyes remain locked on " + enemy.getName() + ", the cold precision of his stance a silent warning.");

                dialogues.add("Predictable.");
                dialogues.add("Is that all?");
                dialogues.add("Heat meets steel, and you still lose.");

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

    public void receiveTimeEffect(Monster enemy, Monster you) {
        runTimeHeal();
        runTimeDamage();
    }

    public String useRandomAttack(Monster you, Monster enemy) {
        String skillName;

        ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        if (newSkillCooldowns.get(0) > 0) {
            newSkillCooldowns.set(0, 0);
        }
        if (newSkillCooldowns.get(1) > 0) {
            newSkillCooldowns.set(1, 0);
        }
        updateSkillCooldowns(newSkillCooldowns);

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
                events.add(getName() + " raises his weapon with mechanical precision, a sharp hiss preceding the release of a concentrated stream of fire. The flames arc through the air, ignoring " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " attempted evasions and defenses, licking at its body with relentless heat.");
                events.add("The air ripples as " + getName() + " unleashes a jet of fire, the intensity undeniable. " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " defenses shimmer for a moment before yielding, the flames clinging to him like a vengeful specter, refusing to be extinguished.");
                events.add(getName() + " doesn’t rush or hesitate; his trigger pull is deliberate, sending fire cascading toward " + enemy.getName() + ". The burning effect takes hold instantly, the flames lingering, gnawing at " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " resolve as " + getName() + " calmly steps forward.");

                dialogues.add("You can’t dodge heat.");
                dialogues.add("It’s not personal; it’s physics.");
                dialogues.add("You’ll burn, no matter where you stand.");

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
                events.add(getName() + " plants his feet firmly, his hands steady on the weapon as a deep roar signals the ignition of an intense inferno. A torrent of flames erupts from the nozzle, larger and fiercer than before, enveloping " + enemy.getName() + " entirely. The fire clings like molten chains, devouring any chance of defense or evasion.");
                events.add("The flames surge forward in a concentrated burst, the heat so intense it warps the air. " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " form is obscured by the blinding inferno, the burning effect doubling in ferocity, searing relentlessly into its flesh.");
                events.add(getName() + prompt.getApostrophe(getName()) + " posture remains unmoved, the roaring blaze reflecting off his mask. The doubled intensity of the flames is not an act of desperation but a calculated execution of overwhelming power. " + enemy.getName() + " has no choice but to endure or succumb.");

                dialogues.add("Let’s turn up the heat.");
                dialogues.add("There’s no escaping this inferno.");
                dialogues.add("Your defenses mean nothing. This ends in flames.");

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
                events.add(getName() + " steps back calmly, his movements methodical as he switches out the gas canister. The faint hiss of compressed fuel fills the air, a chilling contrast to the roaring fire moments earlier. Meanwhile, " + enemy.getName() + ", stunned and immobilized, watches helplessly, the air heavy with impending doom.");
                events.add("The flamethrower hisses as the new gas canister locks into place. " + getName() + prompt.getApostrophe(getName()) + " unhurried pace seems to mock his opponent, who remains frozen in place by the residual heat and suffocating aura of the attack.");
                events.add("As the nozzle flickers with a fresh burst of fire, " + getName() + " tilts his head slightly, his stance as impassive as ever. The momentary pause is not a reprieve but a prelude to another onslaught.");

                dialogues.add("Out of breath? Allow me to reload.");
                dialogues.add("Stay still. This won’t take long.");
                dialogues.add("A brief pause… for your end.");

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
        }

        reduceCooldown(skillIndex);
        return skillName;
    }

    //simple attack with burn effect that will ignore target defense and dodge
    @Override
    public void basicAttack(Monster you, Monster enemy) {
        enemy.setDefense(0);
        enemy.setDodge(0);

        enemy.receiveHit(you, enemy);

        enemy.getDamageOverTime().add(120);
        enemy.getDamageOverTimeValue().add(12);

        enemy.setDefense(enemy.getMaxDefense());
        enemy.setDodge(enemy.getMaxDodge());
    }

    //same as basic attack but doubles the damage and effect when gas reaches 0,
    //the effect will be divided by half until reloading
    private void skill1(Monster you, Monster enemy) {
        enemy.setDefense(0);
        enemy.setDodge(0);

        if (gas <= 0) {
            gas = 0;

            setAttack(getAttack() / 2);
            enemy.getDamageOverTime().add(60);
            enemy.getDamageOverTimeValue().add(12);
        } else {
            setAttack(getMaxAttack());
            enemy.getDamageOverTime().add(120);
            enemy.getDamageOverTimeValue().add(12);
        }

        enemy.receiveHit(you, enemy);

        enemy.setDefense(enemy.getMaxDefense());
        enemy.setDodge(enemy.getMaxDodge());

        gas--;
    }

    //resets the gas and stuns enemy
    private void skill2(Monster you, Monster enemy) {
        gas = maxGas;
        enemy.setStun(2);
    }
}
