package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.BrawlersTenacityTalent;
import org.jetbrains.annotations.NotNull;

public class BrawlersTenacityEffect extends MobEffect {

    public BrawlersTenacityEffect(MobEffectCategory mobEffectCategory, int color){
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        BrawlersTenacityTalent brawlersTenacityTalent = new BrawlersTenacityTalent();
        if (entity instanceof Player player) {
            player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                brawlersTenacityTalent.applyValues(player);
            });
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        if (entity instanceof Player player) {
            new BrawlersTenacityTalent().removeModifier(player);
            player.level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0f, 0.1f);
            //player.sendSystemMessage(Component.literal("Damage bonus lost...")); //debug code
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }

    public static void updateEffectDuration(Player player, int duration) {
        if (player.hasEffect(ModEffects.BRAWLERS_TENACITY.get())) {
            MobEffectInstance effectInstance = player.getEffect(ModEffects.BRAWLERS_TENACITY.get());
            if (effectInstance != null) {
                effectInstance.update(new MobEffectInstance(ModEffects.BRAWLERS_TENACITY.get(), duration, effectInstance.getAmplifier()));
            }
        } else {
            player.addEffect(new MobEffectInstance(ModEffects.BRAWLERS_TENACITY.get(), duration, 0));
        }
    }
}
