package com.pocketraiders.controller;

import com.pocketraiders.model.JSONManager;
import com.pocketraiders.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditDialogController {
    private Player player;
    private String action;
    private Stage stage;

    @FXML private Label editLabel, warningLabel;
    @FXML private TextField areaTextField;
    @FXML private Button changeBtn;

    public void setup(Player player, String action, Stage stage) {
        this.stage = stage;
        this.player = player;
        this.action = action;

        if(action.equals("edit_username")) {
            editLabel.setText("ENTER NEW USERNAME");
        } else {
            editLabel.setText("ENTER NEW PASSWORD");
        }
    }

    public void processChange(ActionEvent event) throws IOException {
        if(areaTextField.getText().isBlank()) {
            warningLabel.setText("INPUT CANNOT BE EMPTY");
        } else {
            if (this.action.equals("edit_username")) {
                String oldUsername = this.player.getUsername();
                JSONManager save = new JSONManager(this.player);
                player.setUsername(areaTextField.getText());
                save.update(oldUsername);
                stage.close();
            } else {
                player.setPassword(areaTextField.getText());
                stage.close();
            }
        }
    }

}
