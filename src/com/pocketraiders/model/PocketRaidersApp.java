package com.pocketraiders.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PocketRaidersApp extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/pocketraiders/view/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Pocket Raiders");
        stage.getIcons().add(new Image("/com/pocketraiders/resources/logo-images/original.png"));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

}
