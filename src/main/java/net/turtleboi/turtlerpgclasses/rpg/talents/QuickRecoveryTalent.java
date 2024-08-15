package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;

import java.util.List;

public class QuickRecoveryTalent extends Talent {
    private static final String Name = "Quick Recovery";

    @Override
    public String getName() {
        return Name;
    }

    public static double getCooldownReductionValue(int points) {
        double[] cooldownValues = {5.0, 10.0, 15.0, 20.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, cooldownValues.length - 1));
        return cooldownValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double cooldownValue = getCooldownReductionValue(talentPoints);

        applyModifier(player,
                ModAttributes.COOLDOWN_REDUCTION.get(),
                getAttributeName("Cooldown"),
                -cooldownValue,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                ModAttributes.COOLDOWN_REDUCTION.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}
