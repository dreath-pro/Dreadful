package com.example.dreadful.characters;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

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
    private Context context;
    private ArrayList<String> events = new ArrayList<>(), dialogues = new ArrayList<>();

    public GodOfDeath(Context context, ImageView yourImage, Prompt prompt) {
        super(context, yourImage, "God of Death", R.drawable.character_god_of_death, "right", 210,
                null, null,
                500000, 8500, 50, 50,
                new String[]{"Decay Touch", "Pray For The Living", "Time Before Death", "Afterlife"},
                new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0});
        this.context = context;
        this.prompt = prompt;
    }

    public void damageExpression(int level) {
        // 0 - low,
        // 1 - moderate,
        // 2 - strong,
        // 3 - critical
        switch (level) {
            case 0:
                events.add("The " + getName() + " remains completely still, showing no sign of pain or concern. The world around you flickers briefly as if reality itself is growing unstable.");
                events.add("Barely touched, the " + getName() + prompt.getApostrophe(getName()) + " skull tilts slightly in acknowledgment, though the screen twitches as if something just went wrong within the game.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("W̵e̕ak̴. Yo͟u̷ w͠o̧n'́t̕ eve̸n̡ ̨́s̷̛c͡r͠atc̨̨h̛ ͞m͢͞e.");
                dialogues.add("Ý̴ou c̸̛̀án͡not͞ ̵hàr͡m w͞hat is à͞͡lręady d̢̨è͝a̷d.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                break;
            case 1:
                events.add("The attack lands, and for a moment, the " + getName() + " seems to pause in its prayer. The game glitches briefly, a static hum filling your ears.");
                events.add("The blow lands, more forceful than before, but the " + getName() + " remains composed. The atmosphere distorts slightly, hinting at the untouchable nature of this entity.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("You th͞i͠n͢k thìs m̛ea̵n͏s s̛o͡me͝th͝in͠g͏? I̵ am b̀eyo͡nd́ i͠t al̷l̨.");
                dialogues.add("M̵̢or̸e, bu̴t ̀͢s͠tilļ fa͟r̡ ̴̷͘t͘oǫ ̶we̸͟ák͡.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                break;
            case 2:
                events.add("The impact sends a ripple through the air, and for a moment, the " + getName() + " flickers as if skipping frames, but it remains unmoved, unfazed.");
                events.add("Your strong strike reverberates through the battlefield, but the " + getName() + prompt.getApostrophe(getName()) + " prayer remains unchanged. It almost seems to acknowledge the futility of the effort with its unmoving form.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Ahh͟...͟ b͟u͢t ̷yo̡ur ͠e͠ǹde͠avor ̡is f̡u͞ti̛le.");
                dialogues.add("H̡it̵ ͝m͘e̛ a͡ll͡ ͡yo̷u wi͘ll...͠ ̴it ch̢ang̸es n͞ot̢hi͏ng͢.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                break;
            case 3:
                events.add("Your critical strike lands, and the world seems to unravel for a moment, the screen tearing and glitching violently. The " + getName() + " remains, mocking your effort through its very existence.");
                events.add("A devastating blow, but as the " + getName() + " takes it, the ground beneath begins to tremble as the screen distorts wildly, a reminder that you are facing an unstoppable force.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("T̶r̸y al̕l͠ ͢you͏ w̡ish... but y͟ou w̸i̸ll ͞never es̶cap̕e ḿe.");
                dialogues.add("Yo͘ư ̵d̕o̕ ńot f̡ight mè. Y̵ou fi͝g͡h͝t̡ t͞he ͞i̴ne̕vitab̸le.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                break;
        }
    }

    public void receiveHit(Player hitter, Player target) {
        String result = receiveHitLogic(hitter, target);
        switch (result) {
            case "DODGE":
                events.add("The " + getName() + " seems to blur and flicker out of existence as your strike passes through it. The very air around you shifts, as if the laws of reality are momentarily suspended.");
                events.add("As the attack is launched, the " + getName() + " vanishes, leaving nothing but static in its place. The game glitches momentarily before the skeletal figure reappears, still locked in its eerie prayer.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Díd͏ yo͠ư r̸e̸ally think yo͞u co̵ul̴d hi͢t̢ m̢é?");
                dialogues.add("Y̴ou m͝i̶ght͠ ̀h͡it t̀he ͏gr̀a͡v͡e befo͟r̡e yo̷u ́e͏v̀en͟ t̛óu͘ch́ m̢e.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                break;
            case "BLOCKED":
                events.add("The " + getName() + " raises one skeletal hand, and as your attack connects, a faint ripple of energy dissipates the blow. The game glitches briefly, a reminder that you face something beyond comprehension.");
                events.add("The blow lands squarely, yet it feels like striking stone. The " + getName() + " remains still, as if the very concept of harm is meaningless to it. The screen flickers, subtly reinforcing the futility of your actions.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Yo̡uŕ e̛ffo̷rt̷s a̴re... pa̕t͡heti͡c.");
                dialogues.add("St́r͡ik̡e̕ as ͏yo͝u ̸wil̛l̷...̢ ̶it ͠matters ńot.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
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

    public void receiveTimeEffect(Player hitter, Player target) {
        if (isClockOn) {
            timeBeforeDeath--;

            if (!hasStatus(hitter, "Time Before Death", 1).isEmpty()) {
                int index = Integer.parseInt(hasStatus(hitter, "Time Before Death", 1));
                hitter.getStatusValue().set(index, hitter.getStatusValue().get(index) - 1);

                if (timeBeforeDeath <= 0) {
                    setAttack(hitter.getMaxHealth() * 2);
                    hitter.receiveHit(target, hitter);
                    setAttack(getMaxAttack());

                    timeBeforeDeath = 0;

                    hitter.getStatus().remove(index);
                    hitter.getStatusValue().remove(index);
                }
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
                events.add("As the " + getName() + " reaches out, a cold, decaying energy clings to " + target.getName() + prompt.getApostrophe(target.getName()) + " form. Though the initial touch is light, the poison begins to seep in, slow but relentless. The game glitches subtly as if the code itself is decaying.");
                events.add("With a mere graze, the " + getName() + " transfers a lingering rot into " + target.getName() + ". The poison begins to eat away at health, spreading its effects over time. The screen flickers, mirroring the slow destruction " + target.getName() + " now faces.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("To̵uch̡ ̵o͟f de͘ath,̕ th͟ou sha̷l̀l not es̵cape̷ ẃha̛t́ ͝ís c͝e̡rt̷ain.");
                dialogues.add("E͡vęr̸y̵ b͞r͝e͏at̸h t͘ake̕n͝,̡ a ste̷p ̛c̵l͡oser t͠o me̢.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                basicAttack(hitter, target);
                break;
            case 1:
                events.add("The " + getName() + " clasps its skeletal hands in prayer, and with a sudden surge of energy, its body is fully restored. Meanwhile, " + target.getName() + prompt.getApostrophe(target.getName()) + " vitality is cut in half, drained as if the very essence of life is ripped from them. The screen glitches, flickering violently as the life force shifts.");
                events.add("The " + getName() + " offers a twisted prayer, and in response, its health returns to full. " + target.getName() + prompt.getApostrophe(target.getName()) + " own life essence is then siphoned, their health halved in an instant, as if fate itself has deemed them unworthy of survival. The game stutters, reflecting the disruption of balance.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Pr͝ay al͠l̸ y͠ou wi͘s̡h, th͘e͝ liv͏i͟nǵ sh̶al̕l̶ s͡uffe͝r.");
                dialogues.add("R̷es̶to͟rat͏i͠oǹ fo͢r̵ m̀e, e̵r̵ad͞i̷cat͢íon̢ f̨o̡r ̶thee.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                skill1(hitter, target);
                break;
            case 2:
                events.add("The " + getName() + " extends one bony finger, and " + target.getName() + prompt.getApostrophe(target.getName()) + " vision blurs. A countdown appears above their head, ticking down the moments until their demise. The air becomes thick with inevitability, as if death is already at the door, waiting. The screen begins to glitch, flickering like a dying flame.");
                events.add("With a subtle motion, the " + getName() + " marks " + target.getName() + ". A ghostly timer appears, counting the seconds until life will be forcefully taken. The game’s audio distorts, the sound of a ticking clock mingling with the pulsing hum of impending death.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("T̴h͏e̷ c͟lǫck ͡ti͝c̨k̢s. In e͝nd̵, th̵e҉r͢e ́sha͠ll b͘è no̶ esc͏ap̕e.");
                dialogues.add("C͡o̕unt̶ y̛o͡u̴r̛ ̨bre̵áth́s,̧ yǫur ͢t͏i͝m̨e i̵s͠ f̢lee̵t͢i͘ng.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

                skill2(hitter, target);
                break;
            case 3:
                events.add("The " + getName() + " gestures with a slow, deliberate motion. The very air around " + target.getName() + " seems to rot as the poison effect takes hold, creeping through their body. Health begins to steadily drain, the lasting decay a reminder that even death itself is not an escape. The screen glitches faintly, distorting like the gradual unraveling of life.");
                events.add("A soft, chilling prayer escapes from the " + getName() + ", and with it, a wave of decay spreads toward " + target.getName() + ". Poison seeps into their soul, causing their health to wither away slowly. The game flickers as the damage lingers, drawing out the torment beyond what seems mortal.");
                prompt.selectRandomEvent(events);
                prompt.getEventColor().add(ContextCompat.getColor(context, R.color.yellow_orange));
                events.clear();

                dialogues.add("Th͜e̢ ̡aft̀er͘li͞fe͠ i͘s̡ ͏m͢ore ̨p̕a͠i͟n ̸th̴an̢ th͘e en͝d.");
                dialogues.add("N̨ot̡ ̛ev̀en͢ ̷dea͠t͞h s̷h̶a̶ll͝ s̛pa̛r̢e t͞hee҉ fr̀o͏m̡ s͢ưf̕fe̢r͢in̢g͝.");
                if(prompt.selectRandomDialogue(this, dialogues, true))
                {
                    prompt.getDialogueColor().add(ContextCompat.getColor(context, R.color.violet));
                }
                dialogues.clear();

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

    //basic attack with damage over time that last for 4 turns
    @Override
    public void basicAttack(Player hitter, Player target) {
        target.setDefense(0);
        target.receiveHit(hitter, target);
        target.setDefense(getMaxDefense());

        target.getDamageOverTime().add(4500);
        target.getDamageOverTimeValue().add(12);
    }

    //resets the health to max and then reduce enemy health by 50% of it's max health
    private void skill1(Player hitter, Player target) {
        setHealth(getMaxHealth());

        double halfHealthDamage = ((double) 50 / 100) * target.getMaxHealth();
        setAttack((int) halfHealthDamage);
        target.setDefense(0);

        target.receiveHit(hitter, target);

        target.setDefense(getMaxDefense());
        setAttack(getMaxAttack());
    }

    //give the target time before death ranges from 6 to 10
    private void skill2(Player hitter, Player target) {
        isClockOn = true;
        timeBeforeDeath = random.nextInt(4) + 6;

        target.receiveStatus(target, "Time Before Death", timeBeforeDeath);
    }

    //same as basic attack but enhanced
    private void skill3(Player hitter, Player target) {
        target.setDefense(0);
        target.receiveHit(hitter, target);
        target.setDefense(getMaxDefense());

        target.getDamageOverTime().add(4500 * 2);
        target.getDamageOverTimeValue().add(12);
    }
}
