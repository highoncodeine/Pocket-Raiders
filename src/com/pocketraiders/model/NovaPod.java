package com.pocketraiders.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NovaPod extends Pod {

    public NovaPod() {
        super("Nova", 120);
        this.description = "Forged in the heart of a dying star, the Nova Pod drifts endlessly through the void, " +
                "carrying fragments of forgotten constellations. Ancient starmancers believed these pods were vessels " +
                "of fate, each one sealed with a shard of celestial power destined for a chosen bearer.";

        sortRaiders(initializeNovaRaiders());
    }

    private ArrayList<Raider> initializeNovaRaiders() {
        ArrayList<Raider> novaRaiders = new ArrayList<>();
        novaRaiders.add(new CommonRaider(1, "Staragazer", "Nova", "/raider-images/staragazer.png"));
        novaRaiders.add(new CommonRaider(2, "Nebula", "Nova", "/raider-images/nebula.png"));
        novaRaiders.add(new CommonRaider(3, "Hubble", "Nova", "/raider-images/hubble.png"));
        novaRaiders.add(new RareRaider(4, "Pluto", "Nova", "/raider-images/pluto.png"));
        novaRaiders.add(new RareRaider(5, "Orion", "Nova", "/raider-images/orion.png"));
        novaRaiders.add(new RareRaider(6, "Radiar T.", "Nova", "/raider-images/radiar t..png"));
        novaRaiders.add(new LegendaryRaider(7, "Voidrunner", "Nova", "/raider-images/voidrunner.png"));
        novaRaiders.add(new LegendaryRaider(8, "Aetherion", "Nova", "/raider-images/staragazer.png"));
        novaRaiders.add(new MythicalRaider(9, "Supernova", "Nova", "/raider-images/staragazer.png"));
        novaRaiders.add(new MythicalRaider(10, "Aetherion Prime", "Nova", "/raider-images/staragazer.png"));

        return novaRaiders;
    }
}
