package net.turtleboi.turtlerpgclasses.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.init.ModDamageSources;
import net.turtleboi.turtlerpgclasses.particle.ModParticles;
import net.turtleboi.turtlerpgclasses.rpg.talents.LifeLeechTalent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TurtleRPGClasses.MOD_ID)
public class AttributeEvents {

    @SubscribeEvent
    public static void applyAttributeEffects(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            applyRangedDamageBonus(event, player);
            applyLifeSteal(event, player);
            applyCriticalHit(event, player);
        } else if (event.getEntity() instanceof Player player) {
            applyDamageResistance(event, player);
        }
    }

    @SubscribeEvent
    public static void onPlayerHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance healingEffectivenessAttribute = player.getAttribute(ModAttributes.HEALING_EFFECTIVENESS.get());
            if (healingEffectivenessAttribute != null) {
                double healingEffectiveness = healingEffectivenessAttribute.getValue();
                float originalHealAmount = event.getAmount();
                float adjustedHealAmount = (float) (originalHealAmount * healingEffectiveness);
                event.setAmount(adjustedHealAmount);
            }
        }
    }

    private static void applyRangedDamageBonus(LivingHurtEvent event, Player player) {
        AttributeInstance rangedBonusAttribute = player.getAttribute(ModAttributes.RANGED_DAMAGE.get());
        if (rangedBonusAttribute != null && isRangedDamage(event.getSource())) {
            double rangedBonus = rangedBonusAttribute.getValue();
            float newDamage = (float) (event.getAmount() + rangedBonus);
            event.setAmount(newDamage);
        }
    }

    private static void applyLifeSteal(LivingHurtEvent event, Player player) {
        if (isMeleeAttack(event, player) || isPlayerAppliedBleed(event, player)) {
            AttributeInstance lifeStealAttribute = player.getAttribute(ModAttributes.LIFE_STEAL.get());
            if (lifeStealAttribute != null && new LifeLeechTalent().isActive(player)) {
                double lifeStealPercentage = lifeStealAttribute.getValue() / 100.0;
                float damage = event.getAmount();
                float healAmount = getHealAmount(event, damage, (float) lifeStealPercentage);
                if (healAmount > 0) {
                    player.heal(healAmount);
                    spawnLifeDrainParticles(event.getEntity(), player, healAmount);
                }
            }
        }
    }

    private static boolean isMeleeAttack(LivingHurtEvent event, Player player) {
        return event.getSource().getEntity() == player && event.getSource().getDirectEntity() == player;
    }

    private static boolean isPlayerAppliedBleed(LivingHurtEvent event, Player player) {
        if (event.getSource() instanceof EntityDamageSource && event.getSource().getMsgId().equals("bleed")) {
            Entity source = event.getSource().getEntity();
            if (source instanceof Player) {
                return source == player;
            }
        }
        return false;
    }

    private static void applyCriticalHit(LivingHurtEvent event, Player player) {
        AttributeInstance critChanceAttribute = player.getAttribute(ModAttributes.CRITICAL_CHANCE.get());
        AttributeInstance critDamageAttribute = player.getAttribute(ModAttributes.CRITICAL_DAMAGE.get());
        if (critChanceAttribute != null && critDamageAttribute != null) {
            int critChance = (int) critChanceAttribute.getValue();
            double critDamage = critDamageAttribute.getValue();
            int chance = new Random().nextInt(100) + 1;

            if (chance <= critChance) {
                float originalDamage = event.getAmount();
                float newDamage = (float) (originalDamage * critDamage);
                event.setAmount(newDamage);

                Entity target = event.getEntity();
                if (target.level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 2; i++) {
                        serverLevel.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(0.5), target.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
                    }
                }
            }
        }
    }

    private static void applyDamageResistance(LivingHurtEvent event, Player player) {
        AttributeInstance damageResistanceAttribute = player.getAttribute(ModAttributes.DAMAGE_RESISTANCE.get());
        if (damageResistanceAttribute != null) {
            double damageResistance = damageResistanceAttribute.getValue() / 100.0;
            float originalDamage = event.getAmount();
            float reducedDamage = (float) (originalDamage * damageResistance);
            event.setAmount(reducedDamage);
        }
    }

    private static boolean isRangedDamage(DamageSource source) {
        return source.isProjectile();
    }

    private static float getHealAmount(LivingHurtEvent event, float damage, float lifeLeechPercentage) {
        float healAmount = 0;
        if (!event.getSource().isProjectile() && !event.getSource().isExplosion() && !event.getSource().isMagic() && !(event.getSource() instanceof ModDamageSources.BleedDamageSource)) {
            healAmount = damage * lifeLeechPercentage;
        } else if (event.getSource() instanceof ModDamageSources.BleedDamageSource) {
            healAmount = 1;
        }
        return healAmount;
    }

    private static final Random RANDOM = new Random();
    private static final double SPEED = 0.1;
    private static final double SIZE = 0.02;
    private static final double SIZE_VARIATION = 0.03;
    private static final double POSITION_VARIATION = 0.1;
    private static final double SPEED_VARIATION = 0.02;

    private static void spawnLifeDrainParticles(LivingEntity targetEntity, Player player, float healAmount) {
        if (!player.level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) player.level;
            double targetX = targetEntity.getX();
            double targetY = targetEntity.getY() + targetEntity.getEyeHeight() / 2.0;
            double targetZ = targetEntity.getZ();
            double playerX = player.getX();
            double playerY = player.getY() + player.getEyeHeight() / 2.0;
            double playerZ = player.getZ();
            double dx = playerX - targetX;
            double dy = playerY - targetY;
            double dz = playerZ - targetZ;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            int particlesPerHealth = 8;
            int numberOfParticles = (int) (healAmount * particlesPerHealth);
            if (numberOfParticles > 0) {
                double interval = distance / numberOfParticles;
                for (int i = 0; i < numberOfParticles; i++) {
                    double size = SIZE + RANDOM.nextDouble() * SIZE_VARIATION;
                    double progress = interval * i;
                    double xPos = targetX + dx * (progress / distance) + (RANDOM.nextDouble() - 0.5) * POSITION_VARIATION;
                    double yPos = targetY + dy * (progress / distance) + (RANDOM.nextDouble() - 0.5) * POSITION_VARIATION;
                    double zPos = targetZ + dz * (progress / distance) + (RANDOM.nextDouble() - 0.5) * POSITION_VARIATION;
                    double xSpeed = dx * SPEED / distance + (RANDOM.nextDouble() - 0.5) * SPEED_VARIATION;
                    double ySpeed = dy * SPEED / distance + (RANDOM.nextDouble() - 0.5) * SPEED_VARIATION;
                    double zSpeed = dz * SPEED / distance + (RANDOM.nextDouble() - 0.5) * SPEED_VARIATION;
                    serverLevel.sendParticles(ModParticles.LIFE_DRAIN_PARTICLES.get(),
                            xPos, yPos, zPos,
                            1, xSpeed, ySpeed, zSpeed, size);
                }
            }
        }
    }


}


