package net.turtleboi.turtlerpgclasses.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import java.util.function.Supplier;

public class ClassSelectionS2CPacket {
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

    public ClassSelectionS2CPacket(String className, String subclassName) {
        playerClassName = className;
        playerSubclassName = subclassName;
    }

    public ClassSelectionS2CPacket(FriendlyByteBuf buf) {
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
            // HERE WE ARE ON THE CLIENT :)
            ClientClassData.setPlayerClass(getPlayerClassName());
            ClientClassData.setPlayerSubclass(getPlayerSubclassName());
        });
        return true;
    }
}
