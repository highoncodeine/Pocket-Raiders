package com.pocketraiders.model;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Random;

public class Raider {
    private int id; // three digit id
    private String name;
    private Rarity rarity;
    private String pod;
    private String spritePath;
    private Image sprite;
    private int copies;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int hp;
    private int maxHp;
    private int attackMin;
    private int attackMax;

    private final Random random = new Random();

    public Raider(int id, String name, Rarity rarity, int hp, String pod, String spritePath) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.pod = pod;
        this.copies = 0;
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 20;
        this.hp = hp;
        this.maxHp = hp;
        this.attackMin = 1;
        this.attackMax = 5;
        this.spritePath = spritePath;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
    }

    public Raider(int id, String name, Rarity rarity, String pod, String spritePath, int copies, int level,
                  int xp, int xpToNextLevel, int hp, int maxHp, int attackMin, int attackMax) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.pod = pod;
        this.spritePath = spritePath;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
        this.copies = copies;
        this.level = level;
        this.xp = xp;
        this.xpToNextLevel = xpToNextLevel;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public String getPod() {
        return this.pod;
    }

    public int getHp() {
        return this.hp;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public String getSpritePath() {
        return this.spritePath;
    }

    public Image getSprite() {
        return this.sprite;
    }

    public int getCopies() { return this.copies; }

    public int getLevel() { return this.level; }

    public int getXp() { return this.xp; }

    public int getXpToNextLevel() { return this.xpToNextLevel; }

    public int getAttackMin() { return this.attackMin; }

    public int getAttackMax() { return this.attackMax; }

    public void incrementCopy() {
        this.copies++;
    }

    public void incrementXp(int xp) {
        int excessXp = 0;
        this.xp += xp;
        if(this.xp >= xpToNextLevel) {
            excessXp = Math.abs(xpToNextLevel - this.xp);
            levelUp();
        }
        this.xp += excessXp;
    }

    private void levelUp() {
        this.level++;
        this.xpToNextLevel += 10;
        this.xp = 0;

        this.hp += 5;
        this.maxHp += 5;

        switch(this.rarity) {
            case Rarity.COMMON -> {
                this.attackMin += 1;
                this.attackMax += 1;
            }
            case Rarity.RARE -> {
                this.attackMin += 2;
                this.attackMax += 2;
            }
            case LEGENDARY -> {
                this.attackMin += 3;
                this.attackMax += 3;
            }
            case MYTHICAL -> {
                this.attackMin += 4;
                this.attackMax += 4;
            }
        }
    }

    public void takeDamage(int damage) {
        if(this.hp - damage <= 0) {
            this.hp = 0;
        } else {
            this.hp -= damage;
        }
    }

    public int generateAttack() {
        return random.nextInt(this.attackMin, this.attackMax + 1);
    }

    public void resetHp() {
        this.hp = this.maxHp;
    }
}
