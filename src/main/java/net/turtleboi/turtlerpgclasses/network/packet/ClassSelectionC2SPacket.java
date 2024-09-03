package net.turtleboi.turtlerpgclasses.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.capabilities.PlayerClassProvider;
import net.turtleboi.turtlerpgclasses.capabilities.resources.PlayerResourceProvider;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;
import net.turtleboi.turtlerpgclasses.rpg.attributes.ClassAttributeManager;

import java.util.Objects;
import java.util.function.Supplier;

import static net.turtleboi.turtlerpgclasses.rpg.attributes.ClassAttributeManager.applyClassAttributes;

public class ClassSelectionC2SPacket {
    private final String playerClassName;
    private final String playerSubclassName;

    private String getPlayerClassName() {
        if (playerClassName != null) {
            return playerClassName;
        } else {
            return "No Class";
        }
    }

    public String getPlayerSubclassName() {
        if (playerSubclassName != null) {
            return playerSubclassName;
        } else {
            return "No Class";
        }
    }

    public ClassSelectionC2SPacket(String className, String subclassName) {
        playerClassName = className;
        playerSubclassName = subclassName;
    }

    public ClassSelectionC2SPacket(FriendlyByteBuf buf) {
        playerClassName = buf.readUtf(32767);
        playerSubclassName = buf.readUtf(32767);
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(getPlayerClassName());
        buf.writeUtf(getPlayerSubclassName());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // We're on the server :)
            ServerPlayer player = context.getSender();
            assert player != null;
            player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerClass -> {
                ClassAttributeManager.resetAttributes(player);
                playerClass.setRpgClass(playerClassName);
                playerClass.setRpgSubclass(playerSubclassName);
                ClassAttributeManager.applyClassAttributes(player);
                ModNetworking.sendToPlayer(new ClassSelectionS2CPacket(playerClass.getRpgClass(), playerClass.getRpgSubclass()), player);
            });
            player.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(playerResource -> {
                String warrior = Component.translatable("class.warrior.name").getString();
                String ranger = Component.translatable("class.ranger.name").getString();
                String mage = Component.translatable("class.mage.name").getString();
                if (warrior.equals(playerClassName)){
                    playerResource.setStamina(playerResource.getMaxStamina());
                    Objects.requireNonNull(player.getAttribute(ModAttributes.STAMINA_RECHARGE.get())).setBaseValue(0.1D);
                } else if (ranger.equals(playerClassName)){
                    playerResource.setEnergy(playerResource.getMaxEnergy());
                    Objects.requireNonNull(player.getAttribute(ModAttributes.ENERGY_RECHARGE.get())).setBaseValue(4.0D);
                } else if (mage.equals(playerClassName)){
                    playerResource.setMana(playerResource.getMaxMana());
                    Objects.requireNonNull(player.getAttribute(ModAttributes.MANA_RECHARGE.get())).setBaseValue(0.33D);
                }
            });
        });
        return true;
    }
}
