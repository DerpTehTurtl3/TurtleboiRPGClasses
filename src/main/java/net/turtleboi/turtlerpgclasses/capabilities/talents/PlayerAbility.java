package net.turtleboi.turtlerpgclasses.capabilities.talents;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AutoRegisterCapability
public class PlayerAbility {
    private int brawlerArmorBonus;
    private int brawlerArmorTimer;
    private int brawlerHitCounter;
    private int maxBrawlerArmorBonus;
    private boolean brawlersTenacityTriggered = false;

    private LivingEntity targetEntity;
    private boolean isCharging = false;
    private int chargeTicks = 0;
    private Vec3 lastChargePosition;
    private Vec3 strafeDirection;
    private String lastObstacle;

    private boolean executeActive;

    private boolean isStampeding = false;
    private int stampedeTicks = 0;

    private boolean isTaunting = false;

    private int focusedStrikesCounter;
    private double focusedStrikesTimer;
    private int focusedStrikesThreshold = 0;
    private int focusedStrikesDamage = 0;
    private int focusedStrikesMaxDamage = 0;
    public int currentBonusDamage;

    private int windedEffectDuration = 0;
    private int secondWindSpeedDuration = 0;
    private boolean secondWindTriggered = false;

    private boolean warlordRallyTriggered = false;
    private int exhaustedDuration = 0;

    private double lifeLeechPercentage;

    private Map<String, UUID> talentUUIDs = new HashMap<>();

    public boolean hasTalentUUID(String talentName) {
        return talentUUIDs.containsKey(talentName);
    }

    public UUID getTalentUUID(String talentName) {
        if (!hasTalentUUID(talentName)) {
            UUID uuid = UUID.randomUUID();
            setTalentUUID(talentName, uuid);
            return uuid;
        }
        return talentUUIDs.get(talentName);
    }

    public void setTalentUUID(String talentName, UUID uuid) {
        talentUUIDs.put(talentName, uuid);
    }

    // Getter for the target
    public LivingEntity getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(LivingEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    //Brawler's Tenacity Talent
    public int getBrawlerArmorBonus() {
        return brawlerArmorBonus;
    }

    public void setBrawlerArmorBonus(int brawlerArmorBonus) {
        this.brawlerArmorBonus = brawlerArmorBonus;
    }

    public void setBrawlerHitCounter(int brawlerHitCounter) {
        this.brawlerHitCounter = brawlerHitCounter;
    }

    public int getBrawlerHitCounter() {
        return brawlerHitCounter;
    }

    public void incrementBrawlerHitCounter() {
        this.brawlerHitCounter++;
    }

    public int getMaxBrawlerArmorBonus() {
        return maxBrawlerArmorBonus;
    }

    public void setMaxBrawlerArmorBonus(int maxBrawlerArmorBonus) {
        this.maxBrawlerArmorBonus = maxBrawlerArmorBonus;
    }

    //Charge Ability
    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    public int getChargeTicks() {
        return chargeTicks;
    }

    public void setChargeTicks(int chargeTicks) {
        this.chargeTicks = chargeTicks;
    }

    public void incrementChargeTicks() {
        this.chargeTicks++;
    }

    public Vec3 getLastChargePosition() {
        return lastChargePosition;
    }

    public void setLastChargePosition(Vec3 lastChargePosition) {
        this.lastChargePosition = lastChargePosition;
    }

    public Vec3 getStrafeDirection() {
        return strafeDirection;
    }

    public void setStrafeDirection(Vec3 strafeDirection) {
        this.strafeDirection = strafeDirection;
    }

    public void setLastObstacle(String obstacle) {
        this.lastObstacle = obstacle;
    }

    public String getLastObstacle() {
        return lastObstacle;
    }

    //Execute Ability
    public boolean isExecuteActive() {
        return executeActive;
    }

    public void setExecuteActive(boolean executeActive) {
        this.executeActive = executeActive;
    }

    //Taunt Ability
    public boolean isTaunting() {
        return isTaunting;
    }

    public void setTaunting(boolean taunting) {
        isTaunting = taunting;
    }

    //Second Wind Talent
    public boolean isSecondWindTriggered() {
        return secondWindTriggered;
    }

    public void setSecondWindTriggered(boolean triggered) {
        this.secondWindTriggered = triggered;
    }

    public void setWindedDuration(int ticks) {
        this.windedEffectDuration = ticks;
    }

    public int getWindedDuration() {
        return this.windedEffectDuration;
    }

    //Warlord's Presence Talent
    public boolean isRallyTriggered() {
        return warlordRallyTriggered;
    }

    public void setRallyTriggered(boolean triggered) {
        this.warlordRallyTriggered = triggered;
    }

    public void setDefeatedDuration(int ticks) {
        this.exhaustedDuration = ticks;
    }

    public int getExhaustedDuration() {
        return this.exhaustedDuration;
    }

    //Focused Strikes Talent
    public void incrementFocusedStrikesCounter() {
        this.focusedStrikesCounter++;
    }

    public void resetFocusedStrikes() {
        this.focusedStrikesCounter = 0;
        this.currentBonusDamage = 0;
    }

    public void setFocusedStrikesCounter(int counter) {
        this.focusedStrikesCounter = counter;
    }

    public int getFocusedStrikesCounter() {
        return focusedStrikesCounter;
    }

    public int getFocusedStrikesThreshold() {
        return focusedStrikesThreshold;
    }

    public void setFocusedStrikesThreshold(int threshold) {
        this.focusedStrikesThreshold = threshold;
    }

    public int getFocusedStrikesDamage() {
        return focusedStrikesDamage;
    }

    public void setFocusedStrikesDamage(int damage) {
        this.focusedStrikesDamage = damage;
    }

    public int getFocusedStrikesMaxDamage() {
        return focusedStrikesMaxDamage;
    }

    public void setFocusedStrikesMaxDamage(int maxDamage) {
        this.focusedStrikesMaxDamage = maxDamage;
    }

    public int getCurrentBonusDamage() {
        return currentBonusDamage;
    }

    public void addBonusDamage(int bonus) {
        this.currentBonusDamage = Math.min(this.currentBonusDamage + bonus, this.focusedStrikesMaxDamage);
    }

    //Stampede Talent
    public boolean isStampeding() {
        return isStampeding;
    }

    public void setStampeding(boolean stampeding) {
        isStampeding = stampeding;
    }

    public int getStampedeTicks() {
        return stampedeTicks;
    }

    public void setStampedeTicks(int stampedeTicks) {
        this.stampedeTicks = stampedeTicks;
    }

    public void incrementStampedeTicks() {
        this.stampedeTicks++;
    }

    public void copyFrom(PlayerAbility source) {
        this.brawlerArmorBonus = source.brawlerArmorBonus;
        this.brawlerArmorTimer = source.brawlerArmorTimer;
        this.brawlerHitCounter = source.brawlerHitCounter;
        this.maxBrawlerArmorBonus = source.maxBrawlerArmorBonus;
        this.brawlersTenacityTriggered = source.brawlersTenacityTriggered;

        this.focusedStrikesCounter = source.focusedStrikesCounter;
        this.focusedStrikesTimer = source.focusedStrikesTimer;
        this.focusedStrikesDamage = source.focusedStrikesDamage;
        this.focusedStrikesMaxDamage = source.focusedStrikesMaxDamage;
        this.currentBonusDamage = source.currentBonusDamage;
        this.focusedStrikesThreshold = source.focusedStrikesThreshold;

        this.windedEffectDuration = source.windedEffectDuration;
        this.secondWindSpeedDuration = source.secondWindSpeedDuration;
        this.secondWindTriggered = source.secondWindTriggered;

        this.warlordRallyTriggered = source.warlordRallyTriggered;
        this.exhaustedDuration = source.exhaustedDuration;

        this.lifeLeechPercentage = source.lifeLeechPercentage;

        this.talentUUIDs = new HashMap<>(source.talentUUIDs);
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("brawlerArmorBonus", brawlerArmorBonus);
        nbt.putInt("brawlerArmorTimer", brawlerArmorTimer);
        nbt.putInt("brawlerHitCounter", brawlerHitCounter);
        nbt.putInt("maxBrawlerArmorBonus", maxBrawlerArmorBonus);
        nbt.putBoolean("brawlersTenacityTriggered", brawlersTenacityTriggered);

        nbt.putInt("focusedStrikesCounter", focusedStrikesCounter);
        nbt.putDouble("focusedStrikesTimer", focusedStrikesTimer);
        nbt.putInt("focusedStrikesThreshold", focusedStrikesThreshold);
        nbt.putInt("focusedStrikesDamage", focusedStrikesDamage);
        nbt.putInt("focusedStrikesMaxDamage", focusedStrikesMaxDamage);
        nbt.putInt("currentBonusDamage", currentBonusDamage);

        nbt.putInt("windedEffectDuration", windedEffectDuration);
        nbt.putInt("secondWindSpeedDuration", secondWindSpeedDuration);
        nbt.putBoolean("secondWindTriggered", secondWindTriggered);

        nbt.putBoolean("warlordRallyTriggered", warlordRallyTriggered);
        nbt.putInt("warlordRallyCooldown", exhaustedDuration);

        nbt.putDouble("lifeLeechPercentage", lifeLeechPercentage);

        CompoundTag uuidTag = new CompoundTag();
        for (Map.Entry<String, UUID> entry : talentUUIDs.entrySet()) {
            uuidTag.putUUID(entry.getKey(), entry.getValue());
        }
        nbt.put("TalentUUIDs", uuidTag);
    }

    public void loadNBTData(CompoundTag nbt) {
        brawlerArmorBonus = nbt.getInt("brawlerArmorBonus");
        brawlerArmorTimer = nbt.getInt("brawlerArmorTimer");
        brawlerHitCounter = nbt.getInt("brawlerHitCounter");
        maxBrawlerArmorBonus = nbt.getInt("maxBrawlerArmorBonus");
        brawlersTenacityTriggered = nbt.getBoolean("brawlersTenacityTriggered");

        focusedStrikesCounter = nbt.getInt("focusedStrikesCounter");
        focusedStrikesTimer = nbt.getDouble("focusedStrikesTimer");
        focusedStrikesThreshold = nbt.getInt("focusedStrikesThreshold");
        focusedStrikesDamage = nbt.getInt("focusedStrikesDamage");
        focusedStrikesMaxDamage = nbt.getInt("focusedStrikesMaxDamage");
        currentBonusDamage = nbt.getInt("currentBonusDamage");

        windedEffectDuration = nbt.getInt("windedEffectDuration");
        secondWindSpeedDuration = nbt.getInt("secondWindSpeedDuration");
        secondWindTriggered = nbt.getBoolean("secondWindTriggered");

        warlordRallyTriggered = nbt.getBoolean("warlordRallyTriggered");
        exhaustedDuration = nbt.getInt("exhaustedDuration");

        lifeLeechPercentage = nbt.getDouble("lifeLeechPercentage");

        CompoundTag uuidTag = nbt.getCompound("TalentUUIDs");
        for (String key : uuidTag.getAllKeys()) {
            talentUUIDs.put(key, uuidTag.getUUID(key));
        }
    }
}

