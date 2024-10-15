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
    private Animation shakeAnimation;
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
    private TestActivity testActivity;

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

        this.testActivity = testActivity;
        this.prompt = new Prompt();
        this.yourImage = yourImage;
        this.yourHealthBar = yourHealthBar;
        this.yourName = yourName;
        this.resizeImage = new ResizeImage(context);
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
    }

    /**
     * if he dies theres a 50/50 chance he will transform into a strong mutant, or will just die easily
     */
    public void receiveHit(Player hitter, Player target) {
        receiveHitLogic(hitter, target);

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
            if (dissolve <= 0)
            {
                if(!hasStatus(target, "Dissolve Armor", 100).isEmpty())
                {
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
