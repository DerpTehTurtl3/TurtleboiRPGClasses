package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents.active;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.turtleboi.turtlerpgclasses.rpg.talents.ActiveAbility;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;
import java.util.Map;

public class HuntersMarkTalent extends ActiveAbility {
    private static final String Name = Component.translatable("talents.hunters_mark").getString();

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public Map<String, Integer> getResourceCosts(Player player) {
        return Map.of();
    }

    @Override
    public int getCooldownSeconds() {
        return 0;
    }

    @Override
    public boolean activate(Player player) {
        return false;
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
        return 0;
    }

    public static double getHealthValue(int points) {
        return points * 2;
    }

    @Override
    public void applyAttributes(Player player) {
        //int talentPoints = getPoints(player);
        //double healthValue = getHealthValue(talentPoints);
//
        //applyModifier(player,
        //        Attributes.MAX_HEALTH,
        //        getAttributeName("Health"),
        //        healthValue,
        //        AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                //Attributes.MAX_HEALTH
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}