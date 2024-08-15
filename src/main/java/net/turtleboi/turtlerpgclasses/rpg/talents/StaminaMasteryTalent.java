package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;

import java.util.Arrays;
import java.util.List;

public class StaminaMasteryTalent extends Talent {
    private static final String Name = "Stamina Mastery";

    @Override
    public String getName() {
        return Name;
    }

    public static double getStaminaValue(int points) {
        double[] staminaValues = {2.0, 4.0, 6.0, 8.0, 10.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaValues.length - 1));
        return staminaValues[currentRankIndex];
    }

    public static double getStaminaRechargeRateValue(int points) {
        double[] staminaRechargeRateValues = {0.05, 0.07, 0.10, 0.15, 0.25};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaRechargeRateValues.length - 1));
        return staminaRechargeRateValues[currentRankIndex];
    }

    public static double getStaminaValueForPaladin(int points) {
        double[] staminaValues2 = {1.0, 2.0, 3.0, 4.0, 5.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaValues2.length - 1));
        return staminaValues2[currentRankIndex];
    }

    public static double getStaminaRechargeRateValueForPaladin(int points) {
        double[] staminaRechargeRateValues2 = {0.025, 0.05, 0.085, 0.12, 0.1625};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaRechargeRateValues2.length - 1));
        return staminaRechargeRateValues2[currentRankIndex];
    }

    public static double getManaValue(int points) {
        double[] manaValues = {10.0, 20.0, 30.0, 40.0, 60.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, manaValues.length - 1));
        return manaValues[currentRankIndex];
    }

    public static double getManaRechargeRateValue(int points) {
        double[] manaRechargeRateValues = {0.025, 0.05, 0.085, 0.12, 0.1625};
        int currentRankIndex = Math.max(0, Math.min(points - 1, manaRechargeRateValues.length - 1));
        return manaRechargeRateValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        double staminaValue = getStaminaValue(talentPoints);
        double staminaRechargeRateValue = getStaminaRechargeRateValue(talentPoints);
        double staminaValueForPaladin = getStaminaValueForPaladin(talentPoints);
        double staminaRechargeRateValueForPaladin = getStaminaRechargeRateValueForPaladin(talentPoints);
        double manaValue = getManaValue(talentPoints);
        double manaRechargeRateValue = getManaRechargeRateValue(talentPoints);

        PathOfThePaladinSubclass pathOfThePaladinSubclass = new PathOfThePaladinSubclass();
        if (pathOfThePaladinSubclass.isActive(player)) {
            applyModifier(player,
                    ModAttributes.MAX_STAMINA.get(),
                    getAttributeName("StaminaPaladin"),
                    staminaValueForPaladin,
                    AttributeModifier.Operation.ADDITION);
            applyModifier(player,
                    ModAttributes.STAMINA_RECHARGE.get(),
                    getAttributeName("StaminaRechargePaladin"),
                    staminaRechargeRateValueForPaladin,
                    AttributeModifier.Operation.MULTIPLY_TOTAL);
            applyModifier(player,
                    ModAttributes.MAX_MANA.get(),
                    getAttributeName("ManaPaladin"),
                    manaValue,
                    AttributeModifier.Operation.ADDITION);
            applyModifier(player,
                    ModAttributes.MANA_RECHARGE.get(),
                    getAttributeName("ManaRechargePaladin"),
                    manaRechargeRateValue,
                    AttributeModifier.Operation.MULTIPLY_TOTAL);
        } else {
            applyModifier(player,
                    ModAttributes.MAX_STAMINA.get(),
                    getAttributeName("Stamina"),
                    staminaValue,
                    AttributeModifier.Operation.ADDITION);
            applyModifier(player,
                    ModAttributes.STAMINA_RECHARGE.get(),
                    getAttributeName("StaminaRecharge"),
                    staminaRechargeRateValue,
                    AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return Arrays.asList(
                ModAttributes.MAX_STAMINA.get(),
                ModAttributes.STAMINA_RECHARGE.get(),
                ModAttributes.MAX_MANA.get(),
                ModAttributes.MANA_RECHARGE.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}