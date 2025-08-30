package com.pocketraiders.controller;

import com.pocketraiders.model.JSONManager;
import com.pocketraiders.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenuController implements Initializable {
    private Player[] players;
    private String[] passwords;
    private Stage stage;

    @FXML private Label playerNameLabel, playerNameLabel1, playerNameLabel2, playerInfoLabel, playerInfoLabel1, playerInfoLabel2, warningLabel;
    @FXML private Button  openBtn, openBtn1, openBtn2, registerBtn;
    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordTextField;

    private Label[] playerNameLabels;
    private Label[] playerInfoLabels;
    private Button[] openBtns;

    public void setUp(Stage stage) {
        this.stage = stage;
        JSONManager saves = new JSONManager();
        players = saves.loadAllPlayers();

        for(int i = 0; i < 3; i++) {
            if(players[i] != null) {
                Player player = players[i];
                playerNameLabels[i].setText(player.getUsername());
                playerInfoLabels[i].setText("LEVEL " + player.getLevel() + " - " + player.getLumens()
                        + " LUMENS - " + player.getOwnedRaiders().size() + " OWNED RAIDERS");
                openBtns[i].setOnAction(event -> showPasswordDialog(player));
            } else {
                playerNameLabels[i].setVisible(false);
                playerInfoLabels[i].setVisible(false);
                openBtns[i].setVisible(false);
            }
        }
    }

    public void registerPlayer(ActionEvent event) throws IOException {
        String username = this.userNameTextField.getText();
        String password = this.passwordTextField.getText();

        if(username != null && !username.isBlank() && password != null && !password.isBlank()) {
            Player player = new Player(1234412, username, password);

            JSONManager newSave = new JSONManager(player);
            newSave.save();
            switchToMainMenu(player);
        } else {
            warningLabel.setVisible(true);
        }
    }

    public void switchToMainMenu(Player player) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController controller = loader.getController();
            controller.setStage(stage);
            controller.setUp(player);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPasswordDialog(Player player) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/PasswordDialog.fxml"));
            Parent root = loader.load();

            PasswordDialogController controller = loader.getController();
            controller.setUp(player, stage);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Verification for " + player.getUsername());
            dialogStage.setResizable(false);
            dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo-images/inverted.png")));
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerNameLabels = new Label[] {
                playerNameLabel, playerNameLabel1, playerNameLabel2
        };
        playerInfoLabels = new Label[] {
                playerInfoLabel, playerInfoLabel1, playerInfoLabel2
        };
        openBtns = new Button[]{
                openBtn, openBtn1, openBtn2
        };
    }
}
