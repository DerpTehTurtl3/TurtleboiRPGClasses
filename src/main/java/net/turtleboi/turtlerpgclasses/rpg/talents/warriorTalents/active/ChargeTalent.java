package net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.active;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlecore.effect.CoreEffects;
import net.turtleboi.turtlecore.util.TargetingUtils;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;
import net.turtleboi.turtlerpgclasses.rpg.talents.ActiveAbility;
import net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.MomentumTalent;
import net.turtleboi.turtlerpgclasses.util.EffectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeTalent extends ActiveAbility {
    private static final String Name = Component.translatable("talents.charge").getString();

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

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public Map<String, Integer> getResourceCosts(Player player) {
        Map<String, Integer> costs = new HashMap<>();
        costs.put("stamina", 5);
        return costs;
    }

    @Override
    public int getCooldownSeconds() {
        return 10;
    }

    @Override
    public boolean activate(Player player) {
        LivingEntity target;
        if (TargetingUtils.isLockedOn(player)){
            target = TargetingUtils.getLockedTarget(player);
        } else {
            target = TargetingUtils.getTarget(player);
        }

        if (target != null) {
            player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbilities -> {
                playerAbilities.setTargetEntity(target);
                EffectUtils.applyRootedEffect(player, target, 60, 0);
                playerAbilities.setCharging(true);
                MomentumTalent momentumTalent = new MomentumTalent();
                if (momentumTalent.isActive(player)) {
                    momentumTalent.applyAttributes(player);
                }
            });
            return true;
        } else {
            player.sendSystemMessage(Component.literal("No target found"));
            return false;
        }
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
        return 0;
    }
}
