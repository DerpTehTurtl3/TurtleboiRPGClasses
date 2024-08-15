package net.turtleboi.turtlerpgclasses.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkDirection;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.network.packet.*;
import net.turtleboi.turtlerpgclasses.network.packet.abilities.*;
import net.turtleboi.turtlerpgclasses.network.packet.experience.RemoveExperienceC2SPacket;
import net.turtleboi.turtlerpgclasses.network.packet.experience.SyncExperienceS2CPacket;
import net.turtleboi.turtlerpgclasses.network.packet.experience.UpdateExperienceC2SPacket;
import net.turtleboi.turtlerpgclasses.network.packet.resources.PlayerResourcesS2CPacket;

public class ModNetworking {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }
    public static void register () {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(TurtleRPGClasses.MOD_ID, "networking"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ClassSelectionC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ClassSelectionC2SPacket::new)
                .encoder(ClassSelectionC2SPacket::toBytes)
                .consumerMainThread(ClassSelectionC2SPacket::handle)
                .add();

        net.messageBuilder(ClassSelectionS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClassSelectionS2CPacket::new)
                .encoder(ClassSelectionS2CPacket::toBytes)
                .consumerMainThread(ClassSelectionS2CPacket::handle)
                .add();

        net.messageBuilder(OpenClassSelectionScreenPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(OpenClassSelectionScreenPacket::new)
                .encoder(OpenClassSelectionScreenPacket::toBytes)
                .consumerMainThread(OpenClassSelectionScreenPacket::handle)
                .add();

        net.messageBuilder(OpenEditUIScreenPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(OpenEditUIScreenPacket::new)
                .encoder(OpenEditUIScreenPacket::toBytes)
                .consumerMainThread(OpenEditUIScreenPacket::handle)
                .add();

        net.messageBuilder(PlayerResourcesS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerResourcesS2CPacket::new)
                .encoder(PlayerResourcesS2CPacket::toBytes)
                .consumerMainThread(PlayerResourcesS2CPacket::handle)
                .add();

        net.messageBuilder(UnleashFuryC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UnleashFuryC2SPacket::new)
                .encoder(UnleashFuryC2SPacket::toBytes)
                .consumerMainThread(UnleashFuryC2SPacket::handle)
                .add();

        net.messageBuilder(SteelBarbsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SteelBarbsC2SPacket::new)
                .encoder(SteelBarbsC2SPacket::toBytes)
                .consumerMainThread(SteelBarbsC2SPacket::handle)
                .add();

        net.messageBuilder(DivineSanctuaryC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DivineSanctuaryC2SPacket::new)
                .encoder(DivineSanctuaryC2SPacket::toBytes)
                .consumerMainThread(DivineSanctuaryC2SPacket::handle)
                .add();

        net.messageBuilder(ChargeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChargeC2SPacket::new)
                .encoder(ChargeC2SPacket::toBytes)
                .consumerMainThread(ChargeC2SPacket::handle)
                .add();

        net.messageBuilder(TauntC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TauntC2SPacket::new)
                .encoder(TauntC2SPacket::toBytes)
                .consumerMainThread(TauntC2SPacket::handle)
                .add();

        net.messageBuilder(ColossusC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ColossusC2SPacket::new)
                .encoder(ColossusC2SPacket::toBytes)
                .consumerMainThread(ColossusC2SPacket::handle)
                .add();

        net.messageBuilder(ExecuteC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExecuteC2SPacket::new)
                .encoder(ExecuteC2SPacket::toBytes)
                .consumerMainThread(ExecuteC2SPacket::handle)
                .add();

        net.messageBuilder(RemoveExperienceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RemoveExperienceC2SPacket::new)
                .encoder(RemoveExperienceC2SPacket::toBytes)
                .consumerMainThread(RemoveExperienceC2SPacket::handle)
                .add();

        net.messageBuilder(SyncExperienceS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncExperienceS2CPacket::new)
                .encoder(SyncExperienceS2CPacket::toBytes)
                .consumerMainThread(SyncExperienceS2CPacket::handle)
                .add();

        net.messageBuilder(UpdateExperienceC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpdateExperienceC2SPacket::new)
                .encoder(UpdateExperienceC2SPacket::toBytes)
                .consumerMainThread(UpdateExperienceC2SPacket::handle)
                .add();

        net.messageBuilder(WrathOfTheWarlordC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(WrathOfTheWarlordC2SPacket::new)
                .encoder(WrathOfTheWarlordC2SPacket::toBytes)
                .consumerMainThread(WrathOfTheWarlordC2SPacket::handle)
                .add();

        net.messageBuilder(WordOfHonorC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(WordOfHonorC2SPacket::new)
                .encoder(WordOfHonorC2SPacket::toBytes)
                .consumerMainThread(WordOfHonorC2SPacket::handle)
                .add();
    }
    public static <MSG> void sendToServer (MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer (MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
