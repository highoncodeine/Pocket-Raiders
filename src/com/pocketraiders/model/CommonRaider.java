package com.pocketraiders.model;

public class CommonRaider extends Raider{

    public CommonRaider(int id, String name, String pod, String spritePath) {
        super(id, name, Rarity.COMMON, pod, spritePath);
    }
}
