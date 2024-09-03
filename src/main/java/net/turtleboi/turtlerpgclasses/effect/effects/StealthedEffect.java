package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents.SteadyBreathingTalent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class StealthedEffect extends MobEffect {

    private UUID stealthUUID = UUID.fromString("e2b39ff6-1641-4b8f-89ca-cad46b532687");

    public StealthedEffect(MobEffectCategory mobEffectCategory, int color){
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.setForcedPose(Pose.CROUCHING);
            player.getAttribute(Attributes.FOLLOW_RANGE).addTransientModifier(
                    new AttributeModifier(stealthUUID, "StealthedDetectionReduction", -28, AttributeModifier.Operation.ADDITION)
            );
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        if (entity instanceof Player player) {
            player.setForcedPose(null);
            player.getAttribute(Attributes.FOLLOW_RANGE).removeModifier(stealthUUID);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }
}
