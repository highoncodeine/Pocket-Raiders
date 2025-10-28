package com.pocketraiders.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static final Map<String, MediaPlayer> audios = new HashMap<>();
    private static MediaPlayer backgroundMusic;

    static {
        load("click", "/audio/button.wav");
        load("raider-max", "/audio/raider-max.wav");
        load("raidboss-max" ,"/audio/raidboss-max.wav");
        load("raider-death", "/audio/raider-death.mp3");
        load("rare-reveal", "/audio/rare-reveal.wav");
        load("common-reveal", "/audio/common-reveal.wav");
        load("raider-death", "/audio/raider-death.mp3");
    }

    private static void load(String pathName, String path) {
        try {
            String mediaPath = AudioManager.class.getResource(path).toExternalForm();
            Media media = new Media(mediaPath);
            audios.put(pathName, new MediaPlayer(media));
        } catch (Exception e) {
            System.err.println("Failed to load sound");
        }
    }

    public static void playMainBgMusic() {
        stopBackgroundMusic();
        try {
            String mediaPath = AudioManager.class.getResource("/audio/main-menu.wav").toExternalForm();
            Media media = new Media(mediaPath);
            backgroundMusic = new MediaPlayer(media);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.setVolume(0.5);
            backgroundMusic.play();
        } catch (Exception e) {
            System.err.println("Failed to load sound");
        }
    }

    public static void playNovaBgMusic() {
        stopBackgroundMusic();
        try {
            String mediaPath = AudioManager.class.getResource("/audio/nova-raidboss.wav").toExternalForm();
            Media media = new Media(mediaPath);
            backgroundMusic = new MediaPlayer(media);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.setVolume(0.5);
            backgroundMusic.play();
        } catch (Exception e) {
            System.err.println("Failed to load sound");
        }
    }

    public static void playNullBgMusic() {
        stopBackgroundMusic();
        try {
            String mediaPath = AudioManager.class.getResource("/audio/null-raidboss.wav").toExternalForm();
            Media media = new Media(mediaPath);
            backgroundMusic = new MediaPlayer(media);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.setVolume(0.5);
            backgroundMusic.play();
        } catch (Exception e) {
            System.err.println("Failed to load sound");
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
            backgroundMusic = null;
        }
    }

    public static void play(String name) {
        MediaPlayer audio = audios.get(name);
        if (audio != null) {
            audio.stop(); // restart if already playing
            if(name == "common-reveal") {
                audio.setVolume(0.2);
                audio.play();
            } else {
                audio.play();
            }
        }
    }
}
