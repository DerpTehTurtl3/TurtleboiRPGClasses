package net.turtleboi.turtlerpgclasses.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import java.util.function.Supplier;

public class ClassSelectionS2CPacket {
    private String playerClass = "No class";
    private String playerSubclass = "No subclass";

    public ClassSelectionS2CPacket(String playerClass, String playerSubclass) {
        this.playerClass = playerClass;
        this.playerSubclass = playerSubclass;
    }

    public ClassSelectionS2CPacket(FriendlyByteBuf buf) {
        this.playerClass = buf.readUtf();
        this.playerSubclass = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(playerClass);
        buf.writeUtf(playerSubclass);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT :)
            ClientClassData.setPlayerClass(playerClass);
            ClientClassData.setPlayerSubclass(playerSubclass);
        });
        return true;
    }
}
