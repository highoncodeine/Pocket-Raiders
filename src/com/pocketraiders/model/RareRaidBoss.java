package com.pocketraiders.model;

public class RareRaidBoss extends RaidBoss {
    public RareRaidBoss(int id, String name, String pod, String spritePath) {
        super(id, name, Rarity.RARE, 500, pod, 10, 15, 2000, 150, 200, 5, spritePath);
    }

    public RareRaidBoss(int id, String name, String pod) {
        super(id, name, Rarity.RARE, 500, pod, 10, 15, 2000, 150, 200, 5);
    }
}
