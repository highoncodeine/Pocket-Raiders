package com.pocketraiders.controller;

import com.pocketraiders.model.*;
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
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class SelectRaidersController implements Initializable {
    private Player player;
    private ArrayList<Raider> ownedRaiders;
    private Raider[] selectedRaiders;
    private ArrayList<RaidBoss> raidBosses;
    private RaidBoss currentRaidBoss;
    private int currentRaidBossIndex;
    private Stage stage;

    private Label[] raiderNameLabels;
    private Label[] raiderLevelLabels;
    private ImageView[] raiderSpriteImgs;
    private Rectangle[] raiderRectangles;
    private Raider[] raiderSlot;
    private int MAX_PAGE;
    private int currentPage;

    @FXML private Label raiderNameLabel, raiderNameLabel1, raiderNameLabel2, raiderNameLabel3, raiderNameLabel4, raiderNameLabel5, raidBossLabel;
    @FXML private Label raiderLevelLabel, raiderLevelLabel1, raiderLevelLabel2, raiderLevelLabel3, raiderLevelLabel4, raiderLevelLabel5;
    @FXML private Button backBtn, previousBtn, nextBtn, startRaidBtn;
    @FXML private ImageView raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raiderSpriteImg3, raiderSpriteImg4, raiderSpriteImg5, raidBossSprite;
    @FXML private ImageView selectedRaiderImg, selectedRaiderImg1, selectedRaiderImg2;
    @FXML private Rectangle raiderRectangle, raiderRectangle1, raiderRectangle2, raiderRectangle3, raiderRectangle4, raiderRectangle5;

    public void setUp(Player player, Stage stage) {
        this.stage = stage;
        this.player = player;
        this.ownedRaiders = player.getOwnedRaiders();

        setUpRaidBoss(player);

        this.MAX_PAGE = (int) Math.ceil((double) player.getOwnedRaiders().size() / 6);
        this.currentPage = 1;

        if(MAX_PAGE == 1) {
            nextBtn.setDisable(true);
        }

        previousBtn.setDisable(true);
        startRaidBtn.setDisable(true);

        displayRaiders(1);
    }

    private void displayRaiders(int page) {
        for (int j = 0; j < 6; j++) {
            raiderNameLabels[j].setText("");
            raiderLevelLabels[j].setText("");
            raiderSpriteImgs[j].setImage(null);
            raiderSpriteImgs[j].setOnMouseClicked(null);
            raiderSpriteImgs[j].setOnMouseEntered(null);
            raiderSpriteImgs[j].setOnMouseExited(null);
            raiderSlot[j] = null;
        }

        int start = (page - 1) * 6;
        int end = Math.min(start + 6, ownedRaiders.size());

        for (int i = start; i < end; i++) {
            Raider raider = ownedRaiders.get(i);
            int slot = i - start;

            setUpRaiderLabels(raider, slot);
            raiderLevelLabels[slot].setText("Level " + raider.getLevel());
            raiderSpriteImgs[slot].setImage(raider.getSprite());

            raiderSpriteImgs[slot].setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    showRaiderDialog(raider);
                } else if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    clickAction(raider);
                }
            });

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
        updateRaiderRectangles();
    }

    public void previousPage(ActionEvent event) throws IOException {
        if (currentPage > 1) {
            currentPage--;
            displayRaiders(currentPage);
        }
        updateRaiderRectangles();
    }

    private void selectRaider(Raider raider) {
        for (int i = 0; i < selectedRaiders.length; i++) {
            if (selectedRaiders[i] == null) {
                selectedRaiders[i] = raider;
                break;
            }
        }
        updateSelectedRaiderImages();
        updateRaiderRectangles();
    }

    private void unselectRaider(Raider raider) {
        for (int i = 0; i < selectedRaiders.length; i++) {
            if(selectedRaiders[i] != null) {
                if (selectedRaiders[i].getId() == raider.getId()) {
                    selectedRaiders[i] = null;
                    updateSelectedRaiderImages();
                }
            }
        }
        updateRaiderRectangles();
    }

    private boolean isAlreadySelected(Raider raider) {
        for (int i = 0; i < selectedRaiders.length; i++) {
            if(selectedRaiders[i] != null) {
                if (selectedRaiders[i].getId() == raider.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void clickAction(Raider raider) {
        if(isAlreadySelected(raider)) {
            unselectRaider(raider);
            checkSelectedRaider();
        } else if(selectedRaiders[0] != null && selectedRaiders[1] != null && selectedRaiders[2] != null) {
            return;
        } else {
            selectRaider(raider);
            checkSelectedRaider();
        }
    }

    private void checkSelectedRaider() {
        startRaidBtn.setDisable(selectedRaiders[0] == null && selectedRaiders[1] == null && selectedRaiders[2] == null);
    }

    private void updateSelectedRaiderImages() {
        ImageView[] slots = { selectedRaiderImg, selectedRaiderImg1, selectedRaiderImg2 };
        for(int i = 0; i < 3; i++) {
            if(selectedRaiders[i] != null) {
                slots[i].setImage(selectedRaiders[i].getSprite());
            } else {
                slots[i].setImage(null);
            }
        }
    }

    private void updateRaiderRectangles() {
        for (int j = 0; j < raiderSlot.length; j++) {
            boolean isSelected = false;

            if (raiderSlot[j] != null) {
                for (Raider selected : selectedRaiders) {
                    if (selected != null && selected.getId() == raiderSlot[j].getId()) {
                        isSelected = true;
                        break;
                    }
                }
            }

            if (isSelected) {
                raiderRectangles[j].setStyle("-fx-fill: #FFFFFFFF");
            } else {
                raiderRectangles[j].setStyle("-fx-fill: #000000");
            }
        }
    }

    private void setUpRaiderLabels(Raider raider, int slot) {
        raiderNameLabels[slot].setText(raider.getName());

        switch(raider.getRarity()) {
            case Rarity.COMMON -> raiderNameLabels[slot].setStyle("-fx-text-fill: #FFFFFFFF");
            case Rarity.RARE -> raiderNameLabels[slot].setStyle("-fx-text-fill: #0033ff");
            case Rarity.LEGENDARY -> raiderNameLabels[slot].setStyle("-fx-text-fill: #EEFF00");
            case Rarity.MYTHICAL -> raiderNameLabels[slot].setStyle("-fx-text-fill: #ff0000");
        }
    }

    private void setUpRaidBoss(Player player) {
        if(player.getCurrentRaidBoss() == null) {
            player.setCurrentRaidBoss(raidBosses.get(player.getCurrentRaidBossIndex()));
            this.currentRaidBoss = player.getCurrentRaidBoss();
        } else {
            this.currentRaidBoss = player.getCurrentRaidBoss();
        }
        raidBossSprite.setImage(player.getCurrentRaidBoss().getSprite());
        raidBossLabel.setText("RAID BOSS " + player.getCurrentRaidBoss().getName().toUpperCase(Locale.ROOT) + " - HP " +
                player.getCurrentRaidBoss().getHp());
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

    public void switchToRaid(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/Raid.fxml"));
        Parent root = loader.load();
        RaidController controller = loader.getController();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.setUp(this.player, this.selectedRaiders, this.currentRaidBoss, stage);
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
                raiderNameLabel, raiderNameLabel1, raiderNameLabel2, raiderNameLabel3, raiderNameLabel4, raiderNameLabel5
        };
        raiderLevelLabels = new Label[] {
                raiderLevelLabel, raiderLevelLabel1, raiderLevelLabel2, raiderLevelLabel3, raiderLevelLabel4, raiderLevelLabel5
        };
        raiderSpriteImgs = new ImageView[] {
                raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raiderSpriteImg3, raiderSpriteImg4, raiderSpriteImg5
        };
        raiderRectangles = new Rectangle[] {
                raiderRectangle ,raiderRectangle1, raiderRectangle2, raiderRectangle3, raiderRectangle4, raiderRectangle5
        };
        raidBosses = new ArrayList<>();
        raidBosses.add(new CommonRaidBoss(1, "Staragazer", "Nova", "/raider-images/staragazer.png"));
        raidBosses.add(new CommonRaidBoss(2, "Nebula", "Nova", "/raider-images/nebula.png"));
        raidBosses.add(new CommonRaidBoss(3, "Hubble", "Nova", "/raider-images/hubble.png"));
        raidBosses.add(new RareRaidBoss(4, "Pluto", "Nova", "/raider-images/pluto.png"));
        raidBosses.add(new RareRaidBoss(5, "Orion", "Nova", "/raider-images/orion.png"));
        raidBosses.add(new RareRaidBoss(6, "Radiar T.", "Nova", "/raider-images/radiar_t.png"));
        raidBosses.add(new LegendaryRaidBoss(7, "Voidrunner", "Nova", "/raider-images/voidrunner.png"));
        raidBosses.add(new LegendaryRaidBoss(8, "Aetherion", "Nova", "/raider-images/aetherion.png"));
        raidBosses.add(new MythicalRaidBoss(9, "Supernova", "Nova", "/raider-images/supernova.png"));
        raidBosses.add(new MythicalRaidBoss(10, "Aetherion Prime", "Nova", "/raider-images/aetherion_prime.png"));

        raiderSlot = new Raider[6];
        selectedRaiders = new Raider[3];
    }
}
