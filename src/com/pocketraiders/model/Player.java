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
            ownedRaiders.add(selectedRaider);
        }
        return selectedRaider;
    }

}
