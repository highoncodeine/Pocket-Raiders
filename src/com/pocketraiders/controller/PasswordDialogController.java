package com.pocketraiders.controller;

import com.pocketraiders.model.JSONManager;
import com.pocketraiders.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordDialogController {
    private Player player;
    private Stage stage;

    @FXML private Label warningLabel;
    @FXML private Button openBtn;
    @FXML private PasswordField passwordTextField;

    public void setUp(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
    }

    public void verify(ActionEvent event) throws IOException {
        if(player.getPassword().equals(passwordTextField.getText())) {
            switchToMainMenu(this.player, event);
        } else {
            warningLabel.setVisible(true);
        }
    }

    public void switchToMainMenu(Player player, ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController controller = loader.getController();
            controller.setStage(stage);
            controller.setUp(player);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();

            Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
