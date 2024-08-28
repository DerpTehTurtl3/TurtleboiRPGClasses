package net.turtleboi.turtlerpgclasses.client.ui.resources;

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
    private static final int stamina = 0;
    private static final int energy = 2 * ResourceBar.barHeight;
    private static final int mana = 4 * ResourceBar.barHeight;

    public static final IGuiOverlay HUD_RESOURCESNUMBER = (gui, poseStack, partialTick, width, height) -> {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        Font font = minecraft.font;

        if (player == null) return;
        //if (mainResourceBar == null || secondaryResourceBar == null) {
            initializeResourceBars(player);
        //}

        if (mainResourceBar != null) {
            mainResourceBar.render(poseStack, font);
        }
        if (secondaryResourceBar != null) {
            secondaryResourceBar.render(poseStack, font);
        }
    };

    public static void initializeResourceBars(Player player) {
        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        String className = ClientClassData.getPlayerClass();
        String subclassName = ClientClassData.getPlayerSubclass();

        String warrior = Component.translatable("class.warrior.name").getString();
        String ranger = Component.translatable("class.ranger.name").getString();
        String mage = Component.translatable("class.mage.name").getString();
        String paladin = Component.translatable("subclass.paladin.name").getString();

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

        int resourceBarWidth = ResourceBar.barWidth - 2 * ResourceBar.barXOffset;
        if (warrior.equals(className)) {
            if (paladin.equals(subclassName)) {
                mainResourceBar = new ResourceBar.Builder()
                        .setPosition(mainCoords[0], mainCoords[1])
                        .setFilledWidth(((getStamina() / getMaxStamina()) * resourceBarWidth))
                        .setText(Component.literal(getStamina() + "/" + (int) getMaxStamina()))
                        .setTextColor(0xE50000)
                        .setBarTypeYOffset(stamina)
                        .setIsMain(true)
                        .build();
                secondaryResourceBar = new ResourceBar.Builder()
                        .setPosition(secondaryCoords[0], secondaryCoords[1])
                        .setFilledWidth(((getMana() / getMaxMana()) * resourceBarWidth))
                        .setText(Component.literal(getMana() + "/" + (int) getMaxMana()))
                        .setTextColor(0x55FFFF)
                        .setBarTypeYOffset(mana)
                        .setIsMain(false)
                        .build();
            } else {
                mainResourceBar = new ResourceBar.Builder()
                        .setPosition(mainCoords[0], mainCoords[1])
                        .setFilledWidth(((getStamina() / getMaxStamina()) * resourceBarWidth))
                        .setText(Component.literal(getStamina() + "/" + (int) getMaxStamina()))
                        .setTextColor(0xE50000)
                        .setBarTypeYOffset(stamina)
                        .setIsMain(true)
                        .build();
            }
        } else if (ranger.equals(className)) {
            mainResourceBar = new ResourceBar.Builder()
                    .setPosition(mainCoords[0], mainCoords[1])
                    .setFilledWidth(((getEnergy() / getMaxEnergy()) * resourceBarWidth))
                    .setText(Component.literal(getEnergy() + "/" + (int) getMaxEnergy()))
                    .setTextColor(0x6AFE6A)
                    .setBarTypeYOffset(energy)
                    .setIsMain(true)
                    .build();
        } else if (mage.equals(className)) {
            mainResourceBar = new ResourceBar.Builder()
                    .setPosition(mainCoords[0], mainCoords[1])
                    .setFilledWidth(((getMana() / getMaxMana()) * resourceBarWidth))
                    .setText(Component.literal(getMana() + "/" + (int) getMaxMana()))
                    .setTextColor(0x55FFFF)
                    .setBarTypeYOffset(mana)
                    .setIsMain(true)
                    .build();
        }
    }

    private static int getStamina(){
        return ClientResourceData.getStamina();
    }

    private static int getEnergy(){
        return ClientResourceData.getEnergy();
    }

    private static int getMana(){
        return ClientResourceData.getMana();
    }

    private static float getMaxStamina(){
        return ClientResourceData.getMaxStamina();
    }

    private static float getMaxEnergy(){
        return ClientResourceData.getMaxEnergy();
    }

    private static float getMaxMana(){
        return ClientResourceData.getMaxMana();
    }

    private static int[] calculateStartCoordinates(int screenWidth, int screenHeight, int offsetX, int offsetY, MovableUIComponent.Anchor anchor) {
        int x = 0, y = 0;
        y = switch (anchor) {
            case TOP_LEFT -> {
                x = offsetX;
                yield offsetY;
            }
            case TOP_RIGHT -> {
                x = screenWidth - offsetX - ResourceBar.barWidth;
                yield offsetY;
            }
            case BOTTOM_LEFT -> {
                x = offsetX;
                yield screenHeight - offsetY - ResourceBar.barHeight;
            }
            case BOTTOM_RIGHT -> {
                x = screenWidth - offsetX - ResourceBar.barWidth;
                yield screenHeight - offsetY - ResourceBar.barHeight;
            }
            case TOP_CENTER -> {
                x = (screenWidth / 2) + offsetX - (ResourceBar.barWidth / 2);
                yield offsetY;
            }
            case BOTTOM_CENTER -> {
                x = (screenWidth / 2) + offsetX - (ResourceBar.barWidth / 2);
                yield screenHeight - offsetY - ResourceBar.barHeight;
            }
            case CENTER -> {
                x = (screenWidth / 2) + offsetX - (ResourceBar.barWidth / 2);
                yield (screenHeight / 2) + offsetY - (ResourceBar.barHeight / 2);
            }
        };
        return new int[]{x, y};
    }
}
