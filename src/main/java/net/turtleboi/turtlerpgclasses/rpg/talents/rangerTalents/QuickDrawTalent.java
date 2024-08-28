package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class QuickDrawTalent extends Talent {
    private static final String Name = Component.translatable("talents.quick_draw").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getDrawSpeed(int points) {
        double[] drawSpeedValues = {5.0, 12.0, 20.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, drawSpeedValues.length - 1));
        return drawSpeedValues[currentRankIndex];
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
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}