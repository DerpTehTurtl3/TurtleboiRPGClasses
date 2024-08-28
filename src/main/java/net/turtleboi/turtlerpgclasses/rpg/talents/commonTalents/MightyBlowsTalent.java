package net.turtleboi.turtlerpgclasses.rpg.talents.commonTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class MightyBlowsTalent extends Talent {
    private static final String Name = Component.translatable("talents.mighty_blows").getString();

    @Override
    public String getName() {
        return Name;
    }

    public static double getAttackValue(int points) {
        return points * 0.5;
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double attackValue = getAttackValue(talentPoints);

        applyModifier(player,
                Attributes.ATTACK_DAMAGE,
                getAttributeName("Attack"),
                attackValue,
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
}
