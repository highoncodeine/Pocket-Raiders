package com.pocketraiders.controller;

import com.pocketraiders.model.Player;
import com.pocketraiders.model.Raider;
import com.pocketraiders.model.Rarity;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class RaiderDialogController {
    private Player player;
    private Raider raider;
    private Stage stage;

    @FXML private Label raiderNameLabel, raiderLevelLabel, raiderMinLabel, raiderMaxLabel, raiderSpecialAbilityLabel, raiderCopiesLabel, raiderXpToNextLevelLabel;
    @FXML private ProgressBar raiderXpBar;
    @FXML private ImageView raiderSpriteImg, raiderBackgroundImg;
    @FXML private Rectangle rarityRectangle;


    public void setUp(Player player, Raider raider) {
        this.player = player;
        this.raider = raider;
        this.raiderXpBar.setProgress((double) raider.getXp() / raider.getXpToNextLevel());
        this.raiderXpToNextLevelLabel.setText(this.raider.getXp() + "/" + this.raider.getXpToNextLevel());
        this.raiderSpriteImg.setOnMouseClicked(mouseEvent -> {
            incrementRaider();

            ScaleTransition st = new ScaleTransition(Duration.millis(150), raiderSpriteImg);
            st.setFromX(1.0);
            st.setFromY(1.0);
            st.setToX(1.2); // 20% bigger
            st.setToY(1.2);

            st.setAutoReverse(true);
            st.setCycleCount(2); // grow then shrink back
            st.play();
        });

        updateUI();
    }

    private void setUpBackground(String pod) {
        switch(pod) {
            case "Nova" -> raiderBackgroundImg.setImage(new Image(getClass().getResourceAsStream("/bg-images/novapodmenu.png")));
            case "Null" -> raiderBackgroundImg.setImage(new Image(getClass().getResourceAsStream("/bg-images/nullpodmenu.png")));
        }
    }

    private void setUpRarityRectangle(Rarity rarity) {
        switch(rarity) {
            case Rarity.COMMON -> rarityRectangle.setStyle("-fx-text-fill: #FFFFFFFF");
            case Rarity.RARE -> rarityRectangle.setStyle("-fx-text-fill: #0033ff");
            case Rarity.LEGENDARY -> rarityRectangle.setStyle("-fx-fill: #eeff00");
            case Rarity.MYTHICAL -> rarityRectangle.setStyle("-fx-fill: #ff0000");
        }
    }

    private void incrementRaider() {
        this.raider.incrementXp(1);
        this.raiderXpBar.setProgress((double) raider.getXp() / raider.getXpToNextLevel());
        this.raiderXpToNextLevelLabel.setText(this.raider.getXp() + "/" + this.raider.getXpToNextLevel());

        if(this.raider.getXp() == 0) {
            raiderLevelLabel.setText("Level: " + raider.getLevel());
            raiderMinLabel.setText("MIN ATTACK DAMAGE: " + raider.getAttackMin());
            raiderMaxLabel.setText("MAX ATTACK DAMAGE: " + raider.getAttackMax());
        }
    }

    private void updateUI() {
        raiderNameLabel.setText(raider.getName());
        raiderLevelLabel.setText("Level: " + raider.getLevel());
        raiderCopiesLabel.setText("" + raider.getCopies());
        raiderMinLabel.setText("MIN ATTACK DAMAGE: " + raider.getAttackMin());
        raiderMaxLabel.setText("MAX ATTACK DAMAGE: " + raider.getAttackMax());
        raiderSpecialAbilityLabel.setText("SPECIAL ABILITY: NONE");
        raiderSpriteImg.setImage(raider.getSprite());
        setUpBackground(raider.getPod());
        setUpRarityRectangle(raider.getRarity());
    }

}
