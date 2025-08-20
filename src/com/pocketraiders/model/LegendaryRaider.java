package com.pocketraiders.model;

public class LegendaryRaider extends Raider implements SpecialAbility{

    public LegendaryRaider(int id, String name) {
        super(id, name, Rarity.LEGENDARY);
    }
}
