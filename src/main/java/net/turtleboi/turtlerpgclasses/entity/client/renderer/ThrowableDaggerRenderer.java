package net.turtleboi.turtlerpgclasses.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.turtleboi.turtlerpgclasses.entity.weapons.ThrowableDagger;
import org.jetbrains.annotations.NotNull;

public class ThrowableDaggerRenderer extends EntityRenderer<ThrowableDagger> {

    public ThrowableDaggerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ThrowableDagger entity, float yaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();

        if (!entity.hasHitBlock() || entity.isReturning()) {
            poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(90F));

            if (!entity.isReturning()) {
                float spin = (entity.tickCount + partialTicks) * 20.0F;
                poseStack.mulPose(Vector3f.XP.rotationDegrees(spin));
            }

            entity.lastYP = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
            entity.lastZP = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        } else {
            poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.lastYP - 90.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(entity.lastZP + 90.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(90F));
            float rotateVariation = entity.getRotateVariation();
            poseStack.mulPose(Vector3f.XP.rotationDegrees(200F + rotateVariation));
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = entity.getPickupItem();

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.HEAD, light, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.getId());

        poseStack.popPose();

        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThrowableDagger entity) {
        return entity.getTexture();
    }
}
