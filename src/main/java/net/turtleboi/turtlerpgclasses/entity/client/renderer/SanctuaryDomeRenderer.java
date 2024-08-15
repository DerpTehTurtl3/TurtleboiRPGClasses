package net.turtleboi.turtlerpgclasses.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.entity.abilities.SanctuaryDomeEntity;
import net.turtleboi.turtlerpgclasses.entity.client.SanctuaryDomeModel;

public class SanctuaryDomeRenderer extends EntityRenderer<SanctuaryDomeEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/entity/sanctuary_dome.png");
    private final SanctuaryDomeModel<SanctuaryDomeEntity> model;
    public SanctuaryDomeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SanctuaryDomeModel<>(context.bakeLayer(SanctuaryDomeModel.SANCTUARY_DOME_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(SanctuaryDomeEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SanctuaryDomeEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 1.5D, 0.0D);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-entity.getYRot()));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));

        float growthFactor = (float) entity.growthFactor;
        poseStack.scale(growthFactor, growthFactor, growthFactor);

        float alpha = entity.alpha;

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, light);
    }
}
