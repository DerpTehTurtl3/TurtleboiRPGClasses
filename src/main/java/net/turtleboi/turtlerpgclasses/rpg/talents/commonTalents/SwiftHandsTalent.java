package net.turtleboi.turtlerpgclasses.rpg.talents.commonTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class SwiftHandsTalent extends Talent {
    private static final String Name = Component.translatable("talents.swift_hands").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getAttackSpeed(int points) {
        return points * 4;
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        applyModifier(player,
                Attributes.ATTACK_SPEED,
                getAttributeName("AttackSpeed"),
                getAttackSpeed(talentPoints)/100,
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