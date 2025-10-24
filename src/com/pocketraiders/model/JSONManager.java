package com.pocketraiders.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JSONManager {
    private Player player;
    private JSONObject object;
    private static final Path SAVE_DIR = Paths.get("saves");

    public JSONManager(Player player) {
        this.player = player;
        this.object = new JSONObject();
    }

    public JSONManager() {
        this.object = new JSONObject();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void createObject() {
        object = new JSONObject();

        object.put("id", player.getId());
        object.put("username", player.getUsername());
        object.put("password", player.getPassword());
        object.put("level", player.getLevel());
        object.put("xp", player.getXp());
        object.put("xpToNextLevel", player.getXpToNextLevel());
        object.put("lumens", player.getLumens());
        object.put("currentRaidBossIndex", player.getCurrentRaidBossIndex());

        if (player.getCurrentRaidBoss() != null) {
            JSONObject raidBossObj = new JSONObject();
            RaidBoss raidBoss = player.getCurrentRaidBoss();

            raidBossObj.put("id", raidBoss.getId());
            raidBossObj.put("name", raidBoss.getName());
            raidBossObj.put("rarity", raidBoss.getRarity());
            raidBossObj.put("pod", raidBoss.getPod());
            raidBossObj.put("sprite", raidBoss.getSpritePath());
            raidBossObj.put("hp", raidBoss.getHp());
            raidBossObj.put("maxHp", raidBoss.getMaxHp());
            raidBossObj.put("attackMin", raidBoss.getAttackMin());
            raidBossObj.put("attackMax", raidBoss.getAttackMax());
            raidBossObj.put("lumenReward", raidBoss.getLumenReward());
            raidBossObj.put("xpReward", raidBoss.getXpReward());
            raidBossObj.put("playerXpReward", raidBoss.getPlayerXpReward());
            raidBossObj.put("copiesReward", raidBoss.getCopiesReward());
            raidBossObj.put("copy", raidBoss.getSpritePath());
            raidBossObj.put("lumenPity", raidBoss.getLumenPity());

            object.put("raidBoss", raidBossObj);
        } else {
            object.put("raidBoss", JSONObject.NULL);
        }

        JSONArray ownedRaidersArr = new JSONArray();
        ArrayList<Raider> ownedRaiders = player.getOwnedRaiders();
        if (ownedRaiders != null) {
            for (Raider raider : ownedRaiders) {
                if (raider == null) continue;

                JSONObject raiderObj = new JSONObject();
                raiderObj.put("id", raider.getId());
                raiderObj.put("name", raider.getName());
                raiderObj.put("rarity", raider.getRarity());
                raiderObj.put("pod", raider.getPod());
                raiderObj.put("sprite", raider.getSpritePath());
                raiderObj.put("copies", raider.getCopies());
                raiderObj.put("level", raider.getLevel());
                raiderObj.put("xp", raider.getXp());
                raiderObj.put("xpToNextLevel", raider.getXpToNextLevel());
                raiderObj.put("hp", raider.getHp());
                raiderObj.put("maxHp", raider.getMaxHp());
                raiderObj.put("attackMin", raider.getAttackMin());
                raiderObj.put("attackMax", raider.getAttackMax());

                ownedRaidersArr.put(raiderObj);
            }
        }
        object.put("ownedRaiders", ownedRaidersArr);

        JSONArray favoriteRaiderArr = new JSONArray();
        Raider[] favoriteRaiders = player.getFavoriteRaiders();
        if (favoriteRaiders != null) {
            for(Raider raider: favoriteRaiders) {
                if(raider == null) continue;

                JSONObject raiderObj = new JSONObject();
                raiderObj.put("id", raider.getId());
                raiderObj.put("name", raider.getName());
                raiderObj.put("rarity", raider.getRarity());
                raiderObj.put("pod", raider.getPod());
                raiderObj.put("sprite", raider.getSpritePath());
                raiderObj.put("copies", raider.getCopies());
                raiderObj.put("level", raider.getLevel());
                raiderObj.put("xp", raider.getXp());
                raiderObj.put("xpToNextLevel", raider.getXpToNextLevel());
                raiderObj.put("hp", raider.getHp());
                raiderObj.put("maxHp", raider.getMaxHp());
                raiderObj.put("attackMin", raider.getAttackMin());
                raiderObj.put("attackMax", raider.getAttackMax());

                favoriteRaiderArr.put(raiderObj);
            }
        }
        object.put("favoriteRaiders", favoriteRaiderArr);
    }

    public Player loadPlayer(String fileName) {
        try {
            String file = (fileName == null || fileName.isEmpty()) ? "player.json" : fileName;
            Path savePath = Paths.get("saves", file);

            Files.createDirectories(savePath.getParent());

            String content = Files.readString(savePath, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(content);

            int id = jsonObject.getInt("id");
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            int level = jsonObject.getInt("level");
            int xp = jsonObject.getInt("xp");
            int xpToNextLevel = jsonObject.getInt("xpToNextLevel");
            int lumens = jsonObject.getInt("lumens");
            int currentRaidBossIndex = jsonObject.getInt("currentRaidBossIndex");

            RaidBoss raidBoss = null;
            if (!jsonObject.isNull("raidBoss")) {
                JSONObject raidBossObj = jsonObject.getJSONObject("raidBoss");

                int bossId = raidBossObj.getInt("id");
                String bossName = raidBossObj.getString("name");
                Rarity rarity = Rarity.valueOf(raidBossObj.getString("rarity").toUpperCase());
                String pod = raidBossObj.getString("pod");
                String spritePath = raidBossObj.getString("sprite");
                int bossHp = raidBossObj.getInt("hp");
                int bossMaxHp = raidBossObj.getInt("maxHp");
                int attackMin = raidBossObj.getInt("attackMin");
                int attackMax = raidBossObj.getInt("attackMax");
                int lumenReward = raidBossObj.getInt("lumenReward");
                int xpReward = raidBossObj.getInt("xpReward");
                int playerXpReward = raidBossObj.getInt("playerXpReward");
                int copiesReward = raidBossObj.getInt("copiesReward");
                String copy = raidBossObj.optString("copy", spritePath); // fallback
                int lumenPity = raidBossObj.getInt("lumenPity");

                raidBoss = new RaidBoss(
                        bossId, bossName, rarity, pod, spritePath,
                        bossHp, bossMaxHp, attackMin, attackMax,
                        lumenReward, xpReward, playerXpReward,
                        copiesReward, copy, lumenPity
                );
            }

            ArrayList<Raider> ownedRaiders = new ArrayList<>();
            JSONArray ownedRaidersArr = jsonObject.optJSONArray("ownedRaiders");
            if (ownedRaidersArr != null) {
                for (int i = 0; i < ownedRaidersArr.length(); i++) {
                    if (ownedRaidersArr.isNull(i)) continue;

                    JSONObject raiderObj = ownedRaidersArr.getJSONObject(i);

                    int raiderId = raiderObj.getInt("id");
                    String raiderName = raiderObj.getString("name");
                    Rarity raiderRarity = Rarity.valueOf(raiderObj.getString("rarity").toUpperCase());
                    String raiderPod = raiderObj.getString("pod");
                    String raiderSprite = raiderObj.getString("sprite");
                    int raiderCopies = raiderObj.getInt("copies");
                    int raiderLevel = raiderObj.getInt("level");
                    int raiderXp = raiderObj.getInt("xp");
                    int raiderXpToNextLevel = raiderObj.getInt("xpToNextLevel");
                    int raiderHp = raiderObj.getInt("hp");
                    int raiderMaxHp = raiderObj.getInt("maxHp");
                    int raiderAttackMin = raiderObj.getInt("attackMin");
                    int raiderAttackMax = raiderObj.getInt("attackMax");

                    Raider raider = new Raider(
                            raiderId, raiderName, raiderRarity, raiderPod, raiderSprite,
                            raiderCopies, raiderLevel, raiderXp, raiderXpToNextLevel,
                            raiderHp, raiderMaxHp, raiderAttackMin, raiderAttackMax
                    );

                    ownedRaiders.add(raider);
                }
            }

            Raider[] favoriteRaiders = new Raider[3];
            JSONArray favoriteRaidersArr = jsonObject.optJSONArray("favoriteRaiders");
            if(favoriteRaiders != null) {
                for(int i = 0; i < 3; i++) {
                    if(favoriteRaidersArr.isNull(i)) continue;

                    JSONObject raiderObj = favoriteRaidersArr.getJSONObject(i);

                    int raiderId = raiderObj.getInt("id");
                    String raiderName = raiderObj.getString("name");
                    Rarity raiderRarity = Rarity.valueOf(raiderObj.getString("rarity").toUpperCase());
                    String raiderPod = raiderObj.getString("pod");
                    String raiderSprite = raiderObj.getString("sprite");
                    int raiderCopies = raiderObj.getInt("copies");
                    int raiderLevel = raiderObj.getInt("level");
                    int raiderXp = raiderObj.getInt("xp");
                    int raiderXpToNextLevel = raiderObj.getInt("xpToNextLevel");
                    int raiderHp = raiderObj.getInt("hp");
                    int raiderMaxHp = raiderObj.getInt("maxHp");
                    int raiderAttackMin = raiderObj.getInt("attackMin");
                    int raiderAttackMax = raiderObj.getInt("attackMax");

                    Raider raider = new Raider(
                            raiderId, raiderName, raiderRarity, raiderPod, raiderSprite,
                            raiderCopies, raiderLevel, raiderXp, raiderXpToNextLevel,
                            raiderHp, raiderMaxHp, raiderAttackMin, raiderAttackMax
                    );

                    favoriteRaiders[i] = raider;
                }
            }

            return new Player(
                    id, username, password, level, xp, xpToNextLevel, lumens,
                    currentRaidBossIndex, raidBoss, ownedRaiders, favoriteRaiders
            );

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void print() {
        try {
            Files.createDirectories(SAVE_DIR);

            Path savePath = SAVE_DIR.resolve(player.getUsername() + ".json");

            try (FileWriter file = new FileWriter(savePath.toFile())) {
                file.write(object.toString(4));
            }

            System.out.println("Saved player data to: " + savePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        createObject();
        print();
    }

    public void update(String oldUsername) {
        if (oldUsername != null && !oldUsername.equals(player.getUsername())) {
            deleteJsonFile(oldUsername);
        }
        save();
    }

    public static boolean deleteJsonFile(String username) {
        File file = new File("saves/" + username + ".json");
        return file.exists() && file.delete();
    }

    public Player[] loadAllPlayers() {
        Player[] players = new Player[3];
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(SAVE_DIR, "*.json")) {
            int index = 0;
            for (Path path : stream) {
                if (index >= 3) break;
                Player p = loadPlayer(path.getFileName().toString());
                if (p != null) players[index++] = p;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }
}
