package net.turtleboi.turtlerpgclasses.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.effect.CoreEffects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EffectUtils {
    private static final Map<UUID, UUID> rootedEntities = new HashMap<>();

    public static void applyRootedEffect(Player player, LivingEntity target, int duration, int amplifier) {
        if (target != null && player != null) {
            // Apply the Rooted effect
            target.addEffect(new MobEffectInstance(CoreEffects.ROOTED.get(), duration, amplifier));

            // Store the player who applied the effect
            rootedEntities.put(target.getUUID(), player.getUUID());
        }
    }

    public static UUID getRootingPlayerUUID(LivingEntity entity) {
        return rootedEntities.get(entity.getUUID());
    }

    public static void clearRootingPlayer(LivingEntity entity) {
        rootedEntities.remove(entity.getUUID());
    }
}
