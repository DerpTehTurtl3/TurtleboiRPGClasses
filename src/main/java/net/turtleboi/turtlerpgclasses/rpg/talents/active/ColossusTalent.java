package net.turtleboi.turtlerpgclasses.rpg.talents.active;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColossusTalent extends ActiveAbility{
    private static final String Name = "Colossus";

    @Override
    public void applyAttributes(Player player) {

    }

    public static double getAttack() {
        return 0.33;
    }

    public static double getDamageResistance() {
        return -34.0;
    }

    @Override
    public void applyEffectAttributes(Player player) {
        applyEffectModifier(player,
                Attributes.ATTACK_DAMAGE,
                getEffectAttributeName("Attack", "colossus"),
                getAttack(),
                AttributeModifier.Operation.MULTIPLY_TOTAL);
        applyEffectModifier(player,
                ModAttributes.DAMAGE_RESISTANCE.get(),
                getEffectAttributeName("DamageResistance", "colossus"),
                getDamageResistance(),
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of();
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return Arrays.asList(
                Attributes.ATTACK_DAMAGE,
                ModAttributes.DAMAGE_RESISTANCE.get()
        );
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public Map<String, Integer> getResourceCosts(Player player) {
        Map<String, Integer> costs = new HashMap<>();
        costs.put("stamina", 10);
        return costs;
    }

    @Override
    public int getCooldownSeconds() {
        return 60;
    }

    @Override
    public boolean activate(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.COLOSSUS.get(), getDuration(), 0));
        return true;
    }

    @Override
    public SoundEvent[] getAbilitySounds() {
        return new SoundEvent[0];
    }

    @Override
    public void spawnAbilityEntity(Player player, Level level) {

    }

    @Override
    public int getDuration() {
        return 300;
    }
}
