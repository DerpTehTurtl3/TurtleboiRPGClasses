package net.turtleboi.turtlerpgclasses.rpg.classes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Ranger extends Talent {
    private static final String Name = "Ranger";

    @Override
    public String getName() {
            return Name;
    }

    public void applyAttributes(Player player) {
        applyModifier(player,
                Attributes.MOVEMENT_SPEED,
                getAttributeName("Movespeed"),
                0.1,
                AttributeModifier.Operation.MULTIPLY_TOTAL);

        applyModifier(player,
                Attributes.ATTACK_SPEED,
                getAttributeName("AttackSpeed"),
                0.25,
                AttributeModifier.Operation.MULTIPLY_BASE);

        applyModifier(player,
                CoreAttributes.RANGED_DAMAGE.get(),
                getAttributeName("RangedDamage"),
                2.0,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return Arrays.asList(
                Attributes.MOVEMENT_SPEED,
                Attributes.ATTACK_SPEED,
                CoreAttributes.RANGED_DAMAGE.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}


