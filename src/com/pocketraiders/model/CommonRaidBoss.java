package com.pocketraiders.model;

public class CommonRaidBoss extends RaidBoss {
    public CommonRaidBoss(int id, String name, String spritePath) {
        super(id, name, Rarity.COMMON, 250, 5, 10, 1000, 200, spritePath);
    }
}
