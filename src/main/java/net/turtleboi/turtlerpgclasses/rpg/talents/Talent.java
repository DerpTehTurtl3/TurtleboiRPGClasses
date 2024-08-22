package net.turtleboi.turtlerpgclasses.rpg.talents;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.turtleboi.turtlecore.util.StringUtils;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.capabilities.talents.PlayerAbilityProvider;
import net.turtleboi.turtlerpgclasses.capabilities.talents.TalentStates;
import net.turtleboi.turtlerpgclasses.capabilities.talents.TalentStatesProvider;
import net.turtleboi.turtlerpgclasses.client.ui.talenttrees.talentnodes.TalentButton;

import java.util.*;

public abstract class Talent {

    public abstract String getName();

    public String getTalentName() {
        return StringUtils.sanitizeString(getName()) + "Talent";
    }

    private UUID getTalentUUID(Player player) {
        return player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY)
                .map(capability -> capability.getTalentUUID(getTalentName()))
                .orElse(UUID.randomUUID());
    }

    public String getAttributeName(String attributeSuffix) {
        return "RPGTalent_" + getTalentName() + "_" + attributeSuffix;
    }

    public String getEffectAttributeName(String attributeSuffix, String effectName) {
        return "RPGTalent_" + attributeSuffix + "_" + effectName + "_effect";
    }

    public int getPoints(Player player) {
        return player.getCapability(TalentStatesProvider.TALENT_STATES).map(capability -> TalentStates.getPoints(getTalentName())).orElse(0);
    }

    public TalentButton.TalentState getState(Player player) {
        return player.getCapability(TalentStatesProvider.TALENT_STATES).map(capability -> TalentStates.getState(getTalentName())).orElse(TalentButton.TalentState.LOCKED);
    }

    public boolean isActive(Player player) {
        return getState(player) == TalentButton.TalentState.ACTIVE;
    }

    public void setActive(Player player) {
        player.getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(talentStates ->
                TalentStates.setState(getTalentName(), TalentButton.TalentState.ACTIVE)
        );
    }

    public boolean isLocked(Player player) {
        TalentButton.TalentState state = getState(player);
        return state == TalentButton.TalentState.LOCKED || state == TalentButton.TalentState.UNIQUE_LOCKED;
    }

    public void setLocked(Player player) {
        player.getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(talentStates ->
                TalentStates.setState(getTalentName(), TalentButton.TalentState.LOCKED)
        );
    }

    public boolean isUnlocked(Player player) {
        return getState(player) == TalentButton.TalentState.UNLOCKED;
    }

    public void resetTalent(Player player) {
        player.getCapability(TalentStatesProvider.TALENT_STATES).ifPresent(talentStates -> {
            TalentStates.setPoints(getTalentName(), 0);
            TalentStates.setState(getTalentName(), TalentButton.TalentState.LOCKED);
        });
    }

    public void applyModifier(Player player, Attribute attribute, String name, double value, AttributeModifier.Operation operation) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null) {
            UUID modifierUUID = getTalentUUID(player);
            if (attributeInstance.getModifier(modifierUUID) != null) {
                attributeInstance.removeModifier(modifierUUID);
            }
            attributeInstance.addPermanentModifier(new AttributeModifier(modifierUUID, name, value, operation));
        }
    }

    public void applyEffectModifier(Player player, Attribute attribute, String name, double value, AttributeModifier.Operation operation) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null) {
            UUID modifierUUID = UUID.nameUUIDFromBytes((name).getBytes());
            AttributeModifier existingModifier = attributeInstance.getModifier(modifierUUID);
            if (existingModifier != null) {
                attributeInstance.removeModifier(modifierUUID);
            }
            AttributeModifier newModifier = new AttributeModifier(modifierUUID, name, value, operation);
            attributeInstance.addTransientModifier(newModifier);
        }
    }

    public abstract void applyAttributes(Player player);

    public abstract void applyEffectAttributes(Player player);

    public abstract List<Attribute> getRPGAttributes();

    public abstract List<Attribute> getRPGEffectAttributes();

    protected Set<MobCategory> getAllowedCategories() {
        return null;
    }

    public boolean isAllowedCategory(MobCategory category) {
        return getAllowedCategories().contains(category);
    }

    public void removeModifier(Player player) {
        for (Attribute attribute : getRPGAttributes()) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance != null) {
                for (AttributeModifier modifier : attributeInstance.getModifiers()) {
                    if (modifier.getName().startsWith("RPGTalent_" + getTalentName())) {
                        attributeInstance.removeModifier(modifier);
                    }
                }
            }
        }
    }

    public void removeEffectModifier(Player player, String effectName) {
        for (Attribute attribute : getRPGEffectAttributes()) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance != null) {
                for (AttributeModifier modifier : attributeInstance.getModifiers()) {
                    if (modifier.getName().endsWith(effectName +"_effect")) {
                        attributeInstance.removeModifier(modifier);
                    }
                }
            }
        }
    }

    public ResourceLocation getIconTexture() {
        return new ResourceLocation(TurtleRPGClasses.MOD_ID, "textures/gui/talents/talent_icons/" + StringUtils.sanitizeName(getName()) + "_icon.png");
    }
}
