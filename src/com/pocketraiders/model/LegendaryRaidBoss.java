package com.pocketraiders.model;

public class LegendaryRaidBoss extends RaidBoss{
    public LegendaryRaidBoss(int id, String name, String spritePath) {
        super(id, name, Rarity.LEGENDARY, 750, 15, 20, 3000, 500, spritePath);
    }
}
