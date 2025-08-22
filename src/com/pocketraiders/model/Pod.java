package com.pocketraiders.model;

import java.util.ArrayList;
import java.util.Random;

public class Pod {
    protected String name;
    protected String description;
    protected int lumenCost;
    protected ArrayList<Raider> allRaiders;
    protected ArrayList<CommonRaider> commonRaiders;
    protected ArrayList<RareRaider> rareRaiders;
    protected ArrayList<LegendaryRaider> legendaryRaiders;
    protected ArrayList<MythicalRaider> mythicalRaiders;

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

    public Pod(String name, int lumenCost) {
        this.name = name;
        this.lumenCost = lumenCost;
        this.allRaiders = new ArrayList<>();
        this.commonRaiders = new ArrayList<>();
        this.rareRaiders = new ArrayList<>();
        this.legendaryRaiders = new ArrayList<>();
        this.mythicalRaiders = new ArrayList<>();
    }

    public int getLumenCost() {
        return this.lumenCost;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<Raider> getAllRaiders() {
        return this.allRaiders;
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

    protected void sortRaiders(ArrayList<Raider> availableRaiders) {
        for(Raider availableRaider: availableRaiders) {
            switch(availableRaider.getRarity()) {
                case Rarity.COMMON:
                    commonRaiders.add((CommonRaider) availableRaider);
                    allRaiders.add(availableRaider);
                    break;
                case Rarity.RARE:
                    rareRaiders.add((RareRaider) availableRaider);
                    allRaiders.add(availableRaider);
                    break;
                case Rarity.LEGENDARY:
                    legendaryRaiders.add((LegendaryRaider) availableRaider);
                    allRaiders.add(availableRaider);
                    break;
                case Rarity.MYTHICAL:
                    mythicalRaiders.add((MythicalRaider) availableRaider);
                    allRaiders.add(availableRaider);
                    break;
            }
        }
    }
}
