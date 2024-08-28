package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class RenownedHunterTalent extends Talent {
    private static final String Name = Component.translatable("talents.renowned_hunter").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getRangeDamage(int points) {
        double[] rangeDamageValues = {1.0, 1.5, 2.0, 2.5, 3.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, rangeDamageValues.length - 1));
        return rangeDamageValues[currentRankIndex];
    }

    public double getAttackSpeed(int points) {
        double[] attackSpeedValues = {6.0, 12.0, 18.0, 24.0, 30.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, attackSpeedValues.length - 1));
        return attackSpeedValues[currentRankIndex];
    }

    public double getCriticalHitChance(int points) {
        double[] criticalHitChanceValues = {5.0, 7.0, 10.0, 15.0, 25.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, criticalHitChanceValues.length - 1));
        return criticalHitChanceValues[currentRankIndex];
    }

    public double getCriticalHitDamage(int points) {
        double[] criticalHitDamageValues = {10.0, 15.0, 22.5, 35.0, 50.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, criticalHitDamageValues.length - 1));
        return criticalHitDamageValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);

        applyModifier(player,
                CoreAttributes.RANGED_DAMAGE.get(),
                getAttributeName("RangedDamage"),
                getRangeDamage(talentPoints),
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                Attributes.ATTACK_SPEED,
                getAttributeName("AttackSpeed"),
                getAttackSpeed(talentPoints)/100,
                AttributeModifier.Operation.MULTIPLY_BASE);
        applyModifier(player,
                CoreAttributes.CRITICAL_CHANCE.get(),
                getAttributeName("CriticalChance"),
                getCriticalHitChance(talentPoints),
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                CoreAttributes.CRITICAL_DAMAGE.get(),
                getAttributeName("CriticalDamage"),
                getCriticalHitDamage(talentPoints)/100,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                CoreAttributes.RANGED_DAMAGE.get(),
                Attributes.ATTACK_SPEED,
                CoreAttributes.CRITICAL_CHANCE.get(),
                CoreAttributes.CRITICAL_DAMAGE.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}