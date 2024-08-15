package net.turtleboi.turtlerpgclasses.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.turtleboi.turtlerpgclasses.capabilities.talents.TalentStates;

public class SetTalentPointsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tbrpg")
                .then(Commands.literal("setTalentPoints")
                        .then(Commands.argument("points", IntegerArgumentType.integer(0))
                                .executes(SetTalentPointsCommand::setTalentPoints))));
    }

    private static int setTalentPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        int points = IntegerArgumentType.getInteger(context, "points");

        // Set the talent points to the specified value
        TalentStates.setPurchasedTalentPoints(player, points);

        player.sendSystemMessage(Component.literal("Your talent points have been set to " + points + "."));
        return 1;
    }
}
