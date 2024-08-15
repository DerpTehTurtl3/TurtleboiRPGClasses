package net.turtleboi.turtlerpgclasses.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.capabilities.PlayerClassProvider;
import net.turtleboi.turtlerpgclasses.capabilities.resources.PlayerResourceProvider;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;

import java.util.function.Supplier;

import static net.turtleboi.turtlerpgclasses.rpg.attributes.ClassAttributeManager.applyClassAttributes;

public class ClassSelectionC2SPacket {
    private final String playerClass;
    private final String playerSubclass;
    public ClassSelectionC2SPacket(String playerClass, String playerSubclass) {
        this.playerClass = playerClass;
        this.playerSubclass = playerSubclass;
    }

    public ClassSelectionC2SPacket(FriendlyByteBuf buf) {
        this.playerClass = buf.readUtf(32767);
        this.playerSubclass = buf.readUtf(32767);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.playerClass);
        buf.writeUtf(this.playerSubclass);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // We're on the server :)
            ServerPlayer player = context.getSender();
            assert player != null;
            player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerClass -> {
                playerClass.setRpgClass(this.playerClass);
                playerClass.setRpgSubclass(this.playerSubclass);
                applyClassAttributes(player, this.playerClass);
                ModNetworking.sendToPlayer(new ClassSelectionS2CPacket(playerClass.getRpgClass(), playerClass.getRpgSubclass()), player);
            });
            player.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(playerResource -> {
                if ("Warrior".equals(playerClass)){
                    playerResource.setStamina(playerResource.getMaxStamina());
                    playerResource.setStaminaRecharge(0.1);
                } else if ("Ranger".equals(playerClass)){
                    playerResource.setEnergy(playerResource.getMaxEnergy());
                    playerResource.setEnergyRecharge(2);
                } else if ("Mage".equals(playerClass)){
                    playerResource.setMana(playerResource.getMaxMana());
                    playerResource.setManaRecharge(0.33);
                }
            });
        });
        return true;
    }

    public String getPlayerClass() {
        return playerClass;
    }

}
