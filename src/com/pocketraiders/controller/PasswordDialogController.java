package com.pocketraiders.controller;

import com.pocketraiders.model.AudioManager;
import com.pocketraiders.model.JSONManager;
import com.pocketraiders.model.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PasswordDialogController implements Initializable {
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
        AudioManager.play("click");
        if(player.getPassword().equals(passwordTextField.getText())) {
            switchToMainMenu(this.player, event);
        } else {
            warningLabel.setVisible(true);
        }
    }


    public void switchToMainMenu(Player player, ActionEvent event){
        AudioManager.play("click");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController controller = loader.getController();
            controller.setStage(stage);
            controller.setUp(player);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();

            Stage dialogStage = (Stage) passwordTextField.getScene().getWindow();
            dialogStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AudioManager.play("click");
        Platform.runLater(() -> {
            passwordTextField.getScene().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        verify(new ActionEvent(event.getSource(), null));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
    }
}
