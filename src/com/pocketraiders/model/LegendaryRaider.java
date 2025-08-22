package com.pocketraiders.model;

public class LegendaryRaider extends Raider implements SpecialAbility{

    public LegendaryRaider(int id, String name, String spritePath) {
        super(id, name, Rarity.LEGENDARY, spritePath);
    }
}
