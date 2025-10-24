package com.easeon.ss.pickupradius;

import com.easeon.ss.core.api.common.base.BaseValueModule;
import com.easeon.ss.core.api.events.EaseonServerTick;
import com.easeon.ss.core.api.events.EaseonServerTick.TickTask;
import net.fabricmc.api.ModInitializer;

public class Easeon extends BaseValueModule implements ModInitializer {
    private TickTask task;

    public Easeon() {
        super(5, 1, 15);
    }

    @Override
    public void onInitialize() {
        logger.info("Initialized!");
    }

    public void updateTask() {
        if (config.value > 1 && task == null) {
            task = EaseonServerTick.register((server) ->
                EaseonServerTickHandler.onServerTick(server, config.value));
        }
        if (config.value == 1 && task != null) {
            EaseonServerTick.unregister(task);
            task = null;
        }
    }
}