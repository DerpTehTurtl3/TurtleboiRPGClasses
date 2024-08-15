package net.turtleboi.turtlerpgclasses.item.weapon;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.turtleboi.turtlerpgclasses.entity.ModEntities;
import net.turtleboi.turtlerpgclasses.entity.abilities.UnleashFuryEntity;
import net.turtleboi.turtlerpgclasses.util.PartyUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BarbarianAxeItem extends SwordItem {
    public static final Rarity LEGENDARY = Rarity.create("LEGENDARY", ChatFormatting.GOLD);

    public BarbarianAxeItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return LEGENDARY;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("item.turtlerpgclasses.barbarian_axe.desc")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_AXE) ? 9.0F : super.getDestroySpeed(stack, state);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (!player.getCooldowns().isOnCooldown(this)) {
                abilitySound(player, level);
                UnleashFuryEntity unleashFuryEntity = new UnleashFuryEntity(ModEntities.UNLEASH_FURY.get(), level);
                unleashFuryEntity.setOwner(player);
                player.level.addFreshEntity(unleashFuryEntity);
                unleashFuryEntity.setYRot(player.getYRot());
                unleashFury(player, level, itemStack);
                player.getCooldowns().addCooldown(this, 600); //600 ticks or 30 second cooldown
                return InteractionResultHolder.success(itemStack);
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    private void unleashFury(Player player, Level level, ItemStack itemStack) {
        double RANGE = 3.0;
        List<Entity> nearbyEntities = level.getEntitiesOfClass(Entity.class, new AABB(
                player.getX() - RANGE, player.getY() - RANGE, player.getZ() - RANGE,
                player.getX() + RANGE, player.getY() + RANGE, player.getZ() + RANGE));
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && entity != player && !PartyUtils.isAlly(player, entity)) {
                LivingEntity target = (LivingEntity) entity;
                double attackDamage = calculateAttackDamage(player, itemStack, target);
                target.hurt(DamageSource.playerAttack(player), (float) attackDamage);
                applyEnchantments(player, target, itemStack);
            }
        }
    }

    private double calculateAttackDamage(Player player, ItemStack itemStack, LivingEntity target) {
        double baseDamage = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        double weaponDamage = ((SwordItem) itemStack.getItem()).getDamage();
        double enchantmentBonus = EnchantmentHelper.getDamageBonus(itemStack, target.getMobType());
        return baseDamage + weaponDamage + enchantmentBonus;
    }

    private void applyEnchantments(Player player, LivingEntity target, ItemStack itemStack) {
        EnchantmentHelper.doPostHurtEffects(target, player);
        EnchantmentHelper.doPostDamageEffects(player, target);
        int fireAspectLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemStack);
        if (fireAspectLevel > 0) {
            target.setSecondsOnFire(fireAspectLevel * 4);
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(stack.getItem()) || enchantment.category == EnchantmentCategory.WEAPON || enchantment.category == EnchantmentCategory.DIGGER;
    }

    private void abilitySound(Player player, Level level) {
        level.playSound(null, player.getOnPos(), SoundEvents.POLAR_BEAR_DEATH, SoundSource.PLAYERS,
                1.25F, level.random.nextFloat() * 0.1F + 0.9F);
        level.playSound(null, player.getOnPos(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS,
                1.25F, level.random.nextFloat() * 0.1F + 0.9F);
    }
}
