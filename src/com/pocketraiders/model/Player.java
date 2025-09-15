package com.pocketraiders.model;

import java.util.ArrayList;

public class Player {
    private int id;
    private String username;
    private String password;
    private int level;
    private int xp;
    private int xpToNextLevel;
    private int lumens; //in-game currency
    private int currentRaidBossIndex;
    private RaidBoss currentRaidBoss;
    private ArrayList<Raider> ownedRaiders;
    private Raider[] favoriteRaiders;

    public Player(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ownedRaiders = new ArrayList<>();
        this.level = 1;
        this.xp = 0;
        this.xpToNextLevel = 350;
        this.lumens = 300;
        this.currentRaidBoss = null;
        this.currentRaidBossIndex = 0;
    }

    public Player(int id, String username, String password, int level, int xp, int xpToNextLevel, int lumens,
                  int currentRaidBossIndex, RaidBoss currentRaidBoss, ArrayList<Raider> ownedRaiders) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.xp = xp;
        this.xpToNextLevel = xpToNextLevel;
        this.lumens = lumens;
        this.currentRaidBossIndex = currentRaidBossIndex;
        this.currentRaidBoss = currentRaidBoss;
        this.ownedRaiders = ownedRaiders;
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

    public int getLevel() {
        return this.level;
    }

    public int getXp() {
        return this.xp;
    }

    public int getXpToNextLevel() {
        return this.xpToNextLevel;
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

    public Raider[] getFavoriteRaiders() { return this.favoriteRaiders; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

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

    public void addFavoriteRaider(Raider newRaider) {
        for(int i = 0; i < 3; i++){
            if(favoriteRaiders[i] == null) {
                favoriteRaiders[i] = newRaider;
            }
        }
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

    public void addOwnedRaider(Raider raider) {
        if(checkIfOwned(raider)){
            addCopy(raider);
        } else {
            ownedRaiders.add(raider);
        }
    }

    private boolean checkIfOwned(Raider raider) {
        for (Raider owned : ownedRaiders) {
            if (raider.getName().equals(owned.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addCopy(Raider raider) {
        for (Raider owned : ownedRaiders) {
            if (raider.getName().equals(owned.getName())) {
                owned.incrementCopy();
            }
        }
    }

    public void incrementXp(int xp) {
        int excessXp = 0;
        this.xp += xp;
        if(this.xp >= xpToNextLevel) {
            excessXp = Math.abs(xpToNextLevel - this.xp);
            levelUp();
        }
        this.xp += excessXp;
    }

    private void levelUp() {
        this.level++;
        this.xp = 0;
    }

    public void incrementRaidBossIndex() {
        this.currentRaidBossIndex += 1;
    }

}
