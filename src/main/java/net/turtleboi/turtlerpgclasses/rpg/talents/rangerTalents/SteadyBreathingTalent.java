package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.init.CoreAttributes;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class SteadyBreathingTalent extends Talent {
    private static final String Name = Component.translatable("talents.steady_breathing").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getDamage(int points) {
        return points;
    }

    @Override
    public void applyAttributes(Player player) {

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
        return List.of(
                Attributes.ATTACK_DAMAGE,
                CoreAttributes.RANGED_DAMAGE.get()
        );
    }
}