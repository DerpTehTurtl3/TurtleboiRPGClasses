package net.turtleboi.turtlerpgclasses.client.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;

@Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InventoryOverlay {

    @SubscribeEvent
    public static void onScreenRenderPost(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof InventoryScreen) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            Font font = minecraft.font;

            if (player == null) return;

            PoseStack poseStack = event.getPoseStack();

            String classText = ClientClassData.getPlayerClass();
            String subclassText = ClientClassData.getPlayerSubclass();

            int screenWidth = event.getScreen().width;
            int screenHeight = event.getScreen().height;

            int classTextWidth = font.width(classText);
            int subclassTextWidth = font.width(subclassText);

            int classTextX = ((screenWidth - classTextWidth)/2) - 37;
            int subclassTextX = ((screenWidth - subclassTextWidth)/2) - 37;
            int classTextY = (screenHeight / 2) - 105;
            int subclassTextY = (screenHeight / 2) - 95;

            font.draw(poseStack, classText, classTextX, classTextY, 0xFFFFFF);
            font.draw(poseStack, subclassText, subclassTextX, subclassTextY, 0xFFFFFF);
        }
    }
}
