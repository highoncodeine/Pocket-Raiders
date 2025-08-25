package com.pocketraiders.controller;

import com.pocketraiders.model.*;
import com.pocketraiders.view.NovaPodView;
import com.pocketraiders.view.NullPodView;
import com.pocketraiders.view.PodView;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReleaseMenuController {
    private Player player;
    private ArrayList<PodView> podViews;
    private PodView currentView;
    private int currentViewIndex;
    private Stage stage;

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
        this.currentView = podViews.getFirst();

        lumenCountLabel.setText("" + player.getLumens());

        if(player.getLumens() < 120) {
            this.selectBtn.setDisable(true);
        }
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

    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController controller = loader.getController();
        controller.setUp(this.player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToReleaseReveal(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/ReleaseReveal.fxml"));
        Parent root = loader.load();
        ReleaseRevealController controller = loader.getController();
        controller.setUp(this.player, this.currentView);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
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

            if(this.player.getLumens() < this.currentView.getLumenCost()) {
                this.selectBtn.setDisable(true);
            } else {
                this.selectBtn.setDisable(false);
            }

            fadeInAll.play();
        });

        fadeOutAll.play();
    }

}
