package com.pocketraiders.controller;

import com.pocketraiders.model.AudioManager;
import com.pocketraiders.model.Player;
import com.pocketraiders.model.Raider;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectFavoriteDialogController implements Initializable {
    private Player player;
    private Stage stage;
    private Raider[] favoriteRaiders;
    private Raider[] displayedRaiders;
    private ArrayList<Raider> ownedRaiders;
    private int maxPage;
    private int currentPage;

    private ImageView[] raiderSpriteImgs;
    private ImageView[] selectedRaiderImgs;
    private Rectangle[] raiderRectangles;

    @FXML private ImageView raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raiderSpriteImg3, raiderSpriteImg4, raiderSpriteImg5, raiderSpriteImg6, raiderSpriteImg7,
            raiderSpriteImg8, raiderSpriteImg9, raiderSpriteImg10, raiderSpriteImg11;
    @FXML private ImageView selectedRaiderImg, selectedRaiderImg1, selectedRaiderImg2;
    @FXML private Rectangle raiderRectangle, raiderRectangle1, raiderRectangle2, raiderRectangle3, raiderRectangle4, raiderRectangle5, raiderRectangle6, raiderRectangle7,
            raiderRectangle8, raiderRectangle9, raiderRectangle10, raiderRectangle11;
    @FXML private Button saveBtn, previousBtn, nextBtn;

    public void setUp(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        this.favoriteRaiders = player.getFavoriteRaiders();
        this.ownedRaiders = player.getOwnedRaiders();
        this.displayedRaiders = new Raider[12];
        this.maxPage = (int) Math.ceil((double) player.getOwnedRaiders().size() / 12);
        this.currentPage = 1;
        if(this.maxPage == 1) {
            nextBtn.setDisable(true);
        }
        previousBtn.setDisable(true);
        displayRaiders(1);
        updateRaiderRectangles();
        updateSelectedRaiderImages();
    }

    private void displayRaiders(int currentPage) {
        for (int j = 0; j < displayedRaiders.length; j++) {
            displayedRaiders[j] = null;
        }

        for(int i = 0; i < 3; i++) {
            if(favoriteRaiders[i] != null) {
                selectedRaiderImgs[i].setImage(favoriteRaiders[i].getSprite());
            }
        }

        for (int j = 0; j < 12; j++) {
            raiderSpriteImgs[j].setImage(null);
            raiderSpriteImgs[j].setOnMouseClicked(null);
            raiderSpriteImgs[j].setOnMouseEntered(null);
            raiderSpriteImgs[j].setOnMouseExited(null);
        }

        int start = (currentPage - 1) * 12;
        int end = Math.min(start + 12, ownedRaiders.size());

        for (int i = start; i < end; i++) {
            Raider raider = ownedRaiders.get(i);
            int slot = i - start;

            raiderSpriteImgs[slot].setImage(raider.getSprite());

            raiderSpriteImgs[slot].setOnMouseClicked(mouseEvent -> {
                    clickAction(raider);
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

            displayedRaiders[slot] = raider;
        }
        updateNavigationButtons();
    }

    private void updateNavigationButtons() {
        previousBtn.setDisable(currentPage == 1);
        nextBtn.setDisable(currentPage == maxPage);
    }

    public void nextPage(ActionEvent event) throws IOException {
        if (currentPage < maxPage) {
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
        for (int i = 0; i < favoriteRaiders.length; i++) {
            if (favoriteRaiders[i] == null) {
                favoriteRaiders[i] = raider;
                break;
            }
        }
        updateSelectedRaiderImages();
        updateRaiderRectangles();
    }

    private void unselectRaider(Raider raider) {
        for (int i = 0; i < favoriteRaiders.length; i++) {
            if(favoriteRaiders[i] != null) {
                if (favoriteRaiders[i].getId() == raider.getId()) {
                    favoriteRaiders[i] = null;
                    updateSelectedRaiderImages();
                }
            }
        }
        updateRaiderRectangles();
    }

    private boolean isAlreadySelected(Raider raider) {
        for (int i = 0; i < favoriteRaiders.length; i++) {
            if(favoriteRaiders[i] != null) {
                if (favoriteRaiders[i].getId() == raider.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void clickAction(Raider raider) {
        AudioManager.play("click");
        if(isAlreadySelected(raider)) {
            unselectRaider(raider);
        } else if(favoriteRaiders[0] != null && favoriteRaiders[1] != null && favoriteRaiders[2] != null) {
            return;
        } else {
            selectRaider(raider);
        }
    }


    private void updateSelectedRaiderImages() {
        ImageView[] slots = { selectedRaiderImg, selectedRaiderImg1, selectedRaiderImg2 };
        for(int i = 0; i < 3; i++) {
            if(favoriteRaiders[i] != null) {
                Raider raider = favoriteRaiders[i];
                slots[i].setImage(favoriteRaiders[i].getSprite());
                slots[i].setOnMouseClicked(mouseEvent -> {
                    unselectRaider(raider);
                });
            } else {
                slots[i].setImage(null);
            }
        }
    }

    private void updateRaiderRectangles() {
        for (int j = 0; j < displayedRaiders.length; j++) {
            boolean isSelected = false;

            if (displayedRaiders[j] != null) {
                for (Raider selected : favoriteRaiders) {
                    if (selected != null && selected.getId() == displayedRaiders[j].getId()) {
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

    public void switchToMainMenu(ActionEvent event) {
        try {
            Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dialogStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        raiderSpriteImgs = new ImageView[] {
                raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raiderSpriteImg3, raiderSpriteImg4, raiderSpriteImg5, raiderSpriteImg6, raiderSpriteImg7,
                raiderSpriteImg8, raiderSpriteImg9, raiderSpriteImg10, raiderSpriteImg11
        };
        selectedRaiderImgs = new ImageView[] {
                selectedRaiderImg, selectedRaiderImg1, selectedRaiderImg2
        };
        raiderRectangles = new Rectangle[] {
                raiderRectangle, raiderRectangle1, raiderRectangle2, raiderRectangle3, raiderRectangle4, raiderRectangle5, raiderRectangle6, raiderRectangle7,
                raiderRectangle8, raiderRectangle9, raiderRectangle10, raiderRectangle11
        };

        for(int i = 0; i < 3; i++) {
            raiderSpriteImgs[i].setImage(null);
        }
    }


}
