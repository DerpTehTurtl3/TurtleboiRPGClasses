package net.turtleboi.turtlerpgclasses.rpg.talents.active;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;
import net.turtleboi.turtlerpgclasses.capabilities.resources.PlayerResourceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VictoriousCryTalent extends ActiveAbility {
    private static final String Name = "Victorious Cry";

    @Override
    public String getName() {
        return Name;
    }

    private static final Map<UUID, Long> tauntEndTimeMap = new HashMap<>();

    public void applyTauntBonus(Player player, LivingEntity target) {
        int talentPoints = getPoints(player);
        int bonusDuration = getBonusDuration(talentPoints);

        UUID targetId = target.getUUID();
        long endTime = player.level.getGameTime() + bonusDuration;

        tauntEndTimeMap.put(targetId, endTime);
    }

    private static int getBonusDuration(int points) {
        int[] bonusDurations = {100, 100, 100};
        int currentRankIndex = Math.max(0, Math.min(points - 1, bonusDurations.length - 1));
        return bonusDurations[currentRankIndex];
    }

    public void handleDamageBoost(Player player, LivingEntity target, float originalDamage) {
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            if (playerAbility.isTaunting()) {
                LivingEntity tauntTarget = playerAbility.getTargetEntity();
                if (tauntTarget != null) {
                    UUID targetId = tauntTarget.getUUID();
                    long tauntEndTime = tauntEndTimeMap.get(targetId);

                    if (tauntTarget.equals(target) && player.level.getGameTime() <= tauntEndTime) {
                        int talentPoints = getPoints(player);
                        double damageBonus = getDamageBonus(talentPoints);
                        target.hurt(DamageSource.playerAttack(player), originalDamage * (float) (1 + damageBonus));
                        //player.sendSystemMessage(Component.literal("Bonus damage!")); //Debug Code
                    } else {
                        target.hurt(DamageSource.playerAttack(player), originalDamage);
                    }
                }
            }
        });
    }

    private static double getDamageBonus(int points) {
        double[] damageBonusValues = {0.12, 0.17, 0.24};
        int currentRankIndex = Math.max(0, Math.min(points - 1, damageBonusValues.length - 1));
        return damageBonusValues[currentRankIndex];
    }

    public void onTargetDeath(Player player, LivingEntity target) {
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            LivingEntity tauntTarget = playerAbility.getTargetEntity();

            if (tauntTarget == null || !tauntTarget.getUUID().equals(target.getUUID())) {
                return;
            }

            UUID targetId = target.getUUID();
            if (!tauntEndTimeMap.containsKey(targetId)) {
                return;
            }

            long currentTime = player.level.getGameTime();
            long endTime = tauntEndTimeMap.get(targetId);

            if (currentTime <= endTime) {
                int talentPoints = getPoints(player);
                double staminaRestore = getStaminaRestore(talentPoints);
                double healAmount = getHealAmount(talentPoints);

                TauntTalent taunt = new TauntTalent();
                taunt.resetAbilityCooldown(player);

                player.getCapability(PlayerResourceProvider.PLAYER_RESOURCE).ifPresent(playerResource -> {
                    playerResource.addStamina((int) (playerResource.getMaxStamina() * staminaRestore));
                    player.heal((float) healAmount);
                });

                tauntEndTimeMap.remove(targetId);
                //player.sendSystemMessage(Component.literal("Victorious Cry activated! Cooldown of Taunt refreshed, stamina restored, and health healed.")); //Debug Code
            }
        });
    }

    private static double getStaminaRestore(int points) {
        double[] staminaRestoreValues = {0.10, 0.1625, 0.25};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaRestoreValues.length - 1));
        return staminaRestoreValues[currentRankIndex];
    }

    private static double getHealAmount(int points) {
        double[] healValues = {5.0, 7.0, 12.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, healValues.length - 1));
        return healValues[currentRankIndex];
    }

    @Override
    public Map<String, Integer> getResourceCosts(Player player) {
        return Map.of();
    }

    @Override
    public int getCooldownSeconds() {
        return 0;
    }

    @Override
    public boolean activate(Player player) {
        return false;
    }

    @Override
    public SoundEvent[] getAbilitySounds() {
        return new SoundEvent[0];
    }

    @Override
    public void spawnAbilityEntity(Player player, Level level) {

    }

    @Override
    public int getDuration() {
        return 100;
    }

    @Override
    public void applyAttributes(Player player) {

    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of();
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}
