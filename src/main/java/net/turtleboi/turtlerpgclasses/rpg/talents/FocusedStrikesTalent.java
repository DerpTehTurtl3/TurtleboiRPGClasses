package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FocusedStrikesTalent extends Talent {
    private static final String Name = "Focused Strikes";

    @Override
    public String getName() {
        return Name;
    }

    public static int getDamageIncrease(int points) {
        int[] damageValues = {1, 1, 1, 2, 2};
        int currentRankIndex = Math.max(0, Math.min(points - 1, damageValues.length - 1));
        return damageValues[currentRankIndex];
    }

    public static int getHitThreshold(int points) {
        int[] hitThresholds = {4, 3, 3, 2, 2};
        int currentRankIndex = Math.max(0, Math.min(points - 1, hitThresholds.length - 1));
        return hitThresholds[currentRankIndex];
    }

    public static int getDamageMaximum(int points) {
        int[] damageMaximumValues = {4, 6, 8, 12, 16};
        int currentRankIndex = Math.max(0, Math.min(points - 1, damageMaximumValues.length - 1));
        return damageMaximumValues[currentRankIndex];
    }

    public void applyValues(Player player) {
        int talentPoints = getPoints(player);

        int damageIncrease = getDamageIncrease(talentPoints);
        int hitThreshold = getHitThreshold(talentPoints);
        int damageMaximum = getDamageMaximum(talentPoints);

        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            playerAbility.setFocusedStrikesThreshold(hitThreshold);
            playerAbility.setFocusedStrikesDamage(damageIncrease);
            playerAbility.setFocusedStrikesMaxDamage(damageMaximum);
        });
    }

    private static int getDamageBonus(Player player) {
        AtomicInteger damageBonus = new AtomicInteger(0);
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            damageBonus.set(playerAbility.getCurrentBonusDamage());
        });
        return damageBonus.get();
    }

    @Override
    public void applyAttributes(Player player) {
        applyModifier(player,
                Attributes.ATTACK_DAMAGE,
                getAttributeName("Attack"),
                getDamageBonus(player),
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                Attributes.ATTACK_DAMAGE
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }

    private static final float basePitch = 1.0f;
    private static final float pitchIncrement = 1.05946f;

    public void playHitSound(Player player, int currentBonus) {
        float pitch = basePitch * (float) Math.pow(pitchIncrement, currentBonus);
        player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5f, pitch);
    }

    public void playUpgradeSound(Player player, int currentBonus) {
        float pitch = basePitch * (float) Math.pow(pitchIncrement, currentBonus);
        player.level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0f, pitch);
    }
}
