package com.easeon.ss.pickupradius;

import com.easeon.ss.core.api.events.EaseonServerTick.TickTask;
import com.easeon.ss.core.api.events.EaseonServerTick;
import com.easeon.ss.core.api.registry.EaseonCommand;
import com.easeon.ss.core.util.mod.EaseonModInfo;
import com.easeon.ss.core.util.system.EaseonLogger;
import net.fabricmc.api.ModInitializer;

public class Easeon implements ModInitializer {
    private static final EaseonLogger logger = EaseonLogger.get();
    public static final EaseonModInfo info = EaseonModInfo.get(Easeon.class);
    public static final Config config = Config.getInstance();
    private static TickTask tickTask;

    @Override
    public void onInitialize() {
        EaseonCommand.register(Command::register);
        updatePickupRadiusTask();

        logger.info("Initialized!");
    }

    public static void updatePickupRadiusTask() {
        if (config.pickupRadius > 1 && tickTask == null) {
            tickTask = EaseonServerTick.register(PickupRadiusHandler::onServerTick);
            logger.debug("PickupRadius enabled with radius: {}", config.pickupRadius);
        }
        if (config.pickupRadius == 1 && tickTask != null) {
            EaseonServerTick.unregister(tickTask);
            tickTask = null;
            logger.debug("PickupRadius disabled (using default)");
        }
    }
}