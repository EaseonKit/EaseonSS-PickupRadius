package com.easeon.ss.pickupradius;

import com.easeon.ss.core.wrapper.EaseonPlayer;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;

public class EaseonServerTickHandler {
    public static void onServerTick(MinecraftServer server, int radius) {
        for (var player : server.getPlayerManager().getPlayerList()) {
            handlePlayerPickup(new EaseonPlayer(player), radius);
        }
    }

    private static void handlePlayerPickup(EaseonPlayer player, int radius) {
        var world = player.easeonWorld();
        var range = new Box(
            player.getX() - radius, player.getY() - radius, player.getZ() - radius,
            player.getX() + radius, player.getY() + radius, player.getZ() + radius
        );

        for (var item : world.getEntitiesByClass(ItemEntity.class, range, entity -> !entity.cannotPickup())) {
            if (player.squaredDistanceTo(item) <= radius * radius) {
                item.onPlayerCollision(player.get());
            }
        }

        for (var xpOrb : world.getEntitiesByClass(ExperienceOrbEntity.class, range, entity -> true)) {
            if (player.squaredDistanceTo(xpOrb) <= radius * radius) {
                xpOrb.onPlayerCollision(player.get());
            }
        }
    }

}
