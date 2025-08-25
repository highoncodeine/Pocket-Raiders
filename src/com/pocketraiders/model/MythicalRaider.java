package com.pocketraiders.model;

public class MythicalRaider extends Raider implements SpecialAbility{

    public MythicalRaider(int id, String name, String pod, String spritePath) {
        super(id, name, Rarity.MYTHICAL, pod, spritePath);
    }
}
