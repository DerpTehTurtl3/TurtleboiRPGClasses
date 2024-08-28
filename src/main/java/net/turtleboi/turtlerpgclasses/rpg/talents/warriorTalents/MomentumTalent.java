package net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;

import java.util.List;

public class MomentumTalent extends Talent {
    private static final String Name = Component.translatable("talents.momentum").getString();

    @Override
    public String getName() {
        return Name;
    }

    public static double getMoveSpeedValue(int points) {
        double[] moveSpeedValues = {0.05, 0.10, 0.18};
        int index = Math.max(0, Math.min(points - 1, moveSpeedValues.length - 1));
        return moveSpeedValues[index];
    }

    public static int getStrengthLevel(int points) {
        int[] strengthLevels = {1, 2, 3};
        int index = Math.max(0, Math.min(points - 1, strengthLevels.length - 1));
        return strengthLevels[index];
    }

    public static int getDuration(int points) {
        int[] durations = {120, 160, 200};
        int index = Math.max(0, Math.min(points - 1, durations.length - 1));
        return durations[index];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);

        double moveSpeedValue = getMoveSpeedValue(talentPoints);
        int strengthLevel = getStrengthLevel(talentPoints);
        int duration = getDuration(talentPoints);

        applyModifier(player,
                Attributes.MOVEMENT_SPEED,
                getAttributeName("MovementSpeed"),
                moveSpeedValue,
                AttributeModifier.Operation.ADDITION);
        player.addEffect(new MobEffectInstance(ModEffects.MOMENTUM.get(), duration, 0));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, strengthLevel - 1));
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                Attributes.MOVEMENT_SPEED
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}
