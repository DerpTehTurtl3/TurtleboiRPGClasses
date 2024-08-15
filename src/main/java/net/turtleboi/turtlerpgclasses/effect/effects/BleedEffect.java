package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.init.ModDamageSources;

public class BleedEffect extends MobEffect {
    protected int tick;
    private Entity source;

    public BleedEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    public void setSource(Entity source) {
        this.source = source;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level.isClientSide) {
            ++tick;
            int interval = Math.max(10, 40 - (10 * amplifier));
            float damage = 1.0F + amplifier;

            if (tick >= interval) {
                livingEntity.hurt(ModDamageSources.causeBleedDamage(this.source), damage);
                tick = 0;
            }
        }
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void applyOrAmplifyBleed(LivingEntity entity, int duration, int amplifier, Entity source) {
        MobEffectInstance existingEffect = entity.getEffect(ModEffects.BLEEDING.get());
        if (existingEffect != null) {
            int newAmplifier = existingEffect.getAmplifier() + amplifier + 1;
            int newDuration = Math.max(existingEffect.getDuration(), duration);
            MobEffectInstance newEffect = new MobEffectInstance(ModEffects.BLEEDING.get(), newDuration, newAmplifier);
            ((BleedEffect) ModEffects.BLEEDING.get()).setSource(source);
            entity.addEffect(newEffect);
        } else {
            BleedEffect bleedEffect = (BleedEffect) ModEffects.BLEEDING.get();
            bleedEffect.setSource(source);
            entity.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), duration, amplifier));
        }
    }
}
