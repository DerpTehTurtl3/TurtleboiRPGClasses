package net.turtleboi.turtlerpgclasses.client.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.capabilities.talents.TalentStates;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;
import net.turtleboi.turtlerpgclasses.network.packet.ClassSelectionC2SPacket;
import net.turtleboi.turtlerpgclasses.rpg.classes.Mage;
import net.turtleboi.turtlerpgclasses.rpg.classes.Ranger;
import net.turtleboi.turtlerpgclasses.rpg.classes.Warrior;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.ranges.Range;

public class ClassSelectionScreen extends Screen {

    public ClassSelectionScreen() {
        super(Component.translatable("SelectClass"));
    }

    @Override
    protected void init() {
        super.init();
        int buttonWidth = 128;
        int buttonHeight = 144;
        int buttonSpacing = 16;

        int startX = this.width / 2 - (3 * buttonWidth + 2 * buttonSpacing) / 2;

        this.addRenderableWidget(new ClassButton(
                startX,
                this.height / 2 - buttonHeight / 2,
                buttonWidth,
                buttonHeight,
                Component.translatable("class.warrior.name"),
                Component.translatable("class.warrior.description"),
                Component.translatable("class.warrior.features"),
                button ->
                        handleClassSelection("Warrior")
        ));

        startX += buttonWidth + buttonSpacing;

        this.addRenderableWidget(new ClassButton(
                startX,
                this.height / 2 - buttonHeight / 2,
                buttonWidth,
                buttonHeight,
                Component.translatable("class.ranger.name"),
                Component.translatable("class.ranger.description"),
                Component.translatable("class.ranger.features"),
                button ->
                        handleClassSelection("Ranger")
        ));

        startX += buttonWidth + buttonSpacing;

        this.addRenderableWidget(new ClassButton(
                startX,
                this.height / 2 - buttonHeight / 2,
                buttonWidth,
                buttonHeight,
                Component.translatable("class.mage.name"),
                Component.translatable("class.mage.description"),
                Component.translatable("class.mage.features"),
                button ->
                        handleClassSelection("Mage")
        ));
    }

    private void handleClassSelection(String className) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            TalentStates.resetAllTalents(player);
            TalentStates.setPurchasedTalentPoints(player, 0);
                ModNetworking.sendToServer(new ClassSelectionC2SPacket(className, "No Subclass"));
                if ("Warrior".equals(className)) {
                    new Warrior().setActive(player);
                } else if ("Ranger".equals(className)) {
                    new Ranger().setActive(player);
                } else if ("Mage".equals(className)) {
                    new Mage().setActive(player);
                }
                this.onClose();
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 20, 16777215);
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }
}