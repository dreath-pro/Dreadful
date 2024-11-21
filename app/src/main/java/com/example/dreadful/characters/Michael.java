package com.example.dreadful.characters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.models.Monster;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Michael extends Monster {
    private Random random = new Random();
    private Prompt prompt;
    private Context context;
    private Animation shakeAnimation;
    private ImageView yourImage, enemyImage;
    private double shieldPercentage = 35;
    private final double maxShieldPercentage = 35;
    private int enemyCurrentHealth;
    private boolean isPetrifyActivated = false;
    private int petrification = 0;
    private int shield = 0;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private MonsterDatabase monsterDatabase;

    public Michael(Context context, ImageView yourImage, Prompt prompt, ImageView enemyImage) {
        super(context, yourImage, "Michael", R.drawable.character_michael, "left", 180,
                null, null, 20500, 1800, 58, 0);

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
        this.monsterDatabase = new MonsterDatabase(context);
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                events.add("A faint impact brushes across " + getName() + prompt.getApostrophe(getName()) + " mossy exterior, leaving a thin line of dust in its wake.");
                events.add("The blow barely leaves a mark, only displacing a few flecks of moss from his surface.");
                events.add("A light scratch appears across " + getName() + prompt.getApostrophe(getName()) + " shoulder, but the statue remains unmoving, almost unbothered.");
                events.add("A gentle impact meets " + getName() + prompt.getApostrophe(getName()) + " stone frame, barely causing a stir as he stands unmoved.");

                dialogues.add("Rrrrk… krrrk.");
                dialogues.add("Chk-chk.");
                dialogues.add("Rrkk… shhrk.");
                dialogues.add("Crrrkkk…");

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
                events.add("A moderate strike chips away a small portion of moss, and " + getName() + prompt.getApostrophe(getName()) + " head creaks slightly as if acknowledging the hit.");
                events.add("A crack appears in " + getName() + prompt.getApostrophe(getName()) + " stone shoulder, sending small dust particles floating down as he tilts toward the attacker.");
                events.add("The impact displaces part of his moss-covered forearm, and his stony torso shifts slightly, grinding in response.");
                events.add("A noticeable hit strikes " + getName() + prompt.getApostrophe(getName()) + " chest, leaving behind a slight fissure. His arm jerks slightly, sword scraping with a dull grind.");

                dialogues.add("Grrrkk… kreeek.");
                dialogues.add("Kkkkkrrrk… chrrrrkk.");
                dialogues.add("Shhhhrrrk… kkkkkrrrrk.");
                dialogues.add("Grrrrk… kreeenk.");

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
                events.add("A powerful hit strikes " + getName() + ", causing deep cracks to form along his stone arm, moss crumbling to the ground as he responds with a slow, grinding twist.");
                events.add("The heavy impact echoes through " + getName() + prompt.getApostrophe(getName()) + " frame, splintering part of his chest. His stone eyes narrow, grinding with determination.");
                events.add(getName() + prompt.getApostrophe(getName()) + " sword arm takes a hard blow, cracking the stone at the elbow. Slowly, he raises his sword, the sound echoing through the air.");
                events.add("A strong strike hits " + getName() + prompt.getApostrophe(getName()) + " side, splintering his outer layer. He shifts, emitting a grinding roar that sends dust and gravel tumbling.");

                dialogues.add("SSSHHHRRRRK… GRRRNNNNK.");
                dialogues.add("CRRRRRRRKKK… SHRRRR.");
                dialogues.add("GRRRRAAAKKK… SHHHHRRRR.");
                dialogues.add("SHRRRRKK… GRRNNNNN.");

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
                events.add("The blow shatters a section of " + getName() + prompt.getApostrophe(getName()) + " stone chest, scattering shards and leaving an open fissure. His head jerks violently, emitting a bone-chilling roar of grinding stone.");
                events.add("A devastating impact strikes his side, causing a chunk of his arm to break off. " + getName() + prompt.getApostrophe(getName()) + " body emits a furious grinding sound as he raises his weapon in challenge.");
                events.add("The critical hit blasts " + getName() + prompt.getApostrophe(getName()) + " shoulder, leaving a gaping crack. He shifts, each movement punctuated by a horrifying grind that echoes through the battlefield.");
                events.add("A destructive blow crushes " + getName() + prompt.getApostrophe(getName()) + " chest, splintering his torso. He stands unfazed, his head turning slowly as the grinding fills the air like an otherworldly scream.");

                dialogues.add("SSSHHRRRRAAAKK… GRRRRRR!");
                dialogues.add("CRRRRRAAAAK… GRROOOOONK.");
                dialogues.add("GRRRRAAAAAK… SHRRRNNNNK!");
                dialogues.add("SSSSHHHHRRRRR… GRRRROOOOONNKKK.");

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

    public void receiveHit(Monster enemy, Monster you) {
        int originalDamage = enemy.getAttack();
        double percentage = shieldPercentage / 100;
        int reducedAttack = (int) (originalDamage * (1 - percentage));
        enemy.setAttack(reducedAttack);

        if (enemy.getAttack() <= getDefense()) {
            events.add(enemy.getName() + prompt.getApostrophe(enemy.getName()) + " delicate limbs swipe at " + getName() + ", but he intercepts with a slow, grinding turn. The strike merely skims off his weathered surface, like rain on rock.");
            events.add("The hit lash toward " + getName() + ", but his stone arm raises just in time, blocking the strike with effortless resistance.");
            events.add("The quick, probing strike clinks harmlessly off his forearm. " + getName() + " doesn’t flinch, moss floating softly to the ground.");
            events.add(enemy.getName() + prompt.getApostrophe(enemy.getName()) + " attack barely dents " + getName() + prompt.getApostrophe(getName()) + " still frame, like water splashing against a cliff.");

            dialogues.add("Krrrk… chhhh.");
            dialogues.add("Trsss… krrrk.");
            dialogues.add("Chk… rrrr.");
            dialogues.add("Ssssk… trrrrk.");

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
            enemy.setAttack(enemy.getAttack() - getDefense());

            double damagePercentage = (double) enemy.getAttack() / getHealth() * 100;
            damageExpression((int) damagePercentage);

            setHealth(getHealth() - enemy.getAttack());
            yourImage.startAnimation(shakeAnimation);
        }

        enemy.setAttack(enemy.getMaxAttack());
    }

    public void receiveTimeEffect(Monster enemy, Monster you) {
        runTimeHeal();

        shield--;
        if(shield <= 0)
        {
            shield = 0;
            shieldPercentage = maxShieldPercentage;
        }

        if(isPetrifyActivated)
        {
            petrification--;
            if(petrification <= 0)
            {
                enemyImage.setColorFilter(null);
                enemy.setHealth(enemyCurrentHealth);
                enemyCurrentHealth = enemy.getHealth();
                enemy.setDefense(enemy.getMaxDefense());
                enemy.setDodge(enemy.getMaxDodge());
                isPetrifyActivated = false;
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
                events.add(getName() + " raises his sword in solemn judgment, the blade aglow with a faint, ghostly aura. The air around " + enemy.getName() + " seems to grow dense, thickening with unseen hands that tether it to the spot. " + getName() + prompt.getApostrophe(getName()) + " stone blade arcs down in a swift, decisive slash, unstoppable and inevitable.");
                events.add("With a grinding movement, " + getName() + prompt.getApostrophe(getName()) + " sword arm lifts, emitting an eerie resonance as moss and dust fall away. The ground trembles, and " + enemy.getName() + " is locked in place, unable to evade. In one smooth motion, " + getName() + prompt.getApostrophe(getName()) + " blade descends, sealing " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " fate.");
                events.add("A low, unsettling groan vibrates from " + getName() + " as he executes " + skillName + ", his moss-covered blade igniting with a dull, spectral light. The air thickens, binding " + enemy.getName() + " in an unseen hold. " + getName() + prompt.getApostrophe(getName()) + " sword slices through the space between them with relentless force.");
                events.add("With a deliberate, heavy motion, " + getName() + " raises his sword, each shift of his stone form echoing against the silence. " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " attempts to evade are halted by an invisible pull, grounding it in place. In a single, undeniable strike, " + getName() + prompt.getApostrophe(getName()) + " sword cuts down in judgment.");

                dialogues.add("Grrrrnnn… SWISH!");
                dialogues.add("CRRRK… WHOOOSH!");
                dialogues.add("RRRRRNNNNK… SHNK!");
                dialogues.add("GRAAAA… THUNK!");

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
                events.add("As dark energies cling to " + getName() + prompt.getApostrophe(getName()) + " stone form, he lets out a low, rumbling groan. A surge of purifying energy wells up within him, and with a powerful pulse, he unleashes it outward. The force sweeps away all curses, erupting into a single crushing wave that impacts " + enemy.getName() + ", scattering it back with the sheer intensity.");
                events.add("The corruption weighing on " + getName() + prompt.getApostrophe(getName()) + " moss-covered body shatters as he releases " + skillName + ". The radiant energy expands, banishing the curses from his form in a single powerful sweep that collides with " + enemy.getName() + ", sending it recoiling from the sheer impact.");
                events.add(getName() + prompt.getApostrophe(getName()) + " stony hand trembles, and his mossy figure is wreathed in a sudden surge of pure energy. In an instant, he unleashes the " + skillName + ", tearing the curses from his form and propelling a forceful impact toward " + enemy.getName() + ", striking it with an undodgeable blow.");
                events.add("Encased in darkness, " + getName() + prompt.getApostrophe(getName()) + " stone body strains, then releases a burst of radiance that expels the afflictions on him. The force sweeps outward in a crushing wave, connecting with " + enemy.getName() + " with a single, concussive strike that echoes through the battlefield.");

                dialogues.add("THRRUM… WHUMP!");
                dialogues.add("WHRRR… BOOM!");
                dialogues.add("GRRRRK… CRASH!");
                dialogues.add("WHHHHRR… THUD!");

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
                events.add(getName() + prompt.getApostrophe(getName()) + " empty gaze fixes upon " + enemy.getName() + ", and a deep, echoing hum builds in the air. A spectral resonance pulses outward from his stone form, unraveling the enemy’s hidden defenses. Buffs dissolve, and " + enemy.getName() + " falters, momentarily frozen under " + getName() + prompt.getApostrophe(getName()) + " relentless stare as all pretense is stripped away.");
                events.add("With the weight of ages in his stare, " + getName() + " releases a silent judgment. A wave of resonant energy expands outward, nullifying " + getName() + prompt.getApostrophe(getName()) + " protective enchantments and halting its movement. Stunned, the enemy is left vulnerable, its hidden strengths unveiled and scattered.");
                events.add("The stone guardian’s gaze holds " + enemy.getName() + " captive as a wave of invisible power emanates, banishing every regenerative effect and buff. The overwhelming force of " + getName() + prompt.getApostrophe(getName()) + " truth renders his enemy stunned, momentarily held in the grip of exposure and judgment.");
                events.add("A deep, unsettling vibration echoes as " + getName() + " taps into the " + skillName + ". This force scours through " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " form, dispelling all enhancements and halting its movement as if paralyzed under " + getName() + prompt.getApostrophe(getName()) + " unwavering scrutiny. For a brief moment, " + enemy.getName() + " is left stripped and defenseless.");

                dialogues.add("HUUUMMM… CRRRNK!");
                dialogues.add("GRRRRR… THRUM!");
                dialogues.add("MMMMM… SNAP!");
                dialogues.add("RRRRRMMMM… KRRSH!");

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
                events.add("A thick, ethereal barrier takes shape around " + getName() + ", moss-covered and ancient, fortifying his form. This aura doesn’t just defend; it strikes back, sending waves of retributive energy toward " + enemy.getName() + " as " + getName() + prompt.getApostrophe(getName()) + " form glows with an ominous light. The harder " + enemy.getName() + " presses, the fiercer the shield’s retaliation.");
                events.add("As " + getName() + " raises his moss-encrusted sword, a shield of spectral light wraps around him, thickening like ancient stone. This barrier pulses outward, sending shockwaves toward " + enemy.getName() + " with each of its attacks, as though the shield itself holds an ancient grudge.");
                events.add(getName() + prompt.getApostrophe(getName()) + " form emanates a resonant energy as the " + skillName + " materializes, amplifying its defenses. The shield flares with each enemy strike, retaliating with bursts of energy that lash out, striking " + enemy.getName() + " and leaving it reeling from each forceful pulse.");
                events.add("The ancient stone of " + getName() + prompt.getApostrophe(getName()) + " form glows as a translucent, moss-covered shield appears around him. It’s as much a weapon as it is a defense, sending jolts of energy outward to any who attack, each impact like the crushing weight of judgment upon " + enemy.getName() + ".");

                dialogues.add("GRRRNN… THWACK!");
                dialogues.add("WHRRR… KRA-KOOM!");
                dialogues.add("HMMMM… SLAM!");
                dialogues.add("MMMMMM… CRUNCH!");

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
                events.add("With a commanding presence, " + getName() + " raises his moss-covered sword, and a chilling energy surrounds him. The air thickens as he intones the ancient power of " + skillName + ". Void Reaper becomes ensnared in a shimmering stone-like shell, its form petrifying as " + getName() + prompt.getApostrophe(getName()) + " judgment reduces it to mere fragments of ash, leaving its defenses completely irrelevant.");
                events.add("A powerful surge emanates from " + getName() + ", enveloping " + enemy.getName() + " in an aura of immobilizing energy. In an instant, it’s as if time freezes, and the enemy turns to stone, helpless against the overwhelming power. " + getName() + prompt.getApostrophe(getName()) + " sword strikes down, reducing " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " essence to dust, its defenses shattered under the weight of judgment.");
                events.add("As " + getName() + prompt.getApostrophe(getName()) + " sword glows with an ancient light, he calls forth " + skillName + ". The air crackles with energy, ensnaring " + enemy.getName() + " in a grip of stone. With a decisive thrust, " + getName() + prompt.getApostrophe(getName()) + " power obliterates its form, turning it into nothing but ash and dust, completely disregarding its attempts to defend or evade.");
                events.add(getName() + " stands resolute, and with a swift, calculated motion, he activates " + skillName + ". " + enemy.getName() + " finds itself encased in a petrifying force, every ounce of its vitality drained and reduced to ash, as " + getName() + prompt.getApostrophe(getName()) + " unyielding judgment disregards all attempts to escape or resist.");

                dialogues.add("GRRRRR… CRACK!");
                dialogues.add("WHIRR… SHATTER!");
                dialogues.add("VROOOM… CRUMBLE!");
                dialogues.add("THRUM… DUST!");

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
            case 5:
                events.add(getName() + prompt.getApostrophe(getName()) + " ancient form resonates with a sorrowful energy as he channels " + skillName + ". A shadowy aura envelops his sword, and he thrusts forward, marking " + enemy.getName() + " with a sigil of decay. A wave of toxic energy seeps into its being, causing pain to fester while " + getName() + " delivers a swift, piercing strike, leaving the enemy weakened and vulnerable.");
                events.add("With a heavy sigh, " + getName() + " channels the power of " + skillName + ". The air grows thick with a miasma of decay as he unleashes the effect on " + enemy.getName() + ". The enemy’s form ripples with anguish, and as " + getName() + " strikes, the lingering poison takes root, ensuring the decay continues to gnaw at its vitality.");
                events.add("As " + getName() + " raises his sword, he channels the somber energy of " + skillName + ". A dark aura cascades around " + enemy.getName() + ", inflicting a decaying curse. The essence of decay begins to seep into its very core, and in the same motion, " + getName() + " delivers a precise strike, reinforcing the enemy’s suffering as the poison lingers on.");
                events.add(getName() + " stands resolute as shadows swirl around him, invoking " + skillName + ". A toxic haze envelops " + enemy.getName() + ", heralding its gradual decline. With a sharp, decisive movement, " + getName() + " strikes, delivering a solid hit while ensuring the enemy is marked by the poison’s lingering grip.");

                dialogues.add("SHHHH… STAB!");
                dialogues.add("FSSSS… THUNK!");
                dialogues.add("WOOOOSH… SLICE!");
                dialogues.add("ZZZZZ… CRUNCH!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill5(you, enemy);
                break;
        }

        reduceCooldown(skillIndex);
        return skillName;
    }

    //attack that will ignore dodge
    @Override
    public void basicAttack(Monster you, Monster enemy) {
        enemy.setDodge(0);
        enemy.receiveHit(you, enemy);
        enemy.setDodge(enemy.getMaxDodge());
    }

    //cleanse all status and damage over time while damaging the enemy
    private void skill1(Monster you, Monster enemy) {
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
        enemy.setDodge(0);
        enemy.receiveHit(you, enemy);
        enemy.setDodge(enemy.getMaxDodge());
        setAttack(getMaxAttack());
    }

    //cleanse all enemy's status and heal over time and also stunning them
    private void skill2(Monster you, Monster enemy) {
        ArrayList<String> newStatusList = enemy.getStatusList().getValue();
        if (newStatusList == null) {
            newStatusList = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValueList = enemy.getStatusValueList().getValue();
        if (newStatusValueList == null) {
            newStatusValueList = new ArrayList<>();
        }

        newStatusList.clear();
        newStatusValueList.clear();

        enemy.updateStatusList(newStatusList);
        enemy.updateStatusValueList(newStatusValueList);

        enemy.getHealOverTime().clear();
        enemy.getHealOverTimeValue().clear();

        enemy.setStun(3);
    }

    //provides a temporary shield and damaging enemy at the same time
    private void skill3(Monster you, Monster enemy) {
        shieldPercentage *= 2;
        shield = 5;

        setAttack(getAttack() + 1230);
        setAttack(getAttack() * 2);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());
    }

    //petrify enemy and reduce their health to ash and their dodge while ignoring their defense
    private void skill4(Monster you, Monster enemy) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0f);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        enemyImage.setColorFilter(filter);

        enemyCurrentHealth = enemy.getHealth();
        enemy.setHealth(2800);
        enemy.setDefense(0);
        enemy.setDodge(0);

        enemy.setStun(3);

        petrification = 6;
        isPetrifyActivated = true;
    }

    //give enemy damage over time and hit them
    private void skill5(Monster you, Monster enemy)
    {
        enemy.receiveHit(you, enemy);

        enemy.getDamageOverTime().add(500);
        enemy.getDamageOverTimeValue().add(15);
    }
}
