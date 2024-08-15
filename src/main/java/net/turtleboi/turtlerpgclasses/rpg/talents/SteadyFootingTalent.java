package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import java.util.List;

public class SteadyFootingTalent extends Talent {
    private static final String Name = "Steady Footing";

    @Override
    public String getName() {
        return Name;
    }

    public static double getKnockbackResistanceValue(int points) {
        return points * 0.03;
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double knockbackResistanceValue = getKnockbackResistanceValue(talentPoints);

        applyModifier(player,
                Attributes.KNOCKBACK_RESISTANCE,
                getAttributeName("KnockbackResistance"),
                knockbackResistanceValue,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                Attributes.KNOCKBACK_RESISTANCE
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}