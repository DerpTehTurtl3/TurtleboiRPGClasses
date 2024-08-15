package net.turtleboi.turtlerpgclasses.effect.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StunEffect extends MobEffect {
    private static final Map<Mob, StunData> stunnedMobs = new HashMap<>();

    public StunEffect(MobEffectCategory mobEffectCategory, int color){
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.getAbilities().mayBuild = false;
            player.getAbilities().invulnerable = false;
            player.getAbilities().instabuild = false;
            player.setDeltaMovement(0, 0, 0);
            player.setSprinting(false);
            player.setJumping(false);
            player.getInventory().selected = 0;
        } else if (entity instanceof Mob mob) {
            if (!stunnedMobs.containsKey(mob)) {
                StunData stunData = new StunData(mob.goalSelector, mob.targetSelector);
                stunnedMobs.put(mob, stunData);
                mob.goalSelector.getAvailableGoals().forEach(goal -> mob.goalSelector.removeGoal(goal.getGoal()));
                mob.targetSelector.getAvailableGoals().forEach(goal -> mob.targetSelector.removeGoal(goal.getGoal()));
            }
            mob.setDeltaMovement(0, 0, 0);
            mob.hurtMarked = true;
            mob.setSprinting(false);
            mob.setJumping(false);
            mob.getNavigation().stop();
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        if (entity instanceof Player player) {
            player.getAbilities().mayfly = true;
            player.getAbilities().mayBuild = true;
            player.getAbilities().invulnerable = true;
            player.getAbilities().instabuild = true;
        } else if (entity instanceof Mob mob) {
            if (stunnedMobs.containsKey(mob)) {
                StunData stunData = stunnedMobs.remove(mob);
                stunData.restoreGoals(mob);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier){
        return true;
    }

    private static class StunData {
        private final GoalSelector goalSelector;
        private final GoalSelector targetSelector;

        public StunData(GoalSelector goalSelector, GoalSelector targetSelector) {
            this.goalSelector = goalSelector;
            this.targetSelector = targetSelector;
        }

        public void restoreGoals(Mob mob) {
            mob.goalSelector.getAvailableGoals().forEach(goal -> mob.goalSelector.removeGoal(goal.getGoal()));
            mob.targetSelector.getAvailableGoals().forEach(goal -> mob.targetSelector.removeGoal(goal.getGoal()));
            goalSelector.getAvailableGoals().forEach(goal -> mob.goalSelector.addGoal(goal.getPriority(), goal.getGoal()));
            targetSelector.getAvailableGoals().forEach(goal -> mob.targetSelector.addGoal(goal.getPriority(), goal.getGoal()));
        }
    }
}
