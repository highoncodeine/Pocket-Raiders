package com.pocketraiders.controller;

import com.pocketraiders.model.Player;
import com.pocketraiders.model.Raider;
import com.pocketraiders.model.Rarity;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Locale;

public class RaiderDialogController {
    private Player player;
    private Raider raider;

    @FXML private Label raiderNameLabel, raiderLevelLabel, raiderMinLabel, raiderMaxLabel, raiderSpecialAbilityLabel, raiderHpLabel,
            raiderCopiesLabel, raiderXpToNextLevelLabel, lumenBonusLabel, idLabel, podLabel, rarityLabel;
    @FXML private ProgressBar raiderXpBar;
    @FXML private ImageView raiderSpriteImg, raiderBackgroundImg, lumenBonusImg;


    public void setUp(Player player, Raider raider) {
        this.player = player;
        this.raider = raider;
        setUpProgressBar(this.raider);
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

    private void setUpRarityLabel(Rarity rarity) {
        switch(rarity) {
            case Rarity.COMMON -> rarityLabel.setText("COMMON");
            case Rarity.RARE -> rarityLabel.setText("RARE");
            case Rarity.LEGENDARY -> rarityLabel.setText("LEGENDARY");
            case Rarity.MYTHICAL -> rarityLabel.setText("MYTHICAL");
        }
    }

    private void setUpProgressBar(Raider raider) {
        this.raiderXpBar.setProgress((double) raider.getXp() / raider.getXpToNextLevel());
        this.raiderXpToNextLevelLabel.setText(raider.getXp() + "/" + raider.getXpToNextLevel());
    }

    private void incrementRaider() {
        this.raider.incrementXp(1);
        this.raiderXpBar.setProgress((double) raider.getXp() / raider.getXpToNextLevel());
        this.raiderXpToNextLevelLabel.setText(this.raider.getXp() + "/" + this.raider.getXpToNextLevel());

        if(this.raider.getXp() == 0) {
            if(this.raider.getLevel() % 10 == 0) {
                this.player.addLumens(150);
                this.lumenBonusLabel.setVisible(false);
                this.lumenBonusImg.setVisible(false);
            } else if((this.raider.getLevel() + 1) % 10 == 0) {
                this.lumenBonusLabel.setVisible(true);
                this.lumenBonusImg.setVisible(true);
            }

            raiderLevelLabel.setText("Level: " + raider.getLevel());
            raiderHpLabel.setText("HP: " + raider.getHp());
            raiderMinLabel.setText("MIN ATTACK DAMAGE: " + raider.getAttackMin());
            raiderMaxLabel.setText("MAX ATTACK DAMAGE: " + raider.getAttackMax());

        }
    }

    private void updateUI() {
        raiderNameLabel.setText(raider.getName());
        raiderLevelLabel.setText("Level: " + raider.getLevel());
        idLabel.setText("0" + raider.getId());
        podLabel.setText(raider.getPod().toUpperCase(Locale.ROOT) + " POD");
        raiderCopiesLabel.setText("" + raider.getCopies());
        raiderHpLabel.setText("HP: " + raider.getHp());
        raiderMinLabel.setText("MIN ATTACK DAMAGE: " + raider.getAttackMin());
        raiderMaxLabel.setText("MAX ATTACK DAMAGE: " + raider.getAttackMax());
        raiderSpecialAbilityLabel.setText("SPECIAL ABILITY: NONE");
        raiderSpriteImg.setImage(raider.getSprite());
        setUpBackground(raider.getPod());
        setUpRarityLabel(raider.getRarity());
    }

}
