package com.pocketraiders.model;

public class CommonRaidBoss extends RaidBoss {
    public CommonRaidBoss(int id, String name, String pod, String spritePath) {
        super(id, name, Rarity.COMMON, 250, pod, 5, 10, 1000, 200, 300, 10, spritePath);
    }
}
