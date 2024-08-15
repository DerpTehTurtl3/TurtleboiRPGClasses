package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;

public class CombatVeteranTalent extends Talent {
    public static final String Name = "Combat Veteran";

    @Override
    public String getName() {
        return Name;
    }

    public static double getMeleeDamageValue(int points) {
        double[] meleeDamageValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, meleeDamageValues.length - 1));
        return meleeDamageValues[currentRankIndex];
    }

    public static double getAttackSpeedValue(int points) {
        double[] attackSpeedValues = {0.06, 0.12, 0.18, 0.24, 0.30};
        int currentRankIndex = Math.max(0, Math.min(points - 1, attackSpeedValues.length - 1));
        return attackSpeedValues[currentRankIndex];
    }

    public static double getHealthValue(int points) {
        double[] healthValues = {4.0, 8.0, 12.0, 16.0, 20.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, healthValues.length - 1));
        return healthValues[currentRankIndex];
    }

    public static double getArmorValue(int points) {
        double[] armorValues = {2.0, 3.0, 4.0, 5.0, 6.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, armorValues.length - 1));
        return armorValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);

        double meleeDamageValue = getMeleeDamageValue(talentPoints);
        double attackSpeedValue = getAttackSpeedValue(talentPoints);
        double healthValue = getHealthValue(talentPoints);
        double armorValue = getArmorValue(talentPoints);

        applyModifier(player,
                Attributes.ATTACK_DAMAGE,
                getAttributeName("Attack"),
                meleeDamageValue,
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                Attributes.ATTACK_SPEED,
                getAttributeName("AttackSpeed"),
                attackSpeedValue,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        applyModifier(player,
                Attributes.MAX_HEALTH,
                getAttributeName("Health"),
                healthValue,
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                Attributes.ARMOR,
                getAttributeName("Armor"),
                armorValue,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return Arrays.asList(
                Attributes.ATTACK_DAMAGE,
                Attributes.ATTACK_SPEED,
                Attributes.MAX_HEALTH,
                Attributes.ARMOR
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}