package net.turtleboi.turtlerpgclasses.item.potion;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlerpgclasses.capabilities.resources.PlayerResourceProvider;

public class ManaPotionItem extends Item {

    public ManaPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player && !pLevel.isClientSide) {
            player.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(resource -> {
                resource.addMana(50);
            });
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
                player.addItem(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
