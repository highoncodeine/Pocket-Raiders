package com.pocketraiders.model;

import javafx.scene.image.Image;

import java.net.URL;

public abstract class Raider {
    private int id; // three digit id
    private String name;
    private Rarity rarity;
    private Image sprite;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int attackMin;
    private int attackMax;

    public Raider(int id, String name, Rarity rarity, String spritePath) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 100;
        this.attackMin = 1;
        this.attackMax = 3;
        this.sprite = new Image(getClass().getResourceAsStream(spritePath));
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
}
