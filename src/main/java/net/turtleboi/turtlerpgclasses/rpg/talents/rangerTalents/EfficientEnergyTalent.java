package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class EfficientEnergyTalent extends Talent {
    private static final String Name = Component.translatable("talents.efficient_energy").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getEnergyValue(int points) {
        return points * 10;
    }

    public double getCooldownReductionValue(int points) {
        return (points * 5) + 5;
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double energyValue = getEnergyValue(talentPoints);
        double cooldownValue = getCooldownReductionValue(talentPoints);

        applyModifier(player,
                ModAttributes.MAX_ENERGY.get(),
                getAttributeName("MaxEnergy"),
                energyValue,
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                CoreAttributes.COOLDOWN_REDUCTION.get(),
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
                ModAttributes.MAX_ENERGY.get(),
                CoreAttributes.COOLDOWN_REDUCTION.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}