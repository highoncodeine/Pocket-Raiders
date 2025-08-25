package com.pocketraiders.model;

import java.util.ArrayList;

public class Player {
    private int id;
    private String username;
    private String password;
    private ArrayList<Raider> ownedRaiders;
    private int lumens; //in-game currency

    public Player(int id, String username, String password, int lumens) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ownedRaiders = new ArrayList<>();
        this.lumens = lumens;
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

    private void deductLumens(int lumenCost) {
        this.lumens -= lumenCost;
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
