package net.turtleboi.turtlerpgclasses.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.util.TargetingUtils;

public class ArrowRenderer {
    private static final ResourceLocation ARROW_TEXTURE = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/target_arrow.png");

    public static void renderTargetArrow(Player player, PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        LivingEntity target = TargetingUtils.getTarget(player, 25.0);
        if (target != null) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ARROW_TEXTURE);

            if (target instanceof Monster) {
                // Hostile mobs (Red Tint)
                RenderSystem.setShaderColor(2.0F, 0.158F, 0.0F, 1.0F);
            } else {
                // Friendly or neutral mobs (Green Tint)
                RenderSystem.setShaderColor(0.737F, 2.0F, 0.0F, 1.0F);
            }

            double x = target.getX();
            double y = target.getY() + target.getBbHeight() + 0.5;
            double z = target.getZ();
            Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().getPosition();
            double screenX = x - cameraPos.x;
            double screenY = y - cameraPos.y;
            double screenZ = z - cameraPos.z;
            poseStack.pushPose();
            poseStack.translate(screenX, screenY, screenZ);
            poseStack.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
            poseStack.scale(-0.075F, -0.075F, 1.0F);
            GuiComponent.blit(poseStack, -4, -4, 0, 0, 8, 8, 8, 8);
            poseStack.popPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
