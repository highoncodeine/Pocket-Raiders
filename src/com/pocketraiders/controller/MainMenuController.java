package com.pocketraiders.controller;

import com.pocketraiders.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;


public class MainMenuController {
    private Player player;
    private Stage stage;

    @FXML private Label playerInfoLabel;

    public void setUp(Player player) {
        this.player = player;
        playerInfoLabel.setText("USER: " + this.player.getUsername() + " | ID:" + this.player.getId());
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
        controller.setUp(this.player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

}
