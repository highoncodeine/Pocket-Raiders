package com.pocketraiders.model;

import javafx.scene.image.Image;

public class RaidBoss {
    private int id;
    private String name;
    private Rarity rarity;
    private Image sprite;
    private int hp;
    private int attackMin;
    private int attackMax;
    private int lumenReward;
    private int xpReward;
    private int lumenPity;

    public RaidBoss(int id, String name, Rarity rarity, int hp, int attackMin, int attackMax, int lumenReward, int xpReward, String spritePath) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.hp = hp;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
        this.lumenReward = lumenReward;
        this.lumenPity = 300;
        this.xpReward = xpReward;
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

    public Image getSprite() {
        return this.sprite;
    }

    public int getHp() {
        return this.hp;
    }

    public int getAttackMin() {
        return this.attackMin;
    }

    public int getAttackMax() {
        return this.attackMax;
    }

    public int getLumenReward() {
        return lumenReward;
    }

    public int getLumenPity() {
        return this.lumenPity;
    }

}
