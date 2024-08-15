package net.turtleboi.turtlerpgclasses.client.ui.resources;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;

public class ResourceBar {
    private static final ResourceLocation resourceBars = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/resourcebars.png");
    public static final int barWidth = 80;
    public static final int barHeight = 15;
    public static final int barXOffset = 3;

    private int x;
    private int y;
    private final int filledWidth;
    private final Component text;
    private final int textColor;
    private final int barTypeYOffset;
    private final boolean isMain;

    public ResourceBar(int x, int y, int filledWidth, Component text, int textColor, int barTypeYOffset, boolean isMain) {
        this.x = x;
        this.y = y;
        this.filledWidth = filledWidth;
        this.text = text;
        this.textColor = textColor;
        this.barTypeYOffset = barTypeYOffset;
        this.isMain = isMain;
    }

    public void render(PoseStack poseStack, Font font) {
        RenderSystem.setShaderTexture(0, resourceBars);
        GuiComponent.blit(poseStack, x, y, 0, barTypeYOffset, barWidth, barHeight, 80, 90);
        GuiComponent.blit(poseStack, x + barXOffset, y, barXOffset, barTypeYOffset + barHeight, filledWidth, barHeight, 80, 90);
        int textWidth = font.width(text) / 2;
        int textX = x + (barWidth / 2) - textWidth;
        int textY = y + (barHeight / 2); // Adjust textY to center text vertically
        font.draw(poseStack, text, textX - 1, textY, 0x330000);
        font.draw(poseStack, text, textX + 1, textY, 0x330000);
        font.draw(poseStack, text, textX, textY - 1, 0x330000);
        font.draw(poseStack, text, textX, textY + 1, 0x330000);
        font.draw(poseStack, text, textX, textY, textColor);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static class Builder {
        private int x;
        private int y;
        private int filledWidth;
        private Component text;
        private int textColor;
        private int barTypeYOffset;
        private boolean isMain;

        public Builder setPosition(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder setFilledWidth(int filledWidth) {
            this.filledWidth = filledWidth;
            return this;
        }

        public Builder setText(Component text) {
            this.text = text;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setBarTypeYOffset(int barTypeYOffset) {
            this.barTypeYOffset = barTypeYOffset;
            return this;
        }

        public Builder setIsMain(boolean isMain) {
            this.isMain = isMain;
            return this;
        }

        public ResourceBar build() {
            return new ResourceBar(x, y, filledWidth, text, textColor, barTypeYOffset, isMain);
        }
    }
}
