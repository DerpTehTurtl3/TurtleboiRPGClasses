package net.turtleboi.turtlerpgclasses.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;

public class ClassButton extends Button {
    private static final ResourceLocation CLASS_WIDGET = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/class_widget.png");
    private static final ResourceLocation CLASS_WIDGET_SELECTED = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/class_widget_selected.png");
    private static final ResourceLocation CLASS_WIDGET_BACKGROUND = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/class_widget_background.png");

    private final Component className, classDescription, classFeatures;

    public ClassButton(int x, int y, int width, int height, Component className, Component classDescription, Component classFeatures, Button.OnPress onPress) {
        super(x, y, width, height, className, onPress);
        this.className = className;
        this.classDescription = classDescription;
        this.classFeatures = classFeatures;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, isHoveredOrFocused() ? CLASS_WIDGET_SELECTED : CLASS_WIDGET);
        blit(poseStack, x, y, 0, 0, width, height, 128, 144);

        drawCenteredString(poseStack, minecraft.font, className, x + width / 2, y + 7, 0xFFFFFF);

        RenderSystem.setShaderTexture(0, CLASS_WIDGET_BACKGROUND);
        blit(poseStack, x + 4, y + 20, 0, 0, 120, 119, 120, 119);

        renderTextWithinBounds(poseStack, classDescription, x + 8, y + 24, 120 - 14);
        renderTextWithinBounds(poseStack, classFeatures, x + 8, y + 24 + 9 * getLineCount(classDescription) + 12, 120 - 14);
    }

    private void renderTextWithinBounds(PoseStack poseStack, Component text, int x, int y, int width) {
        Minecraft minecraft = Minecraft.getInstance();
        for (FormattedCharSequence line : minecraft.font.split(text, width)) {
            minecraft.font.draw(poseStack, line, x, y, 0xFFFFFF);
            y += 9;
        }
    }

    private int getLineCount(Component text) {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.font.split(text, 120 - 12).size();
    }
}
