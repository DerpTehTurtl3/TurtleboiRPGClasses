package net.turtleboi.turtlerpgclasses.rpg.talents.rangerTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class VineWhipTalent extends Talent {
    private static final String Name = Component.translatable("talents.vine_whip").getString();

    @Override
    public String getName() {
        return Name;
    }

    public double getRootDuration(int points) {
        double[] rootDurationValues = {2.0, 3.0, 5.0, 7.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, rootDurationValues.length - 1));
        return rootDurationValues[currentRankIndex];
    }

    public int getSwiftnessAmplifier(int points) {
        int[] swiftnessAmplifierValues = {0, 0, 1, 1};
        int currentRankIndex = Math.max(0, Math.min(points - 1, swiftnessAmplifierValues.length - 1));
        return swiftnessAmplifierValues[currentRankIndex];
    }

    public String getSwiftnessComponent(int points) {
        String[] swiftComponents = {"Swiftness", "Swiftness", "Swiftness II", "Swiftness II"};
        int currentRankIndex = Math.max(0, Math.min(points - 1, swiftComponents.length - 1));
        return swiftComponents[currentRankIndex];
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
        return List.of();
    }
}