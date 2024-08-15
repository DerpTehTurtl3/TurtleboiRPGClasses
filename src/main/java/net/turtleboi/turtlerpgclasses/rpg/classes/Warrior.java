package net.turtleboi.turtlerpgclasses.rpg.classes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Warrior extends Talent {
    private static final String Name = "Warrior";

    @Override
    public String getName() {
        return Name;
    }

    public void applyAttributes(Player player) {
        applyModifier(player,
                Attributes.MAX_HEALTH,
                getAttributeName("Health"),
                4.0,
                AttributeModifier.Operation.ADDITION);

        applyModifier(player,
                Attributes.ATTACK_DAMAGE,
                getAttributeName("Attack"),
                2.0,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return Arrays.asList(
                Attributes.MAX_HEALTH,
                Attributes.ATTACK_DAMAGE
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}


