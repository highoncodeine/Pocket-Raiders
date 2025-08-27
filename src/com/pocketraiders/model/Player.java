package com.pocketraiders.model;

import java.util.ArrayList;

public class Player {
    private int id;
    private String username;
    private String password;
    private ArrayList<Raider> ownedRaiders;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int lumens; //in-game currency
    private RaidBoss currentRaidBoss;
    private int currentRaidBossIndex;


    public Player(int id, String username, String password, int lumens) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ownedRaiders = new ArrayList<>();
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 350;
        this.lumens = lumens;
        this.currentRaidBoss = null;
        this.currentRaidBossIndex = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<Raider> getOwnedRaiders() {
        return this.ownedRaiders;
    }

    public int getLumens() {
        return this.lumens;
    }

    public RaidBoss getCurrentRaidBoss() {
        return this.currentRaidBoss;
    }

    public int getCurrentRaidBossIndex() {
        return this.currentRaidBossIndex;
    }

    public void setCurrentRaidBoss(RaidBoss raidBoss) {
        this.currentRaidBoss = raidBoss;
    }

    public void incrementCurrentRaidBossIndex() {
        this.currentRaidBossIndex++;
    }

    private void deductLumens(int lumenCost) {
        this.lumens -= lumenCost;
    }

    public void addLumens(int lumenCount) {
        this.lumens += lumenCount;
    }

    public Raider drawRaider(Pod pod) {
        Raider selectedRaider = null;

        if (lumens >= pod.getLumenCost()) {
            deductLumens(pod.getLumenCost());

            selectedRaider = pod.selectRandomRaider();
            if(checkIfOwned(selectedRaider)) {
                addCopy(selectedRaider);
            } else {
                ownedRaiders.add(selectedRaider);
            }
        }
        return selectedRaider;
    }

    private boolean checkIfOwned(Raider raider) {
        for(int i = 0; i < ownedRaiders.size(); i++) {
            if(raider.getName() == ownedRaiders.get(i).getName()) {
                return true;
            }
        }

        return false;
    }

    private void addCopy(Raider raider) {
        for(int i = 0; i < ownedRaiders.size(); i++) {
            if(raider.getName() == ownedRaiders.get(i).getName()) {
                ownedRaiders.get(i).incrementCopy();
            }
        }
    }

}
