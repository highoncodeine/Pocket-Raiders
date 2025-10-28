package com.pocketraiders.controller;

import com.pocketraiders.model.AudioManager;
import com.pocketraiders.model.Player;
import com.pocketraiders.model.RaidBoss;
import com.pocketraiders.model.Raider;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WinMenuController implements Initializable {
    private Player player;
    private Raider[] selectedRaiders;
    private RaidBoss raidBoss;
    private boolean raidersWin;
    private Stage stage;

    @FXML private Label winnerLabel, raiderNameLabel, raiderNameLabel1, raiderNameLabel2;
    @FXML private Label raiderXpLabel, raiderXpLabel1, raiderXpLabel2, playerLevelLabel, playerXpLabel;
    @FXML private Label lumenRewardLabel, copiesRewardLabel;
    @FXML private Button releaseZoneBtn, continueBtn;
    @FXML private ProgressBar playerXpBar;
    @FXML private ImageView backgroundImg, copySpriteImg;

    private Label[] raiderNameLabels;
    private Label[] raiderXpLabels;

    public void setUp(Player player, Raider[] selectedRaiders, RaidBoss raidBoss, boolean raidersWin) {
        this.raidBoss = raidBoss;
        this.raidersWin = raidersWin;
        setUpPlayer(player);
        setUpRaiders(selectedRaiders);
        this.playerXpBar.setProgress((double)player.getXp() / player.getXpToNextLevel());
        concludeWinner(raidersWin);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUpRaiders(Raider[] selectedRaiders) {
        this.selectedRaiders = selectedRaiders;
        for(int i = 0; i < 3; i++) {
            if(selectedRaiders[i] != null) {
                raiderNameLabels[i].setText(selectedRaiders[i].getName());
                raiderXpLabels[i].setText("+" + raidBoss.getXpReward() + "XP");
            } else {
                raiderNameLabels[i].setText("");
                raiderXpLabels[i].setText("");
            }
        }
    }

    private void setUpPlayer(Player player) {
        this.player = player;
        playerLevelLabel.setText("LEVEL " + player.getLevel());
    }

    private void concludeWinner(boolean raidersWin) {
        if(raidersWin) {
            concludeRaidersWin();
        } else {
            concludeRaidBossWin();
        }
    }

    private void concludeRaidersWin() {
        winnerLabel.setText("THE RAIDERS WINS");
        lumenRewardLabel.setText("x" + raidBoss.getLumenReward());
        copiesRewardLabel.setText("x" + raidBoss.getCopiesReward());
        copySpriteImg.setImage(raidBoss.getSprite());

        for(int i = 0; i < 3; i++) {
            if(selectedRaiders[i] != null) {
                selectedRaiders[i].incrementXp(raidBoss.getXpReward());
                selectedRaiders[i].resetHp();
                raiderNameLabels[i].setText(selectedRaiders[i].getName());
                raiderXpLabels[i].setText("+" + raidBoss.getXpReward() + "XP");
            }
        }
        playerXpLabel.setText("+" + raidBoss.getPlayerXpReward() + "XP");

        int oldValue = this.player.getXp();
        player.incrementXp(raidBoss.getPlayerXpReward());
        int newValue = this.player.getXp();
        animateXpBar(this.playerXpBar, oldValue, newValue, player.getXpToNextLevel());

        playerLevelLabel.setText("Level " + player.getLevel());
        player.addLumens(raidBoss.getLumenReward());
        for(int i = 0; i < raidBoss.getCopiesReward(); i++) {
            player.addOwnedRaider(raidBoss.getCopy());
        }
        this.player.setCurrentRaidBoss(null);
        this.player.incrementRaidBossIndex();
    }

    private void concludeRaidBossWin() {
        winnerLabel.setText("THE RAID BOSS PITIES YOU");
        lumenRewardLabel.setText("x" + raidBoss.getLumenPity());
        copiesRewardLabel.setText("");
        copySpriteImg.setImage(null);

        for(int i = 0; i < 3; i++) {
            raiderNameLabels[i].setText("");
            raiderXpLabels[i].setText("");
        }

        playerXpLabel.setText("+0XP");
        player.addLumens(raidBoss.getLumenPity());
    }

    private void animateXpBar(ProgressBar bar, int oldXp, int newXp, int xpToNextLevel) {
        double oldValue = (double) oldXp / xpToNextLevel;
        double newValue = (double) newXp / xpToNextLevel;

        if (oldValue < 0) oldValue = 0;
        if (newValue < 0) newValue = 0;
        if (oldValue > 1) oldValue = 1;
        if (newValue > 1) newValue = 1;

        Timeline timeline;

        if (newValue < oldValue) {
            // leveled up
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(bar.progressProperty(), oldValue)),
                    new KeyFrame(Duration.millis(400),
                            new KeyValue(bar.progressProperty(), 1.0)),
                    new KeyFrame(Duration.millis(401),
                            new KeyValue(bar.progressProperty(), 0.0)),
                    new KeyFrame(Duration.millis(800),
                            new KeyValue(bar.progressProperty(), newValue))
            );
        } else {
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(bar.progressProperty(), oldValue)),
                    new KeyFrame(Duration.millis(500),
                            new KeyValue(bar.progressProperty(), newValue))
            );
        }

        timeline.play();
    }

    @FXML
    public void switchToReleaseZone(ActionEvent event) throws IOException {
        AudioManager.stopBackgroundMusic();
        AudioManager.playMainBgMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/ReleaseZone.fxml"));
        Parent root = loader.load();
        ReleaseZoneController controller = loader.getController();
        controller.setUp(player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToSelectRaiders(ActionEvent event) throws IOException {
        AudioManager.stopBackgroundMusic();
        AudioManager.playMainBgMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/SelectRaiders.fxml"));
        Parent root = loader.load();
        SelectRaidersController controller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.setUp(this.player, stage);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        raiderNameLabels = new Label[] {
                raiderNameLabel, raiderNameLabel1, raiderNameLabel2
        };
        raiderXpLabels = new Label[] {
                raiderXpLabel, raiderXpLabel1, raiderXpLabel2
        };
    }
}
