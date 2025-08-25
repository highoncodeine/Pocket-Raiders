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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ReleaseRevealController {
    private Player player;
    private PodView podview;
    private Stage stage;

    @FXML private Button releaseAgainBtn, continueBtn, releaseRaiderBtn;
    @FXML private Label lumenCountLabel, lumenCostLabel, announcementMainLabel, announcementRarityLabel, indicatorLabel;
    @FXML private ImageView backgroundImg, spriteImg;
    @FXML private Rectangle indicatorRectangle;

    public void setUp(Player player, PodView podview) {
        this.player = player;
        this.podview = podview;

        announcementMainLabel.setText("");
        announcementRarityLabel.setText("");
        lumenCountLabel.setText("" + player.getLumens());
        backgroundImg.setImage(podview.getBackground());
        spriteImg.setImage(podview.getPodSprite());
    }

    @FXML
    private void releaseRaider(ActionEvent event) throws IOException {
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
        }

    }

    private void updateUIState() {
        lumenCountLabel.setText("" + player.getLumens());
        releaseRaiderBtn.setVisible(false);
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
        indicatorRectangle.setVisible(true);
        indicatorRectangle.setOpacity(0);

        indicatorLabel.setVisible(true);
        indicatorLabel.setOpacity(0);
        if (rarity == Rarity.LEGENDARY) {
            indicatorLabel.setText("A LEGENDARY RAIDER HAS BEEN RELEASED");
            indicatorRectangle.setStyle("-fx-fill: #eeff00");
        } else {
            indicatorLabel.setText("A MYTHICAL RAIDER HAS BEEN RELEASED");
            indicatorRectangle.setStyle("-fx-fill: #ff0000");
        }

        FadeTransition showIndicator = new FadeTransition(Duration.millis(1000), indicatorRectangle);
        showIndicator.setFromValue(0);
        showIndicator.setToValue(1);

        FadeTransition showIndicatorLabel = new FadeTransition(Duration.millis(1000), indicatorLabel);
        showIndicatorLabel.setFromValue(0);
        showIndicatorLabel.setToValue(1);

        FadeTransition concealIndicator = new FadeTransition(Duration.millis(1000), indicatorRectangle);
        concealIndicator.setFromValue(1);
        concealIndicator.setToValue(0);
        concealIndicator.setOnFinished(ev -> indicatorRectangle.setVisible(false));

        FadeTransition concealIndicatorLabel = new FadeTransition(Duration.millis(1000), indicatorLabel);
        concealIndicatorLabel.setFromValue(1);
        concealIndicatorLabel.setToValue(0);
        concealIndicatorLabel.setOnFinished(ev -> indicatorLabel.setVisible(false));

        ParallelTransition fadeInIndicators = new ParallelTransition(showIndicator, showIndicatorLabel);
        ParallelTransition fadeOutIndicators = new ParallelTransition(concealIndicator, concealIndicatorLabel);

        // --- Only after indicators are done, set the sprite ---
        fadeOutIndicators.setOnFinished(e -> {
            spriteImg.setOpacity(0);
            spriteImg.setScaleX(0.7);
            spriteImg.setScaleY(0.7);
            spriteReveal.play();
        });

        new SequentialTransition(fadeInIndicators, fadeOutIndicators).play();
    }

    public void switchToReleaseMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/ReleaseMenu.fxml"));
        Parent root = loader.load();
        ReleaseMenuController controller = loader.getController();
        controller.setUp(this.player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}
