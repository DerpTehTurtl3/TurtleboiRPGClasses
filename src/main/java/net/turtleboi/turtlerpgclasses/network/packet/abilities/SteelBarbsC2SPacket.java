package net.turtleboi.turtlerpgclasses.network.packet.abilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.capabilities.resources.PlayerResourceProvider;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;
import net.turtleboi.turtlerpgclasses.network.packet.resources.PlayerResourcesS2CPacket;
import net.turtleboi.turtlerpgclasses.rpg.talents.PathOfTheJuggernautSubclass;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.ActiveAbility;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.SteelBarbsTalent;

import java.util.function.Supplier;

public class SteelBarbsC2SPacket {

    public SteelBarbsC2SPacket(){
    }

    public SteelBarbsC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // We're on the server :)
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            if (player == null) return;
            player.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(playerResource -> {
                ModNetworking.sendToPlayer(
                        new PlayerResourcesS2CPacket(
                                playerResource.getStamina(),
                                playerResource.getMaxStamina(),
                                playerResource.getEnergy(),
                                playerResource.getMaxEnergy(),
                                playerResource.getMana(),
                                playerResource.getMaxMana()), player);
                String playerClass = ClientClassData.getPlayerClass();
                if ("Warrior".equals(playerClass)) {
                    ActiveAbility ability = null;
                    PathOfTheJuggernautSubclass pathOfTheJuggernautSubclass = new PathOfTheJuggernautSubclass();
                    if (pathOfTheJuggernautSubclass.isActive(player)) {
                        ability = new SteelBarbsTalent();
                    }
                    if (ability != null) {
                        ability.useAbility(player, playerResource);
                    } else if (pathOfTheJuggernautSubclass.isActive(player)) {
                        player.sendSystemMessage(Component.translatable("not_bound"));
                    }
                }
            });
        });
        return true;
    }
}
