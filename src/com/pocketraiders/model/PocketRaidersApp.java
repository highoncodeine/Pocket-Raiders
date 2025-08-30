package com.pocketraiders.model;

import com.pocketraiders.controller.LoginMenuController;
import com.pocketraiders.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PocketRaidersApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
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
