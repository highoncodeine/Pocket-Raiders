package com.pocketraiders.model;

import java.util.ArrayList;
import java.util.Random;

public class Pod {
    private String name;
    private int lumenCost;
    private ArrayList<CommonRaider> commonRaiders;
    private ArrayList<RareRaider> rareRaiders;
    private ArrayList<LegendaryRaider> legendaryRaiders;
    private ArrayList<MythicalRaider> mythicalRaiders;

    private static Random random = new Random();

    public Pod(String name, ArrayList<Raider> availableRaiders, int lumenCost) {
        this.name = name;
        this.lumenCost = lumenCost;

        this.commonRaiders = new ArrayList<>();
        this.rareRaiders = new ArrayList<>();
        this.legendaryRaiders = new ArrayList<>();
        this.mythicalRaiders = new ArrayList<>();

        sortRaiders(availableRaiders);
    }

    public int getLumenCost() {
        return this.lumenCost;
    }

    public Raider selectRandomRaider() {
        int selector = random.nextInt(1, 101);

        if(selector >= 1 && selector <= 78) {
            // common pull
            return commonRaiders.get(random.nextInt(commonRaiders.size()));
        } else if(selector >= 79 && selector <= 93) {
            // rare pull
            return rareRaiders.get(random.nextInt(rareRaiders.size()));
        } else if (selector >= 94 && selector <= 98) {
            // legendary pull
            return legendaryRaiders.get(random.nextInt(legendaryRaiders.size()));
        } else if (selector == 99 || selector == 100) {
            // mythical pull
            return mythicalRaiders.get(random.nextInt(mythicalRaiders.size()));
        } else {
            return null;
        }
    }

    private void sortRaiders(ArrayList<Raider> availableRaiders) {
        for(Raider availableRaider: availableRaiders) {
            switch(availableRaider.getRarity()) {
                case Rarity.COMMON:
                    commonRaiders.add((CommonRaider) availableRaider);
                    break;
                case Rarity.RARE:
                    rareRaiders.add((RareRaider) availableRaider);
                    break;
                case Rarity.LEGENDARY:
                    legendaryRaiders.add((LegendaryRaider) availableRaider);
                    break;
                case Rarity.MYTHICAL:
                    mythicalRaiders.add((MythicalRaider) availableRaider);
                    break;
            }
        }
    }
}
