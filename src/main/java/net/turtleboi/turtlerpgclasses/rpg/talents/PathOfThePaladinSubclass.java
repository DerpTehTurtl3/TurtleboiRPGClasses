package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;

import java.util.Arrays;
import java.util.List;

public class PathOfThePaladinSubclass extends Talent {
    public static final String Name = "Path of the Paladin Subclass";

    @Override
    public String getName() {
        return Name;
    }

    public static String getSubclassName() {
        return "Paladin";
    }

    public static int getMaxManaValue() {
        return -100;
    }

    public static double getManaRechargeValue() {
        return 0.08;
    }

    @Override
    public void applyAttributes(Player player) {

        int maxManaValue = getMaxManaValue();
        double manaRechargeValue = getManaRechargeValue();

        applyModifier(player,
                ModAttributes.MAX_MANA.get(),
                getAttributeName("Mana"),
                maxManaValue,
                AttributeModifier.Operation.ADDITION);

        applyModifier(player,
                ModAttributes.MANA_RECHARGE.get(),
                getAttributeName("ManaRecharge"),
                manaRechargeValue,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return Arrays.asList(
                ModAttributes.MAX_MANA.get(),
                ModAttributes.MANA_RECHARGE.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}
