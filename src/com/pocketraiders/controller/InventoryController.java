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
    private int MAX_PAGE;
    private int currentPage;

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
        this.MAX_PAGE = (int) Math.ceil((double) player.getOwnedRaiders().size() / 10);
        this.currentPage = 1;

        if(MAX_PAGE == 1) {
            nextBtn.setDisable(true);
        }

        previousBtn.setDisable(true);

        displayRaiders(1);
    }

    private void displayRaiders(int page) {
        for (int j = 0; j < 10; j++) {
            raiderNameLabels[j].setText("");
            raiderExpLevels[j].setText("");
            raiderSpriteImgs[j].setImage(null);
            raiderSpriteImgs[j].setOnMouseClicked(null);
            raiderSpriteImgs[j].setOnMouseEntered(null);
            raiderSpriteImgs[j].setOnMouseExited(null);
            raiderSlot[j] = null;
        }

        int start = (page - 1) * 10;
        int end = Math.min(start + 10, ownedRaiders.size());

        for (int i = start; i < end; i++) {
            Raider raider = ownedRaiders.get(i);
            int slot = i - start;

            raiderNameLabels[slot].setText(raider.getName());
            raiderExpLevels[slot].setText("Level " + raider.getLevel());
            raiderSpriteImgs[slot].setImage(raider.getSprite());

            raiderSpriteImgs[slot].setOnMouseClicked(event -> showRaiderDialog(raider));

            raiderSpriteImgs[slot].setOnMouseEntered(mouseEvent -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(150), raiderSpriteImgs[slot]);
                st.setToX(1.2);
                st.setToY(1.2);
                st.play();
            });

            raiderSpriteImgs[slot].setOnMouseExited(mouseEvent -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(150), raiderSpriteImgs[slot]);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            });

            raiderSlot[slot] = raider;
        }

        updateNavigationButtons();
    }

    private void updateNavigationButtons() {
        previousBtn.setDisable(currentPage == 1);
        nextBtn.setDisable(currentPage == MAX_PAGE);
    }

    public void nextPage(ActionEvent event) throws IOException {
        if (currentPage < MAX_PAGE) {
            currentPage++;
            displayRaiders(currentPage);
        }
    }

    public void previousPage(ActionEvent event) throws IOException {
        if (currentPage > 1) {
            currentPage--;
            displayRaiders(currentPage);
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
            dialogStage.setTitle("Raider - " + raider.getName());
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
