package net.turtleboi.turtlerpgclasses.network.packet.experience;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;

import java.util.function.Supplier;

public class RemoveExperienceC2SPacket {
    private final int xpToRemove;

    public RemoveExperienceC2SPacket(int xpToRemove) {
        this.xpToRemove = xpToRemove;
    }

    public RemoveExperienceC2SPacket(FriendlyByteBuf buf) {
        this.xpToRemove = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(xpToRemove);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.giveExperiencePoints(-xpToRemove);
                ModNetworking.sendToPlayer(new SyncExperienceS2CPacket(player.totalExperience, player.experienceLevel, player.experienceProgress), player);
            }
        });
        return true;
    }
}