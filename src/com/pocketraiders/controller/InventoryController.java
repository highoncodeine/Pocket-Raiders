package com.pocketraiders.controller;

import com.pocketraiders.model.Player;
import com.pocketraiders.model.Raider;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InventoryController implements Initializable{
    private Player player;
    private ArrayList<Raider> ownedRaiders;
    private Stage stage;
    private Label[] raiderNameLabels;
    private Label[] raiderExpLevels;
    private ImageView[] raiderSpriteImgs;
    private Raider[] raiderSlot;

    @FXML private Button backBtn, previousBtn, nextBtn;
    @FXML private Label raiderNameLabel, raiderNameLabel1, raiderNameLabel2, raiderNameLabel3, raiderNameLabel4, raiderNameLabel5, raiderNameLabel6,
            raiderNameLabel7, raiderNameLabel8, raiderNameLabel9;
    @FXML private Label raiderExpLevel, raiderExpLevel1, raiderExpLevel2, raiderExpLevel3, raiderExpLevel4, raiderExpLevel5, raiderExpLevel6,
            raiderExpLevel7, raiderExpLevel8, raiderExpLevel9;
    @FXML private ImageView raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raiderSpriteImg3, raiderSpriteImg4, raiderSpriteImg5, raiderSpriteImg6,
            raiderSpriteImg7, raiderSpriteImg8, raiderSpriteImg9;

    public void setup(Player player) {
        this.player = player;
        this.ownedRaiders = player.getOwnedRaiders();

        for (int i = 0; i < ownedRaiders.size(); i++) {
            if (ownedRaiders.get(i) != null) {
                Raider raider = ownedRaiders.get(i);
                final int index = i;

                raiderNameLabels[index].setText(raider.getName());
                raiderExpLevels[index].setText("Level " + raider.getLevel());
                raiderSpriteImgs[index].setImage(raider.getSprite());

                raiderSpriteImgs[index].setOnMouseClicked(event -> {
                    showRaiderDialog(raider);
                });

                raiderSpriteImgs[index].setOnMouseEntered(mouseEvent -> {
                    ScaleTransition st = new ScaleTransition(Duration.millis(150), raiderSpriteImgs[index]);
                    st.setToX(1.2);
                    st.setToY(1.2);
                    st.play();
                });

                raiderSpriteImgs[index].setOnMouseExited(mouseEvent -> {
                    ScaleTransition st = new ScaleTransition(Duration.millis(150), raiderSpriteImgs[index]);
                    st.setToX(1.0);
                    st.setToY(1.0);
                    st.play();
                });

                raiderSlot[index] = raider;
            }
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController controller = loader.getController();
        controller.setUp(this.player);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public void showRaiderDialog(Raider raider) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/RaiderDialog.fxml"));
            Parent root = loader.load();

            RaiderDialogController controller = loader.getController();
            controller.setUp(this.player, raider);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Inventory" + raider.getName());
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
        raiderNameLabels = new Label[] {
                raiderNameLabel, raiderNameLabel1, raiderNameLabel2, raiderNameLabel3, raiderNameLabel4, raiderNameLabel5, raiderNameLabel6,
                raiderNameLabel7, raiderNameLabel8, raiderNameLabel9
        };
        raiderExpLevels = new Label[] {
                raiderExpLevel, raiderExpLevel1, raiderExpLevel2, raiderExpLevel3, raiderExpLevel4, raiderExpLevel5, raiderExpLevel6,
                raiderExpLevel7, raiderExpLevel8, raiderExpLevel9
        };
        raiderSpriteImgs = new ImageView[] {
                raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raiderSpriteImg3, raiderSpriteImg4, raiderSpriteImg5, raiderSpriteImg6,
                raiderSpriteImg7, raiderSpriteImg8, raiderSpriteImg9
        };
        raiderSlot = new Raider[10];
    }
}
