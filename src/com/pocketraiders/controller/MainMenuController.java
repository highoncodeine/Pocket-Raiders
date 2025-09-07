package com.pocketraiders.controller;

import com.pocketraiders.model.JSONManager;
import com.pocketraiders.model.Player;
import com.pocketraiders.model.Raider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;


public class MainMenuController {
    private Player player;
    private Stage stage;

    @FXML private Label nameLabel, idLabel, lumenCountLabel, levelLabel, xpToNextLevelLabel, bestRaiderLabel, ownedRaidersLabel;
    @FXML private ProgressBar xpBar;
    @FXML private ImageView bestRaiderSpriteImg;

    public void setUp(Player player) {
        this.player = player;
        nameLabel.setText(player.getUsername());
        idLabel.setText("ID: " + player.getId());
        lumenCountLabel.setText("" + player.getLumens());
        levelLabel.setText("LEVEL " + player.getLevel());
        xpToNextLevelLabel.setText("" + (player.getXpToNextLevel() - player.getXp()));
        ownedRaidersLabel.setText("" + player.getOwnedRaiders().size());
        xpBar.setProgress((double) player.getXp() / player.getXpToNextLevel());

        Raider bestRaider = getBestRaider();
        if(bestRaider != null) {
            bestRaiderLabel.setText(bestRaider.getName());
            for (Raider raiders : player.getOwnedRaiders()) {
                if (raiders.getName().equals(bestRaider.getName())) {
                    bestRaiderSpriteImg.setImage(raiders.getSprite());
                }
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Raider getBestRaider() {
        Raider bestRaider = null;
        int bestLevel = 0;

        for(Raider selected: player.getOwnedRaiders()) {
            if(selected.getLevel() > bestLevel) {
                bestLevel = selected.getLevel();
                bestRaider = selected;
            }
        }
        return bestRaider;
    }

    public void save(ActionEvent event) throws IOException {
        JSONManager json = new JSONManager(this.player);
        json.save();
    }

    @FXML
    public void switchToReleaseZone(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/ReleaseZone.fxml"));
        Parent root = loader.load();
        ReleaseZoneController controller = loader.getController();
        controller.setUp(player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToInventory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/Inventory.fxml"));
        Parent root = loader.load();
        InventoryController controller = loader.getController();
        controller.setup(this.player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToSelectRaiders(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/SelectRaiders.fxml"));
        Parent root = loader.load();
        SelectRaidersController controller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.setUp(this.player, stage);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void showEditUsernameDialog(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/EditDialog.fxml"));
            Parent root = loader.load();

            EditDialogController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Username");
            dialogStage.setResizable(false);
            dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo-images/inverted.png")));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setup(player, "edit_username", dialogStage);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            dialogStage.show();
            dialogStage.setOnHidden(windowEvent -> this.setUp(this.player));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showEditPasswordDialog(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/EditDialog.fxml"));
            Parent root = loader.load();

            EditDialogController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Password");
            dialogStage.setResizable(false);
            dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo-images/inverted.png")));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setup(player, "edit_password", dialogStage);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            dialogStage.show();
            dialogStage.setOnHidden(windowEvent -> this.setUp(this.player));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
