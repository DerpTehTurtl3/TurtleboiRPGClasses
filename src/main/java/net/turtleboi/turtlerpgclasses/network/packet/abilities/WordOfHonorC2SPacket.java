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
import net.turtleboi.turtlerpgclasses.rpg.talents.active.ActiveAbility;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.GuardiansOathTalent;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.WarlordsPresenceTalent;

import java.util.function.Supplier;

public class WordOfHonorC2SPacket {

    public WordOfHonorC2SPacket(){
    }

    public WordOfHonorC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // We're on the server :)
            ServerPlayer player = context.getSender();
            assert player != null;
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
                    GuardiansOathTalent guardiansOathTalent = new GuardiansOathTalent();
                    if (guardiansOathTalent.isActive(player)) {
                        ability = new GuardiansOathTalent();
                    }
                    if (ability != null) {
                        ability.useAbility(player, playerResource);
                    } else if (guardiansOathTalent.isActive(player)) {
                        player.sendSystemMessage(Component.translatable("not_bound"));
                    }
                }
            });
        });
        return true;
    }
}