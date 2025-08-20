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

    public int getLumens() {
        return this.lumens;
    }

    private void deductLumens(int lumenCost) {
        this.lumens -= lumenCost;
    }

    public void drawRaider(Pod pod) {
        if(lumens >= pod.getLumenCost()) {
            deductLumens(pod.getLumenCost());

            Raider selectedRaider = pod.selectRandomRaider();
            System.out.println("You have received " + selectedRaider.getName() + " [" +
                    selectedRaider.getRarity() + "]");
            ownedRaiders.add(selectedRaider);
        } else {
            System.out.println("Not enough lumens");
        }
    }

}
