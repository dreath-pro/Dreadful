package com.example.dreadful.models;

import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class Character {
    private int id;
    private String name;
    private String image;
    private String imageDirection;
    private String size; // average, titan
    private String[] transformation;
    private int health, attack, defense, dodge;
    private int maxHealth, maxAttack, maxDefense, maxDodge;

    private Random random = new Random();

    public Character()
    {

    }

    public Character(String name, String image, String imageDirection, String size, String[] transformation, int health, int attack, int defense, int dodge)
    {
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
    }

    public Character(int id, String name, String image, String imageDirection, String size, String[] transformation, int health, int attack, int defense, int dodge)
    {
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
    }

    public void receiveHit(Character hitter, Character target)
    {
        int antiDodge = random.nextInt(100) + 1;
        if (antiDodge <= getDodge())
            return;

        hitter.setAttack(hitter.getAttack() - getDefense());
        setHealth(getHealth() - hitter.getAttack());
        hitter.setAttack(hitter.getMaxAttack());
    }

    public void basicAttack(Character hitter, Character target)
    {
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
        if(health <= 0)
        {
            health = 0;
        }
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        if(attack <= 0)
        {
            attack = 0;
        }
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        if(defense <= 0)
        {
            defense = 0;
        }
        this.defense = defense;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        if(dodge <= 0)
        {
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
}
