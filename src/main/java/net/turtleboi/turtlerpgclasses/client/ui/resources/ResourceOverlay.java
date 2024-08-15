package net.turtleboi.turtlerpgclasses.client.ui.resources;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.client.ClientResourceData;
import net.turtleboi.turtlerpgclasses.client.ui.MovableUIComponent;
import net.turtleboi.turtlerpgclasses.config.UIConfig;

public class ResourceOverlay {
    private static ResourceBar mainResourceBar;
    private static ResourceBar secondaryResourceBar;

    public static final IGuiOverlay HUD_RESOURCESNUMBER = (gui, poseStack, partialTick, width, height) -> {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        Font font = minecraft.font;

        if (player == null) return;
        initializeResourceBars();

        mainResourceBar.render(poseStack, font);
        if (secondaryResourceBar != null) {
            secondaryResourceBar.render(poseStack, font);
        }
    };

    public static void initializeResourceBars() {
        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        String playerClass = ClientClassData.getPlayerClass();
        String playerSubclass = ClientClassData.getPlayerSubclass();

        int[] mainCoords = calculateStartCoordinates(
                screenWidth,
                screenHeight,
                UIConfig.resourceMainX.get(),
                UIConfig.resourceMainY.get(),
                MovableUIComponent.Anchor.fromString(
                        UIConfig.resourceMainAnchor.get()));
        int[] secondaryCoords = calculateStartCoordinates(
                screenWidth,
                screenHeight,
                UIConfig.resourceSecondaryX.get(),
                UIConfig.resourceSecondaryY.get(),
                MovableUIComponent.Anchor.fromString(
                        UIConfig.resourceSecondaryAnchor.get()));

        mainResourceBar = null;
        secondaryResourceBar = null;

        if ("Warrior".equals(playerClass)) {
            if ("Paladin".equals(playerSubclass)) {
                mainResourceBar = new ResourceBar.Builder()
                        .setPosition(mainCoords[0], mainCoords[1])
                        .setFilledWidth((int) ((ClientResourceData.getStamina() / (float) ClientResourceData.getMaxStamina()) * (ResourceBar.barWidth - 2 * ResourceBar.barXOffset)))
                        .setText(Component.literal(ClientResourceData.getStamina() + "/" + ClientResourceData.getMaxStamina()))
                        .setTextColor(0xE50000)
                        .setBarTypeYOffset(0 * ResourceBar.barHeight)
                        .setIsMain(true)
                        .build();

                secondaryResourceBar = new ResourceBar.Builder()
                        .setPosition(secondaryCoords[0], secondaryCoords[1])
                        .setFilledWidth((int) ((ClientResourceData.getMana() / (float) ClientResourceData.getMaxMana()) * (ResourceBar.barWidth - 2 * ResourceBar.barXOffset)))
                        .setText(Component.literal(ClientResourceData.getMana() + "/" + ClientResourceData.getMaxMana()))
                        .setTextColor(0x55FFFF)
                        .setBarTypeYOffset(4 * ResourceBar.barHeight)
                        .setIsMain(false)
                        .build();
            } else {
                mainResourceBar = new ResourceBar.Builder()
                        .setPosition(mainCoords[0], mainCoords[1])
                        .setFilledWidth((int) ((ClientResourceData.getStamina() / (float) ClientResourceData.getMaxStamina()) * (ResourceBar.barWidth - 2 * ResourceBar.barXOffset)))
                        .setText(Component.literal(ClientResourceData.getStamina() + "/" + ClientResourceData.getMaxStamina()))
                        .setTextColor(0xE50000)
                        .setBarTypeYOffset(0 * ResourceBar.barHeight)
                        .setIsMain(true)
                        .build();
            }
        } else if ("Ranger".equals(playerClass)) {
            mainResourceBar = new ResourceBar.Builder()
                    .setPosition(mainCoords[0], mainCoords[1])
                    .setFilledWidth((int) ((ClientResourceData.getEnergy() / (float) ClientResourceData.getMaxEnergy()) * (ResourceBar.barWidth - 2 * ResourceBar.barXOffset)))
                    .setText(Component.literal(ClientResourceData.getEnergy() + "/" + ClientResourceData.getMaxEnergy()))
                    .setTextColor(0x6AFE6A)
                    .setBarTypeYOffset(2 * ResourceBar.barHeight)
                    .setIsMain(true)
                    .build();
        } else if ("Mage".equals(playerClass)) {
            mainResourceBar = new ResourceBar.Builder()
                    .setPosition(mainCoords[0], mainCoords[1])
                    .setFilledWidth((int) ((ClientResourceData.getMana() / (float) ClientResourceData.getMaxMana()) * (ResourceBar.barWidth - 2 * ResourceBar.barXOffset)))
                    .setText(Component.literal(ClientResourceData.getMana() + "/" + ClientResourceData.getMaxMana()))
                    .setTextColor(0x55FFFF)
                    .setBarTypeYOffset(4 * ResourceBar.barHeight)
                    .setIsMain(true)
                    .build();
        }
    }

    private static int[] calculateStartCoordinates(int screenWidth, int screenHeight, int offsetX, int offsetY, MovableUIComponent.Anchor anchor) {
        int x = 0, y = 0;
        switch (anchor) {
            case TOP_LEFT:
                x = offsetX;
                y = offsetY;
                break;
            case TOP_RIGHT:
                x = screenWidth - offsetX - ResourceBar.barWidth;
                y = offsetY;
                break;
            case BOTTOM_LEFT:
                x = offsetX;
                y = screenHeight - offsetY - ResourceBar.barHeight;
                break;
            case BOTTOM_RIGHT:
                x = screenWidth - offsetX - ResourceBar.barWidth;
                y = screenHeight - offsetY - ResourceBar.barHeight;
                break;
            case TOP_CENTER:
                x = (screenWidth / 2) + offsetX - (ResourceBar.barWidth / 2);
                y = offsetY;
                break;
            case BOTTOM_CENTER:
                x = (screenWidth / 2) + offsetX - (ResourceBar.barWidth / 2);
                y = screenHeight - offsetY - ResourceBar.barHeight;
                break;
            case CENTER:
                x = (screenWidth / 2) + offsetX - (ResourceBar.barWidth / 2);
                y = (screenHeight / 2) + offsetY - (ResourceBar.barHeight / 2);
                break;
        }
        return new int[]{x, y};
    }
}
