package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

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
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public HellKnight(Context context, ImageView yourImage, ProgressBar yourHealthBar, TestActivity testActivity) {
        super(context, yourImage, "Hell Knight", R.drawable.character_hell_knight, "right", 155,
                new int[]{R.drawable.character_hell_knight_2}, null,
                20000, 1000, 1000, 5,
                new String[]{"Burn Slash", "Knight's Breath", "Enhanced Armor", "Emberguard", "Dragon Form",
                        "Burn Claw", "Dragon's Breath", "Enhanced Scale", "Hellguard", "Human Form"},
                new int[]{0, 4, 9, 7, 0,
                        0, 4, 9, 7, 0},

                new int[]{0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0});

        this.prompt = new Prompt(testActivity);
        this.prompt = testActivity.getPrompt();
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
                events.add(getName() + " received a light blow, barely disrupting its eerie calm as it swayed slightly.");
                events.add(getName() + " received a light blow, but the impact barely registered.");
                events.add("A minor hit grazed the " + getName() + ", leaving it unfazed but slightly annoyed.");
                events.add("The attack barely scratched the surface, and the " + getName() + " let out a dismissive gurgle.");
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Gurgle-grrrgh!");
                dialogues.add("Grrrgh.");
                dialogues.add("Gurgle.");
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
            case 1:
                events.add(getName() + " was struck with moderate force, causing a ripple through its tentacles.");
                events.add("The strike landed with a solid thud, causing " + getName() + prompt.getApostrophe(getName()) + " tentacles to twitch in irritation.");
                events.add("With a moderate impact, the " + getName() + " staggered but maintained its menacing stance.");
                events.add("The attack pushed the " + getName() + " back a step, its gurgling growl growing more pronounced.\n");
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Grrraahhh!");
                dialogues.add("Grrrrr!");
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
            case 2:
                events.add(getName() + " took a strong hit, the force momentarily disrupting its control over the void.");
                events.add(getName() + " absorbed a strong blow, the force rippling through its body like a shockwave.");
                events.add("The powerful hit sent tremors through the void around it, and the Reaper emitted a low growl of defiance.");
                events.add("The strike hit hard, causing the " + getName() + " to falter briefly, its tentacles flailing in agitation.");
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Grrr-glkkk!");
                dialogues.add("Ggrrrhhhaaaaahhh!");
                dialogues.add("Grrraaaah!");
                prompt.selectRandomDialogue(this, dialogues, true);
                dialogues.clear();

                break;
            case 3:
                events.add(getName() + " suffered a critical blow, staggering back as time itself flickered around it.");
                events.add("A critical blow struck with devastating force, causing " + getName() + " to reel as time itself wavered.");
                events.add("The blow resonated through the void, forcing the " + getName() + " to momentarily falter, shadows swirling in agitation.");
                events.add("The devastating impact sent the " + getName() + " crashing back, its growl turning into a deep, anguished roar.");
                prompt.selectRandomEvent(events);
                events.clear();

                dialogues.add("Ggrrrhhhaaaaahhh!");
                dialogues.add("Ggrrrk-kh!");
                prompt.selectRandomDialogue(this, dialogues, true);
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
        if (antiDodge <= getDodge())
            return;

        if (ember > 0) {
            setAttack(hitter.getAttack());
            hitter.receiveHit(target, hitter);
            setAttack(getMaxAttack());

            hitter.setAttack(hitter.getAttack() * (int) (1 - (double) flameShield));
        }

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());
        yourImage.startAnimation(shakeAnimation);
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        runTimeHeal();

        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for (int i = 0; i <= getDamageOverTime().size() - 1; i++) {
            if (getDamageOverTimeValue().get(i) > 0) {
                if(ember > 0)
                {
                    setHealth(getHealth() - getDamageOverTime().get(i) * (int) (1 - (double) flameShield));
                }else
                {
                    setHealth(getHealth() - getDamageOverTime().get(i));
                }

                getDamageOverTimeValue().set(i, getDamageOverTimeValue().get(i) - 1);

                tempDot.add(getDamageOverTime().get(i));
                tempDotValue.add(getDamageOverTimeValue().get(i));
            }
        }

        setDamageOverTime(tempDot);
        setDamageOverTimeValue(tempDotValue);


        enhancedDefense--;
        if (enhancedDefense <= 0) {
            enhancedDefense = 0;

            if (!hasStatus(target, "Enhanced Armor", 1).isEmpty()) {
                setDefense(getMaxDefense());

                int index = Integer.parseInt(hasStatus(target, "Enhanced Armor", 1));
                getStatusValue().remove(index);
                getStatus().remove(index);
            }

            if(!hasStatus(target, "Enhanced Scale", 1).isEmpty())
            {
                setDefense(getMaxDefense());

                int index = Integer.parseInt(hasStatus(target, "Enhanced Scale", 1));
                getStatusValue().remove(index);
                getStatus().remove(index);
            }
        }

        ember--;
        if (ember <= 0) {
            ember = 0;

            if (!hasStatus(target, "Flame Shield", 1).isEmpty()) {
                flameShield = 0;

                int index = Integer.parseInt(hasStatus(target, "Flame Shield", 1));
                getStatusValue().remove(index);
                getStatus().remove(index);
            }
        }
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;
        int skillIndex;

        if(getSkillCooldowns()[4] > 0)
        {
            getSkillCooldowns()[4] = 0;
        }
        if(getSkillCooldowns()[5] > 0)
        {
            getSkillCooldowns()[5] = 0;
        }
        if(getSkillCooldowns()[9] > 0)
        {
            getSkillCooldowns()[9] = 0;
        }

        do {
            if(form == 0)
            {
                skillIndex = random.nextInt(5);
            }else
            {
                skillIndex = random.nextInt(5) + 5;
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
            case 3:
                skill3(hitter, target);
                break;
            case 4:
                skill4(hitter, target);
                break;

            //dragon form
            case 5:
                skill5(hitter, target);
                break;
            case 6:
                skill6(hitter, target);
                break;
            case 7:
                skill7(hitter, target);
                break;
            case 8:
                skill8(hitter, target);
                break;
            case 9:
                skill9(hitter, target);
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
