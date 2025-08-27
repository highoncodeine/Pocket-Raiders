package com.pocketraiders.model;

public class MythicalRaidBoss extends RaidBoss{
    public MythicalRaidBoss(int id, String name, String spritePath) {
        super(id, name, Rarity.MYTHICAL, 1000, 20, 25, 5000, 600, spritePath);
    }
}
