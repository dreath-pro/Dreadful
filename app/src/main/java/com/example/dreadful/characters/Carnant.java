package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.logics.ResizeImage;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class Carnant extends Player {
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
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public Carnant(Context context, ImageView yourImage, ProgressBar yourHealthBar, TextView yourName, TestActivity testActivity) {
        super(context, yourImage, "Psycho Killer", R.drawable.character_psychopath, "left", 140,
                new int[]{R.drawable.character_carnant}, null,
                2100, 180, 10, 40,
                new String[]{"Bat Slam", "Hard Swing", "Left Kick",
                        "Tentacle Pierce", "Venom Puncture", "Poison Regen", "Dissolve Armor"},
                new int[]{0, 4, 2,
                        0, 1, 5, 4},

                new int[]{0, 0, 0,
                        0, 0, 0, 0});

        this.prompt = new Prompt(testActivity);
        this.prompt = testActivity.getPrompt();
        this.yourImage = yourImage;
        this.yourHealthBar = yourHealthBar;
        this.yourName = yourName;
        this.resizeImage = new ResizeImage(context);
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

                prompt.selectRandomEvent(events);
                events.clear();

                prompt.selectRandomDialogue(this, dialogues, true);
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

                prompt.selectRandomEvent(events);
                events.clear();

                prompt.selectRandomDialogue(this, dialogues, true);
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

                prompt.selectRandomEvent(events);
                events.clear();

                prompt.selectRandomDialogue(this, dialogues, true);
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

                prompt.selectRandomEvent(events);
                events.clear();

                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
        }
    }

    /**
     * if he dies theres a 50/50 chance he will transform into a strong mutant, or will just die easily
     */
    public void receiveHit(Player hitter, Player target) {
        String result = receiveHitLogic(hitter, target);
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

                prompt.selectRandomEvent(events);
                events.clear();

                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();
                break;
            case "BLOCKED":
                if (form == 0) {
                    events.add("The attack cut through the air as " + getName() + " swiftly dodged to the side, laughing maniacally at his own reflexes.");

                    dialogues.add("Hah! You missed! Come on, don’t disappoint me!");
                } else {
                    events.add(getName() + prompt.getApostrophe(getName()) + " tentacles lashed out, pulling him out of the path of Dreath’s attack just in time. His growling laugh echoed as he taunted his foe.");

                    dialogues.add("Grraa… dodged again, Dreath… what now?");
                }

                prompt.selectRandomEvent(events);
                events.clear();

                prompt.selectRandomDialogue(this, dialogues, true);
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

    public void receiveTimeEffect(Player hitter, Player target) {
        runTimeHeal();
        runTimeDamage();

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
                if (!hasStatus(target, "Dissolve Armor", 100).isEmpty()) {
                    setDefense(getMaxDefense());

                    int index = Integer.parseInt(hasStatus(target, "Dissolve Armor", 100));
                    getStatusValue().remove(index);
                    getStatus().remove(index);
                }
            }
        }
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;
        int skillIndex;

        if (getSkillCooldowns()[4] > 0) {
            getSkillCooldowns()[4] = 0;
        }

        do {
            if (form == 0) {
                skillIndex = random.nextInt(3);
            } else {
                skillIndex = random.nextInt(3) + 3;
            }
            //skillIndex = random.nextInt(getSkillNames().length);
        } while (getSkillCooldowns()[skillIndex] > 0);

        skillName = getSkillNames()[skillIndex];
        switch (skillIndex) {
            //human form
            case 0:
                basicAttack(hitter, target);
                break;
            case 1:
                skill1(hitter, target);
                break;
            case 2:
                skill2(hitter, target);
                break;

            //mutant form
            case 3:
                skill3(hitter, target);
                break;
            case 4:
                skill4(hitter, target);
                break;
            case 5:
                skill5(hitter, target);
                break;
            case 6:
                skill6(hitter, target);
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

    //simple attack
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
    }

    //simple attack with stun
    private void skill1(Player hitter, Player target) {
        target.receiveHit(hitter, target);
        target.setStun(3);
    }

    //simple attack
    private void skill2(Player hitter, Player target) {
        target.receiveHit(hitter, target);
    }

    //attack that will pierce through defense
    private void skill3(Player hitter, Player target) {
        target.setDefense(0);
        setAttack(getAttack() * 2);

        target.receiveHit(hitter, target);

        setAttack(getMaxAttack());
        target.setDefense(target.getMaxDefense());
    }

    //same effect with basic attack but with enhanced damage and follow up damage over time that last for 4 turns
    private void skill4(Player hitter, Player target) {
        target.setDefense(0);
        setAttack(getAttack() * 4);

        target.receiveHit(hitter, target);

        setAttack(getMaxAttack());
        target.setDefense(target.getMaxDefense());

        target.getDamageOverTime().add(venom);
        target.getDamageOverTimeValue().add(12);
    }

    //simple heal over time that last for 4 turns while also applying venom that last for 4 turns
    private void skill5(Player hitter, Player target) {
        getHealOverTime().add(heal * 2);
        getHealOverTimeValue().add(12);

        target.getDamageOverTime().add(venom);
        target.getDamageOverTimeValue().add(12);
    }

    //increase dodge while applying dissolve armor status and venom that last for 4 turns
    private void skill6(Player hitter, Player target) {
        setDodge(getDodge() + 80);
        receiveStatus(hitter, "Dissolve Armor", 100);

        target.getDamageOverTime().add(venom);
        target.getDamageOverTimeValue().add(12);
    }
}
