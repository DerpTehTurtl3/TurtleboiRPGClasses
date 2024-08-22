package net.turtleboi.turtlerpgclasses.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.turtleboi.turtlerpgclasses.capabilities.PlayerClassProvider;
import net.turtleboi.turtlerpgclasses.capabilities.talents.TalentStates;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.client.ui.cooldowns.CooldownOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.resources.ResourceOverlay;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;
import net.turtleboi.turtlerpgclasses.network.packet.ClassSelectionS2CPacket;
import net.turtleboi.turtlerpgclasses.rpg.classes.Mage;
import net.turtleboi.turtlerpgclasses.rpg.classes.Ranger;
import net.turtleboi.turtlerpgclasses.rpg.classes.Warrior;

import static net.turtleboi.turtlerpgclasses.rpg.attributes.ClassAttributeManager.applyClassAttributes;

public class ResetTalentsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tbrpg")
                .then(Commands.literal("resetTalents")
                        .executes(ResetTalentsCommand::resetTalents)));
    }

    private static int resetTalents(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();

        // Reset all talent points
        TalentStates.resetAllTalents(player);
        TalentStates.clearPoints();
        player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerClass -> {
            String className = playerClass.getRpgClass();
            switch (className) {
                case "Warrior":
                    new Warrior().setActive(player);
                    break;
                case "Ranger":
                    new Ranger().setActive(player);
                    break;
                case "Mage":
                    new Mage().setActive(player);
                    break;
            }
        });
        CooldownOverlay.initializeSlots(player);
        ResourceOverlay.initializeResourceBars();

        player.sendSystemMessage(Component.literal("Your talents have been reset"));
        return 1;
    }
}
