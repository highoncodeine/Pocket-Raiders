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
    private float statsIncreaseRate;
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
        this.statsIncreaseRate = 1;
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 20;
        this.hp = hp;
        this.maxHp = hp;
        this.attackMin = 1;
        this.attackMax = 5;
        this.spritePath = spritePath;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
        computeStatsIncreaseRate();
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
        this.statsIncreaseRate = 1;
        this.level = level;
        this.xp = xp;
        this.xpToNextLevel = xpToNextLevel;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
        computeStatsIncreaseRate();
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
        computeStatsIncreaseRate();
    }

    public void incrementXp(int xp) {
        this.xp += xp;
        while(this.xp >= xpToNextLevel) {
            int excessXp = 0;
            excessXp = Math.abs(xpToNextLevel - this.xp);
            levelUp();
            this.xp += excessXp;
        }
    }

    private void levelUp() {
        this.level++;
        this.xpToNextLevel += 10;
        this.xp = 0;


        switch(this.rarity) {
            case Rarity.COMMON -> {
                this.hp += 5;
                this.maxHp += 5;
                this.attackMin = (int) (Math.ceil((this.attackMin + 1) * this.statsIncreaseRate));
                this.attackMax = attackMin + 5;
            }
            case Rarity.RARE -> {
                this.hp += 6;
                this.maxHp += 6;
                this.attackMin = (int) (Math.ceil((this.attackMin + 2) * this.statsIncreaseRate));
                this.attackMax = attackMin + 5;
            }
            case LEGENDARY -> {
                this.hp += 7;
                this.maxHp += 7;
                this.attackMin = (int) (Math.ceil((this.attackMin + 3) * this.statsIncreaseRate));
                this.attackMax = attackMin + 5;
            }
            case MYTHICAL -> {
                this.hp += 10;
                this.maxHp += 10;
                this.attackMin = (int) (Math.ceil((this.attackMin + 4) * this.statsIncreaseRate));
                this.attackMax = attackMin + 5;
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

    private void computeStatsIncreaseRate() {
        switch(this.rarity) {
            case COMMON -> this.statsIncreaseRate = 1f + ((float)(Math.floor((double) copies / 25) * 0.07));
            case RARE -> this.statsIncreaseRate = 1f + ((float)(Math.floor((double) copies/ 15) * 0.07));
            case LEGENDARY -> this.statsIncreaseRate = 1f + ((float)(Math.floor((double) copies / 5) * 0.07));
            case MYTHICAL -> this.statsIncreaseRate = 1f + ((float)(Math.floor((double) copies / 2) * 0.07));
        }
    }

    public int generateAttack() {
        return random.nextInt(this.attackMin, this.attackMax + 1);
    }

    public void resetHp() {
        this.hp = this.maxHp;
    }
}
