package net.turtleboi.turtlerpgclasses.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.turtleboi.turtlerpgclasses.entity.weapons.ThrowableDagger;

public class ThrowableDaggerModel<T extends ThrowableDagger> extends EntityModel<T> {
    public static final ModelLayerLocation DAGGER_LAYER = new ModelLayerLocation(new ResourceLocation("modid", "throwable_dagger"), "main");
    private final ModelPart dagger;

    public ThrowableDaggerModel(ModelPart root) {
        this.dagger = root.getChild("dagger");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition dagger = partdefinition.addOrReplaceChild("dagger", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, 5.0F, 4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-0.5F, 4.0F, 3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(1, 9).addBox(-0.5F, 3.0F, 2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(2, 8).addBox(-0.5F, 2.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(3, 5).addBox(-0.5F, 1.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(4, 5).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(4, 3).addBox(-0.5F, -1.0F, -3.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(4, 1).addBox(-0.5F, -2.0F, -4.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(6, 1).addBox(-0.5F, -3.0F, -5.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(7, 0).addBox(-0.5F, -4.0F, -6.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-0.5F, -5.0F, -6.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(9, 0).addBox(-0.5F, -6.0F, -6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3, 0.0F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        dagger.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {

    }
}
