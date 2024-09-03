package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class EvasiveManeuversTalent extends Talent {
    private static final String Name = Component.translatable("talents.evasive_maneuvers").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getDodgeChance(int points) {
        double[] dodgeChanceValues = {10.0, 15.0, 22.5, 35.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, dodgeChanceValues.length - 1));
        return dodgeChanceValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {

    }

    @Override
    public void applyEffectAttributes(Player player) {
        int talentPoints = getPoints(player);

        applyEffectModifier(player,
                CoreAttributes.DODGE_CHANCE.get(),
                getEffectAttributeName("DodgeChance", "evasivemaneuvers"),
                getDodgeChance(talentPoints),
                AttributeModifier.Operation.ADDITION);

        if (player.getHealth() <= (player.getMaxHealth()/2)){
            applyEffectModifier(player,
                    CoreAttributes.DODGE_CHANCE.get(),
                    getEffectAttributeName("DodgeChance2", "evasivemaneuvers"),
                    getDodgeChance(talentPoints),
                    AttributeModifier.Operation.ADDITION);
        }
    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                CoreAttributes.DODGE_CHANCE.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of(
                CoreAttributes.DODGE_CHANCE.get()
        );
    }
}