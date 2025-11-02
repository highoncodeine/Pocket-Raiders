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
    private Raider[] favoriteRaiders;
    private ArrayList<RaidBoss> raidBosses;
    private RaidBoss currentRaidBoss;
    private ArrayList<String> spritePaths;
    private Stage stage;

    private Label[] raiderNameLabels;
    private Label[] raiderLevelLabels;
    private ImageView[] raiderSpriteImgs;
    private Rectangle[] raiderRectangles;
    private Raider[] raiderSlot;
    private int MAX_PAGE;
    private int currentPage;

    @FXML private Label raiderNameLabel, raiderNameLabel1, raiderNameLabel2, raiderNameLabel3, raiderNameLabel4, raiderNameLabel5, raidBossLabel;
    @FXML private Label raiderLevelLabel, raiderLevelLabel1, raiderLevelLabel2, raiderLevelLabel3, raiderLevelLabel4, raiderLevelLabel5, stageLevelLabel;
    @FXML private Button backBtn, previousBtn, nextBtn, startRaidBtn;
    @FXML private ImageView raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raiderSpriteImg3, raiderSpriteImg4, raiderSpriteImg5, raidBossSprite;
    @FXML private ImageView selectedRaiderImg, selectedRaiderImg1, selectedRaiderImg2;
    @FXML private Rectangle raiderRectangle, raiderRectangle1, raiderRectangle2, raiderRectangle3, raiderRectangle4, raiderRectangle5;

    public void setUp(Player player, Stage stage) {
        this.stage = stage;
        this.player = player;
        this.ownedRaiders = player.getOwnedRaiders();
        this.favoriteRaiders = player.getFavoriteRaiders();

        prepareRaidBossData();
        setUpRaidBoss(player);

        previousBtn.setDisable(true);
        startRaidBtn.setDisable(true);

        this.MAX_PAGE = (int) Math.ceil((double) player.getOwnedRaiders().size() / 6);
        this.currentPage = 1;

        if(MAX_PAGE == 1) {
            nextBtn.setDisable(true);
        }

        for(int i = 0; i < 3; i++) {
            if(favoriteRaiders[i] != null) {
                selectedRaiders[i] = favoriteRaiders[i];
            }
        }

        updateSelectedRaiderImages();
        updateRaiderRectangles();
        checkSelectedRaider();

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
        AudioManager.play("click");
        if (currentPage < MAX_PAGE) {
            currentPage++;
            displayRaiders(currentPage);
        }
        updateRaiderRectangles();
    }

    public void previousPage(ActionEvent event) throws IOException {
        AudioManager.play("click");
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
        AudioManager.play("click");
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
            AudioManager.play("click");
            unselectRaider(raider);
            checkSelectedRaider();
        } else if(selectedRaiders[0] != null && selectedRaiders[1] != null && selectedRaiders[2] != null) {
            return;
        } else {
            AudioManager.play("click");
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
                Raider raider = selectedRaiders[i];
                slots[i].setImage(selectedRaiders[i].getSprite());
                slots[i].setOnMouseClicked(mouseEvent -> {
                   unselectRaider(raider);
                   checkSelectedRaider();
                });
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
        stageLevelLabel.setText("LEVEL: " + player.getLevel());
        if(player.getCurrentRaidBoss() == null) {
            player.setCurrentRaidBoss(raidBosses.get(player.getCurrentRaidBossIndex()));
            player.getCurrentRaidBoss().setImage(spritePaths.get(player.getCurrentRaidBossIndex()));
            player.getCurrentRaidBoss().scale(this.player.getLevel());
            this.currentRaidBoss = player.getCurrentRaidBoss();
        } else {
            this.currentRaidBoss = player.getCurrentRaidBoss();
        }
        raidBossSprite.setImage(player.getCurrentRaidBoss().getSprite());
        raidBossLabel.setText("" + player.getCurrentRaidBoss().getName().toUpperCase(Locale.ROOT) + " | HP " +
                player.getCurrentRaidBoss().getHp());
    }

    private void prepareRaidBossData() {
        spritePaths = new ArrayList<>();
        spritePaths.add("/raider-images/staragazer.png");
        spritePaths.add("/raider-images/nebula.png");
        spritePaths.add("/raider-images/hubble.png");
        spritePaths.add("/raider-images/pluto.png");
        spritePaths.add("/raider-images/orion.png");
        spritePaths.add("/raider-images/radiar_t.png");
        spritePaths.add("/raider-images/voidrunner.png");
        spritePaths.add("/raider-images/aetherion.png");
        spritePaths.add("/raider-images/supernova.png");
        spritePaths.add("/raider-images/aetherion_prime.png");
        spritePaths.add("/raider-images/bytebug.png");
        spritePaths.add("/raider-images/firewall.png");
        spritePaths.add("/raider-images/packet_tracer.png");
        spritePaths.add("/raider-images/staragazer.png");
        spritePaths.add("/raider-images/scripython.png");
        spritePaths.add("/raider-images/seer.png");
        spritePaths.add("/raider-images/staragazer.png");
        spritePaths.add("/raider-images/staragazer.png");
        spritePaths.add("/raider-images/staragazer.png");
        spritePaths.add("/raider-images/staragazer.png");

        raidBosses = new ArrayList<>();
        raidBosses.add(new CommonRaidBoss(1, "Staragazer", "Nova"));
        raidBosses.add(new CommonRaidBoss(2, "Nebula", "Nova"));
        raidBosses.add(new CommonRaidBoss(3, "Hubble", "Nova"));
        raidBosses.add(new RareRaidBoss(4, "Pluto", "Nova"));
        raidBosses.add(new RareRaidBoss(5, "Orion", "Nova"));
        raidBosses.add(new RareRaidBoss(6, "Radiar T.", "Nova"));
        raidBosses.add(new LegendaryRaidBoss(7, "Voidrunner", "Nova"));
        raidBosses.add(new LegendaryRaidBoss(8, "Aetherion", "Nova"));
        raidBosses.add(new MythicalRaidBoss(9, "Supernova", "Nova"));
        raidBosses.add(new MythicalRaidBoss(10, "Aetherion Prime", "Nova"));
        raidBosses.add(new CommonRaidBoss(11, "Bytebug", "Null"));
        raidBosses.add(new CommonRaidBoss(12, "Firewall", "Null"));
        raidBosses.add(new RareRaidBoss(13, "Packet Tracer", "Null"));
        raidBosses.add(new RareRaidBoss(14, "Scripython", "Null"));
        raidBosses.add(new RareRaidBoss(15, "Rabbit", "Null"));
        raidBosses.add(new RareRaidBoss(16, "Seer", "Null"));
        raidBosses.add(new LegendaryRaidBoss(17, "Overclocke", "Null"));
        raidBosses.add(new LegendaryRaidBoss(18, "Cipherstorm", "Null"));
        raidBosses.add(new MythicalRaidBoss(19, "WannaCry1", "Null"));
        raidBosses.add(new MythicalRaidBoss(20, "[REDACTED]", "Null"));
    }


    public void switchToMainMenu(ActionEvent event) throws IOException {
        AudioManager.play("click");
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
        AudioManager.play("click");
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
        AudioManager.play("click");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pocketraiders/view/RaiderDialog.fxml"));
            Parent root = loader.load();

            RaiderDialogController controller = loader.getController();
            controller.setUp(this.player, raider);

            Stage dialogStage = new Stage();
            dialogStage.setTitle(raider.getId() + " - " + raider.getName().toUpperCase(Locale.ROOT));
            dialogStage.setResizable(false);
            dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo-images/inverted.png")));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setOnHidden(event -> {
                displayRaiders(this.currentPage);
            });


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

        raiderSlot = new Raider[6];
        selectedRaiders = new Raider[3];
    }
}
