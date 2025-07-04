package com.example.dreadful.characters;

import android.content.Context;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.logics.ResizeImage;
import com.example.dreadful.models.Monster;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class VoidReaper extends Monster {
    private Random random = new Random();
    private ImageView yourImage;
    private ConstraintLayout backgroundImage;
    private ArrayList<Integer> backgroundList;
    private int selectedBackground;
    private ResizeImage resizeImage;
    private Prompt prompt;
    private int voidTime = 0;
    private int fatigue = 0;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private MonsterDatabase monsterDatabase;

    public VoidReaper(Context context)
    {
        super("010-VOID", context, null, "Void Reaper", R.drawable.character_void_reaper, "left", 150,
                new int[]{R.drawable.character_void_reaper_2},
                new int[]{R.drawable.background_void_1, R.drawable.background_void_2},
                60000, 1800, 500, 15);
    }

    public VoidReaper(Context context, ImageView yourImage, ConstraintLayout backgroundImage, ArrayList<Integer> backgroundList, int selectedBackground, Prompt prompt) {
        super("010-VOID", context, yourImage, "Void Reaper", R.drawable.character_void_reaper, "left", 150,
                new int[]{R.drawable.character_void_reaper_2},
                new int[]{R.drawable.background_void_1, R.drawable.background_void_2},
                60000, 1800, 500, 15);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Chrono Reap");
        skillNames.add("Time Fracture");
        skillNames.add("Dimension Shift");
        skillNames.add("Reverse Dash");
        skillNames.add("Singularity Slash");
        skillNames.add("Void Fatigue");
        skillNames.add("Temporal Reset");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(3);
        maxSkillCooldowns.add(4);
        maxSkillCooldowns.add(5);
        maxSkillCooldowns.add(10);
        updateMaxSkillCooldowns(maxSkillCooldowns);

        ArrayList<Integer> skillCooldowns = new ArrayList<>();
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
        this.backgroundImage = backgroundImage;
        this.backgroundList = backgroundList;
        this.selectedBackground = selectedBackground;
        this.monsterDatabase = new MonsterDatabase(context);
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                events.add(getName() + " received a light blow, barely disrupting its eerie calm as it swayed slightly.");
                events.add(getName() + " received a light blow, but the impact barely registered.");
                events.add("A minor hit grazed the " + getName() + ", leaving it unfazed but slightly annoyed.");
                events.add("The attack barely scratched the surface, and the " + getName() + " let out a dismissive gurgle.");

                dialogues.add("Gurgle-grrrgh!");
                dialogues.add("Grrrgh.");
                dialogues.add("Gurgle.");

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
                events.add(getName() + " was struck with moderate force, causing a ripple through its tentacles.");
                events.add("The strike landed with a solid thud, causing " + getName() + prompt.getApostrophe(getName()) + " tentacles to twitch in irritation.");
                events.add("With a moderate impact, the " + getName() + " staggered but maintained its menacing stance.");
                events.add("The attack pushed the " + getName() + " back a step, its gurgling growl growing more pronounced.\n");

                dialogues.add("Grrraahhh!");
                dialogues.add("Grrrrr!");

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
                events.add(getName() + " took a strong hit, the force momentarily disrupting its control over the void.");
                events.add(getName() + " absorbed a strong blow, the force rippling through its body like a shockwave.");
                events.add("The powerful hit sent tremors through the void around it, and the Reaper emitted a low growl of defiance.");
                events.add("The strike hit hard, causing the " + getName() + " to falter briefly, its tentacles flailing in agitation.");

                dialogues.add("Grrr-glkkk!");
                dialogues.add("Ggrrrhhhaaaaahhh!");
                dialogues.add("Grrraaaah!");

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
                events.add(getName() + " suffered a critical blow, staggering back as time itself flickered around it.");
                events.add("A critical blow struck with devastating force, causing " + getName() + " to reel as time itself wavered.");
                events.add("The blow resonated through the void, forcing the " + getName() + " to momentarily falter, shadows swirling in agitation.");
                events.add("The devastating impact sent the " + getName() + " crashing back, its growl turning into a deep, anguished roar.");

                dialogues.add("Ggrrrhhhaaaaahhh!");
                dialogues.add("Ggrrrk-kh!");

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
        if (!monsterDatabase.doesSelectedDataExist(getUniqueId())) {
            monsterDatabase.addMonster(this);
        }
    }

    /**
     * if the attacker hits, the void reaper will be mark with time passed status by 1 and it will stack with
     * each hits
     */
    public void receiveHit(Monster enemy, Monster you) {
        if (!hasStatus(enemy, "Fatigue", 1).isEmpty()) {
            enemy.setAttack(enemy.getAttack() - 250);
        }
        if (hasStatus(enemy, "Fatigue", 1).isEmpty()) {
            enemy.setAttack(enemy.getMaxAttack());
        }

        String result = receiveHitLogic(enemy, you);
        switch (result) {
            case "DODGE":
                events.add(getName() + " slithered out of harm’s way, its tentacles curling gracefully as it evaded the blow.");
                events.add("The attack sliced through the air, missing as the " + getName() + " dissolved momentarily into shadow, reappearing behind its foe.");
                events.add(getName() + " sidestepped the strike with an unnatural fluidity, leaving only a wisp of the void in its place.");
                events.add("The blow came close, but the " + getName() + prompt.getApostrophe(getName()) + " body twisted like smoke, avoiding the hit without effort.");
                events.add("Time seemed to warp as the " + getName() + " shifted, the attack passing harmlessly through the space it once occupied.");
                events.add("With a flicker of violet energy, the " + getName() + " vanished, dodging the attack just before it could connect.");

                dialogues.add("Shhhrrrgh.");
                dialogues.add("Grrrkk.");
                dialogues.add("Hhhrrrhh.");
                dialogues.add("Rrrraahhh.");
                dialogues.add("Fffshhh.");
                dialogues.add("Grrrah!");

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
            case "BLOCKED":
                events.add(getName() + " raised its scythe just in time, the blade deflecting the blow with a harsh, metallic clang.");
                events.add("The strike connected with " + getName() + prompt.getApostrophe(getName()) + " tentacles, but they coiled tightly, absorbing the impact without yielding.");
                events.add("The " + getName() + prompt.getApostrophe(getName()) + " shadowy form solidified as it met the attack head-on, dark energy rippling outward from the point of contact.");
                events.add("With a swift movement, " + getName() + " interposed its scythe between itself and the blow, the void around it shimmering from the force of the block.");
                events.add(getName() + prompt.getApostrophe(getName()) + " tentacles whipped around, forming a defensive barrier that absorbed the attack, sending vibrations through the void.");
                events.add("The strike clashed against " + getName() + prompt.getApostrophe(getName()) + " scythe, sparks flying as it growled lowly, holding its ground with unearthly strength.");

                dialogues.add("Chhrrk!");
                dialogues.add("Grrrrhhh.");
                dialogues.add("Thrrrssshh!");
                dialogues.add("Hhhhkkk!");
                dialogues.add("Grrraaakk!");
                dialogues.add("Rrrrgggh!");

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

        receiveStatus(you, "Time Passed", 1);
    }

    public void receiveTimeEffect(Monster enemy, Monster you) {
        runTimeHeal();
        runTimeDamage();

        ArrayList<String> newStatus = getStatusList().getValue();
        if (newStatus == null) {
            newStatus = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
        if (newStatusValue == null) {
            newStatusValue = new ArrayList<>();
        }

        ArrayList<String> enemyStatus = getStatusList().getValue();
        if (enemyStatus == null) {
            enemyStatus = new ArrayList<>();
        }

        ArrayList<Integer> enemyStatusValue = getStatusValueList().getValue();
        if (enemyStatusValue == null) {
            enemyStatusValue = new ArrayList<>();
        }

        voidTime--;
        if (voidTime <= 0) {
            backgroundImage.setBackgroundResource(backgroundList.get(selectedBackground));
            yourImage.setImageResource(getImage());
            resizeImage.scale(yourImage, getSize());
            voidTime = 0;

            if (!hasStatus(you, "Endless Void", 20).isEmpty()) {
                int index = Integer.parseInt(hasStatus(you, "Endless Void", 20));
                newStatusValue.remove(index);
                newStatus.remove(index);

                updateStatusList(newStatus);
                updateStatusValueList(newStatusValue);
            }
        }

        fatigue--;
        if (fatigue <= 0) {
            fatigue = 0;

            if (!hasStatus(enemy, "Fatigue", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(enemy, "Fatigue", 1));

                enemy.setAttack(enemy.getMaxAttack());
                enemy.setDodge(enemy.getMaxDodge());
                enemy.setDefense(enemy.getMaxDefense());

                enemyStatusValue.remove(index);
                enemyStatus.remove(index);

                enemy.updateStatusList(enemyStatus);
                enemy.updateStatusValueList(enemyStatusValue);
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
                events.add(getName() + " raised its scythe, and the void itself seemed to shudder. Time slowed as a violet aura crackled around its blade, distorting reality with every swing.");
                events.add("With a swift, sweeping motion, " + getName() + prompt.getApostrophe(getName()) + " scythe carved through time, leaving trails of dark energy. The air pulsed as moments were ripped apart, merging past and present.");
                events.add(getName() + prompt.getApostrophe(getName()) + " tentacles spread wide as it unleashed " + skillName + ", bending time around its foe. The very fabric of space trembled, caught in the grasp of the scythe.");
                events.add("A dark haze surrounded the " + getName() + " as it channeled the energy of " + skillName + ". Shadows stretched and twisted, bending toward the scythe's blade as time splintered.");
                events.add(getName() + prompt.getApostrophe(getName()) + " scythe shimmered with an unholy glow, and with one massive swing, it cut through reality itself. A deep, resonant hum filled the void as moments fractured.");
                events.add("With a deadly calm, " + getName() + " invoked " + skillName + ". A wave of violet light pulsed forward, and every second felt stretched and warped in its wake.");

                dialogues.add("Rrrrhhhaaaa!");
                dialogues.add("Shhhhhrrrkkk!");
                dialogues.add("Ghrrrrrhhh!");
                dialogues.add("Ggrrrhhhk!");
                dialogues.add("Fffssshhh!");
                dialogues.add("Rrrrraaahhh!");

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
                events.add(getName() + " tensed, its violet energy condensing until, in a sudden explosion, the very air seemed to shatter. Time itself fractured, catching " + enemy.getName() + " off-guard as they froze in place, stunned by the sudden distortion.");
                events.add("A low, reverberating hum grew from within " + getName() + ", culminating in a burst of fractured light that rippled outward. " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " movements halted, ensnared in the warped flow of time.");
                events.add("With a sharp, guttural growl, " + getName() + " unleashed " + skillName + ". Waves of dark energy erupted from its form, splitting through space and freezing " + enemy.getName() + " in a suspended daze.");
                events.add(getName() + prompt.getApostrophe(getName()) + " body pulsed with power, then burst into fragments of shadow that rippled through the battlefield. " + skillName + " around " + enemy.getName() + ", leaving them dazed and paralyzed.");
                events.add("A violet flash engulfed the void, and as the echoes of " + skillName + " faded, " + enemy.getName() + " was left stunned, trapped in the shattered fragments of time.");
                events.add(getName() + " unleashed a fierce pulse, its form expanding as time fractured around it. " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " body shuddered as they froze, their reality warped by the void’s sinister hold.");

                dialogues.add("Brrrraaakkk!");
                dialogues.add("Ghhhrrrzzzh!");
                dialogues.add("Shhhhhrraakk!");
                dialogues.add("Rrrggghhh!");
                dialogues.add("Ffsssshhh!");
                dialogues.add("Grrrkkkshh!");

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
                events.add(getName() + prompt.getApostrophe(getName()) + " tentacles spread wide, and the void around them seemed to warp and twist. In an instant, the world vanished, replaced by a realm of endless darkness and swirling violet energy. " + enemy.getName() + " struggled as " + getName() + prompt.getApostrophe(getName()) + " form shifted, growing larger and more terrifying.");
                events.add("Reality tore apart, and " + enemy.getName() + " found themselves surrounded by the shadowed expanse of " + getName() + prompt.getApostrophe(getName()) + " realm. With a low growl, " + getName() + " began to transform, his body now armored and crackling with dark energy. Each attack rippled through the air, his cooldown lessening as his relentless onslaught continued.");
                events.add("The dimension shifted, pulling " + getName() + " and " + enemy.getName() + " into a swirling cosmos of shadow and violet. With a distorted howl, " + getName() + " morphed into a stronger, monstrous form, his scythe glowing with power. He lunged, unleashing a powerful burst of energy that rattled the dimension, striking with fierce precision.");
                events.add(getName() + prompt.getApostrophe(getName()) + " laughter echoed as he dragged " + enemy.getName() + " into his realm, his form twisting and expanding with newfound power. The void pulsed as he unleashed a barrage of attacks, each one further reducing the delay between his skills.");
                events.add("With a chilling roar, " + getName() + " transported them both to his dominion. Violet shadows danced as he transformed, his aura darkening with increased power. Each blow struck with more ferocity than the last, an unstoppable cascade that reset his abilities with each powerful swing.");
                events.add(enemy.getName() + " barely had a moment to react as " + getName() + prompt.getApostrophe(getName()) + " realm enveloped them. Transformed and pulsing with energy, " + getName() + " unleashed a deadly assault, each strike diminishing his cooldown with the relentless cadence of a nightmare.");

                dialogues.add("Hrrrrraaaaggh!");
                dialogues.add("Grraahhhkk!");
                dialogues.add("Fffsshhhrrrk!");
                dialogues.add("Rrrgghhhah!");
                dialogues.add("Thrrrrssskt!");
                dialogues.add("Grrrrraaahhkkk!");

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
                events.add(getName() + " suddenly burst forward, its form a blur of violet shadows. As it dashed, the very fabric of time around it began to unravel, reversing the flow of moments and healing its wounds.");
                events.add("In an instant, " + getName() + " flickered, appearing to dart backwards through the void. Time itself rewound, restoring health as past injuries faded like echoes in the shadows.");
                events.add("With a swift movement, " + getName() + " activated " + skillName + ", sending ripples through time. As it dashed, the wounds began to seal, and vitality surged back into its body.");
                events.add("The darkness around " + getName() + " twisted as it performed " + skillName + ", gliding through time. As moments rewound, it felt the rush of healing energy enveloping its form, recovering what was lost.");
                events.add(getName() + " surged forward, the air crackling as time itself began to reverse. With each step, health surged back, each injury erasing like a forgotten memory.");
                events.add("As " + getName() + " executed " + skillName + ", the world around it faded and rewound. The shadows coiled around, knitting wounds and renewing strength as it dashed through time.");

                dialogues.add("Rrrrhhk!");
                dialogues.add("Ghhrrrraah!");
                dialogues.add("Fffffssshhh!");
                dialogues.add("Ghhrreeek!");
                dialogues.add("Brrrraaaak!");
                dialogues.add("Grrrhhhk!");

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
                events.add(getName() + " gathered energy, the air around its scythe crackling with dark power. With a swift motion, it swung the blade, creating a rift in space that surged toward " + enemy.getName() + ".");
                events.add("As " + getName() + " unleashed " + skillName + ", a vortex of dark energy erupted from its scythe. The attack cleaved through the air, leaving a trail of distortion that threatened to consume everything in its path.");
                events.add("With a growl that echoed through the void, " + getName() + " launched " + skillName + ". The blade sliced through reality, unleashing a wave of force that sought to tear apart " + enemy.getName() + ".");
                events.add(getName() + prompt.getApostrophe(getName()) + " scythe shimmered with violet energy as it performed " + skillName + ", the very fabric of space warping around it. The attack surged forward, a relentless tide of dark power.");
                events.add("The air thickened with tension as " + getName() + " prepared " + skillName + ". With a devastating arc of its blade, it unleashed the attack, a gravitational pull following in its wake, drawing " + enemy.getName() + " closer to the impending doom.");
                events.add("With a fierce determination, " + getName() + " executed " + skillName + ". The slash unleashed a gravitational shockwave, warping the space around " + enemy.getName() + " and engulfing them in darkness.");

                dialogues.add("Ssssshhhhh!");
                dialogues.add("Fffhrrrraaag!");
                dialogues.add("Rrrraaaashhh!");
                dialogues.add("Grrrrrreeeek!");
                dialogues.add("Brrrraaaak!");
                dialogues.add("Grrraaaaaash!");

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
                events.add("As " + getName() + " loomed closer, it unleashed the dark essence of " + skillName + ". Shadows coiled around " + enemy.getName() + ", sapping their energy and dulling their senses, leaving them vulnerable to the onslaught");
                events.add("With a low, growling hum, " + getName() + " invoked " + skillName + ", dark tendrils swirling through the air and wrapping around " + enemy.getName() + ". Their strength waned, and they staggered under the weight of the encroaching darkness.");
                events.add(getName() + prompt.getApostrophe(getName()) + " scythe glimmered ominously as it initiated " + skillName + ", draining " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " vitality. Each strike felt heavier, their reflexes slowed as shadows engulfed their form, making it harder to resist the coming assault.");
                events.add("As " + enemy.getName() + " felt the effects of " + skillName + ", " + getName() + prompt.getApostrophe(getName()) + " presence became suffocating. The weight of the void pressed down on them, reducing their defense and dodging ability as the shadows seeped deeper.");
                events.add(getName() + " advanced, eyes glinting with malevolence as it activated " + skillName + ". The air thickened with darkness, wrapping around " + enemy.getName() + " and dulling their attacks, making them feel sluggish and weak.");
                events.add("With a chilling growl, " + getName() + " unleashed " + skillName + ", the void swirling around " + enemy.getName() + " and siphoning their strength. The shadows twisted, making each attempt to fight back feel futile and clumsy.");

                dialogues.add("Grrrrrhhhhh!");
                dialogues.add("Ffffssshhh!");
                dialogues.add("Ghrrrraaaak!");
                dialogues.add("Ghrrreeeeek!");
                dialogues.add("Brrrrrraaaah!");
                dialogues.add("Fffffsssrrkk!");

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
            case 6:
                events.add(getName() + " raised its scythe, time rippling as it invoked " + skillName + ". Shadows coiled around, reversing moments as the two combatants were pulled back to the battle’s start, their injuries erased. But for " + enemy.getName() + ", each skill felt further out of reach, as though bound by the weight of time.");
                events.add("With a chilling hum, " + getName() + prompt.getApostrophe(getName()) + " scythe tore through reality, and " + skillName + " triggered. The void swirled, pulling them both back to where it all began. " + enemy.getName() + " felt the oppressive weight of delayed powers, each skill more distant, shackled by lingering time.");
                events.add(getName() + prompt.getApostrophe(getName()) + " form blurred as " + skillName + " unfolded, reversing injuries and pulling both fighters back to the start. Time reset for health but not for power; " + enemy.getName() + " felt the lock on their abilities tighten, skills now tangled in the web of lingering cooldowns.");
                events.add("As " + getName() + " activated " + skillName + ", the battlefield twisted, yanking both back to their starting positions. Their health renewed, but " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " skills were bound tighter, cooldowns extended by the void’s cold grip.");
                events.add("With a dark gleam in its empty gaze, " + getName() + " enacted " + skillName + ". Shadows consumed the field, winding back time. Both returned to the beginning, unharmed, but " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " powers felt trapped, delayed by a spectral weight.");
                events.add(getName() + " invoked " + skillName + ", the air shuddering as time rewound. Both regained their strength, but " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " power was slowed, abilities held back as if bound by spectral chains.");

                dialogues.add("Grraahh… ffsssshhh.");
                dialogues.add("Rrrrhhhkk… gghrraaak!");
                dialogues.add("Ssshhhaaak… ghrrrraaah.");
                dialogues.add("Ghrrrrr… fffrrraaah!");
                dialogues.add("Fffssshhhh… grrahhh.");
                dialogues.add("Ghhrrrrraaakk!");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if(prompt.isTherePopup())
                {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill6(you, enemy);
                break;
        }

        reduceCooldown(skillIndex);
        return skillName;
    }

    private void reduceCoolDown(Monster you, Monster enemy) {
        if (!hasStatus(enemy, "The Void", 1).isEmpty()) {
            ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
            if (newSkillCooldowns == null) {
                newSkillCooldowns = new ArrayList<>();
            }

            for (int i = 1; i <= newSkillCooldowns.size() - 1; i++) {
                newSkillCooldowns.set(i, newSkillCooldowns.get(i) - 1);
                if (newSkillCooldowns.get(i) <= 0) {
                    newSkillCooldowns.set(i, 0);
                }
                updateMaxSkillCooldowns(newSkillCooldowns);
            }
        }

        if (!hasStatus(you, "Endless Void", 20).isEmpty()) {
            ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
            if (newSkillCooldowns == null) {
                newSkillCooldowns = new ArrayList<>();
            }

            for (int i = 1; i <= newSkillCooldowns.size() - 1; i++) {
                newSkillCooldowns.set(i, 0);
                updateMaxSkillCooldowns(newSkillCooldowns);
            }
        }
    }

    //if there is the void mark, the void reaper reduces his cooldown with each attack he deals
    @Override
    public void basicAttack(Monster you, Monster enemy) {
        enemy.receiveHit(you, enemy);
        reduceCoolDown(you, enemy);
    }

    //stuns the enemy and burst them and applies "the void" status to the target
    private void skill1(Monster you, Monster enemy) {
        backgroundImage.setBackgroundResource(getDimension()[1]);
        yourImage.setImageResource(getTransformation()[0]);
        resizeImage.scale(yourImage, 185);
        voidTime = 9;

        setAttack(8500);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());
        receiveStatus(enemy, "The Void", 1);
        enemy.setStun(3);
    }

    //the enemy will be teleported to a different dimension and void reaper will transform, after that
    //he will burst the target and resets all skill cooldown every attack
    private void skill2(Monster you, Monster enemy) {
        backgroundImage.setBackgroundResource(getDimension()[0]);
        yourImage.setImageResource(getTransformation()[0]);
        resizeImage.scale(yourImage, 185);
        voidTime = 9;

        setAttack(12500);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());

        receiveStatus(you, "Endless Void", 20);
        reduceCoolDown(you, enemy);
    }

    //heals base on the time passed buff
    private void skill3(Monster you, Monster enemy) {
        ArrayList<String> newStatus = getStatusList().getValue();
        if (newStatus == null) {
            newStatus = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
        if (newStatusValue == null) {
            newStatusValue = new ArrayList<>();
        }

        if (!hasStatus(you, "Time Passed", 1).isEmpty()) {
            int index = Integer.parseInt(hasStatus(you, "Time Passed", 1));
            int healthPortion = (int) (getMaxHealth() * 0.05);

            for (int i = 0; i <= newStatusValue.get(index) - 1; i++) {
                setHealth(getHealth() + healthPortion);
            }

            newStatusValue.remove(index);
            newStatus.remove(index);

            updateStatusList(newStatus);
            updateStatusValueList(newStatusValue);
        } else {
            setHealth(getHealth() + 750);
        }
    }

    //attack target and deals damage based on the time passed buff
    private void skill4(Monster you, Monster enemy) {
        ArrayList<String> newStatus = getStatusList().getValue();
        if (newStatus == null) {
            newStatus = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
        if (newStatusValue == null) {
            newStatusValue = new ArrayList<>();
        }

        if (!hasStatus(you, "Time Passed", 1).isEmpty()) {
            int index = Integer.parseInt(hasStatus(you, "Time Passed", 1));
            int damagePortion = (int) (enemy.getMaxHealth() * 0.05);

            for (int i = 0; i <= newStatusValue.get(index) - 1; i++) {
                setAttack((getAttack() + damagePortion));
                enemy.receiveHit(you, enemy);
                setAttack(getMaxAttack());
            }

            newStatusValue.remove(index);
            newStatus.remove(index);

            updateStatusList(newStatus);
            updateStatusValueList(newStatusValue);
        } else {
            setAttack(getAttack() + 750);
            enemy.receiveHit(you, enemy);
            setAttack(getMaxAttack());
        }

        reduceCoolDown(you, enemy);
    }

    //reduces enemies stats for 4 turns and applies "fatigue" status to the target
    private void skill5(Monster you, Monster enemy) {
        fatigue = 12;

        receiveStatus(enemy, "Fatigue", 50);
        enemy.setAttack(enemy.getAttack() - 350);
        enemy.setDodge(0);
        enemy.setDefense(0);

        setAttack(getAttack() + 3500);
        enemy.receiveHit(you, enemy);
        setAttack(getMaxAttack());

        reduceCoolDown(you, enemy);
    }

    //resets all the way back to the beginning of the battle and enemies cannot use skill at the start
    private void skill6(Monster you, Monster enemy) {
        enemy.setHealth(enemy.getMaxHealth());
        enemy.setAttack(enemy.getMaxAttack());
        enemy.setDefense(enemy.getMaxDefense());
        enemy.setDodge(enemy.getMaxDodge());
        enemy.setStun(0);
        enemy.setHealOverTime(new ArrayList<>());
        enemy.setHealOverTimeValue(new ArrayList<>());
        enemy.setDamageOverTime(new ArrayList<>());
        enemy.setDamageOverTimeValue(new ArrayList<>());

        ArrayList<Integer> targetSkillCooldowns = enemy.getSkillCooldowns().getValue();
        if (targetSkillCooldowns == null) {
            targetSkillCooldowns = new ArrayList<>();
        }

        for (int i = 1; i <= targetSkillCooldowns.size() - 1; i++) {
            targetSkillCooldowns.set(i, targetSkillCooldowns.get(i) + 5);
            enemy.updateMaxSkillCooldowns(targetSkillCooldowns);
        }

        setHealth(getMaxHealth());
        setAttack(getMaxAttack());
        setDefense(getMaxDefense());
        setDodge(getMaxDodge());
        setStun(0);
        setHealOverTime(new ArrayList<>());
        setHealOverTimeValue(new ArrayList<>());
        setDamageOverTime(new ArrayList<>());
        setDamageOverTimeValue(new ArrayList<>());

        ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        for (int i = 1; i <= newSkillCooldowns.size() - 1; i++) {
            newSkillCooldowns.set(i, 0);
            updateMaxSkillCooldowns(newSkillCooldowns);
        }
    }
}
