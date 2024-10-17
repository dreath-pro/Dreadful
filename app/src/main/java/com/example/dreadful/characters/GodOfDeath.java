package com.example.dreadful.characters;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dreadful.R;
import com.example.dreadful.activities.TestActivity;
import com.example.dreadful.models.Player;
import com.example.dreadful.models.Prompt;

import java.util.ArrayList;
import java.util.Random;

public class GodOfDeath extends Player {
    private Random random = new Random();
    private Prompt prompt;
    private int timeBeforeDeath = 0;
    private boolean isClockOn = false;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public GodOfDeath(Context context, ImageView yourImage, TestActivity testActivity) {
        super(context, yourImage, "God of Death", R.drawable.character_god_of_death, "right", 210,
                null, null,
                500000, 500000, 500000, 50,
                new String[]{"Decay Touch", "Pray For The Living", "Time Before Death", "Afterlife"},
                new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0});

        this.prompt = new Prompt(testActivity);
        this.prompt = testActivity.getPrompt();
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

    public void receiveHit(Player hitter, Player target) {
        receiveHitLogic(hitter, target);
    }

    public void receiveTimeEffect(Player hitter, Player target) {
        if (isClockOn) {
            timeBeforeDeath--;

            if (!hasStatus(hitter, "Time Before Death", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(hitter, "Time Before Death", 1));
                hitter.getStatusValue().set(index, hitter.getStatusValue().get(index) - 1);
            }

            if (timeBeforeDeath <= 0) {
                hitter.setHealth(0);
                timeBeforeDeath = 0;

                int index = Integer.parseInt(hasStatus(hitter, "Time Before Death", 0));
                hitter.getStatus().remove(index);
                hitter.getStatusValue().remove(index);
            }
        }
    }

    public String useRandomAttack(Player hitter, Player target) {
        String skillName;
        int skillIndex = random.nextInt(getSkillNames().length);

        while (getSkillCooldowns()[skillIndex] > 0) {
            skillIndex = random.nextInt(getSkillNames().length);
        }

        if (getSkillCooldowns()[1] > 0) {
            getSkillCooldowns()[1] = 0;
        }
        if (getSkillCooldowns()[2] > 0) {
            getSkillCooldowns()[2] = 0;
        }
        if (getSkillCooldowns()[3] > 0) {
            getSkillCooldowns()[3] = 0;
        }

        skillName = getSkillNames()[skillIndex];
        switch (skillIndex) {
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

    //temporary simple attack
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(getMaxDodge());
    }

    //temporary simple attack
    private void skill1(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(getMaxDodge());
    }

    //give the target time before death ranges from 6 to 10
    private void skill2(Player hitter, Player target) {
        isClockOn = true;
        timeBeforeDeath = random.nextInt(4) + 6;

        target.receiveStatus(target, "Time Before Death", timeBeforeDeath);
    }

    //temporary simple attack
    private void skill3(Player hitter, Player target) {
        target.setDodge(0);
        target.receiveHit(hitter, target);
        target.setDodge(getMaxDodge());
    }
}
