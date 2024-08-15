package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;

import java.util.List;

public class MarathonerTalent extends Talent {
    private static final String Name = "Marathoner";

    @Override
    public String getName() {
        return Name;
    }

    public static double getStaminaValue(int points) {
        double[] staminaValues = {2.0, 4.0, 6.0, 10.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaValues.length - 1));
        return staminaValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double staminaValue = getStaminaValue(talentPoints);

        applyModifier(player,
                ModAttributes.MAX_STAMINA.get(),
                getAttributeName("Stamina"),
                staminaValue,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                ModAttributes.MAX_STAMINA.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}