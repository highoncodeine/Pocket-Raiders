package com.pocketraiders.model;

public class MythicalRaider extends Raider implements SpecialAbility{

    public MythicalRaider(int id, String name, String spritePath) {
        super(id, name, Rarity.MYTHICAL, spritePath);
    }
}
