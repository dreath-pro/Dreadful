package com.example.dreadful.models;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dreadful.R;
import com.example.dreadful.adapters.ViewPrompt;

import java.util.ArrayList;
import java.util.Random;

public abstract class Player {
    private int id;
    private String name;
    private int image;
    private String imageDirection;
    private int size; // average = 150, huge = 170, titan = 210
    private int[] transformation;
    private int[] dimension;
    private int health, attack, defense, dodge;
    private int maxHealth, maxAttack, maxDefense, maxDodge;
    private String[] skillNames;
    private int[] maxSkillCooldowns, skillCooldowns;
    private ArrayList<Integer> healOverTime = new ArrayList<>(), healOverTimeValue = new ArrayList<>();
    private ArrayList<Integer> damageOverTime = new ArrayList<>(), damageOverTimeValue = new ArrayList<>();
    private ArrayList<String> status = new ArrayList<>();
    private ArrayList<Integer> statusValue = new ArrayList<>();
    private int stun;

    private ImageView yourImage;
    private Animation shakeAnimation;

    private Random random = new Random();
    private Prompt prompt;

    public Player() {

    }

    public Player(Context context, ImageView yourImage, String name, int image, String imageDirection, int size, int[] transformation,
                  int[] dimension, int health, int attack, int defense, int dodge, String[] skillNames, int[] maxSkillCooldowns, int[] skillCooldowns, Prompt prompt) {
        this.name = name;
        this.image = image;
        this.imageDirection = imageDirection;
        this.size = size;
        this.transformation = transformation;
        this.dimension = dimension;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.dodge = dodge;
        this.maxHealth = health;
        this.maxAttack = attack;
        this.maxDefense = defense;
        this.maxDodge = dodge;
        this.skillNames = skillNames;
        this.maxSkillCooldowns = maxSkillCooldowns;
        this.skillCooldowns = skillCooldowns;
        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        this.stun = 0;
        this.prompt = prompt;
    }

    public Player(int id, Context context, ImageView yourImage, String name, int image, String imageDirection, int size,
                  int[] transformation, int[] dimension, int health, int attack, int defense, int dodge,
                  String[] skillNames, int[] maxSkillCooldowns, int[] skillCooldowns, Prompt prompt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageDirection = imageDirection;
        this.size = size;
        this.transformation = transformation;
        this.dimension = dimension;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.dodge = dodge;
        this.maxHealth = health;
        this.maxAttack = attack;
        this.maxDefense = defense;
        this.maxDodge = dodge;
        this.skillNames = skillNames;
        this.maxSkillCooldowns = maxSkillCooldowns;
        this.skillCooldowns = skillCooldowns;
        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        this.stun = 0;
        this.prompt = prompt;
    }

    public void receiveStatus(Player target, String statusName, int statusValue) {
        boolean withStatus = false;
        int statusIndex = 0;
        for (int i = 0; i <= target.getStatus().size() - 1; i++) {
            if (target.getStatus().get(i).equals(statusName)) {
                withStatus = true;
                statusIndex = i;
            }
        }

        if (!withStatus) {
            target.getStatus().add(statusName);
            target.getStatusValue().add(statusValue);
        } else {
            target.getStatusValue().set(statusIndex, target.getStatusValue().get(statusIndex) + statusValue);
        }
    }

    public String hasStatus(Player target, String statusName, int statusValue) {
        String hasStatus = "";
        for (int i = 0; i <= target.getStatus().size() - 1; i++) {
            if (target.getStatus().get(i).equals(statusName) && target.getStatusValue().get(i) >= statusValue) {
                hasStatus = Integer.toString(i);
            }
        }

        return hasStatus;
    }

    protected void receiveHitLogic(Player hitter, Player target)
    {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return;

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());
        yourImage.startAnimation(shakeAnimation);
    }

    public abstract void receiveHit(Player hitter, Player target);

    protected void runTimeDamage()
    {
        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for(int i = 0; i <= damageOverTime.size() - 1; i++)
        {
            if (damageOverTimeValue.get(i) > 0) {
                setHealth(getHealth() - getDamageOverTime().get(i));
                damageOverTimeValue.set(i, damageOverTimeValue.get(i) - 1);

                tempDot.add(damageOverTime.get(i));
                tempDotValue.add(damageOverTimeValue.get(i));
            }
        }

        setDamageOverTime(tempDot);
        setDamageOverTimeValue(tempDotValue);
    }

    protected void runTimeHeal() {
        ArrayList<Integer> tempHot = new ArrayList<>();
        ArrayList<Integer> tempHotValue = new ArrayList<>();

        for (int i = 0; i <= healOverTime.size() - 1; i++) {
            if (healOverTimeValue.get(i) > 0) {
                setHealth(getHealth() + getHealOverTime().get(i));
                healOverTimeValue.set(i, healOverTimeValue.get(i) - 1);

                tempHot.add(healOverTime.get(i));
                tempHotValue.add(healOverTimeValue.get(i));
            }
        }

        setHealOverTime(tempHot);
        setHealOverTimeValue(tempHotValue);
    }

    public abstract void receiveTimeEffect(Player hitter, Player target);

    public abstract String useRandomAttack(Player hitter, Player target);

    public void basicAttack(Player hitter, Player target) {
        target.receiveHit(hitter, target);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageDirection() {
        return imageDirection;
    }

    public void setImageDirection(String imageDirection) {
        this.imageDirection = imageDirection;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[] getTransformation() {
        return transformation;
    }

    public void setTransformation(int[] transformation) {
        this.transformation = transformation;
    }

    public int[] getDimension() {
        return dimension;
    }

    public void setDimension(int[] dimension) {
        this.dimension = dimension;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health <= 0) {
            health = 0;
        }

        if (health > getMaxHealth()) {
            health = getMaxHealth();
        }
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        if (attack <= 0) {
            attack = 0;
        }
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        if (defense <= 0) {
            defense = 0;
        }
        this.defense = defense;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        if (dodge <= 0) {
            dodge = 0;
        }
        this.dodge = dodge;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public int getMaxDefense() {
        return maxDefense;
    }

    public int getMaxDodge() {
        return maxDodge;
    }

    public String[] getSkillNames() {
        return skillNames;
    }

    public int[] getSkillCooldowns() {
        return skillCooldowns;
    }

    public void setSkillCooldowns(int[] skillCooldowns) {
        this.skillCooldowns = skillCooldowns;
    }

    public int[] getMaxSkillCooldowns() {
        return maxSkillCooldowns;
    }

    public ArrayList<Integer> getHealOverTime() {
        return healOverTime;
    }

    public void setHealOverTime(ArrayList<Integer> healOverTime) {
        this.healOverTime = healOverTime;
    }

    public ArrayList<Integer> getHealOverTimeValue() {
        return healOverTimeValue;
    }

    public void setHealOverTimeValue(ArrayList<Integer> healOverTimeValue) {
        this.healOverTimeValue = healOverTimeValue;
    }

    public ArrayList<Integer> getDamageOverTime() {
        return damageOverTime;
    }

    public void setDamageOverTime(ArrayList<Integer> damageOverTime) {
        this.damageOverTime = damageOverTime;
    }

    public ArrayList<Integer> getDamageOverTimeValue() {
        return damageOverTimeValue;
    }

    public void setDamageOverTimeValue(ArrayList<Integer> damageOverTimeValue) {
        this.damageOverTimeValue = damageOverTimeValue;
    }

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }

    public ArrayList<Integer> getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(ArrayList<Integer> statusValue) {
        this.statusValue = statusValue;
    }

    public int getStun() {
        return stun;
    }

    public void setStun(int stun) {
        if (stun <= 0) {
            stun = 0;
        }
        this.stun = stun;
    }

    public void bypassSetHealth(int health) {
        this.health = health;
    }

    public void bypassSetMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
