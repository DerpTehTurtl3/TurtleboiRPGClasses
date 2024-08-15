package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import java.util.List;

public class VigorTalent extends Talent {
    private static final String Name = "Vigor";

    @Override
    public String getName() {
        return Name;
    }

    public static double getHealthValue(int points) {
        return points * 2;
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double healthValue = getHealthValue(talentPoints);

        applyModifier(player,
                Attributes.MAX_HEALTH,
                getAttributeName("Health"),
                healthValue,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                Attributes.MAX_HEALTH
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}