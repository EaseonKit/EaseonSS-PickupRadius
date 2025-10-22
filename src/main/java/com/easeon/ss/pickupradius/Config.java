package com.easeon.ss.pickupradius;

import com.easeon.ss.core.api.common.EaseonConfig;

public class Config extends EaseonConfig {
    public static final String CONFIG_PATH = String.format("config/easeon/%s.json", Easeon.info.configName);

    public int pickupRadius = 5;
    public int requiredOpLevel = 2;

    public static Config getInstance() {
        return EaseonConfig.getInstance(Config.class);
    }

    public void setPickupRadius(int radius) {
        this.pickupRadius = radius;
        this.save();
    }
}