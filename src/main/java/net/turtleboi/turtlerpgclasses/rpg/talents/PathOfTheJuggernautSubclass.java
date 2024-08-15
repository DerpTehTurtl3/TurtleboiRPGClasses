package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class PathOfTheJuggernautSubclass extends Talent{
    public static final String Name = "Path of the Juggernaut Subclass";

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void applyAttributes(Player player) {

    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of();
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }

    public static String getSubclassName(){return "Juggernaut";}
}


