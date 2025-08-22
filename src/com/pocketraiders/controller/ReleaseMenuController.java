package com.pocketraiders.controller;

import com.pocketraiders.model.*;
import com.pocketraiders.view.NovaPodView;
import com.pocketraiders.view.NullPodView;
import com.pocketraiders.view.PodView;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ReleaseMenuController {
    private Player player;
    private ArrayList<PodView> podViews;
    private PodView currentView;
    private int currentViewIndex;
    private Stage stage;
    private Scene scene;

    @FXML private Button backBtn, selectBtn, nextBtn;
    @FXML private Label lumenCountLabel;
    @FXML private Label podNameLabel, podDescLabel, podCostLabel;
    @FXML private ImageView backgroundImg, podSpriteImg, containedRaider1Img, containedRaider2Img, containedRaider3Img;

    public void setUp(Player player) {
        this.player = player;
        this.currentViewIndex = 0;
        this.podViews = new ArrayList<>();
        podViews.add(new NovaPodView(new NovaPod()));
        podViews.add(new NullPodView(new NullPod()));

        lumenCountLabel.setText("" + player.getLumens());
    }

    public void switchView(ActionEvent event) {
        if (this.currentViewIndex < this.podViews.size() - 1) {
            this.currentViewIndex++;
        } else {
            this.currentViewIndex = 0;
        }
        this.currentView = podViews.get(this.currentViewIndex);
        updateView();
    }

    private void updateView() {
        // Collect all nodes to animate
        List<Node> nodes = List.of(
                backgroundImg,
                podSpriteImg,
                containedRaider1Img,
                containedRaider2Img,
                containedRaider3Img,
                podNameLabel,
                podDescLabel,
                podCostLabel
        );

        // Create fade-out transitions for all nodes
        ParallelTransition fadeOutAll = new ParallelTransition();
        for (Node node : nodes) {
            FadeTransition ft = new FadeTransition(Duration.millis(600), node);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            fadeOutAll.getChildren().add(ft);
        }

        fadeOutAll.setOnFinished(event -> {
            // Update images and labels after fade-out
            backgroundImg.setImage(currentView.getBackground());
            podSpriteImg.setImage(currentView.getPodSprite());
            containedRaider1Img.setImage(currentView.getRaiders().get(0).getSprite());
            containedRaider2Img.setImage(currentView.getRaiders().get(5).getSprite());
            containedRaider3Img.setImage(currentView.getRaiders().get(8).getSprite());
            podNameLabel.setText(currentView.getName() + " Pod");
            podDescLabel.setText(currentView.getDescription());
            podCostLabel.setText("" + currentView.getLumenCost());

            // Create fade-in transitions for all nodes
            ParallelTransition fadeInAll = new ParallelTransition();
            for (Node node : nodes) {
                FadeTransition ft = new FadeTransition(Duration.millis(600), node);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                fadeInAll.getChildren().add(ft);
            }

            fadeInAll.play();
        });

        fadeOutAll.play();
    }

}
