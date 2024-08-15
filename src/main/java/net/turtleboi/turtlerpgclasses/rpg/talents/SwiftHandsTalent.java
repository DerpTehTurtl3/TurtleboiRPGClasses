package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import java.util.List;

public class SwiftHandsTalent extends Talent {
    private static final String Name = "Swift Hands";

    @Override
    public String getName() {
        return Name;
    }

    public static double getAttackSpeedValue(int points) {
        return points * 0.04;
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double attackSpeedValue = getAttackSpeedValue(talentPoints);

        applyModifier(player,
                Attributes.ATTACK_SPEED,
                getAttributeName("AttackSpeed"),
                attackSpeedValue,
                AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                Attributes.ATTACK_SPEED
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}