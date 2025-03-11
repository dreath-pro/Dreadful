package com.example.dreadful.characters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.dreadful.R;
import com.example.dreadful.databases.MonsterDatabase;
import com.example.dreadful.logics.ResizeImage;
import com.example.dreadful.models.Monster;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Carnant extends Monster {
    private Random random = new Random();
    private ImageView yourImage;
    private ProgressBar yourHealthBar;
    private ResizeImage resizeImage;
    private TextView yourName;
    private Prompt prompt;
    private int form = 0;
    private int increaseHeal = 4, maxIncreaseHeal = 4;
    private int increaseVenom = 4, maxIncreaseVenom = 4;
    private int heal = 300, venom = 100;
    private int dissolve = 0;
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();
    private MonsterDatabase monsterDatabase;

    public Carnant(Context context) {
        super("001-CRNT", context, null, "Psycho Killer", R.drawable.character_psychopath, "left", 140,
                new int[]{R.drawable.character_carnant}, null,
                3000, 300, 10, 40);
    }

    public Carnant(Context context, ImageView yourImage, ProgressBar yourHealthBar, TextView yourName, Prompt prompt) {
        super("001-CRNT", context, yourImage, "Psycho Killer", R.drawable.character_psychopath, "left", 140,
                new int[]{R.drawable.character_carnant}, null,
                3000, 300, 10, 40);

        ArrayList<String> skillNames = new ArrayList<>();
        skillNames.add("Bat Slam");
        skillNames.add("Hard Swing");
        skillNames.add("Left Kick");

        skillNames.add("Tentacle Pierce");
        skillNames.add("Venom Puncture");
        skillNames.add("Poison Regen");
        skillNames.add("Dissolve Armor");
        updateSkillNames(skillNames);

        ArrayList<Integer> maxSkillCooldowns = new ArrayList<>();
        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(4);
        maxSkillCooldowns.add(2);

        maxSkillCooldowns.add(0);
        maxSkillCooldowns.add(1);
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
        skillCooldowns.add(0);
        updateSkillCooldowns(skillCooldowns);

        this.context = context;
        this.prompt = prompt;
        this.yourImage = yourImage;
        this.yourHealthBar = yourHealthBar;
        this.yourName = yourName;
        this.resizeImage = new ResizeImage(context);

        this.monsterDatabase = new MonsterDatabase(context);
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                if (form == 0) {
                    events.add("The attack barely grazed " + getName() + prompt.getApostrophe(getName()) + " arm, but instead of fear, he let out a maniacal laugh, eyes wide with excitement.");
                    events.add(getName() + prompt.getApostrophe(getName()) + " grin stretched wider as the attack barely nicked his skin, his fingers twitching in anticipation.");

                    dialogues.add("Hahaha! You missed the fun part!");
                    dialogues.add("That all you got? Pathetic!");
                } else {
                    events.add("The attack bounced harmlessly off " + getName() + prompt.getApostrophe(getName()) + " red armor, his sharp claws flexing as a guttural growl echoed from his chest.");
                    events.add(getName() + " barely noticed the minor hit, his tentacles twitching as he lurched forward, the hunger for violence burning in his glowing eyes.");

                    dialogues.add("Grraaah… fffsshhh… is that all?!");
                    dialogues.add("Hrrrk… weak… just like the rest!");
                }

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
                if (form == 0) {
                    events.add("The blow made " + getName() + " stumble slightly, but his wild eyes sparkled with bloodlust as he wiped the blood from his mouth.");
                    events.add(getName() + " hissed in pain as the attack sunk into his shoulder, but his insane grin never left his face. His breathing grew heavier, excited by the rush.");

                    dialogues.add("Hrrgh… oh, I like that! Hit me again, if you dare!");
                    dialogues.add("Yessss… that’s it… more blood!");
                } else {
                    events.add("The hit struck " + getName() + prompt.getApostrophe(getName()) + " side, his monstrous form twitching, but he only growled in satisfaction, his claws scraping the ground as he advanced.");
                    events.add("The strike hit " + getName() + prompt.getApostrophe(getName()) + " armor hard, but instead of slowing down, he let out a deep, monstrous laugh, his tentacles flaring as his claws extended.");

                    dialogues.add("Rrrraahhh… now it’s my turn!");
                    dialogues.add("Grrrhhhk… you hit me… now bleed!");
                }

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
                if (form == 0) {
                    events.add("The attack tore into " + getName() + prompt.getApostrophe(getName()) + " flesh, causing him to stagger, but his cackling echoed through the alley as he pushed himself back up.");
                    events.add("Blood gushed from " + getName() + prompt.getApostrophe(getName()) + " wound, but his eyes gleamed with twisted joy, the pain driving him further into madness.");

                    dialogues.add("Hahahaha! That’s the kind of thrill I’m after!");
                    dialogues.add("Grrraahh! Now you’re playing the game right!");
                } else {
                    events.add("The powerful attack crashed into " + getName() + prompt.getApostrophe(getName()) + " chest, cracking his armor. He roared in a mix of pain and rage, his tentacles writhing violently.");
                    events.add(getName() + " reeled back from the blow, but his mutant body surged with fury. His claws scraped the ground as he lunged forward, laughter twisting into a growl.");

                    dialogues.add("Rrrraagh!! Pain… yessss… let’s see if you can survive it!!");
                    dialogues.add("Hehehe… strong… but not strong enough!!");
                }

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
                if (form == 0) {
                    events.add(getName() + " dropped to one knee as the strike nearly killed him, but instead of giving in, he let out an ear-piercing laugh, standing up with blood pouring down his body.");
                    events.add("The critical hit left him staggering, blood dripping from his mouth, but " + getName() + prompt.getApostrophe(getName()) + " laughter only intensified, his psychotic rage burning brighter.");

                    dialogues.add("Hahahahaha!! You… can’t stop me! I’ll take you down… one limb at a time!");
                    dialogues.add("More… MORE!! I’m still standing!! You’re gonna die slowly!");
                } else {
                    events.add(getName() + prompt.getApostrophe(getName()) + " body was slammed into the ground, his armor shattered, blood pouring from deep wounds. But through the pain, his manic laughter pierced the air as he dragged himself back up.");
                    events.add("The critical hit tore into " + getName() + prompt.getApostrophe(getName()) + " body, leaving him bleeding heavily, but his glowing red eyes burned with unstoppable rage. He stood back up, claws twitching as he prepared to attack again.");

                    dialogues.add("Hahahaha!! I’ll tear you apart!! Piece by piece!");
                    dialogues.add("Rrrraghh! You think this is enough?! I’ll make you regret that!!");
                }

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
        if (!monsterDatabase.doesSelectedDataExist(getUniqueId())) {
            monsterDatabase.addMonster(this);
        }
    }

    /**
     * if he dies theres a 50/50 chance he will transform into a strong mutant, or will just die easily
     */
    public void receiveHit(Monster enemy, Monster you) {
        String result = receiveHitLogic(enemy, you);
        switch (result) {
            case "DODGE":
                if (form == 0) {
                    events.add("The attack cut through the air as " + getName() + " swiftly dodged to the side, laughing maniacally at his own reflexes.");
                    events.add(getName() + " ducked just in time to avoid the swing, his deranged grin growing wider as he looked back with crazed delight.");
                    events.add("The attack missed " + getName() + " by inches, and he leaned back, barely flinching, his twisted smirk dripping with mockery.");
                    events.add(getName() + " sidestepped the strike at the last second, his breath ragged with excitement. His psychotic laughter filled the air as he pointed mockingly.");

                    dialogues.add("Hahaha! Too slow, Try harder!");
                    dialogues.add("Tsk, tsk… gonna have to be quicker than that!");
                    dialogues.add("Heh… close! But close don’t count!");
                    dialogues.add("Hah! You missed! Come on, don’t disappoint me!");
                } else {
                    events.add(getName() + prompt.getApostrophe(getName()) + " tentacles lashed out, pulling him out of the path of Dreath’s attack just in time. His growling laugh echoed as he taunted his foe.");
                    events.add("With unnatural speed, " + getName() + " twisted his body to dodge the strike, his armored form gleaming under the dim light as he let out a guttural snarl.");
                    events.add(getName() + prompt.getApostrophe(getName()) + " tentacles whipped the ground, propelling him backward, narrowly avoiding the swing. His glowing eyes locked with malicious glee.");
                    events.add("The hit whizzed past " + getName() + prompt.getApostrophe(getName()) + " armored head as he shifted just in time. He let out a low, rumbling growl as he mocked his opponent.");

                    dialogues.add("Grrahh… too slow! You’ll never catch me!");
                    dialogues.add("Hrrkk… pathetic, Dreath… you missed.");
                    dialogues.add("Rrrahh… nice try, but I’m not that easy to hit!");
                    dialogues.add("Grraa… dodged again, what now?");
                }

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
                if (form == 0) {
                    events.add("The strike crashed against " + getName() + prompt.getApostrophe(getName()) + " arm, the impact ringing out like a bell. Instead of backing down, " + getName() + " grinned maniacally, relishing the confrontation.");
                    events.add(getName() + " raised his arm just in time to deflect the attack, absorbing the blow with a sadistic laugh. He reveled in the contact, enjoying the moment.");
                    events.add("The force of that attack pushed " + getName() + " back a step, but he steadied himself with a cackle, eyes gleaming with madness.");
                    events.add("The assault clanged against " + getName() + prompt.getApostrophe(getName()) + " forearm, leaving a dent in his flesh. He responded with a twisted grin, dripping with bloodlust.");

                    dialogues.add("Heheh! That tickles! Is that all you got?!");
                    dialogues.add("You think a little scratch will stop me? Come on, show me more!");
                    dialogues.add("Agh! Nice try, but you’ll have to hit harder to make me bleed!");
                    dialogues.add("Hah! You’ll need to do better than that! I’m just getting started!");
                } else {
                    events.add(getName() + " braced himself as the attack struck his armored shoulder, the impact sending a shudder through his body, but he laughed in defiance.");
                    events.add("With a swift movement, " + getName() + " raised his claws to block the strike, the sound of collision echoing. He chuckled darkly, his eyes glowing with exhilaration.");
                    events.add("The blow landed squarely on " + getName() + prompt.getApostrophe(getName()) + " armor, and he absorbed the impact with a growl, flexing his claws as he laughed maniacally.");
                    events.add("As the strike met " + getName() + prompt.getApostrophe(getName()) + " armored arm, he stood firm, deflecting the blow effortlessly. His jagged laughter echoed through the battlefield, taunting.");

                    dialogues.add("Grrahh! You think that hurts? I barely felt a thing!");
                    dialogues.add("Rrraah! Nice effort, but you’ll have to do more than that to break through!");
                    dialogues.add("Hrrkk! Is that your best shot? I expected more from you!");
                    dialogues.add("Grraaahh! You’re gonna have to hit harder! I love a challenge!");
                }

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

        if (form == 0) {
            if (getHealth() <= 0) {
                int toTransform = random.nextInt(2);

                if (toTransform == 0) {
                    setName("Carnant");
                    yourName.setText(getName());
                    yourImage.setImageResource(getTransformation()[0]);
                    setImage(getTransformation()[0]);
                    resizeImage.scale(yourImage, 155);
                    bypassSetMaxHealth(8880);
                    setHealth(getMaxHealth());
                    setAttack(1800);
                    setDefense(480);
                    setDodge(0);
                    yourHealthBar.setMax(getMaxHealth());
                    form = 1;
                }
            }
        }
    }

    public void receiveTimeEffect(Monster enemy, Monster you) {
        runTimeHeal();
        runTimeDamage();

        if (form == 0) {
            if (getHealth() <= 0) {
                int toTransform = random.nextInt(2);

                if (toTransform == 0) {
                    setName("Carnant");
                    yourName.setText(getName());
                    yourImage.setImageResource(getTransformation()[0]);
                    setImage(getTransformation()[0]);
                    resizeImage.scale(yourImage, 155);
                    bypassSetMaxHealth(8880);
                    setHealth(getMaxHealth());
                    setAttack(1800);
                    setDefense(480);
                    setDodge(0);
                    yourHealthBar.setMax(getMaxHealth());
                    form = 1;
                }
            }
        }

        if (form == 1) {
            bypassSetHealth(getHealth() + heal);
            if (getHealth() > getMaxHealth()) {
                bypassSetMaxHealth(getHealth());
                yourHealthBar.setMax(getMaxHealth());
            }

            setAttack(getAttack() + 80);

            increaseHeal--;
            if (increaseHeal <= 0) {
                increaseHeal = maxIncreaseHeal;
                heal += 40;
            }

            increaseVenom--;
            if (increaseVenom <= 0) {
                increaseVenom = maxIncreaseVenom;
                venom += 40;
            }

            dissolve--;
            if (dissolve <= 0) {
                if (!hasStatus(you, "Dissolve Armor", 100).isEmpty()) {
                    setDefense(getMaxDefense());

                    int index = Integer.parseInt(hasStatus(you, "Dissolve Armor", 100));

                    ArrayList<String> newStatus = getStatusList().getValue();
                    if (newStatus == null) {
                        newStatus = new ArrayList<>();
                    }

                    ArrayList<Integer> newStatusValue = getStatusValueList().getValue();
                    if (newStatusValue == null) {
                        newStatusValue = new ArrayList<>();
                    }

                    newStatus.remove(index);
                    newStatusValue.remove(index);

                    updateStatusList(newStatus);
                    updateStatusValueList(newStatusValue);
                }
            }
        }
    }

    public String useRandomAttack(Monster you, Monster enemy) {
        String skillName;
        int skillIndex;

        ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        if (newSkillCooldowns.get(3) > 0) {
            newSkillCooldowns.set(3, 0);
        }
        updateSkillCooldowns(newSkillCooldowns);

        do {
            if (form == 0) {
                skillIndex = random.nextInt(3);
            } else {
                skillIndex = random.nextInt(4) + 3;
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
                events.add(getName() + " lunged forward, gathering his strength as he prepared for the " + skillName + ". With a wild grin, he leaped into the air, ready to bring his full weight down on " + enemy.getName() + ".");
                events.add("As he soared through the air, " + getName() + prompt.getApostrophe(getName()) + " laughter echoed ominously. He curled his body into a ball, crashing down with tremendous force, intent on obliterating everything in his path.");
                events.add("The ground shook beneath him as " + getName() + " slammed down, his laughter rising above the chaos. Dust and debris flew as he landed, eyes wide with manic delight.");

                dialogues.add("Hahaha! Time to feel the weight of my madness!");
                dialogues.add("Grraahhh! Here comes the pain!");
                dialogues.add("Hah! Feel that? That’s the sound of your doom!");

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
                events.add(getName() + " tightened his grip on his weapon, a wild glint in his eyes as he swung it back, gathering momentum for the strike. He was ready to unleash his fury.");
                events.add("With a ferocious grin, " + getName() + " unleashed the " + skillName + ", his weapon cutting through the air with deadly precision. He felt the thrill of the impending impact.");
                events.add("The force of the swing echoed through the battlefield as " + getName() + prompt.getApostrophe(getName()) + " weapon collided with " + enemy.getName() + ", sending shockwaves of pain rippling through his opponent. He laughed maniacally at the sight of " + enemy.getName() + " staggering back.");

                dialogues.add("Hahaha! Get ready for a world of hurt!");
                dialogues.add("You will be added to my kill count.");
                dialogues.add("Grrahh! Stunned already? This is too easy!");

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
                events.add(getName() + " kicks at maximum strength!");
                events.add(getName() + " kicks at his target with no hesitation.");
                events.add(getName() + " does a heavy kick.");

                dialogues.add("You are just another corpse.");
                dialogues.add("You should be happy I am giving you chance to live!");
                dialogues.add("Rrraah! You won’t see this coming!");

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

            //mutant form
            case 3:
                events.add(enemy.getName() + " is pierced by " + getName() + prompt.getApostrophe(getName()) + " tentacle.");
                events.add(getName() + " used " + skillName + " to destroy " + enemy.getName() + " with raw strength.");
                events.add(getName() + " quick pierce through " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " body.");

                dialogues.add("I am your greatest kryptonite.");
                dialogues.add("I am infinity, this fight is eternal!");
                dialogues.add("I'm getting stronger!");

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
                events.add(enemy.getName() + " is punctured by a deadly venom.");
                events.add(getName() + " targeted and injected " + enemy.getName() + " with an aggressive venom.");
                events.add(getName() + " injected venom into " + enemy.getName() + prompt.getApostrophe(enemy.getName()) + " body.");

                dialogues.add("You will slowly die by my venom!");
                dialogues.add("Succumb into the afterlife.");
                dialogues.add("No matter how strong you are, you are fragile to my venom!");

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
            case 5:
                events.add(getName() + " ingested his own poison to recover his lost health.");
                events.add(getName() + prompt.getApostrophe(getName()) + " poison takes over his body and recovers those damage cell.");
                events.add(getName() + " absorb his own poison from his tentacle to heal his body.");

                dialogues.add("You can't kill me");
                dialogues.add("I can feel power");
                dialogues.add("The surge of energy is so powerful");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
                    prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.white));
                    prompt.selectRandomMessage(this, dialogues, true);
                }
                dialogues.clear();

                skill5(you, enemy);
                break;
            case 6:
                events.add(getName() + prompt.getApostrophe(getName()) + " body is dissolving making it hard for " + enemy.getName() + " to attack.");
                events.add(getName() + prompt.getApostrophe(getName()) + " cell is liquefied.");
                events.add(getName() + " started to get deformed and agile from incoming attack.");

                dialogues.add("Hehehe you cannot destroy my liquefied body!");
                dialogues.add("I am invincible!");
                dialogues.add("I am blessed to have this power.");

                prompt.getMessageColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                prompt.selectRandomMessage(this, events, false);
                events.clear();

                if (prompt.isTherePopup()) {
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

    //simple attack with stun
    private void skill1(Monster you, Monster enemy) {
        enemy.receiveHit(you, enemy);
        enemy.setStun(3);
    }

    //simple attack
    private void skill2(Monster you, Monster enemy) {
        enemy.receiveHit(you, enemy);
    }

    //attack that will pierce through defense
    private void skill3(Monster you, Monster enemy) {
        enemy.setDefense(0);
        setAttack(getAttack() * 2);

        enemy.receiveHit(you, enemy);

        setAttack(getMaxAttack());
        enemy.setDefense(enemy.getMaxDefense());
    }

    //same effect with basic attack but with enhanced damage and follow up damage over time that last for 4 turns
    private void skill4(Monster you, Monster enemy) {
        enemy.setDefense(0);
        setAttack(getAttack() * 4);

        enemy.receiveHit(you, enemy);

        setAttack(getMaxAttack());
        enemy.setDefense(enemy.getMaxDefense());

        enemy.getDamageOverTime().add(venom);
        enemy.getDamageOverTimeValue().add(12);
    }

    //simple heal over time that last for 4 turns while also applying venom that last for 4 turns
    private void skill5(Monster you, Monster enemy) {
        getHealOverTime().add(heal * 2);
        getHealOverTimeValue().add(12);

        enemy.getDamageOverTime().add(venom);
        enemy.getDamageOverTimeValue().add(12);
    }

    //increase dodge while applying dissolve armor status and venom that last for 4 turns
    private void skill6(Monster you, Monster enemy) {
        setDodge(getDodge() + 80);
        receiveStatus(you, "Dissolve Armor", 100);

        enemy.getDamageOverTime().add(venom);
        enemy.getDamageOverTimeValue().add(12);
    }
}
