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
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class KumoNingyo extends Player {
    private Random random = new Random();
    private ProgressBar yourHealthBar;
    private Prompt prompt;
    private int poison = 10;
    private int creepyStalkerTime = 0;
    private int limbTwitch = 6, maxLimbTwitch = 6;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public KumoNingyo(Context context, ImageView yourImage, ProgressBar yourHealthBar, Prompt prompt) {
        super(context, yourImage, "Kumo Ningyō", R.drawable.character_kumo_ningyo, "left", 190,
                null, null,
                5800, 180, 0, 20,
                new String[]{"Doku Kizu", "Shinobi Ashi Keri", "Tsukurogami", "Kakure Kage", "Ito no Tami"},
                new int[]{0, 3, 3, 3, 6}, new int[]{0, 0, 0, 0, 0});

        this.prompt = prompt;
        this.context = context;
        this.yourHealthBar = yourHealthBar;
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                events.add("A light strike connects with " + getName() + ", barely phasing her. Her limbs shift slightly, the eerie creaking sound echoing as her head tilts unnaturally, her glassy eyes focused with silent intent. The movement is unnerving, yet she stands firm, barely affected.");
                events.add("The hit brushes against " + getName() + prompt.getApostrophe(getName()) + " side, causing a small twitch in one of her many limbs. She tilts her head slowly, her cracked porcelain face expressionless, yet somehow filled with menace as her limbs subtly adjust, as if preparing for something more sinister.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Creeeeak... creak...)");
                dialogues.add("(Crrreeeak... creak...)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
            case 1:
                events.add("The impact lands harder, causing " + getName() + prompt.getApostrophe(getName()) + " limbs to jerk and reposition with a series of sharp cracks. Her body shudders with the force, her head tilting at an unnatural angle, but she steadies herself, her limbs creaking ominously as she regains balance.");
                events.add(getName() + prompt.getApostrophe(getName()) + " body jerks backward with the strike, her limbs flexing and curling as if sensing the threat. She leans forward again, cracked and damaged but undeterred, with each step emitting a hair-raising clicking sound that echoes through the air.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Creeeeaaaaakk... crack crack)");
                dialogues.add("(Crrraaaaack... click-click-click)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
            case 2:
                events.add("The powerful blow hits " + getName() + ", causing a deep fracture to split across her porcelain face. Her many limbs flail wildly, clicking and creaking as they struggle to stabilize her broken form. With a jagged, sharp tilt of her head, she refocuses, her cracked face frozen in a chilling stare.");
                events.add("The strike sends " + getName() + " reeling, fractures forming along her doll-like body, exposing fragments of the wood and old porcelain beneath. She lets out a slow, haunting creak as her limbs twitch back into place, her broken face staring blankly forward, unyielding even as her body splinters.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(CRRRRRAAAAAAKK! Creak-creak-creak)");
                dialogues.add("(CRRRRAAAACCCCK... crackle-click-click)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
            case 3:
                events.add("The devastating blow shatters large portions of " + getName() + prompt.getApostrophe(getName()) + " porcelain skin, leaving her twisted and mangled. Her limbs convulse violently, creaking and cracking as if on the verge of collapse. A high-pitched, eerie clicking sound echoes from her, a sound like laughter, as she remains upright despite the severe damage.");
                events.add("The impact is brutal, sending shards of porcelain flying as " + getName() + prompt.getApostrophe(getName()) + " fragile face splits, exposing dark hollows beneath. Her limbs twitch spasmodically, each one making a sickening crack as they try to support her broken form. She emits a long, continuous creak, her shattered mouth open in a silent scream as she struggles to remain standing.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(CRAAASH! CRRRRRAAAACK-K-K-K)");
                dialogues.add("(CRRRAAAAAACK—SPLINTER!)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
        }
    }

    /**
     * everytime an attacker hit, kumo ningyo will be mark with lost limb buff and healing effects
     * will increase significantly base on the lost limbs buff
     */
    public void receiveHit(Player hitter, Player target) {
        String result = receiveHitLogic(hitter, target);
        switch (result) {
            case "DODGE":
                events.add("With an unnatural twist, " + getName() + prompt.getApostrophe(getName()) + " many limbs shift and contort, pulling her body just out of reach of the attack. Her head jerks to the side as she dodges, her glassy eyes unblinking and fixed on her attacker. The creaking of her limbs fills the air, punctuated by an unsettling silence as she glides back into position, completely untouched.");
                events.add("In an instant, " + getName() + prompt.getApostrophe(getName()) + " limbs extend, arching her backward to evade the strike. Her body folds and bends with impossible grace, each joint clicking softly as she dodges the blow. She tilts her head slowly as if observing her opponent’s mistake, then straightens with a slow, menacing creak.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Squeeeeak… creak-creak-creak)");
                dialogues.add("(Click-click-click… Crrrreeeeak)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                break;
            case "BLOCKED":
            case "":

                break;
            default:
                damageExpression(prompt.measureDamage(Integer.parseInt(result)));
                break;
        }
        receiveStatus(target, "Lost Limbs", 1);
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        limbTwitch--;
        if (limbTwitch <= 0) {
            limbTwitch = maxLimbTwitch;

            if (!hasStatus(target, "Lost Limbs", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(target, "Lost Limbs", 1));
                bypassSetHealth(getHealth() + (1000 * getStatusValue().get(index)));

                if (getHealth() > getMaxHealth()) {
                    bypassSetMaxHealth(getHealth());
                    yourHealthBar.setMax(getMaxHealth());
                }

                poison *= getStatusValue().get(index);
            } else {
                bypassSetHealth(getHealth() + 1000);

                if (getHealth() > getMaxHealth()) {
                    bypassSetMaxHealth(getHealth());
                    yourHealthBar.setMax(getMaxHealth());
                }

                poison += 10;
            }

            events.add("不気味な人形は強くなった。");
            prompt.selectRandomEvent(events);
            prompt.getEventColor().add(ContextCompat.getColor(context, R.color.red));
            events.clear();
        }

        creepyStalkerTime--;
        if (creepyStalkerTime <= 0) {
            setDodge(getMaxDodge());
            creepyStalkerTime = 0;

            if (!hasStatus(target, "Creepy Stalker", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(target, "Creepy Stalker", 1));
                getStatusValue().remove(index);
                getStatus().remove(index);
            }
        }
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
                events.add(getName() + " lunges forward, her limbs a blur as they dart towards " + target.getName() + ". With a swift and precise motion, she slashes through the air, her claws biting into " + target.getName() + ". A sickly green mist erupts from the wound, the poison sinking in and spreading like darkness itself. Her head tilts with a disquieting creak, eyes gleaming as she watches the venom take effect.");
                events.add("In a fluid motion, " + getName() + " strikes, her claws gliding against " + target.getName() + prompt.getApostrophe(target.getName()) + " ethereal surface. The impact resonates with an unsettling crack as the toxic energy flows from her fingertips into her foe. The poison seeps into " + target.getName() + prompt.getApostrophe(target.getName()) + " essence, causing a momentary flicker in its dark form. " + getName() + " stands back, her limbs twitching in anticipation, reveling in the slow agony she has inflicted.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Sccrrreeaaak... crrreeeak-click-click)");
                dialogues.add("(Crrreeaaack... crack-click)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                basicAttack(hitter, target);
                break;
            case 1:
                events.add("With a swift, fluid motion, " + getName() + " darts forward, her limbs extending in a dizzying flurry of strikes. Each of her three attacks lands with a sharp, echoing crack, sending out ripples of green poison that latch onto " + target.getName() + prompt.getApostrophe(target.getName()) + " form. She watches with an eerie stillness as the poison seeps in, her head tilting slightly, and a satisfied creak escaping her lips.");
                events.add(getName() + " dances around " + target.getName() + ", her movements quick and unnervingly graceful. With three precise strikes, she tears into the enemy, leaving behind a trail of sickly green residue. As the poison begins to take effect, she pauses, her limbs quivering with anticipation. Her glassy eyes glint in the dim light as she absorbs the sight of her enemy faltering under the weight of the toxin.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Creeeak... click-click... Sccrrreeeaaak)");
                dialogues.add("(Crrick-crack... tap-tap-tap... Creeeak)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                skill1(hitter, target);
                break;
            case 2:
                events.add(getName() + " pauses, her limbs trembling as dark energy envelops her. She channels the essence of her lost limbs, feeling her vitality surge beyond its former limits. A soft, unsettling creak echoes as her maximum health rewrites itself, each pulse of energy making her feel more powerful. The air around her thickens with a toxic green mist, signaling the enhancement of her poison. She tilts her head, a sinister smile forming as she embraces this newfound strength.");
                events.add("As " + getName() + " activates \"" + getSkillNames()[skillIndex] + "\", the shadows swirl ominously around her. Her form glimmers, limbs reconstituting with an eerie grace, as her health rises and her maximum health increases, defying previous limits. The very fabric of her being seems to shift, and she can feel the potent poison within her intensifying permanently. With a satisfied, chilling laugh, she surveys the battlefield, her eyes sparkling with renewed ferocity.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Creeeak... crack-crack... Sccrrreeeak)");
                dialogues.add("(Creeeak... crrrrack... click-click)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                skill2(hitter, target);
                break;
            case 3:
                events.add("With a sudden and fluid motion, " + getName() + " calls upon the shadows around her, summoning a burst of poisonous energy that explodes outward. The air thickens with a noxious green mist, engulfing her target and causing them to stagger. As the poison seeps into their being, she feels a rush of exhilaration. Her form flickers and shimmers, granting her an impressive agility boost for a limited time. A sly grin forms on her face, knowing she can weave through attacks with newfound agility.");
                events.add(getName() + " elegantly disappears into the shadows, leaving only a whisper of movement behind. As she releases \"" + getSkillNames()[skillIndex] + "\", a cloud of poison bursts forth, catching " + target.getName() + " off guard. The green mist envelops her foe, causing them to falter. With her dodge increased, she stands ready, poised to evade the next attack, her eyes glinting with mischief as she prepares to dance through the chaos.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Creeeak... sccrrreeeak... hissss)");
                dialogues.add("(Creeeak... crack-click... hsssss...)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

                skill3(hitter, target);
                break;
            case 4:
                events.add(getName() + " gracefully weaves her hands in the air, summoning a silken thread that glistens in the dim light. With a swift flick, the spider web shoots forward, ensnaring " + target.getName() + " in its sticky embrace. As the web tightens around her foe, she watches with a twisted glee, savoring the moment as they struggle against the binding threads. The air crackles with tension, and her eerie laughter echoes as she revels in their helplessness.");
                events.add("With a fluid motion, " + getName() + " casts \"" + getSkillNames()[skillIndex] + "\", and the web unfurls like a nightmare, ensnaring " + target.getName() + " in its grasp. The strands shimmer with a malevolent sheen, immobilizing her opponent for a brief moment. As they thrash in vain against their bonds, she tilts her head with a sinister curiosity, her eyes gleaming with the thrill of the hunt, ready to strike while they remain incapacitated.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("(Creeeak... hisss... ssccrrreeeak)");
                dialogues.add("(Creeeak... crick-crack... hsss...)");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.white));
                }
                dialogues.clear();

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

    //every attack has poison effect that last 10 turns
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
        target.getDamageOverTime().add(poison);
        target.getDamageOverTimeValue().add(30);
    }

    //uses base damage 3 times and same poison effect
    private void skill1(Player hitter, Player target) {
        setAttack(getAttack() * 3);
        target.receiveHit(hitter, target);
        setAttack(getMaxAttack());

        target.getDamageOverTime().add(poison * 3);
        target.getDamageOverTimeValue().add(30 * 3);
    }

    //recovers 800 health and multiply by the value of the lost limbs buff and bypasses the max health rewriting it
    //also removes the lost limbs buff, while also increasing the poison effect permanently
    private void skill2(Player hitter, Player target) {
        if (!hasStatus(hitter, "Lost Limbs", 1).isEmpty()) {
            int index = Integer.parseInt(hasStatus(hitter, "Lost Limbs", 1));
            bypassSetHealth(getHealth() + (1000 * getStatusValue().get(index)));

            if (getHealth() > getMaxHealth()) {
                bypassSetMaxHealth(getHealth());
                yourHealthBar.setMax(getMaxHealth());
            }

            poison *= getStatusValue().get(index);

            getStatus().remove(index);
            getStatusValue().remove(index);
        } else {
            bypassSetHealth(getHealth() + 1000);

            if (getHealth() > getMaxHealth()) {
                bypassSetMaxHealth(getHealth());
                yourHealthBar.setMax(getMaxHealth());
            }

            poison += 10;
        }
    }

    //burst attack with poison and add 60% dodge for the hitter's next attack with the same poison effect
    private void skill3(Player hitter, Player target) {
        hitter.setAttack(4500);
        target.receiveHit(hitter, target);
        hitter.setAttack(hitter.getMaxAttack());

        target.getDamageOverTime().add(poison);
        target.getDamageOverTimeValue().add(30);

        creepyStalkerTime = 5;
        receiveStatus(hitter, "Creepy Stalker", 1);
        setDodge(getDodge() + 60);
    }

    //stuns the target
    private void skill4(Player hitter, Player target) {
        target.setStun(4);
    }
}
