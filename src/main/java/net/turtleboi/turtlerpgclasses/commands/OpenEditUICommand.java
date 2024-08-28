package net.turtleboi.turtlerpgclasses.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.turtleboi.turtlerpgclasses.client.ui.cooldowns.CooldownOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.resources.ResourceOverlay;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;
import net.turtleboi.turtlerpgclasses.network.packet.OpenEditUIScreenPacket;

public class OpenEditUICommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tbrpg")
                .then(Commands.literal("reloadUI")
                        .executes(OpenEditUICommand::openEditUIScreen)));
    }

    private static int openEditUIScreen(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        if (player != null) {
            //ModNetworking.sendToPlayer(new OpenEditUIScreenPacket(), player);
            CooldownOverlay.initializeSlots(player);
            ResourceOverlay.initializeResourceBars(player);
        }
        return 1;
    }
}
