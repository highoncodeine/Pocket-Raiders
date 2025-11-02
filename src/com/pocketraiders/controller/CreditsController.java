package com.pocketraiders.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;


public class CreditsController {
    private Stage stage;

    @FXML private ImageView bytebugSprite;
    @FXML private Label passwordLabel;
    @FXML private Hyperlink githubLink;

    public void setUp(Stage stage) {
        this.stage = stage;
        bytebugSprite.setOnMouseClicked(this::showPassword);
        githubLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/highoncodeine/Pocket-Raiders"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    public void showPassword(MouseEvent event) {
        passwordLabel.setVisible(true);
    }

    public void switchToLoginMenu(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/LoginMenu.fxml"));
        Parent root = loader.load();
        LoginMenuController controller = loader.getController();
        controller.setUp(stage);
        stage.setScene(new Scene(root));
        stage.setTitle("Pocket Raiders");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo-images/original.png")));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
