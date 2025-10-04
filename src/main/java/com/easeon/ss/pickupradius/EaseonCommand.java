package com.easeon.ss.pickupradius;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class EaseonCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("easeon")
            .requires(source -> source.hasPermissionLevel(Easeon.CONFIG.getRequiredOpLevel()))
            .then(CommandManager.literal("pickup-radius")
                // 조회
                .executes(ctx -> {
                    int radius = Easeon.CONFIG.getPickupRadius();
                    ctx.getSource().sendFeedback(
                        () -> Text.literal("Easeon ")
                            .formatted(Formatting.YELLOW)
                            .append(Text.literal("Current pickup radius: ")
                                    .formatted(Formatting.WHITE))
                            .append(Text.literal(String.valueOf(radius))
                                    .formatted(Formatting.YELLOW)),
                        false
                    );
                    return 1;
                })
                // 설정
                .then(CommandManager.argument("value", IntegerArgumentType.integer(1, 15))
                    .executes(ctx -> {
                        int oldValue = Easeon.CONFIG.getPickupRadius();
                        int newValue = IntegerArgumentType.getInteger(ctx, "value");
                        Easeon.CONFIG.setPickupRadius(newValue);
                        ctx.getSource().sendFeedback(
                            () -> Text.literal("Easeon ")
                                .formatted(Formatting.YELLOW)
                                .append(Text.literal("Pickup radius changed: ")
                                        .formatted(Formatting.WHITE))
                                .append(Text.literal(String.valueOf(oldValue))
                                        .formatted(Formatting.YELLOW))
                                .append(Text.literal(" → ")
                                        .formatted(Formatting.WHITE))
                                .append(Text.literal(String.valueOf(newValue))
                                        .formatted(Formatting.YELLOW)),
                            true
                        );
                        return 1;
                        })
                )
            )
        );
    }
}