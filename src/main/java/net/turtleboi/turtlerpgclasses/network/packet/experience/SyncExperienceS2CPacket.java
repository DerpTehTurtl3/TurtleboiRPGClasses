package net.turtleboi.turtlerpgclasses.network.packet.experience;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentPointAllocator;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentScreen;

import java.util.function.Supplier;

public class SyncExperienceS2CPacket {
    private final int totalExperience;
    private final int experienceLevel;
    private final float experienceProgress;

    public SyncExperienceS2CPacket(int totalExperience, int experienceLevel, float experienceProgress) {
        this.totalExperience = totalExperience;
        this.experienceLevel = experienceLevel;
        this.experienceProgress = experienceProgress;
    }

    public SyncExperienceS2CPacket(FriendlyByteBuf buf) {
        this.totalExperience = buf.readInt();
        this.experienceLevel = buf.readInt();
        this.experienceProgress = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(totalExperience);
        buf.writeInt(experienceLevel);
        buf.writeFloat(experienceProgress);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.totalExperience = totalExperience;
                player.experienceLevel = experienceLevel;
                player.experienceProgress = experienceProgress;

                if (Minecraft.getInstance().screen instanceof TalentScreen talentScreen) {
                    TalentPointAllocator allocator = talentScreen.getTalentPointAllocator();
                    if (allocator != null) {
                        allocator.handleExperienceSync(totalExperience, experienceLevel, experienceProgress);
                        //System.out.println("Exp sync'd to " + allocator);  //debug code
                    }
                }
            }
        });
        return true;
    }

}
