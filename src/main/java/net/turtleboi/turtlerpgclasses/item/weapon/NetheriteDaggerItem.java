package net.turtleboi.turtlerpgclasses.item.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.entity.weapons.ThrowableDagger;
import org.jetbrains.annotations.NotNull;

public class NetheriteDaggerItem extends SwordItem {
    public NetheriteDaggerItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    private static final ResourceLocation DAGGER_TEXTURE = new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/entity/netherite_dagger.png");

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            float thrownDamage = this.getDamage() / 2.0F;
            ThrowableDagger dagger = new ThrowableDagger(level, player, itemstack, DAGGER_TEXTURE, thrownDamage);
            dagger.setPos(player.getX(), player.getEyeY() - (double)0.1F, player.getZ());
            Vec3 direction = player.getViewVector(1.0F);
            dagger.shoot(direction.x, direction.y, direction.z, 1.5F, 1.0F);
            level.addFreshEntity(dagger);
        }
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, @NotNull Enchantment enchantment) {
        if (enchantment == Enchantments.LOYALTY) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}
