package com.pocketraiders.model;

import javafx.scene.image.Image;

import java.util.Random;

public class RaidBoss {
    private int id;
    private String name;
    private Rarity rarity;
    private String pod;
    private transient Image sprite;
    private transient String spritePath;
    private int hp;
    private int maxHp;
    private int attackMin;
    private int attackMax;
    private int lumenReward;
    private int xpReward;
    private int playerXpReward;
    private int copiesReward;
    private Raider copy;
    private int lumenPity;

    private final Random random = new Random();

    public RaidBoss(int id, String name, Rarity rarity, int hp, String pod, int attackMin, int attackMax, int lumenReward,
                        int xpReward, int playerXpReward, int copiesReward, String spritePath) {
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
        this.playerXpReward = playerXpReward;
        this.copiesReward = copiesReward;
        this.spritePath = spritePath;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
        createRaiderCopy(spritePath);
    }

    public RaidBoss(int id, String name, Rarity rarity, String pod, String spritePath, int hp, int maxHp, int attackMin,
                    int attackMax, int lumenReward, int xpReward, int playerXpReward, int copiesReward, String copy, int lumenPity) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.pod = pod;
        this.spritePath = spritePath;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
        this.hp = hp;
        this.maxHp = maxHp;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
        this.lumenReward = lumenReward;
        this.xpReward = xpReward;
        this.playerXpReward = playerXpReward;
        this.copiesReward = copiesReward;
        createRaiderCopy(copy);
        this.lumenPity = lumenPity;
    }

    public RaidBoss(int id, String name, Rarity rarity, int hp, String pod, int attackMin, int attackMax, int lumenReward,
                    int xpReward, int playerXpReward, int copiesReward) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.pod = pod;
        this.spritePath = null;
        this.sprite = null;
        this.hp = hp;
        this.maxHp = hp;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
        this.lumenReward = lumenReward;
        this.xpReward = xpReward;
        this.playerXpReward = playerXpReward;
        this.copiesReward = copiesReward;
        this.lumenPity = 300;
    }

    private void createRaiderCopy(String spritePath) {
        switch(rarity) {
            case COMMON -> this.copy = new CommonRaider(this.id, this.name, this.pod, spritePath);
            case RARE -> this.copy = new RareRaider(this.id, this.name, this.pod, spritePath);
            case LEGENDARY -> this.copy = new LegendaryRaider(this.id, this.name, this.pod, spritePath);
            case MYTHICAL -> this.copy = new MythicalRaider(this.id, this.name, this.pod, spritePath);
        }
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

    public String getSpritePath() {
        return this.spritePath;
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

    public int getXpReward() {
        return this.xpReward;
    }

    public int getPlayerXpReward() {
        return this.playerXpReward;
    }

    public int getCopiesReward() {
        return this.copiesReward;
    }

    public Raider getCopy() {
        return this.copy;
    }

    public void setImage(String spritePath) {
        createRaiderCopy(spritePath);
        this.spritePath = spritePath;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
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

    public void scale(int level) {
        this.maxHp = (int) Math.round(maxHp * (1 + 0.2 * (level - 1)));
        this.hp = (int) Math.round(hp * (1 + 0.2 * (level - 1)));
        this.attackMin = (int) Math.round(attackMin + 2.0 * (level - 1));
        this.attackMax = (int) Math.round(attackMax + 2.0 * (level - 1));
    }

}
