package net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.active;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.rpg.talents.ActiveAbility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SteelBarbsTalent extends ActiveAbility {
    private static final String Name = Component.translatable("talents.subclass.juggernaut.steel_barbs").getString();

    @Override
    public ResourceLocation getAbilityIcon() {
        return new ResourceLocation(TurtleRPGClasses.MOD_ID,
                "textures/gui/talents/talent_icons/pathofthejuggernautsubclass_icon.png");
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public Map<String, Integer> getResourceCosts(Player player) {
        Map<String, Integer> costs = new HashMap<>();
        costs.put("stamina", 6);
        return costs;
    }

    @Override
    public int getCooldownSeconds() {
        return 30;
    }

    @Override
    public boolean activate(Player player) {
        int duration = getDuration();
        player.addEffect(new MobEffectInstance(ModEffects.STEEL_BARBS.get(), duration, 0));
        return true;
    }

    @Override
    public SoundEvent[] getAbilitySounds() {
        return new SoundEvent[]{SoundEvents.ANVIL_USE};
    }

    @Override
    public void spawnAbilityEntity(Player player, Level level) {
    }

    @Override
    public int getDuration() {
        return 300;
    }

    public double getArmor() {
        return 15.0;
    }

    @Override
    public void applyAttributes(Player player) {

    }

    @Override
    public void applyEffectAttributes(Player player) {
        applyEffectModifier(player,
                Attributes.ARMOR,
                getEffectAttributeName("Armor", "steelBarbs"),
                getArmor(),
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of(
                Attributes.ARMOR
        );
    }
}
