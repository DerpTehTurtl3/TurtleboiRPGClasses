package net.turtleboi.turtlerpgclasses.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.*;

import java.util.Optional;

public class TargetingUtils {
    public static LivingEntity getTarget(Player player, double range) {
        Vec3 startVec = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getLookAngle();
        Vec3 endVec = startVec.add(lookVec.scale(range));

        EntityHitResult entityHitResult = rayTraceEntities(player, startVec, endVec);
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity) {
            return (LivingEntity) entityHitResult.getEntity();
        }
        return null;
    }

    private static EntityHitResult rayTraceEntities(Player player, Vec3 startVec, Vec3 endVec) {
        double closestDistance = Double.MAX_VALUE;
        EntityHitResult closestEntityHitResult = null;

        for (LivingEntity entity : player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(25))) {
            if (entity != player && entity != null) {
                AABB entityAABB = entity.getBoundingBox().inflate(0.3F);
                Optional<Vec3> optional = entityAABB.clip(startVec, endVec);

                if (optional.isPresent()) {
                    double distance = startVec.distanceTo(optional.get());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestEntityHitResult = new EntityHitResult(entity, optional.get());
                    }
                }
            }
        }
        return closestEntityHitResult;
    }
}
