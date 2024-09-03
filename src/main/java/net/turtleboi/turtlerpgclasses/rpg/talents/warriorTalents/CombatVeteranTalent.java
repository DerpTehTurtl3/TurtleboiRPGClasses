package net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.Arrays;
import java.util.List;

public class CombatVeteranTalent extends Talent {
    private static final String Name = Component.translatable("talents.combat_veteran").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getAttackDamage(int points) {
        double[] attackDamageValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, attackDamageValues.length - 1));
        return attackDamageValues[currentRankIndex];
    }

    public double getAttackSpeed(int points) {
        double[] attackSpeedValues = {6.0, 12.0, 18.0, 24.0, 30.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, attackSpeedValues.length - 1));
        return attackSpeedValues[currentRankIndex];
    }

    public double getHealth(int points) {
        double[] healthValues = {4.0, 8.0, 12.0, 16.0, 20.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, healthValues.length - 1));
        return healthValues[currentRankIndex];
    }

    public double getArmor(int points) {
        double[] armorValues = {2.0, 3.0, 4.0, 5.0, 6.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, armorValues.length - 1));
        return armorValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);

        applyModifier(player,
                Attributes.ATTACK_DAMAGE,
                getAttributeName("Attack"),
                getAttackDamage(talentPoints),
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                Attributes.ATTACK_SPEED,
                getAttributeName("AttackSpeed"),
                getAttackSpeed(talentPoints)/100,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        applyModifier(player,
                Attributes.MAX_HEALTH,
                getAttributeName("Health"),
                getHealth(talentPoints),
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                Attributes.ARMOR,
                getAttributeName("Armor"),
                getArmor(talentPoints),
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