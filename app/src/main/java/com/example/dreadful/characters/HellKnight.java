package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.logics.ResizeImage;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class HellKnight extends Player {
    private Random random = new Random();
    private Animation shakeAnimation;
    private ImageView yourImage;
    private ResizeImage resizeImage;
    private ProgressBar yourHealthBar;
    private Prompt prompt;
    private int form = 0;
    private int enhancedDefense = 0;
    private int flameShield = 0;
    private int ember = 0;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public HellKnight(Context context, ImageView yourImage, ProgressBar yourHealthBar, Prompt prompt) {
        super(context, yourImage, "Hell Knight", R.drawable.character_hell_knight, "right", 155,
                new int[]{R.drawable.character_hell_knight_2}, null,
                20000, 1000, 1000, 5);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Burn Slash");
        skillNames.add("Knight's Breath");
        skillNames.add("Enhanced Armor");
        skillNames.add("Emberguard");
        skillNames.add("Dragon Form");

        skillNames.add("Burn Claw");
        skillNames.add("Dragon's Breath");
        skillNames.add("Enhanced Scale");
        skillNames.add("Hellguard");
        skillNames.add("Human Form");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(4);
        maxSkillCooldowns.add(9);
        maxSkillCooldowns.add(7);
        maxSkillCooldowns.add(0);

        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(4);
        maxSkillCooldowns.add(9);
        maxSkillCooldowns.add(7);
        maxSkillCooldowns.add(0);
        updateMaxSkillCooldowns(maxSkillCooldowns);

        ArrayList<Integer> skillCooldowns = new ArrayList<>();
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);

        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        skillCooldowns.add(0);
        updateSkillCooldowns(skillCooldowns);

        this.context = context;
        this.prompt = prompt;
        this.resizeImage = new ResizeImage(context);
        this.yourImage = yourImage;
        this.yourHealthBar = yourHealthBar;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                if (form == 0) {
                    events.add("The blow barely causes a ripple in his flames as " + getName() + " lets out a mocking chuckle, shaking his head in disappointment, the fire in his armor glowing steadily.");
                    events.add("He casually brushes off the attack, his armor giving off a faint hiss as the flames die back down, clearly unimpressed by the effort.");

                    dialogues.add("Pathetic! I expected more from a so-called warrior.");
                    dialogues.add("Your strength is as fleeting as a dying ember.");
                } else {
                    events.add("His armored scales barely register the attack, a low growl escaping his throat as his fiery gaze locks onto his attacker.");
                    events.add("His massive body doesn’t budge, and he lets out a low, rumbling growl as smoke billows from his nostrils, unimpressed.");

                    dialogues.add("That tickled. Try harder worm, Grrrrrr…");
                    dialogues.add("You dare face me with such pitiful strength? Grrrrrraaahh…");
                }

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
                if (form == 0) {
                    events.add("His armor flares up as the attack lands, heat radiating from his form, causing the ground beneath him to blacken. He cracks his neck, unfazed, but with a smirk of approval.");
                    events.add("The flames around him flicker wildly, his gauntlet gripping his weapon tighter, the heat from the attack causing a few cracks to appear in the scorched earth below him.");

                    dialogues.add("You’ve got fire, but not enough to match me!");
                    dialogues.add("I can feel a spark. Let’s see if you can fan it into something real.");
                } else {
                    events.add("His wings twitch as the attack lands, embers glowing on his scales as he lets out a menacing roar, shaking the ground around him.");
                    events.add("The blow causes his armored tail to lash out, flames erupting along his spine as he growls fiercely, the heat radiating from his body intensifying.");

                    dialogues.add("A little better... but still not enough! RRRRAARRHH!");
                    dialogues.add("You’ve got some fire in you... but I’ll show you a real inferno! RRRRRAAAHHHH!!");
                }

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
                if (form == 0) {
                    events.add("The impact sends " + getName() + " sliding back, his armor hissing as flames burst from the cracks in the metal. He grins, eyes glowing with fiery excitement as his strength builds.");
                    events.add("He staggers briefly, but the flames in his armor grow wilder, flickering violently as he slams his sword into the ground, steadying himself, his fury igniting.");

                    dialogues.add("Finally! Now, we’re getting somewhere!");
                    dialogues.add("You dare strike me like that? You’ll regret stoking this fire!");
                } else {
                    events.add("The force of the attack makes " + getName() + " stumble, fiery cracks forming along his scales, but he rears back and lets out an earth-shattering roar, the heat from his body intensifying.");
                    events.add("The strike forces him to dig his claws into the ground, his flames roaring as his massive form trembles with anger. He lets out a deafening roar, embers shooting from his maw.");

                    dialogues.add("Yes! You feel that power? Now tremble before mine! RRROOOAAARRHH!!!");
                    dialogues.add("You’ve struck me hard... Now, prepare for the fury of Hell itself! RRRRRAAAWWWRRHHH!!");
                }

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
                if (form == 0) {
                    events.add("The attack sends him crashing to one knee, flames dimming for a brief moment before roaring back to life. His body trembles, but then he stands, more dangerous than ever, the fire around him raging.");
                    events.add("His body convulses under the weight of the blow, his armor cracking and spewing molten lava. A furious roar escapes him as the flames explode outward, turning the ground beneath him to ash.");

                    dialogues.add("That... that actually hurt. But Hell’s flames never die!");
                    dialogues.add("You’ve pushed me to the edge… Now, I’ll drag you down to Hell itself!");
                } else {
                    events.add("The blow cracks his scales, causing lava-like blood to ooze from the wounds, but " + getName() + " roars furiously, his fiery breath scorching the battlefield as he prepares to unleash his full fury.");
                    events.add("His body shakes violently from the critical blow, flames erupting from every crack in his armor. He rises, towering over the battlefield, releasing a roar so loud that the earth quakes beneath him.");

                    dialogues.add("This is... power. But you will feel true terror now! RRRRAAAAWWRRHHH!!!");
                    dialogues.add("You’ve wounded me... Now face the wrath of the abyss! RRRROOOOOAAAAARRRHHHH!!!");
                }

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
     * if ember is active it will add extra shield that will reduce incoming damage and attacker's hit will
     * reflect back at them
     */
    public void receiveHit(Player hitter, Player target) {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge()) {
            if (form == 0) {
                events.add(getName() + " sidesteps effortlessly, his flaming armor leaving a trail of sparks as he moves. His eyes burn with amusement, mocking " + hitter.getName() + prompt.getApostrophe(hitter.getName()) + " efforts.");
                events.add("With a swift twist of his body, " + getName() + " evades the strike, the flames on his armor flaring momentarily, casting a hellish glow around him as he grins with fiery confidence.");

                dialogues.add("Too slow!. You’ll need to be faster to even touch me.");
                dialogues.add("Nice try. But the fires of Hell make me untouchable!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();
            }

            return;
        }

        if (ember > 0) {
            setAttack(hitter.getAttack());
            hitter.receiveHit(target, hitter);
            setAttack(getMaxAttack());

            hitter.setAttack(hitter.getAttack() * (int) (1 - (double) flameShield));
        }

        if (hitter.getAttack() <= getDefense()) {
            if (form == 0) {
                events.add(getName() + " raises his flaming shield just in time, sparks flying as " + hitter.getName() + prompt.getApostrophe(hitter.getName()) + " attack crashes into it. The impact causes the flames on his armor to flare up, but " + getName() + " barely budges, standing firm.");
                events.add("With a loud clang, " + getName() + prompt.getApostrophe(getName()) + " gauntlet meets " + hitter.getName() + prompt.getApostrophe(hitter.getName()) + " attack head-on. His flaming armor absorbs the blow, and he grins, eyes glowing brighter as the flames ripple across his spiked armor.");

                dialogues.add("Hmph. That’s all you’ve got? I’ve faced worse in the pits of Hell.");
                dialogues.add("You’re strong, but not strong enough to break me!");
            } else {
                events.add(getName() + " brings up his massive, armored claw, blocking " + hitter.getName() + prompt.getApostrophe(hitter.getName()) + " strike with a sound like metal grinding against stone. His eyes glow fiercely as he lets out a low growl, flames erupting from his nostrils.");
                events.add("His massive tail swings up just in time, blocking the incoming blow. The force causes flames to burst from the cracks in his scales, but " + getName() + " stands his ground, letting out a booming roar that shakes the battlefield.");

                dialogues.add("You think your strength can match the fury of a dragon?! Grrrrraahhh!");
                dialogues.add("You’ve got power, but it’s nothing against my scales of flame! RRRROOOAAARRRRHHH!!");
            }

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
            hitter.setAttack(hitter.getMaxAttack());
            yourImage.startAnimation(shakeAnimation);
        }
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        runTimeHeal();

        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for (int i = 0; i <= getDamageOverTime().size() - 1; i++) {
            if (getDamageOverTimeValue().get(i) > 0) {
                if (ember > 0) {
                    setHealth(getHealth() - getDamageOverTime().get(i) * (int) (1 - (double) flameShield));
                } else {
                    setHealth(getHealth() - getDamageOverTime().get(i));
                }

                getDamageOverTimeValue().set(i, getDamageOverTimeValue().get(i) - 1);

                tempDot.add(getDamageOverTime().get(i));
                tempDotValue.add(getDamageOverTimeValue().get(i));
            }
        }

        setDamageOverTime(tempDot);
        setDamageOverTimeValue(tempDotValue);


        ArrayList<String> newStatus = getStatusList().getValue();
        if (newStatus == null) {
            newStatus = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
        if (newStatusValue == null) {
            newStatusValue = new ArrayList<>();
        }

        enhancedDefense--;
        if (enhancedDefense <= 0) {
            enhancedDefense = 0;

            if (!hasStatus(target, "Enhanced Armor", 1).isEmpty()) {
                setDefense(getMaxDefense());

                int index = Integer.parseInt(hasStatus(target, "Enhanced Armor", 1));
                newStatusValue.remove(index);
                newStatus.remove(index);

                updateStatusList(newStatus);
                updateStatusValueList(newStatusValue);
            }

            if (!hasStatus(target, "Enhanced Scale", 1).isEmpty()) {
                setDefense(getMaxDefense());

                int index = Integer.parseInt(hasStatus(target, "Enhanced Scale", 1));
                newStatusValue.remove(index);
                newStatus.remove(index);

                updateStatusList(newStatus);
                updateStatusValueList(newStatusValue);
            }
        }

        ember--;
        if (ember <= 0) {
            ember = 0;

            if (!hasStatus(target, "Flame Shield", 1).isEmpty()) {
                flameShield = 0;

                int index = Integer.parseInt(hasStatus(target, "Flame Shield", 1));
                newStatusValue.remove(index);
                newStatus.remove(index);

                updateStatusList(newStatus);
                updateStatusValueList(newStatusValue);
            }
        }
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;
        int skillIndex;

        ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        if (newSkillCooldowns.get(4) > 0) {
            newSkillCooldowns.set(4, newSkillCooldowns.get(4) - 1);
            updateSkillCooldowns(newSkillCooldowns);
        }

        if (newSkillCooldowns.get(4) > 0) {
            newSkillCooldowns.set(4, 0);
        }
        if (newSkillCooldowns.get(5) > 0) {
            newSkillCooldowns.set(5, 0);
        }
        if (newSkillCooldowns.get(9) > 0) {
            newSkillCooldowns.set(9, 0);
        }
        updateSkillCooldowns(newSkillCooldowns);

        do {
            if (form == 0) {
                skillIndex = random.nextInt(5);
            } else {
                skillIndex = random.nextInt(5) + 5;
            }
            //skillIndex = random.nextInt(getSkillNames().length);
        } while (getSkillCooldowns().getValue().get(skillIndex) > 0);

        ArrayList<String> newSkillNames = getSkillNames().getValue();
        if (newSkillNames == null) {
            newSkillNames = new ArrayList<>();
        }

        skillName = newSkillNames.get(skillIndex);
        switch (skillIndex) {
            //human form
            case 0:
                events.add("With a swift motion, " + getName() + " swings his sword, igniting it with hellfire. The blade blazes bright, leaving a trail of flames as he strikes toward " + target.getName() + ", the air sizzling with heat.");
                events.add("He steps forward, channeling the inferno through his weapon. As he brings the blade down, flames explode outward, creating a fiery arc aimed directly at " + target.getName() + ".");

                dialogues.add("Feel the flames of my wrath!");
                dialogues.add("Let this be a lesson in pain!");

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
                events.add(getName() + " draws in a deep breath, his chest expanding as he channels the flames of the underworld. He exhales with a roar, unleashing a torrent of hellfire that engulfs " + target.getName() + " in a blazing wave.");
                events.add("As the flames burst forth, they scorch the ground and wrap around " + target.getName() + ", burning her relentlessly. The heat radiates around them, and " + getName() + " stands firm, watching the fire do its work with a wicked smile.");

                dialogues.add("Prepare for the inferno!");
                dialogues.add("Feel the heat of a thousand suns!");

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
                events.add(getName() + " slams his gauntleted fists against his chest, and the flames on his armor blaze brighter. As he activates the skill, the heat radiates around him, creating an aura of fiery protection that envelops his form.");
                events.add("As the flames swirl and solidify into a protective barrier, " + getName() + " stands tall, his confidence unwavering. The air around him crackles with energy, as if the very essence of fire has fortified his armor.");

                dialogues.add("Feel the weight of my resolve!");
                dialogues.add("You’ll need more than that to breach my defenses!");

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
                events.add(getName() + " raises his arms, and flames envelop him, forming a shimmering, protective shield of fire. The heat radiates outward, causing the air to shimmer as he prepares to counter any incoming attacks.");
                events.add("With the " + skillName + " activated, the shield crackles with energy, and tendrils of flame reach out toward " + target.getName() + ". As it gets too close, the shield bursts, igniting it with fiery tendrils that cause ongoing damage.");

                dialogues.add("You’ll find my flames are as protective as they are deadly!");
                dialogues.add("Step closer, and you’ll taste the fire of my wrath!");

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
                events.add("Flames swirl around " + getName() + ", intensifying as he begins to shift. His body expands, scales erupting from his armor, and with a thunderous roar, he transforms into a fearsome dragon, radiating immense power.");
                events.add("As his transformation completes, " + getName() + prompt.getApostrophe(getName()) + " max health skyrockets, the essence of fire healing his wounds over time. His scaled form gleams in the light, the very air around him crackling with renewed energy. He feels the weight of his newfound power but knows that agility comes at a cost.");

                dialogues.add("Witness my true form, born of fire and fury! RRRRAAAAAAAHHHHHH!");
                dialogues.add("Feel the strength of my dragon blood coursing through me! RRRRAAAAAAAHHHHHH!");

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

            //dragon form
            case 5:
                events.add(getName() + " raises his massive claw, engulfed in a fiery glow, and brings it crashing down toward " + target.getName() + ". The heat radiates, signaling the impending destruction.");
                events.add("With a swift swipe, he unleashes the " + skillName + ", sending flames cascading in all directions. The intense heat envelops " + target.getName() + " as it feels the searing pain from the powerful attack, leaving it gasping in the aftermath.");

                dialogues.add("Your fate is sealed in flames! RRRRRRHHHHH!");
                dialogues.add("Embrace the flames of your demise! RRRRRRHHHHH!");

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
            case 6:
                events.add(getName() + " inhales deeply, and flames build up within his massive form. With a thunderous roar, he releases a torrent of blazing fire, engulfing " + target.getName() + " entirely. The intense heat singes everything in its path, leaving it scorched and struggling to withstand the burning agony.");
                events.add("As he unleashes the " + skillName + ", a wave of fire surges forward, scorching everything in its wake. " + target.getName() + " is caught within the inferno, the flames licking as it feels the searing burn intensify with each passing second.");

                dialogues.add("Behold the fury of my flames! RRAAAAAHHHHH!");
                dialogues.add("Let the flames consume you! Grrrhhhhh...");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill6(hitter, target);
                break;
            case 7:
                events.add(getName() + " roars, his scales glowing with a deep, fiery hue as they harden into an impenetrable armor. Each scale radiates a fierce heat, creating a protective barrier that few could hope to penetrate.");
                events.add("His body tenses as his scales reinforce, thickening and gleaming with a tempered, darkened sheen. The air around him crackles as his defenses grow, and he stands like an immovable mountain, daring his enemies to try and break him.");

                dialogues.add("My scales are stronger than any steel! Grrrrhhhhh...");
                dialogues.add("You’ll break before I do! Grrrrhhhhh...");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill7(hitter, target);
                break;
            case 8:
                events.add("In dragon form, " + getName() + prompt.getApostrophe(getName()) + " shield roars to life, a fiery barrier surrounding his massive frame. The flames pulse in sync with his breath, growing hotter by the second. Any who come near are met with burning tendrils of fire that scorch them upon contact.");
                events.add("The " + skillName + " wraps around " + getName() + " like a living flame, its searing heat both protecting him and causing " + target.getName() + " to shriek as its burned by the fiery force. The shield not only deflects its attacks but burns it with each strike.");

                dialogues.add("Feel the heat of my wrath, even as you strike! RRRAAAAAAHHHH!");
                dialogues.add("Your fate is sealed—burn in the flames of my shield! Grrrrrrrrrhhh...");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill8(hitter, target);
                break;
            case 9:
                events.add(getName() + prompt.getApostrophe(getName()) + " massive dragon form begins to shrink, his scales retracting, wings folding back, and his claws diminishing into his human form. As he transforms, his body heals from the battle's wounds, though his towering presence is now reduced. A violent burst of energy surges from him, striking " + target.getName() + " and sending her reeling as the remnants of his dragon power lash out.");
                events.add("With a growl, " + getName() + prompt.getApostrophe(getName()) + " dragon body begins to dissipate, flames crackling as he shrinks back to his human size. As the transformation completes, a burst of searing energy erupts from him, knocking " + target.getName() + " back and igniting its body with flames. His armor, though less imposing, now burns with renewed rage, even as his health and defense decrease.");

                dialogues.add("I return, but my flames remain!");
                dialogues.add("Human once more... but still, I burn with fury!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill9(hitter, target);
                break;
        }

        reduceCooldown(skillIndex);
        return skillName;
    }

    //simple basic attack
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
    }

    //burst the target and applies burn that last for 7 turns
    private void skill1(Player hitter, Player target) {
        hitter.setAttack(4000);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        target.getDamageOverTime().add(450);
        target.getDamageOverTimeValue().add(21);
    }

    //increase the defense and applies "enhanced armor" status
    private void skill2(Player hitter, Player target) {
        enhancedDefense = 21;
        setDefense(getDefense() + 350);
        receiveStatus(hitter, "Enhanced Armor", 1);
    }

    //receives a temporary flaming shield, and burns the target for 7 turns
    private void skill3(Player hitter, Player target) {
        ember = 15;
        receiveStatus(hitter, "Flame Shield", 1);
        flameShield = 25;

        target.getDamageOverTime().add(450);
        target.getDamageOverTimeValue().add(21);
    }

    //transform into a dragon and increase the max health and heals yourself overtime at the same time
    //that lasts for 10 turns, your max health, defense will increase but your dodge will be reduced
    private void skill4(Player hitter, Player target) {
        form = 1;
        yourImage.setImageResource(getTransformation()[0]);
        resizeImage.scale(yourImage, 200);

        setDefense(2000);
        setDodge(0);

        double percentageLost = (double) (getMaxHealth() - getHealth()) / getMaxHealth() * 100;

        bypassSetMaxHealth(60000);
        setHealth(getMaxHealth());
        yourHealthBar.setMax(getMaxHealth());

        double reducedNewHealth = getMaxHealth() - (getMaxHealth() * (percentageLost / 100));
        setHealth((int) reducedNewHealth);

        getHealOverTime().add(400);
        getHealOverTimeValue().add(30);
    }


    //simple basic attack in a dragon form
    private void skill5(Player hitter, Player target) {
        target.receiveHit(hitter, target);
    }

    //burst the target and applies burn that last for 7 turns
    private void skill6(Player hitter, Player target) {
        hitter.setAttack(2800);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        target.getDamageOverTime().add(280);
        target.getDamageOverTimeValue().add(21);
    }

    //increase the defense and applies "enhanced scale" status
    private void skill7(Player hitter, Player target) {
        enhancedDefense = 21;
        setDefense(getDefense() + 550);
        receiveStatus(hitter, "Enhanced Scale", 1);
    }

    //receives a temporary flaming shield, and burns the target for 7 turns
    private void skill8(Player hitter, Player target) {
        ember = 15;
        receiveStatus(hitter, "Flame Shield", 1);
        flameShield = 40;

        target.getDamageOverTime().add(280);
        target.getDamageOverTimeValue().add(21);
    }

    //transform into a human again and decrease the max health and heals yourself overtime
    //that lasts for 10 turns, your max health, defense will decrease and your enemy will be burst
    private void skill9(Player hitter, Player target) {
        form = 0;
        yourImage.setImageResource(getImage());
        resizeImage.scale(yourImage, getSize());

        setDefense(getMaxDefense());
        setDodge(getMaxDodge());

        double percentageLost = (double) (getMaxHealth() - getHealth()) / getMaxHealth() * 100;

        bypassSetMaxHealth(20000);
        setHealth(getMaxHealth());
        yourHealthBar.setMax(getMaxHealth());

        double reducedNewHealth = getMaxHealth() - (getMaxHealth() * (percentageLost / 100));
        setHealth((int) reducedNewHealth);

        setAttack(10000);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        getHealOverTime().add(400);
        getHealOverTimeValue().add(30);
    }
}
