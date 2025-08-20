package com.pocketraiders.model;

public abstract class Raider {
    private int id; // three digit id
    private String name;
    private Rarity rarity;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int attackMin;
    private int attackMax;

    public Raider(int id, String name, Rarity rarity) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 100;
        this.attackMin = 1;
        this.attackMax = 3;
    }

    public String getName() {
        return this.name;
    }

    public Rarity getRarity() {
        return this.rarity;
    }
}
