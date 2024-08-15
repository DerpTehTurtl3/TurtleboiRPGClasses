package net.turtleboi.turtlerpgclasses.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.client.render.ArrowRenderer;
import net.turtleboi.turtlerpgclasses.client.render.AuraRenderer;
import net.turtleboi.turtlerpgclasses.client.ui.ClassSelectionScreen;
import net.turtleboi.turtlerpgclasses.client.ui.cooldowns.CooldownOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.resources.ResourceOverlay;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.TalentScreen;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.network.ModNetworking;
import net.turtleboi.turtlerpgclasses.network.packet.abilities.*;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.GuardiansOathTalent;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.WarlordsPresenceTalent;
import net.turtleboi.turtlerpgclasses.util.KeyBinding;

public class ClientEvents {
    public static boolean showChargeCancelMessage = false;

    @Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void openClassSelectionGUI(InputEvent.Key event) {
            if (KeyBinding.OPEN_CLASSSELECT_KEY.consumeClick()) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.setScreen(new ClassSelectionScreen());
            }
        }

        @SubscribeEvent
        public static void openTalentTreeGUI(InputEvent.Key event) {
            if (KeyBinding.OPEN_TALENTTREE_KEY.consumeClick()) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.setScreen(new TalentScreen());
            }
        }

        @SubscribeEvent
        public static void useActiveAbility(InputEvent.Key event) {
            if (KeyBinding.ACTIVE1.consumeClick()) {
                ModNetworking.sendToServer(new UnleashFuryC2SPacket());
                ModNetworking.sendToServer(new SteelBarbsC2SPacket());
                ModNetworking.sendToServer(new DivineSanctuaryC2SPacket());
            }
            if (KeyBinding.ACTIVE2.consumeClick()) {
                ModNetworking.sendToServer(new ChargeC2SPacket());
                ModNetworking.sendToServer(new TauntC2SPacket());
            }
            if (KeyBinding.ACTIVE3.consumeClick()) {
                ModNetworking.sendToServer(new ColossusC2SPacket());
                ModNetworking.sendToServer(new ExecuteC2SPacket());
            }
            if (KeyBinding.ACTIVE4.consumeClick()) {
                ModNetworking.sendToServer(new WrathOfTheWarlordC2SPacket());
                ModNetworking.sendToServer(new WordOfHonorC2SPacket());
            }
        }

        @SubscribeEvent
        public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
            if (showChargeCancelMessage) {
                PoseStack poseStack = event.getPoseStack();
                Minecraft minecraft = Minecraft.getInstance();
                int screenWidth = minecraft.getWindow().getGuiScaledWidth();
                int screenHeight = minecraft.getWindow().getGuiScaledHeight();
                KeyMapping sneakKey = minecraft.options.keyShift;
                String sneakKeyName = sneakKey.getTranslatedKeyMessage().getString();
                String cancelMessage = "Press " + sneakKeyName + " to cancel Charge";

                int xPosition = screenWidth / 2;
                int yPosition = screenHeight / 2 + 30;


                GuiComponent.drawCenteredString(poseStack, minecraft.font, cancelMessage, xPosition, yPosition, 0xFFFFFF);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RenderEvents {

        @SubscribeEvent
        public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
            Player player = event.getEntity();
            PoseStack poseStack = event.getPoseStack();
            float scale = (float) player.getAttributeValue(ModAttributes.PLAYER_SIZE.get());

            if (scale != 1.0F) {
                poseStack.pushPose();
                poseStack.scale(scale, scale, scale);
            }
        }

        @SubscribeEvent
        public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = event.getEntity();
            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            float scale = (float) player.getAttributeValue(ModAttributes.PLAYER_SIZE.get());

            if (scale != 1.0F) {
                poseStack.popPose();
            }

            if (new WarlordsPresenceTalent().isActive(player)) {
                AuraRenderer.renderAura(player, poseStack, event.getPartialTick(), 2);
            } else if (new GuardiansOathTalent().isActive(player)){
                AuraRenderer.renderAura(player, poseStack, event.getPartialTick(), 1);
            }
            bufferSource.endBatch();
        }

        private static int lastScreenWidth = -1;
        private static int lastScreenHeight = -1;

        @SubscribeEvent
        public static void onScreenRender(ScreenEvent.Render.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            Screen currentScreen = minecraft.screen;

            if (currentScreen != null) {
                int currentWidth = minecraft.getWindow().getGuiScaledWidth();
                int currentHeight = minecraft.getWindow().getGuiScaledHeight();

                if (currentWidth != lastScreenWidth || currentHeight != lastScreenHeight) {
                    lastScreenWidth = currentWidth;
                    lastScreenHeight = currentHeight;
                    if (minecraft.player != null) {
                        CooldownOverlay.initializeSlots(minecraft.player);
                        ResourceOverlay.initializeResourceBars();
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onRenderLevelStage(RenderLevelStageEvent event) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            PoseStack poseStack = event.getPoseStack();

            if (player != null && event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
                ArrowRenderer.renderTargetArrow(player, poseStack);
            }
        }
    }
}
