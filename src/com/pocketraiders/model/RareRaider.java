package com.pocketraiders.model;

public class RareRaider extends Raider{
    public RareRaider(int id, String name, String pod, String spritePath) {
        super(id, name, Rarity.RARE, 25, pod, spritePath);
    }
}
