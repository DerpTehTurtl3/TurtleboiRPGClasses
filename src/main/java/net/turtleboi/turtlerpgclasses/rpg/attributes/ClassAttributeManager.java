package net.turtleboi.turtlerpgclasses.rpg.attributes;

import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import net.turtleboi.turtlerpgclasses.capabilities.PlayerClassProvider;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;
import net.turtleboi.turtlerpgclasses.rpg.classes.Warrior;
import net.turtleboi.turtlerpgclasses.rpg.classes.Ranger;
import net.turtleboi.turtlerpgclasses.rpg.classes.Mage;
import net.turtleboi.turtlerpgclasses.rpg.talents.*;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.GuardiansOathTalent;
import net.turtleboi.turtlerpgclasses.rpg.talents.active.WarlordsPresenceTalent;

import java.util.*;

public class ClassAttributeManager {
    public static void applyClassAttributes(Player player, String className) {
        resetAttributes(player);
        switch (className) {
            case "Warrior":
                new Warrior().applyAttributes(player);
                break;
            case "Ranger":
                new Ranger().applyAttributes(player);
                break;
            case "Mage":
                new Mage().applyAttributes(player);
                break;
        }
    }

    public static void applyTalentAttributes(Player player) {
        VigorTalent vigorTalent = new VigorTalent();
        if (vigorTalent.getPoints(player) >= 1) {
            vigorTalent.applyAttributes(player);
        } else if (vigorTalent.getPoints(player) == 0) {
            vigorTalent.removeModifier(player);
        }

        MightyBlowsTalent mightyBlowsTalent = new MightyBlowsTalent();
        if (mightyBlowsTalent.getPoints(player) >= 1){
            mightyBlowsTalent.applyAttributes(player);
        } else if (mightyBlowsTalent.getPoints(player) == 0) {
            mightyBlowsTalent.removeModifier(player);
        }

        BattleHardenedTalent battleHardenedTalent = new BattleHardenedTalent();
        if (battleHardenedTalent.getPoints(player) >= 1){
            battleHardenedTalent.applyAttributes(player);
        } else if (battleHardenedTalent.getPoints(player) == 0) {
            battleHardenedTalent.removeModifier(player);
        }

        SteadyFootingTalent steadyFootingTalent = new SteadyFootingTalent();
        if (steadyFootingTalent.getPoints(player) >= 1) {
            steadyFootingTalent.applyAttributes(player);
        } else if (steadyFootingTalent.getPoints(player) == 0) {
            steadyFootingTalent.removeModifier(player);
        }

        SwiftHandsTalent swiftHandsTalent = new SwiftHandsTalent();
        if (swiftHandsTalent.getPoints(player) >= 1) {
            swiftHandsTalent.applyAttributes(player);
        } else if (swiftHandsTalent.getPoints(player) == 0) {
            swiftHandsTalent.removeModifier(player);
        }

        PathOfTheBarbarianSubclass pathOfTheBarbarianSubclass = new PathOfTheBarbarianSubclass();
        PathOfTheJuggernautSubclass pathOfTheJuggernautSubclass = new PathOfTheJuggernautSubclass();
        PathOfThePaladinSubclass pathOfThePaladinSubclass = new PathOfThePaladinSubclass();

        if (pathOfTheBarbarianSubclass.isActive(player)) {
            player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerSubclass ->
                    playerSubclass.setRpgSubclass(PathOfTheBarbarianSubclass.getSubclassName()));
        } else if (pathOfTheJuggernautSubclass.isActive(player)) {
            player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerSubclass ->
                    playerSubclass.setRpgSubclass(PathOfTheJuggernautSubclass.getSubclassName()));
        } else if (pathOfThePaladinSubclass.isActive(player)) {
            player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerSubclass ->
                    playerSubclass.setRpgSubclass(PathOfThePaladinSubclass.getSubclassName()));
            pathOfThePaladinSubclass.applyAttributes(player);
        } else {
            player.getCapability(PlayerClassProvider.PLAYER_RPGCLASS).ifPresent(playerSubclass ->
                    playerSubclass.setRpgSubclass("No Subclass"));
            pathOfThePaladinSubclass.removeModifier(player);
        }

        MarathonerTalent marathonerTalent = new MarathonerTalent();
        if (marathonerTalent.getPoints(player) >= 1){
            marathonerTalent.applyAttributes(player);
        } else if (marathonerTalent.getPoints(player) == 0) {
            marathonerTalent.removeModifier(player);
        }

        StaminaMasteryTalent staminaMasteryTalent = new StaminaMasteryTalent();
        if (staminaMasteryTalent.getPoints(player) >= 1) {
            staminaMasteryTalent.applyAttributes(player);
        } else if (staminaMasteryTalent.getPoints(player) == 0) {
            staminaMasteryTalent.removeModifier(player);
        }

        QuickRecoveryTalent quickRecoveryTalent = new QuickRecoveryTalent();
        if (quickRecoveryTalent.getPoints(player) >= 1){
            quickRecoveryTalent.applyAttributes(player);
        } else if (quickRecoveryTalent.getPoints(player) == 0) {
            quickRecoveryTalent.removeModifier(player);
        }

        LifeLeechTalent lifeLeechTalent = new LifeLeechTalent();
        if (lifeLeechTalent.getPoints(player) >= 1){
            lifeLeechTalent.applyAttributes(player);
        } else if (lifeLeechTalent.getPoints(player) == 0) {
            lifeLeechTalent.removeModifier(player);
        }

        BrawlersTenacityTalent brawlersTenacityTalent = new BrawlersTenacityTalent();
        if (brawlersTenacityTalent.getPoints(player) >= 1){
            brawlersTenacityTalent.applyValues(player);
        } else if (brawlersTenacityTalent.getPoints(player) == 0) {
            brawlersTenacityTalent.removeModifier(player);
        }

        CombatVeteranTalent combatVeteranTalent = new CombatVeteranTalent();
        if (combatVeteranTalent.getPoints(player) >= 1){
            combatVeteranTalent.applyAttributes(player);
        } else if (combatVeteranTalent.getPoints(player) == 0) {
            combatVeteranTalent.removeModifier(player);
        }

        WarlordsPresenceTalent warlordsPresenceTalent = new WarlordsPresenceTalent();
        if (warlordsPresenceTalent.getPoints(player) >= 1){
            warlordsPresenceTalent.applyAttributes(player);
        } else if (warlordsPresenceTalent.getPoints(player) == 0) {
            warlordsPresenceTalent.removeModifier(player);
        }

        GuardiansOathTalent guardiansOathTalent = new GuardiansOathTalent();
        if (guardiansOathTalent.getPoints(player) >= 1){
            guardiansOathTalent.applyAttributes(player);
        } else if (guardiansOathTalent.getPoints(player) == 0) {
            guardiansOathTalent.removeModifier(player);
        }
    }

    private static final List<Attribute> RPG_ATTRIBUTES = Arrays.asList(
            Attributes.MAX_HEALTH,
            Attributes.MOVEMENT_SPEED,
            Attributes.ATTACK_DAMAGE,
            Attributes.ATTACK_SPEED,
            Attributes.ARMOR,
            Attributes.ARMOR_TOUGHNESS,
            Attributes.KNOCKBACK_RESISTANCE,
            ModAttributes.RANGED_DAMAGE.get(),
            ModAttributes.MAX_STAMINA.get(),
            ModAttributes.MAX_ENERGY.get(),
            ModAttributes.MAX_MANA.get(),
            ModAttributes.STAMINA_RECHARGE.get(),
            ModAttributes.ENERGY_RECHARGE.get(),
            ModAttributes.MANA_RECHARGE.get(),
            ModAttributes.CRITICAL_DAMAGE.get(),
            ModAttributes.CRITICAL_CHANCE.get(),
            ModAttributes.DAMAGE_RESISTANCE.get(),
            ModAttributes.LIFE_STEAL.get(),
            ModAttributes.HEALING_EFFECTIVENESS.get()
    );

    public static void resetAttributes(Player player) {
        for (Attribute attribute : RPG_ATTRIBUTES) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance != null) {
                Collection<AttributeModifier> modifiers = new ArrayList<>(attributeInstance.getModifiers());
                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getName().startsWith("RPGClass_")) {
                        attributeInstance.removeModifier(modifier.getId());
                    }
                    if (modifier.getName().startsWith("RPGTalents_")) {
                        attributeInstance.removeModifier(modifier.getId());
                    }
                }
            }
        }
    }
}