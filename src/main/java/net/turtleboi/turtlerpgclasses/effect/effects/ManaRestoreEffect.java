package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.capabilities.resources.PlayerResourceProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ManaRestoreEffect extends MobEffect {

    public ManaRestoreEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity directSource, @Nullable Entity indirectSource, @NotNull LivingEntity livingEntity, int pAmplifier, double pHealth) {
        if (livingEntity instanceof Player player) {
            player.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(resource -> {
                if (resource.isManaActive()) {
                    int divisor = Math.max(1, 3 - pAmplifier);
                    resource.addMana(resource.getMaxMana() / divisor);
                }
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
