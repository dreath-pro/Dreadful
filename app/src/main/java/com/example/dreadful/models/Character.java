package com.example.dreadful.models;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class Character {
    private int id;
    private String name;
    private String image;
    private String imageDirection;
    private String size; // average, titan
    private String[] transformation;
    private int health, attack, defense, dodge;
    private int maxHealth, maxAttack, maxDefense, maxDodge;
    private String[] skillNames;
    private int[] maxSkillCooldowns, skillCooldowns;
    private ArrayList<Integer> healOverTime = new ArrayList<>(), healOverTimeValue = new ArrayList<>();
    private ArrayList<Integer> damageOverTime = new ArrayList<>(), damageOverTimeValue = new ArrayList<>();
    private ArrayList<String> buffs = new ArrayList<>(), debuffs = new ArrayList<>();
    private ArrayList<Integer> buffsValue = new ArrayList<>(), debuffsValue = new ArrayList<>();

    private Random random = new Random();

    public Character() {

    }

    public Character(String name, String image, String imageDirection, String size, String[] transformation,
                     int health, int attack, int defense, int dodge, String[] skillNames, int[] maxSkillCooldowns, int[] skillCooldowns) {
        this.name = name;
        this.image = image;
        this.imageDirection = imageDirection;
        this.size = size;
        this.transformation = transformation;
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
    }

    public Character(int id, String name, String image, String imageDirection, String size,
                     String[] transformation, int health, int attack, int defense, int dodge,
                     String[] skillNames, int[] maxSkillCooldowns, int[] skillCooldowns) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageDirection = imageDirection;
        this.size = size;
        this.transformation = transformation;
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
    }

    public void receiveHit(Character hitter, Character target) {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return;

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());
    }

    public void receiveTimeHp(Character hitter, Character target) {
        ArrayList<Integer> tempHot = new ArrayList<>();
        ArrayList<Integer> tempHotValue = new ArrayList<>();

        for (int i = 0; i <= getHealOverTime().size() - 1; i++) {
            if (healOverTimeValue.get(i) > 0) {
                setHealth(getHealth() + getHealOverTime().get(i));
                healOverTimeValue.set(i, healOverTimeValue.get(i) - 1);

                tempHot.add(healOverTime.get(i));
                tempHotValue.add(healOverTimeValue.get(i));
            }
        }

        setHealOverTime(tempHot);
        setHealOverTimeValue(tempHotValue);


        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for (int i = 0; i <= getDamageOverTime().size() - 1; i++) {
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

    public abstract void useRandomAttack(Character hitter, Character target);

    public void basicAttack(Character hitter, Character target) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageDirection() {
        return imageDirection;
    }

    public void setImageDirection(String imageDirection) {
        this.imageDirection = imageDirection;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String[] getTransformation() {
        return transformation;
    }

    public void setTransformation(String[] transformation) {
        this.transformation = transformation;
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

    public ArrayList<String> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<String> buffs) {
        this.buffs = buffs;
    }

    public ArrayList<Integer> getBuffsValue() {
        return buffsValue;
    }

    public void setBuffsValue(ArrayList<Integer> buffsValue) {
        this.buffsValue = buffsValue;
    }

    public ArrayList<String> getDebuffs() {
        return debuffs;
    }

    public void setDebuffs(ArrayList<String> debuffs) {
        this.debuffs = debuffs;
    }

    public ArrayList<Integer> getDebuffsValue() {
        return debuffsValue;
    }

    public void setDebuffsValue(ArrayList<Integer> debuffsValue) {
        this.debuffsValue = debuffsValue;
    }
}
