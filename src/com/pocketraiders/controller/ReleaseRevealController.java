package com.pocketraiders.controller;

import com.pocketraiders.model.*;
import com.pocketraiders.view.PodView;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ReleaseRevealController {
    private Player player;
    private PodView podview;
    private Stage stage;

    @FXML private Button releaseAgainBtn, continueBtn;
    @FXML private Label lumenCountLabel, lumenCostLabel, announcementMainLabel, announcementRarityLabel;
    @FXML private ImageView backgroundImg, spriteImg, indicatorImg;

    public void setUp(Player player, PodView podview) throws IOException {
        this.player = player;
        this.podview = podview;

        announcementMainLabel.setText("");
        announcementRarityLabel.setText("");
        lumenCountLabel.setText("" + player.getLumens());
        backgroundImg.setImage(podview.getBackground());
        releaseRaider(new ActionEvent());
    }

    @FXML
    private void releaseRaider(ActionEvent event) throws IOException {
        AudioManager.play("click");
        announcementMainLabel.setText("");
        announcementRarityLabel.setText("");
        Raider selectedRaider = player.drawRaider(podview.getPod());

        // Sprite Setup
        spriteImg.setImage(selectedRaider.getSprite());
        spriteImg.setOpacity(0);
        spriteImg.setScaleX(0.7);
        spriteImg.setScaleY(0.7);

        FadeTransition fadeInSprite = new FadeTransition(Duration.millis(600), spriteImg);
        fadeInSprite.setFromValue(0);
        fadeInSprite.setToValue(1);

        ScaleTransition scaleUpSprite = new ScaleTransition(Duration.millis(600), spriteImg);
        scaleUpSprite.setFromX(0.7);
        scaleUpSprite.setFromY(0.7);
        scaleUpSprite.setToX(1);
        scaleUpSprite.setToY(1);

        ParallelTransition spriteReveal = new ParallelTransition(fadeInSprite, scaleUpSprite);

        //Announcement after sprite reveal
        spriteReveal.setOnFinished(e -> {
            announcementMainLabel.setText(selectedRaider.getName());
            announcementRarityLabel.setText("" + selectedRaider.getRarity());
            switch(selectedRaider.getRarity()) {
                case Rarity.COMMON -> announcementRarityLabel.setStyle("-fx-text-fill: #FFFFFFFF");
                case Rarity.RARE -> announcementRarityLabel.setStyle("-fx-text-fill: #0033ff");
                case Rarity.LEGENDARY -> announcementRarityLabel.setStyle("-fx-text-fill: #eeff00");
                case Rarity.MYTHICAL -> announcementRarityLabel.setStyle("-fx-text-fill: #ff0000");
            }

            // UI state updates
            updateUIState();
        });

        // If legendary or mythical, play indicator first
        if (selectedRaider.getRarity() == Rarity.LEGENDARY || selectedRaider.getRarity() == Rarity.MYTHICAL) {
            playSpecialAnimation(spriteReveal, selectedRaider.getRarity());
        } else {
            spriteReveal.play();
            AudioManager.play("common-reveal");
        }

    }

    private void updateUIState() {
        lumenCountLabel.setText("" + player.getLumens());
        releaseAgainBtn.setVisible(true);
        continueBtn.setVisible(true);
        if (this.player.getLumens() < this.podview.getLumenCost()) {
            releaseAgainBtn.setDisable(true);
            lumenCostLabel.setText("Not enough lumens");
        } else {
            lumenCostLabel.setText("Lumen Cost: " + podview.getPod().getLumenCost());
        }
    }

    private void playSpecialAnimation(ParallelTransition spriteReveal, Rarity rarity) {
        AudioManager.play("rare-reveal");
        indicatorImg.setVisible(true);
        indicatorImg.setOpacity(0);
        spriteImg.setVisible(false);

        if (rarity == Rarity.LEGENDARY) {
            indicatorImg.setImage(new Image(getClass().getResourceAsStream("/bg-images/legendaryrelease.png")));
        } else {
            indicatorImg.setImage(new Image(getClass().getResourceAsStream("/bg-images/mythicalrelease.png")));
        }

        FadeTransition showIndicator = new FadeTransition(Duration.millis(2000), indicatorImg);
        showIndicator.setFromValue(0);
        showIndicator.setToValue(1);

        FadeTransition concealIndicator = new FadeTransition(Duration.millis(2000), indicatorImg);
        concealIndicator.setFromValue(1);
        concealIndicator.setToValue(0);
        concealIndicator.setOnFinished(ev -> indicatorImg.setVisible(false));

        ParallelTransition fadeInIndicators = new ParallelTransition(showIndicator);
        ParallelTransition fadeOutIndicators = new ParallelTransition(concealIndicator);

        // --- Only after indicators are done, set the sprite ---
        fadeOutIndicators.setOnFinished(e -> {
            spriteImg.setVisible(true);
            spriteImg.setOpacity(0);
            spriteImg.setScaleX(0.7);
            spriteImg.setScaleY(0.7);
            spriteReveal.play();
        });

        new SequentialTransition(fadeInIndicators, fadeOutIndicators).play();
    }

    public void switchToReleaseZone(ActionEvent event) throws IOException {
        AudioManager.play("click");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/ReleaseZone.fxml"));
        Parent root = loader.load();
        ReleaseZoneController controller = loader.getController();
        controller.setUp(this.player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}
