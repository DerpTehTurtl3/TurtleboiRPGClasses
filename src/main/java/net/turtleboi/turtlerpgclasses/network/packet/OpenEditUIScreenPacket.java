package net.turtleboi.turtlerpgclasses.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.client.ui.ClassSelectionScreen;
import net.turtleboi.turtlerpgclasses.client.ui.EditUIScreen;

import java.util.function.Supplier;

public class OpenEditUIScreenPacket {

    public OpenEditUIScreenPacket() {
    }

    public OpenEditUIScreenPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf){
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
                net.minecraft.client.Minecraft.getInstance().setScreen(new EditUIScreen());
            });
        return true;
    }
}

