package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class WeakPointsTalent extends Talent {
    private static final String Name = Component.translatable("talents.weak_points").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getCriticalChance(int points) {
        double[] critChanceValues = {5.0, 8.5, 12.75, 15.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, critChanceValues.length - 1));
        return critChanceValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        applyModifier(player,
                CoreAttributes.CRITICAL_CHANCE.get(),
                getAttributeName("CriticalChance"),
                getCriticalChance(talentPoints),
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                CoreAttributes.CRITICAL_CHANCE.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}