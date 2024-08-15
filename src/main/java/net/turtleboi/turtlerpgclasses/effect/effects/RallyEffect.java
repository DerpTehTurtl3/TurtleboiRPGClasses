package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.GuardiansOathTalent;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.WarlordsPresenceTalent;
import net.turtleboi.turtlerpgclasses.util.PartyUtils;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;

public class RallyEffect extends MobEffect {

    public RallyEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide() && entity instanceof Player player) {
            new WarlordsPresenceTalent().applyRallyAttributes(player);
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        if (entity instanceof Player player) {
            new WarlordsPresenceTalent().removeEffectModifier(player, "rally");
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
