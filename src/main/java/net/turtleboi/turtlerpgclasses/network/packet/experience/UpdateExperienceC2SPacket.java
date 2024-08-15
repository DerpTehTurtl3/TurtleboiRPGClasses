package net.turtleboi.turtlerpgclasses.network.packet.experience;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;

import java.util.function.Supplier;

public class UpdateExperienceC2SPacket {

    public UpdateExperienceC2SPacket() {
    }

    public UpdateExperienceC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ModNetworking.sendToPlayer(new SyncExperienceS2CPacket(player.totalExperience, player.experienceLevel, player.experienceProgress), player);
                //System.out.println("Exp updated!");  //debug code
            }
        });
        return true;
    }
}
