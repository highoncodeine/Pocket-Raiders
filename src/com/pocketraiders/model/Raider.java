package com.pocketraiders.model;

import javafx.scene.image.Image;

import java.net.URL;

public abstract class Raider {
    private int id; // three digit id
    private String name;
    private Rarity rarity;
    private String pod;
    private Image sprite;
    private int copies;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int hp;
    private int attackMin;
    private int attackMax;

    public Raider(int id, String name, Rarity rarity, int hp, String pod, String spritePath) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.pod = pod;
        this.copies = 0;
        this.level = 7;
        this.xp = 0;
        this.xpToNextLevel = 50;
        this.hp = hp;
        this.attackMin = 1;
        this.attackMax = 3;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
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
            excessXp = xpToNextLevel - this.xp;
            levelUp();
        }
        this.xp += excessXp;
    }

    private void levelUp() {
        this.level++;
        this.xpToNextLevel += 10;
        this.xp = 0;

        this.hp += 5;

        switch(this.rarity) {
            case Rarity.COMMON -> {
                this.attackMin++;
                this.attackMax++;
            }
            case Rarity.RARE -> {
                this.attackMax += 2;
                this.attackMin += 2;
            }
            case LEGENDARY -> {
                this.attackMin += 3;
                this.attackMax += 3;
            }
            case MYTHICAL -> {
                this.attackMax += 5;
                this.attackMin += 5;
            }
        }
    }
}
