package com.pocketraiders.model;

public class LegendaryRaidBoss extends RaidBoss{
    public LegendaryRaidBoss(int id, String name, String pod, String spritePath) {
        super(id, name, Rarity.LEGENDARY, 750, pod,  15, 20, 3000, 250, 250, 1, spritePath);
    }

    public LegendaryRaidBoss(int id, String name, String pod) {
        super(id, name, Rarity.LEGENDARY, 750, pod,  15, 20, 3000, 250, 250, 1);
    }
}
