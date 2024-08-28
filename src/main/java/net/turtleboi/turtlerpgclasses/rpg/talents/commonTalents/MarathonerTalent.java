package net.turtleboi.turtlerpgclasses.rpg.talents.commonTalents;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.util.StringUtils;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.client.ClientClassData;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.rpg.classes.Ranger;
import net.turtleboi.turtlerpgclasses.rpg.classes.Warrior;
import net.turtleboi.turtlerpgclasses.rpg.talents.Talent;
import net.turtleboi.turtlerpgclasses.rpg.talents.warriorTalents.PathOfThePaladinSubclass;

import java.util.List;

public class MarathonerTalent extends Talent {
    private static final String Name = Component.translatable("talents.marathoner").getString();

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public ResourceLocation getIconTexture() {
        String playerClass = ClientClassData.getPlayerClass();
        if ("Warrior".equals(playerClass)) {
            return new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/talents/talent_icons/warriormarathoner_icon.png");
        } else if ("Ranger".equals(playerClass)) {
            return new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/talents/talent_icons/rangermarathoner_icon.png");
        }
        return null;
    }

    public double getStamina(int points) {
        double[] staminaValues = {2.0, 4.0, 6.0, 10.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaValues.length - 1));
        return staminaValues[currentRankIndex];
    }

    public double getEnergy(int points) {
        double[] staminaValues = {10.0, 20.0, 30.0, 50.0};
        int currentRankIndex = Math.max(0, Math.min(points - 1, staminaValues.length - 1));
        return staminaValues[currentRankIndex];
    }

    @Override
    public void applyAttributes(Player player) {
        int talentPoints = getPoints(player);
        if (new Warrior().isActive(player)) {
            applyModifier(player,
                    ModAttributes.MAX_STAMINA.get(),
                    getAttributeName("Stamina"),
                    getStamina(talentPoints),
                    AttributeModifier.Operation.ADDITION);
        } else if (new Ranger().isActive(player)){
            applyModifier(player,
                    ModAttributes.MAX_ENERGY.get(),
                    getAttributeName("Energy"),
                    getEnergy(talentPoints),
                    AttributeModifier.Operation.ADDITION);
        }
    }

    @Override
    public void applyEffectAttributes(Player player) {

    }

    @Override
    public List<Attribute> getRPGAttributes() {
        return List.of(
                ModAttributes.MAX_STAMINA.get(),
                ModAttributes.MAX_ENERGY.get()
        );
    }

    @Override
    public List<Attribute> getRPGEffectAttributes() {
        return List.of();
    }
}