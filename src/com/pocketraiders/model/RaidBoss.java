package com.pocketraiders.model;

import javafx.scene.image.Image;

import java.util.Random;

public class RaidBoss {
    private int id;
    private String name;
    private Rarity rarity;
    private String pod;
    private Image sprite;
    private int hp;
    private int maxHp;
    private int attackMin;
    private int attackMax;
    private int lumenReward;
    private int xpReward;
    private int lumenPity;

    private final Random random = new Random();

    public RaidBoss(int id, String name, Rarity rarity, int hp, String pod, int attackMin, int attackMax, int lumenReward, int xpReward, String spritePath) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.pod = pod;
        this.hp = hp;
        this.maxHp = hp;
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

    public String getPod() {
        return this.pod;
    }

    public Image getSprite() {
        return this.sprite;
    }

    public int getHp() {
        return this.hp;
    }

    public int getMaxHp() {
        return this.maxHp;
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

}
