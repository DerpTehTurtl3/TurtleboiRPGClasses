package net.turtleboi.turtlerpgclasses.rpg.talents.active;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteTalent extends ActiveAbility{
    private static final String Name = "Execute";

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
        costs.put("stamina", 4);
        return costs;
    }

    @Override
    public int getCooldownSeconds() {
        return 10;
    }

    @Override
    public boolean activate(Player player) {
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            playerAbility.setExecuteActive(true);
            //player.sendSystemMessage(Component.literal("Execute active!"));//Debug code
        });
        return true;
    }

    @Override
    public SoundEvent[] getAbilitySounds() {
        return new SoundEvent[]{SoundEvents.GRINDSTONE_USE};
    }
    @Override
    public void spawnAbilityEntity(Player player, Level level) {

    }

    @Override
    public int getDuration() {
        return getCooldownSeconds() * 20;
    }

    public static void spawnExecuteParticle(LivingEntity target) {
        if (target.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.SWEEP_ATTACK,
                    target.getX(),
                    target.getY(0.5D),
                    target.getZ(),
                    10,
                    0.5,
                    0.5,
                    0.5,
                    0.1);
        }
    }
}
