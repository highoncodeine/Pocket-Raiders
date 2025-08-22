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
    private Scene scene;

    @FXML private Label playerInfoLabel;

    public void setUp(Player player) {
        this.player = new Player(18251021, "hdev", "asterisk", 99999);
        playerInfoLabel.setText("USER: " + this.player.getUsername() + " | ID:" + this.player.getId());
    }

    @FXML
    public void switchToReleaseMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/ReleaseMenu.fxml"));
        Parent root = loader.load();
        ReleaseMenuController controller = loader.getController();
        controller.setUp(player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
}
