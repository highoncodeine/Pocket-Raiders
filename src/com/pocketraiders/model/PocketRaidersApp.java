package com.pocketraiders.model;

import com.pocketraiders.controller.MainMenuController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController controller = loader.getController();
        controller.setUp(new Player(101182410, "hadjj_dev", "asterisk", 999999));
        stage.setScene(new Scene(root));
        stage.setTitle("Pocket Raiders");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo-images/original.png")));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

}
