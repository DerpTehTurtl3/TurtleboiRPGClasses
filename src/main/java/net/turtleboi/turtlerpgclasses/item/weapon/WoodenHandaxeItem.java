package net.turtleboi.turtlerpgclasses.item.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.turtleboi.turtlecore.item.weapon.AbstractHandaxeItem;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;

public class WoodenHandaxeItem extends AbstractHandaxeItem {

    private static final ResourceLocation HANDAXE_TEXTURE = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/item/wooden_handaxe.png");

    public WoodenHandaxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    protected ResourceLocation getHandaxeTexture() {
        return HANDAXE_TEXTURE;
    }
}