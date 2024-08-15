package net.turtleboi.turtlerpgclasses.network.packet.resources;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.client.ClientResourceData;

import java.util.function.Supplier;

public class PlayerResourcesS2CPacket {
    private final int currentStamina;
    private final int maxStamina;
    private final int currentEnergy;
    private final int maxEnergy;
    private final int currentMana;
    private final int maxMana;

    public PlayerResourcesS2CPacket(int currentStamina, int maxStamina, int currentEnergy, int maxEnergy, int currentMana, int maxMana) {
        this.currentStamina = currentStamina;
        this.maxStamina = maxStamina;
        this.currentEnergy = currentEnergy;
        this.maxEnergy = maxEnergy;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
    }

    public PlayerResourcesS2CPacket(FriendlyByteBuf buf) {
        this.currentStamina = buf.readInt();
        this.maxStamina = buf.readInt();
        this.currentEnergy = buf.readInt();
        this.maxEnergy = buf.readInt();
        this.currentMana = buf.readInt();
        this.maxMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(currentStamina);
        buf.writeInt(maxStamina);
        buf.writeInt(currentEnergy);
        buf.writeInt(maxEnergy);
        buf.writeInt(currentMana);
        buf.writeInt(maxMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT :)
            ClientResourceData.setStamina(currentStamina);
            ClientResourceData.setMaxStamina(maxStamina);
            ClientResourceData.setEnergy(currentEnergy);
            ClientResourceData.setMaxEnergy(maxEnergy);
            ClientResourceData.setMana(currentMana);
            ClientResourceData.setMaxMana(maxMana);
        });
        return true;
    }
}
