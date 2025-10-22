package com.easeon.ss.pickupradius;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import static com.easeon.ss.core.constants.Colors.*;
import static com.easeon.ss.core.util.interaction.EaseonMessenger.*;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class Command {
    private static int opLevel;
    private static String name;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        opLevel = Easeon.config.requiredOpLevel;
        name = Easeon.info.name
                .replaceAll(".*- ", "")
                .replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase();

        dispatcher.register(literal("easeon")
            .requires(source -> source.hasPermissionLevel(opLevel))
            .then(literal(name.replaceAll(" ", ""))
                .executes(Command::sendStatus)
                .then(argument("value", IntegerArgumentType.integer(1, 15))
                        .executes(Command::setRadius)
                )
            )
        );
    }

    private static int sendStatus(CommandContext<ServerCommandSource> ctx) {
        int radius = Easeon.config.pickupRadius;
        to(ctx.getSource(), String.format("%scurrent pickup radius: %s%d", PREFIX, YELLOW, radius));
        return 1;
    }

    private static int setRadius(CommandContext<ServerCommandSource> ctx) {
        int oldValue = Easeon.config.pickupRadius;
        int newValue = IntegerArgumentType.getInteger(ctx, "value");

        if (oldValue == newValue) {
            to(ctx.getSource(), String.format("%s%s%s is already %d", PREFIX, YELLOW, name, newValue));
        } else {
            Easeon.config.setPickupRadius(newValue);
            toAll(ctx.getSource(), String.format("%s%s changed: %s%d%s → %s%d", PREFIX, name, YELLOW, oldValue, WHITE, GREEN, newValue));
        }
        return 1;
    }
}
