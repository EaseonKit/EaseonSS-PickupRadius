package com.easeon.ss.pickupradius;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = new File("config/easeon");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "easeon.ss.pickupradius.json");

    private int pickupRadius = 5;
    private int requiredOpLevel = 2;

    public void load() {
        if (!CONFIG_FILE.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            ConfigData data = GSON.fromJson(reader, ConfigData.class);
            if (data != null) {
                this.pickupRadius = data.pickupRadius;
                this.requiredOpLevel = data.requiredOpLevel;
            }
            Easeon.LOGGER.info("Config loaded: pickup radius = {}, required OP level = {}", pickupRadius, requiredOpLevel);
        } catch (IOException e) {
            Easeon.LOGGER.error("Failed to load config", e);
        }
    }

    public void save() {
        try {
            if (!CONFIG_DIR.exists()) {
                CONFIG_DIR.mkdirs();
            }

            ConfigData data = new ConfigData();
            data.pickupRadius = this.pickupRadius;
            data.requiredOpLevel = this.requiredOpLevel;

            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(data, writer);
            }

            Easeon.LOGGER.info("Config saved: pickup radius = {}, required OP level = {}", pickupRadius, requiredOpLevel);
        } catch (IOException e) {
            Easeon.LOGGER.error("Failed to save config", e);
        }
    }

    public int getPickupRadius() {
        return pickupRadius;
    }

    public void setPickupRadius(int radius) {
        this.pickupRadius = radius;
        save();
    }

    public int getRequiredOpLevel() {
        return requiredOpLevel;
    }

    private static class ConfigData {
        public int pickupRadius = 5;
        public int requiredOpLevel = 2;
    }
}