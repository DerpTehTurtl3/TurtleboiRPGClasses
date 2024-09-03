package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents.EvasiveManeuversTalent;
import net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.BrawlersTenacityTalent;
import org.jetbrains.annotations.NotNull;

public class EvasiveManeuversEffect extends MobEffect {

    public EvasiveManeuversEffect(MobEffectCategory mobEffectCategory, int color){
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        EvasiveManeuversTalent evasiveManeuversTalent = new EvasiveManeuversTalent();
        if (entity instanceof Player player) {
            player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
                evasiveManeuversTalent.applyEffectAttributes(player);
            });
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        if (entity instanceof Player player) {
            new EvasiveManeuversTalent().removeEffectModifier(player, "evasivemaneuvers");
            AttributeInstance dodgeChanceAttribute = player.getAttribute(CoreAttributes.DODGE_CHANCE.get());
            if (dodgeChanceAttribute != null) {
                double dodgeChance = dodgeChanceAttribute.getValue();
                //player.sendSystemMessage(Component.literal("Dodge chance lost... Current dodge chance: " + dodgeChance + "%")); // debug code
            } else {
                //player.sendSystemMessage(Component.literal("Dodge chance attribute not found!")); // debug code
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }

    public static void updateEffectDuration(Player player, int duration) {
        if (player.hasEffect(ModEffects.EVASIVE_MANEUVERS.get())) {
            MobEffectInstance effectInstance = player.getEffect(ModEffects.EVASIVE_MANEUVERS.get());
            if (effectInstance != null) {
                int newDuration = effectInstance.getDuration() + duration;
                if (newDuration > 1200) {
                    newDuration = 1200;
                }
                player.addEffect(new MobEffectInstance(ModEffects.EVASIVE_MANEUVERS.get(), newDuration, effectInstance.getAmplifier()));
                //player.sendSystemMessage(Component.literal("Dodge chance extended! New duration: " + newDuration + " ticks"));
            }
        } else {
            player.addEffect(new MobEffectInstance(ModEffects.EVASIVE_MANEUVERS.get(), duration, 0));
            //player.sendSystemMessage(Component.literal("Dodge chance effect applied for " + duration + " ticks"));
        }
    }


}
