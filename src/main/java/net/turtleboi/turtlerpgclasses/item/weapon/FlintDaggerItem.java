package net.turtleboi.turtlerpgclasses.item.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.turtleboi.turtlecore.item.weapon.AbstractDaggerItem;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;

public class FlintDaggerItem extends AbstractDaggerItem {

    private static final ResourceLocation DAGGER_TEXTURE = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/item/flint_dagger.png");

    public FlintDaggerItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    protected ResourceLocation getDaggerTexture() {
        return DAGGER_TEXTURE;
    }
}