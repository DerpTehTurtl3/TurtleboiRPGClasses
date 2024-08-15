package net.turtleboi.turtlerpgclasses.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;

public class AuraRenderer {
    private static final ResourceLocation AURA_TEXTURE = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/aura.png");

    public static void renderAura(Player player, PoseStack poseStack, float partialTicks, int auraType) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, AURA_TEXTURE);

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        long time = System.currentTimeMillis();
        float rotationSpeed = 3.0f;
        float rotationAngle = (time % 360000L) / 1000.0f * rotationSpeed;
        float pulsateSpeed = 0.03f;
        float pulsateAmount = 0.01f;
        float baseScale = 0.15f;
        float scale = baseScale + pulsateAmount * (float) Math.sin((time % 100000L) / 1000.0f * pulsateSpeed * 2 * Math.PI);
        float playerYaw = -player.getYRot();
        float alpha = 1.0F;

        switch (auraType) {
            case 1: // Golden Aura
                RenderSystem.setShaderColor(1.0F, 0.953F, 0.408F, alpha);
                break;
            case 2: // Deep Red Aura
                RenderSystem.setShaderColor(0.718F, 0.098F, 0.176F, alpha);
                break;
            default: // Default Aura
                RenderSystem.setShaderColor(0.616F, 0.616F, 0.616F, alpha);
                break;
        }

        poseStack.scale(scale, scale, scale);
        poseStack.pushPose();
        poseStack.translate(0, (-player.getBbHeight() / 2.0) + 2, 0);

        poseStack.mulPose(Vector3f.YP.rotationDegrees(playerYaw + 45));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotationAngle + 45));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90));

        GuiComponent.blit(poseStack, -12, -12, 0, 0, 24, 24, 24, 24);
        poseStack.popPose();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.disableBlend();
    }
}
