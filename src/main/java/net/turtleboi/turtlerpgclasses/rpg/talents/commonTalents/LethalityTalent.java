package net.turtleboi.turtlerpgclasses.rpg.talents.commonTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class LethalityTalent extends Talent {
    private static final String Name = Component.translatable("talents.lethality").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getArmorPen(int points) {
        return points;
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double armorPenValue = getArmorPen(talentPoints);

        applyModifier(player,
                CoreAttributes.ARMOR_PENETRATION.get(),
                getAttributeName("ArmorPen"),
                armorPenValue,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                CoreAttributes.ARMOR_PENETRATION.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}