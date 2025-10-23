package com.easeon.ss.pickupradius;

import com.easeon.ss.core.util.interaction.EaseonMessageFormat;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import static com.easeon.ss.core.constants.Colors.*;
import static com.easeon.ss.core.util.interaction.EaseonMessenger.*;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class Command {
    private static String displayName;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        displayName = Easeon.info.displayName;

        dispatcher.register(literal("easeon")
            .requires(source -> source.hasPermissionLevel(Easeon.config.requiredOpLevel))
            .then(literal(Easeon.info.commandName)
                .executes(ctx -> to(ctx.getSource(), EaseonMessageFormat.valueStatus(displayName, Easeon.config.pickupRadius)))
                .then(argument("value", IntegerArgumentType.integer(1, 20)).executes(Command::setRadius))
            )
        );
    }

    private static int setRadius(CommandContext<ServerCommandSource> ctx) {
        int oldValue = Easeon.config.pickupRadius;
        int newValue = IntegerArgumentType.getInteger(ctx, "value");

        if (oldValue == newValue) {
            to(ctx.getSource(), EaseonMessageFormat.valueAlready(displayName, newValue));
        } else {
            Easeon.config.setPickupRadius(newValue);
            Easeon.updatePickupRadiusTask();
            toAll(ctx.getSource(), EaseonMessageFormat.valueChanged(displayName, oldValue, newValue));
        }
        return 1;
    }
}