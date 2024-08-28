package net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class BattleHardenedTalent extends Talent {
    private static final String Name = Component.translatable("talents.battle_hardened").getString();

    @Override
    public String getName() {
        return Name;
    }

    public int getArmorIncrease(int points) {
        return points;
    }

    @Override
    public void applyAttributes(Player player) {
        applyModifier(player,
                Attributes.ARMOR,
                getAttributeName("Armor"),
                getArmorIncrease(getPoints(player)),
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                Attributes.ARMOR
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}


