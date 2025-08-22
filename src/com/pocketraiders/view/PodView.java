package com.pocketraiders.view;

import com.pocketraiders.model.Pod;
import com.pocketraiders.model.Raider;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class PodView {
    private Pod pod;
    private String name;
    private int lumenCost;
    private ArrayList<Raider> raiders;
    private String description;
    private Image background;
    private Image podSprite;

    public PodView(Pod pod, String backgroundPath, String spritePath) {
        this.pod = pod;
        this.background = new Image(getClass().getResourceAsStream(backgroundPath));
        this.podSprite = new Image(getClass().getResourceAsStream(spritePath));
        this.name = this.pod.getName();
        this.description = this.pod.getDescription();
        this.lumenCost = this.pod.getLumenCost();
        this.raiders = this.pod.getAllRaiders();
    }

    public Pod getPod() {
        return this.pod;
    }

    public String getName() {
        return this.name;
    }

    public int getLumenCost() {
        return this.lumenCost;
    }

    public ArrayList<Raider> getRaiders() {
        return this.raiders;
    }

    public String getDescription() {
        return this.description;
    }

    public Image getBackground() {
        return this.background;
    }

    public Image getPodSprite() {
        return this.podSprite;
    }

}
