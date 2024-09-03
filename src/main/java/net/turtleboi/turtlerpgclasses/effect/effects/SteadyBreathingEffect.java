package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.rpg.talents.commonTalents.FocusedStrikesTalent;
import net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents.SteadyBreathingTalent;
import org.jetbrains.annotations.NotNull;

public class SteadyBreathingEffect extends MobEffect {

    public SteadyBreathingEffect(MobEffectCategory mobEffectCategory, int color){
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {

    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }

    public static void updateEffectDuration(Player player, int duration) {
        if (player.hasEffect(ModEffects.STEADY_BREATHING.get())) {
            MobEffectInstance effectInstance = player.getEffect(ModEffects.STEADY_BREATHING.get());
            if (effectInstance != null) {
                effectInstance.update(new MobEffectInstance(ModEffects.STEADY_BREATHING.get(), duration, effectInstance.getAmplifier()));
            }
        } else {
            player.addEffect(new MobEffectInstance(ModEffects.STEADY_BREATHING.get(), duration, 0));
        }
    }
}
