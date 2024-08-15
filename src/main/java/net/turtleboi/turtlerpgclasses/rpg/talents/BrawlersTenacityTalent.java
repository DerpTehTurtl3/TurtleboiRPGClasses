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

public class BrawlersTenacityTalent extends Talent{
    public static final String Name = "Brawler's Tenacity";

    @Override
    public String getName() {
        return Name;
    }

    public int getArmorIncrease(int points) {
        int[] armorValues = {1, 1, 2, 2, 3};
        int currentRankIndex = Math.max(0, Math.min(points - 1, armorValues.length - 1));
        return armorValues[currentRankIndex];
    }

    public int getMaxArmor(int points) {
        int[] maxArmorValues = {3, 4, 8, 12, 18};
        int currentRankIndex = Math.max(0, Math.min(points - 1, maxArmorValues.length - 1));
        return maxArmorValues[currentRankIndex];
    }

    private int getArmorBonus(Player player) {
        AtomicInteger armorBonus = new AtomicInteger(0);
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            armorBonus.set(playerAbility.getBrawlerArmorBonus());
        });
        return armorBonus.get();
    }

    public void applyValues(Player player) {
        int talentsPoints = getPoints(player);
        int armorIncrease = getArmorIncrease(talentsPoints);
        int maxArmor = getMaxArmor(talentsPoints);

        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY).ifPresent(playerAbility -> {
            playerAbility.setBrawlerArmorBonus(armorIncrease);
            playerAbility.setMaxBrawlerArmorBonus(maxArmor);
        });
    }

    @Override
    public void applyAttributes(Player player) {
        applyModifier(player,
                Attributes.ARMOR,
                getAttributeName("Armor"),
                getArmorBonus(player),
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                Attributes.ARMOR
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }

    private static final float basePitch = 1.0f;
    private static final float pitchIncrement = 1.05946f;

    public void playHitSound(Player player, int currentBonus) {
        float pitch = basePitch * (float)Math.pow(pitchIncrement, currentBonus);
        player.level.playSound(null, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5f, pitch);
    }

    public void playUpgradeSound(Player player, int currentBonus) {
        float pitch = basePitch * (float)Math.pow(pitchIncrement, currentBonus);
        player.level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0f, pitch);
    }
}
