package net.turtleboi.turtlerpgclasses.rpg.talents.active;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.effect.effects.GuardiansOathEffect;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.util.PartyUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuardiansOathTalent extends ActiveAbility {
    private static final String Name = "Word of Honor";

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public Map<String, Integer> getResourceCosts(Player player) {
        Map<String, Integer> costs = new HashMap<>();
        costs.put("stamina", 15);
        return costs;
    }

    @Override
    public int getCooldownSeconds() {
        return 120;
    }

    @Override
    public boolean activate(Player player) {
        applyBastionEffects(player);
        return true;
    }

    public void applyGuardianOathEffects(Player player) {
        Level level = player.level;
        double radius = 5.0;
        AABB aabb = new AABB(player.blockPosition()).inflate(radius);

        level.getEntitiesOfClass(Player.class, aabb).forEach(ally -> {
            if (PartyUtils.isAlly(player, ally) || ally == player) {
                if (!ally.hasEffect(ModEffects.GUARDIANS_OATH.get())) {
                    ally.addEffect(new MobEffectInstance(ModEffects.GUARDIANS_OATH.get(), 60, 0));
                } else {
                    GuardiansOathEffect.updateEffectDuration(player, 60);
                }
            }
        });
    }

    private void applyBastionEffects(Player player) {
        Level level = player.level;
        double radius = 10.0;
        AABB aabb = new AABB(player.blockPosition()).inflate(radius);

        level.getEntitiesOfClass(Player.class, aabb).forEach(ally -> {
            if (PartyUtils.isAlly(player, ally) || ally == player) {
                ally.addEffect(new MobEffectInstance(ModEffects.BASTION.get(), getDuration(), 0));
            }
        });
    }

    @Override
    public SoundEvent[] getAbilitySounds() {
        return new SoundEvent[]{SoundEvents.PLAYER_LEVELUP};
    }

    @Override
    public void spawnAbilityEntity(Player player, Level level) {

    }

    @Override
    public int getDuration() {
        return 300;
    }

    public double getResistance() {
        return -20.0;
    }

    public double getArmorToughness() {
        return 4.0;
    }

    public double getKnockbackResistance() {
        return 0.20;
    }

    public double getMaxHealth() {
        return 8.0;
    }

    public double getHealingEffectiveness() {
        return 0.15;
    }

    public double getArmor() {
        return 10.0;
    }

    public double getHealPoints() {
        return 4.0;
    }

    public double getAbsorbPoints() {
        return 4.0;
    }

    public double getMaxAbsorbPoints() {
        return 20.0;
    }

    public double getBastionDamageResistance(){
        return -40.0;
    }

    public double getBastionHealingEffectiveness(){
        return 0.20;
    }

    public double getBastionAbsorption(){
        return 60.0;
    }

    public double getBastionHealPerSecond(){
        return 4.0;
    }

    @Override
    public void applyAttributes(Player player) {
        applyModifier(player,
                ModAttributes.DAMAGE_RESISTANCE.get(),
                getAttributeName("DamageResistance"),
                getResistance(),
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                Attributes.ARMOR_TOUGHNESS,
                getAttributeName("ArmorToughness"),
                getArmorToughness(),
                AttributeModifier.Operation.ADDITION);
        applyModifier(player,
                ModAttributes.HEALING_EFFECTIVENESS.get(),
                getAttributeName("HealingEffectiveness"),
                getHealingEffectiveness(),
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        applyModifier(player,
                Attributes.KNOCKBACK_RESISTANCE,
                getAttributeName("MovementSpeed"),
                getKnockbackResistance(),
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        applyModifier(player,
                Attributes.MAX_HEALTH,
                getAttributeName("Health"),
                getMaxHealth(),
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {
        applyEffectModifier(player,
                Attributes.ARMOR,
                getEffectAttributeName("Armor", "guardiansOath"),
                getArmor(),
                AttributeModifier.Operation.ADDITION);
    }

    public void applyBastionAttributes(Player player) {
        applyEffectModifier(player,
                Attributes.ARMOR,
                getEffectAttributeName("Armor", "bastion"),
                getArmor(),
                AttributeModifier.Operation.ADDITION);
        applyEffectModifier(player,
                ModAttributes.DAMAGE_RESISTANCE.get(),
                getEffectAttributeName("DamageResistance", "bastion"),
                getBastionDamageResistance(),
                AttributeModifier.Operation.ADDITION);
        applyEffectModifier(player,
                ModAttributes.HEALING_EFFECTIVENESS.get(),
                getEffectAttributeName("HealingEffectiveness", "bastion"),
                getBastionHealingEffectiveness(),
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return Arrays.asList(
                Attributes.ARMOR,
                Attributes.ARMOR_TOUGHNESS,
                Attributes.KNOCKBACK_RESISTANCE,
                Attributes.MAX_HEALTH,
                ModAttributes.DAMAGE_RESISTANCE.get(),
                ModAttributes.HEALING_EFFECTIVENESS.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return Arrays.asList(
                Attributes.ARMOR,
                ModAttributes.DAMAGE_RESISTANCE.get(),
                ModAttributes.HEALING_EFFECTIVENESS.get()
        );
    }
}
