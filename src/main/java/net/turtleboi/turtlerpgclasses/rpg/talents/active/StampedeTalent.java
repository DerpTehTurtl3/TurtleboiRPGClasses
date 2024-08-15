package net.turtleboi.turtlerpgclasses.rpg.talents.active;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;

import java.util.List;
import java.util.Map;

public class StampedeTalent extends ActiveAbility {
    public static final String Name = "Stampede";

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
        int talentPoints = getPoints(player);
        return Map.of("stamina", (int) getStaminaCost(talentPoints));
    }

    private static double getStaminaCost(int points) {
        double[] staminaValues = {6.0, 6.0, 5.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaValues.length - 1));
        return staminaValues[currentRankIndex];
    }

    @Override
    public int getCooldownSeconds() {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        int talentPoints = getPoints(player);
        return (int) getCooldownValue(talentPoints);
    }

    private static double getCooldownValue(int points) {
        double[] cooldownValues = {12.0, 11.0, 10.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, cooldownValues.length - 1));
        return cooldownValues[currentRankIndex];
    }

    @Override
    public boolean activate(Player player) {
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbilities -> {
            playerAbilities.setStampeding(true);
        });
        return true;
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
