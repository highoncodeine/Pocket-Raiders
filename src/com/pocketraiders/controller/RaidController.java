package com.pocketraiders.controller;

import com.pocketraiders.model.Player;
import com.pocketraiders.model.RaidBoss;
import com.pocketraiders.model.Raider;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Random;

public class RaidController implements Initializable {
    private Player player;
    private Raider[] selectedRaiders;
    private Raider selectedRaider1, selectedRaider2, selectedRaider3;
    private RaidBoss raidBoss;
    private int turnCount;
    private Stage stage;
    private final Random random = new Random();

    @FXML private Label raiderNameLabel, raiderNameLabel1, raiderNameLabel2, raidBossNameLabel;
    @FXML private Label raiderLevelLabel, raiderLevelLabel1, raiderLevelLabel2, raidBossRarityLabel;
    @FXML private Label raiderHpLabel, raiderHpLabel1, raiderHpLabel2, raidBossHpLabel;
    @FXML private Label attackLabel, attackLabel1, attackLabel2, maxLabel, maxLabel1, maxLabel2;
    @FXML private Label turnDeterminerLabel, turnCountLabel, raidBossAttackLabel;
    @FXML private Label hpLabel, hpLabel1, hpLabel2;
    @FXML private ProgressBar raiderHpBar, raiderHpBar1, raiderHpBar2, raidBossHpBar;
    @FXML private Button stopBtn, stopBtn1, stopBtn2, autoModeBtn;
    @FXML private Rectangle raiderRectangle, raiderRectangle1, raiderRectangle2, raidBossRectangle;
    @FXML private ImageView raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2, raidBossSpriteImg, backgroundImg;
    @FXML private ImageView miniRaiderSpriteImg, miniRaiderSpriteImg1, miniRaiderSpriteImg2;
    @FXML private TextArea battleLogTextArea;

    private Label[] raiderNameLabels;
    private Label[] raiderLevelLabels;
    private Label[] raiderHpLabels;
    private Label[] attackLabels;
    private Label[] maxLabels;
    private Label[] hpLabels;
    private ProgressBar[] raiderHpBars;
    private Button[] stopBtns;
    private Rectangle[] raiderRectangles;
    private ImageView[] raiderSpriteImgs;
    private ImageView[] miniRaiderSpriteImgs;

    public void setUp(Player player, Raider[] selectedRaiders, RaidBoss raidBoss) {
        this.player = player;
        this.selectedRaiders = selectedRaiders;
        this.turnCount = 1;
        setUpRaiders(selectedRaiders);
        setUpRaidBoss(raidBoss);
        if(raidBoss.getPod() == "Nova") {
            this.backgroundImg.setImage(new Image(getClass().getResourceAsStream("/bg-images/novapodmenu.png")));
        } else if (raidBoss.getPod() == "Null") {
            this.backgroundImg.setImage(new Image(getClass().getResourceAsStream("/bg-images/nullpodmenu.png")));
        }

        startRaidersTurn();
    }

    private void setUpRaiders(Raider[] selectedRaiders) {
        for(int i = 0; i < 3; i++) {
            if(selectedRaiders[i] != null) {
                raiderNameLabels[i].setText(selectedRaiders[i].getName());
                raiderLevelLabels[i].setText("LVL " + selectedRaiders[i].getLevel());
                raiderHpLabels[i].setText(selectedRaiders[i].getHp() + "/" + selectedRaiders[i].getMaxHp());
                raiderSpriteImgs[i].setImage(selectedRaiders[i].getSprite());
                attackLabels[i].setText("0");
                raiderHpBars[i].setProgress((double) selectedRaiders[i].getHp() / selectedRaiders[i].getMaxHp());
                miniRaiderSpriteImgs[i].setImage(selectedRaiders[i].getSprite());
            } else {
                raiderNameLabels[i].setText("");
                raiderLevelLabels[i].setText("");
                raiderHpLabels[i].setText("");
                hpLabels[i].setVisible(false);
                raiderSpriteImgs[i].setImage(null);
                raiderHpBars[i].setVisible(false);
                miniRaiderSpriteImgs[i].setImage(null);
                raiderRectangles[i].setVisible(false);
                attackLabels[i].setText("X");
                attackLabels[i].setStyle("-fx-text-fill: FF0000FF");
                stopBtns[i].setDisable(true);
            }
        }
    }

    private void setUpRaidBoss(RaidBoss raidBoss) {
        this.raidBoss = raidBoss;
        raidBossNameLabel.setText(raidBoss.getName());
        raidBossRarityLabel.setText("" + raidBoss.getRarity());
        raidBossHpLabel.setText(raidBoss.getHp() + "/" + raidBoss.getMaxHp());
        raidBossSpriteImg.setImage(raidBoss.getSprite());
        raidBossHpBar.setProgress((double) raidBoss.getHp() / raidBoss.getMaxHp());
    }

    private void startRaidersTurn() {
        resetRaidersTurn();
        appendBattleLog("---------------- Raiders Turn ----------------");
        turnDeterminerLabel.setText("RAIDERS TURN");
        turnCountLabel.setText("TURN " + this.turnCount);

        for(int i = 0; i < 3; i++) {

            if(selectedRaiders[i] != null) {
                Raider selected = selectedRaiders[i];
                Label selectedAttack = attackLabels[i];
                Button selectedBtn = stopBtns[i];
                Label selectedMax = maxLabels[i];

                selectedBtn.setDisable(false);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(70), e -> {
                            int value = random.nextInt(selected.getAttackMin(), selected.getAttackMax() + 1);
                            selectedAttack.setText(String.valueOf(value));
                        })
                );

                timeline.setCycleCount(Timeline.INDEFINITE);

                selectedBtn.setOnAction(e -> {
                    timeline.stop();
                    int attack = selected.generateAttack();
                    selectedAttack.setText(String.valueOf(attack));
                    selectedBtn.setDisable(true);
                    if(selected.getAttackMax() == attack) {
                        selectedMax.setVisible(true);
                    }
                    attackRaidBoss(selected, attack);
                    checkIfRaiderTurnDone();
                });

                timeline.play();
            }
        }
    }

    private void attackRaidBoss(Raider selected, int attack) {
        appendBattleLog("[RAIDERS] " + selected.getName() + " dealt " + attack + " damage");
        this.raidBoss.takeDamage(attack);
        updateHp();

    }

    private void resetRaidersTurn() {
        for(int i = 0; i < 3; i++) {
            maxLabels[i].setVisible(false);
        }
        updateDeadRaiders();
    }

    private void updateDeadRaiders() {
        for(int i = 0; i < 3; i++) {
            if(selectedRaiders[i] == null) {
                stopBtns[i].setDisable(true);
                attackLabels[i].setText("X");
                attackLabels[i].setStyle("-fx-text-fill: FF0000FF");
            }
        }
    }

    private void checkIfRaiderTurnDone() {
        if(stopBtn.isDisabled() && stopBtn1.isDisabled() && stopBtn2.isDisabled()) {
            this.turnCount++;
            startRaidBossTurn();
            checkWin();
        }
    }

    private void startRaidBossTurn() {
        Raider targetRaider = selectTarget();
        this.raidBossRectangle.setVisible(true);
        this.raidBossAttackLabel.setVisible(true);
        appendBattleLog("--------------- Raid Boss Turn ---------------");
        appendBattleLog("[RAIDBOSS] " + raidBoss.getName() + " has targeted Raider " + targetRaider.getName());
        turnDeterminerLabel.setText("RAID BOSS TURN");
        turnCountLabel.setText("TURN " + this.turnCount);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(30), e -> {
                    int value = random.nextInt(raidBoss.getAttackMin(), raidBoss.getAttackMax() + 1);
                    raidBossAttackLabel.setText(String.valueOf(value));
                })
        );

        timeline.setCycleCount(100);
        timeline.setOnFinished(e -> {
            int attack = raidBoss.generateAttack();
            raidBossAttackLabel.setText(String.valueOf(attack));
            attackRaider(targetRaider, attack);
            checkWin();
            startRaidersTurn();
            turnCount++;
        });

        timeline.play();
    }

    private Raider selectTarget() {
        ArrayList<Raider> aliveRaiders = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            if(selectedRaiders[i] != null) {
                aliveRaiders.add(selectedRaiders[i]);
            }
        }
        return aliveRaiders.get(random.nextInt(aliveRaiders.size()));
    }

    private void attackRaider(Raider target, int attack) {
        appendBattleLog("[RAIDBOSS] " + raidBoss.getName() + " dealt " + attack + " damage to Raider " + target.getName());
        target.takeDamage(attack);
        updateHp();
        if(target.getHp() == 0) {
            setDead(target);
        }
    }

    private void setDead(Raider raider) {
        for(int i = 0; i < selectedRaiders.length; i++) {
            if(selectedRaiders[i] != null) {
                if (selectedRaiders[i].equals(raider)) {
                    selectedRaiders[i].resetHp();
                    selectedRaiders[i] = null;
                }
            }
        }
    }

    private void updateHp() {
        for (int i = 0; i < selectedRaiders.length; i++) {
            Raider raider = selectedRaiders[i];
            if (raider != null) {
                double oldValue = raiderHpBars[i].getProgress();
                double newValue = (double) raider.getHp() / raider.getMaxHp();

                animateHpBar(raiderHpBars[i], oldValue, newValue);

                raiderHpLabels[i].setText(raider.getHp() + " / " + raider.getMaxHp());
            }
        }

        double oldBossValue = raidBossHpBar.getProgress();
        double newBossValue = (double) raidBoss.getHp() / raidBoss.getMaxHp();

        animateHpBar(raidBossHpBar, oldBossValue, newBossValue);
        raidBossHpLabel.setText(raidBoss.getHp() + " / " + raidBoss.getMaxHp());
    }

    private void checkWin() {
        int deadRaiders = 0;
        for(int i = 0; i < 3; i++) {
            if(selectedRaiders[i] == null) {
                deadRaiders++;
            }
        }

        if(deadRaiders == 3) {
            System.out.println("RAID BOSS WINS");
        } else if(this.raidBoss.getHp() == 0) {
            System.out.println("RAIDERS WINS");
        } else {
            return;
        }
    }

    private void animateHpBar(ProgressBar bar, double oldValue, double newValue) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(bar.progressProperty(), oldValue)),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(bar.progressProperty(), newValue))
        );
        timeline.play();
    }


    private void appendBattleLog(String string) {
        Platform.runLater(() -> {
            battleLogTextArea.appendText(string + "\n");
            battleLogTextArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        raiderNameLabels = new Label[] {
                raiderNameLabel, raiderNameLabel1, raiderNameLabel2
        };
        raiderLevelLabels = new Label[] {
                raiderLevelLabel, raiderLevelLabel1, raiderLevelLabel2
        };
        raiderHpLabels = new Label[] {
                raiderHpLabel, raiderHpLabel1, raiderHpLabel2
        };
        attackLabels = new Label[] {
                attackLabel, attackLabel1, attackLabel2
        };
        maxLabels = new Label[] {
                maxLabel, maxLabel1, maxLabel2
        };
        hpLabels = new Label[] {
               hpLabel, hpLabel1, hpLabel2
        };
        raiderHpBars = new ProgressBar[] {
                raiderHpBar, raiderHpBar1, raiderHpBar2
        };
        stopBtns = new Button[] {
                stopBtn, stopBtn1, stopBtn2
        };
        raiderRectangles = new Rectangle[] {
                raiderRectangle, raiderRectangle1, raiderRectangle2
        };
        raiderSpriteImgs = new ImageView[] {
                raiderSpriteImg, raiderSpriteImg1, raiderSpriteImg2
        };
        miniRaiderSpriteImgs = new ImageView[] {
                miniRaiderSpriteImg, miniRaiderSpriteImg1, miniRaiderSpriteImg2
        };
        battleLogTextArea.setWrapText(true);
        battleLogTextArea.setEditable(false);
    }
}
