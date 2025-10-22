package com.easeon.ss.pickupradius;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;

public class PickupRadiusHandler {
    public static void onServerTick(MinecraftServer server) {
        var radius = Easeon.config.pickupRadius;

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            var world = player.getEntityWorld();
            var range = new Box(
                player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius
            );

            for (ItemEntity item : world.getEntitiesByClass(ItemEntity.class, range, entity -> !entity.cannotPickup())) {
                if (player.squaredDistanceTo(item) <= radius * radius) {
                    item.onPlayerCollision(player);
                }
            }

            // 경험치 오브 처리
            for (ExperienceOrbEntity xpOrb : world.getEntitiesByClass(ExperienceOrbEntity.class, range, entity -> true)) {
                if (player.squaredDistanceTo(xpOrb) <= radius * radius) {
                    xpOrb.onPlayerCollision(player);
                }
            }
        }
    }
}
