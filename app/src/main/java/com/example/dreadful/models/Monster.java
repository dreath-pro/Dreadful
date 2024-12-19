package com.example.dreadful.models;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dreadful.R;

import java.util.ArrayList;
import java.util.Random;

public abstract class Monster {
    private int databaseId;
    private String uniqueId;
    private String name;
    private int image;
    private String imageDirection;
    private int size; // average = 150, huge = 170, titan = 210
    private int[] transformation;
    private int[] dimension;
    private int health, attack, defense, dodge;
    private int maxHealth, maxAttack, maxDefense, maxDodge;
    private ArrayList<Integer> healOverTime = new ArrayList<>(), healOverTimeValue = new ArrayList<>();
    private ArrayList<Integer> damageOverTime = new ArrayList<>(), damageOverTimeValue = new ArrayList<>();
    private MutableLiveData<ArrayList<String>> statusList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Integer>> statusValueList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> skillNames = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Integer>> maxSkillCooldowns = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Integer>> skillCooldowns = new MutableLiveData<>();
    private int stun;

    private ImageView yourImage;
    private Animation shakeAnimation;

    private Random random = new Random();

    public Monster() {

    }

    public Monster(String uniqueId, Context context, ImageView yourImage, String name, int image, String imageDirection, int size, int[] transformation,
                   int[] dimension, int health, int attack, int defense, int dodge) {
        this.uniqueId = uniqueId;
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
        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        this.stun = 0;
    }

    public Monster(int databaseId, String uniqueId, Context context, ImageView yourImage, String name, int image, String imageDirection, int size,
                   int[] transformation, int[] dimension, int health, int attack, int defense, int dodge, Prompt prompt) {
        this.databaseId = databaseId;
        this.uniqueId = uniqueId;
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
        this.yourImage = yourImage;
        this.shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
        this.stun = 0;
    }

    public abstract void damageExpression(int level);

    public abstract void defeatReward();

    public void receiveStatus(Monster target, String statusName, int statusValue) {
        boolean withStatus = false;
        int statusIndex = 0;

        ArrayList<String> newStatusList = target.statusList.getValue();
        if (newStatusList == null) {
            newStatusList = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValueList = target.statusValueList.getValue();
        if (newStatusValueList == null) {
            newStatusValueList = new ArrayList<>();
        }

        for (int i = 0; i < newStatusList.size(); i++) { // Use newStatusList.size()
            if (newStatusList.get(i).equals(statusName)) {
                withStatus = true;
                statusIndex = i;
            }
        }

        /**
         * if the status provided is not found, it will create and add new one
         * but if its already there, it will just update the value
         */
        if (!withStatus) {
            newStatusList.add(statusName);
            newStatusValueList.add(statusValue);

            target.updateStatusList(newStatusList);
            target.updateStatusValueList(newStatusValueList);
        } else {
            newStatusValueList.set(statusIndex, newStatusValueList.get(statusIndex) + statusValue);
            target.updateStatusValueList(newStatusValueList);
        }
    }

    public String hasStatus(Monster target, String statusName, int statusValue) {
        String hasStatus = "";

        ArrayList<String> newStatusList = target.statusList.getValue();
        if (newStatusList == null) {
            newStatusList = new ArrayList<>();
        }

        ArrayList<Integer> newStatusValueList = target.statusValueList.getValue();
        if (newStatusValueList == null) {
            newStatusValueList = new ArrayList<>();
        }

        for (int i = 0; i <= newStatusList.size() - 1; i++) {
            if (newStatusList.get(i).equals(statusName) && newStatusValueList.get(i) >= statusValue) {
                hasStatus = Integer.toString(i);
            }
        }

        return hasStatus;
    }

    protected String receiveHitLogic(Monster enemy, Monster you) {
        String result = ""; //DODGE, BLOCKED, 47% <-- damage percentage
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return "DODGE";

        if (enemy.getAttack() <= getDefense()) {
            result = "BLOCKED";
        } else {
            enemy.setAttack(enemy.getAttack() - getDefense());
            result = Integer.toString(getDamagePercentage(enemy.getAttack(), getHealth()));
            setHealth(getHealth() - enemy.getAttack());
            enemy.setAttack(enemy.getMaxAttack());
            yourImage.startAnimation(shakeAnimation);
        }

        return result;
    }

    private int getDamagePercentage(int damageDealt, int healthLost) {
        double damagePercentage = (double) damageDealt / healthLost * 100;
        return (int) damagePercentage;
    }

    public abstract void receiveHit(Monster enemy, Monster you);

    protected void runTimeDamage() {
        ArrayList<Integer> tempDot = new ArrayList<>();
        ArrayList<Integer> tempDotValue = new ArrayList<>();

        for (int i = 0; i <= damageOverTime.size() - 1; i++) {
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

    public abstract void receiveTimeEffect(Monster enemy, Monster you);

    protected void reduceCooldown(int skillIndex) {
        ArrayList<Integer> newSkillCooldowns = getSkillCooldowns().getValue();
        if (newSkillCooldowns == null) {
            newSkillCooldowns = new ArrayList<>();
        }

        ArrayList<Integer> newMaxSkillCooldowns = getMaxSkillCooldowns().getValue();
        if (newMaxSkillCooldowns == null) {
            newMaxSkillCooldowns = new ArrayList<>();
        }

        for (int i = 0; i <= newSkillCooldowns.size() - 1; i++) {
            if (newSkillCooldowns.get(i) > 0) {
                newSkillCooldowns.set(i, newSkillCooldowns.get(i) - 1);
                if (newSkillCooldowns.get(i) <= 0) {
                    newSkillCooldowns.set(i, 0);
                }

                updateSkillCooldowns(newSkillCooldowns);
            }
        }

        newSkillCooldowns = getSkillCooldowns().getValue();
        newMaxSkillCooldowns = getMaxSkillCooldowns().getValue();

        newSkillCooldowns.set(skillIndex, newMaxSkillCooldowns.get(skillIndex));
        updateSkillCooldowns(newSkillCooldowns);
    }

    public abstract String useRandomAttack(Monster you, Monster enemy);

    public void basicAttack(Monster you, Monster enemy) {
        enemy.receiveHit(you, enemy);
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public LiveData<ArrayList<String>> getSkillNames() {
        return skillNames;
    }

    public void updateSkillNames(ArrayList<String> skillNames) {
        this.skillNames.postValue(skillNames);
    }

    public LiveData<ArrayList<Integer>> getMaxSkillCooldowns() {
        return maxSkillCooldowns;
    }

    public void updateMaxSkillCooldowns(ArrayList<Integer> maxSkillCooldowns) {
        this.maxSkillCooldowns.postValue(maxSkillCooldowns);
    }

    public LiveData<ArrayList<Integer>> getSkillCooldowns() {
        return skillCooldowns;
    }

    public void updateSkillCooldowns(ArrayList<Integer> skillCooldowns) {
        this.skillCooldowns.postValue(skillCooldowns);
    }

    public LiveData<ArrayList<String>> getStatusList() {
        return statusList;
    }

    public void updateStatusList(ArrayList<String> statusList) {
        this.statusList.postValue(statusList);
    }

    public LiveData<ArrayList<Integer>> getStatusValueList() {
        return statusValueList;
    }

    public void updateStatusValueList(ArrayList<Integer> statusValueList) {
        this.statusValueList.postValue(statusValueList);
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
