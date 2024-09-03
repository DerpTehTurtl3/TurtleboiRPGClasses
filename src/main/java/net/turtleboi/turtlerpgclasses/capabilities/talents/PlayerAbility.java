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
    private int brawlerHitCounter;

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
    private int focusedStrikesTier;

    private int windedEffectDuration = 0;
    private int secondWindSpeedDuration = 0;
    private boolean secondWindTriggered = false;

    private boolean warlordRallyTriggered = false;
    private int exhaustedDuration = 0;

    private double lifeLeechPercentage;

    private int steadyBreathingStacks = 0;
    private int steadyBreathingIdleTicks = 0;

    private Map<UUID, Long> lastRootedTimes = new HashMap<>();

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
    public void setBrawlerHitCounter(int brawlerHitCounter) {
        this.brawlerHitCounter = brawlerHitCounter;
    }

    public int getBrawlerHitCounter() {
        return brawlerHitCounter;
    }

    public void incrementBrawlerHitCounter() {
        this.brawlerHitCounter++;
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

    public void setFocusedStrikesCounter(int counter) {
        this.focusedStrikesCounter = counter;
    }

    public int getFocusedStrikesCounter() {
        return focusedStrikesCounter;
    }

    public void incrementFocusedStrikesTier() {
        this.focusedStrikesTier++;
    }

    public void setFocusedStrikesTier(int counter) {
        this.focusedStrikesTier = counter;
    }

    public int getFocusedStrikesTier() {
        return focusedStrikesTier;
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

    //Vine Whip Talent
    public long getLastRootedTime(UUID targetUUID) {
        return lastRootedTimes.getOrDefault(targetUUID, 0L);
    }

    public void setLastRootedTime(UUID targetUUID, long time) {
        lastRootedTimes.put(targetUUID, time);
    }

    //Steady Breathing Talent
    public int getSteadyBreathingStacks() {
        return steadyBreathingStacks;
    }

    public void setSteadyBreathingStacks(int stacks) {
        this.steadyBreathingStacks = stacks;
    }

    public void incrementSteadyBreathingStacks() {
        if (steadyBreathingStacks < 3) { // Assuming max stacks is 3
            this.steadyBreathingStacks++;
        }
    }

    public int getSteadyBreathingIdleTicks() {
        return steadyBreathingIdleTicks;
    }

    public void setSteadyBreathingIdleTicks(int ticks) {
        this.steadyBreathingIdleTicks = ticks;
    }

    public void incrementSteadyBreathingIdleTicks() {
        this.steadyBreathingIdleTicks++;
    }

    public void resetSteadyBreathing() {
        this.steadyBreathingStacks = 0;
        this.steadyBreathingIdleTicks = 0;
    }

    public void copyFrom(PlayerAbility source) {
        this.brawlerHitCounter = source.brawlerHitCounter;

        this.focusedStrikesCounter = source.focusedStrikesCounter;
        this.focusedStrikesTier = source.focusedStrikesTier;

        this.windedEffectDuration = source.windedEffectDuration;
        this.secondWindSpeedDuration = source.secondWindSpeedDuration;
        this.secondWindTriggered = source.secondWindTriggered;

        this.warlordRallyTriggered = source.warlordRallyTriggered;
        this.exhaustedDuration = source.exhaustedDuration;

        this.lifeLeechPercentage = source.lifeLeechPercentage;

        this.lastRootedTimes = new HashMap<>(source.lastRootedTimes);

        this.steadyBreathingStacks = source.steadyBreathingStacks;
        this.steadyBreathingIdleTicks = source.steadyBreathingIdleTicks;

        this.talentUUIDs = new HashMap<>(source.talentUUIDs);
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("brawlerHitCounter", brawlerHitCounter);

        nbt.putInt("focusedStrikesCounter", focusedStrikesCounter);
        nbt.putInt("focusedStrikesTier", focusedStrikesTier);

        nbt.putInt("windedEffectDuration", windedEffectDuration);
        nbt.putInt("secondWindSpeedDuration", secondWindSpeedDuration);
        nbt.putBoolean("secondWindTriggered", secondWindTriggered);

        nbt.putBoolean("warlordRallyTriggered", warlordRallyTriggered);
        nbt.putInt("warlordRallyCooldown", exhaustedDuration);

        nbt.putDouble("lifeLeechPercentage", lifeLeechPercentage);

        nbt.putInt("steadyBreathingStacks", steadyBreathingStacks);
        nbt.putInt("steadyBreathingIdleTicks", steadyBreathingIdleTicks);

        CompoundTag rootTimesTag = new CompoundTag();
        for (Map.Entry<UUID, Long> entry : lastRootedTimes.entrySet()) {
            rootTimesTag.putLong(entry.getKey().toString(), entry.getValue());
        }
        nbt.put("LastRootedTimes", rootTimesTag);

        CompoundTag uuidTag = new CompoundTag();
        for (Map.Entry<String, UUID> entry : talentUUIDs.entrySet()) {
            uuidTag.putUUID(entry.getKey(), entry.getValue());
        }
        nbt.put("TalentUUIDs", uuidTag);
    }

    public void loadNBTData(CompoundTag nbt) {
        brawlerHitCounter = nbt.getInt("brawlerHitCounter");

        focusedStrikesCounter = nbt.getInt("focusedStrikesCounter");
        focusedStrikesTier = nbt.getInt("focusedStrikesTier");

        windedEffectDuration = nbt.getInt("windedEffectDuration");
        secondWindSpeedDuration = nbt.getInt("secondWindSpeedDuration");
        secondWindTriggered = nbt.getBoolean("secondWindTriggered");

        warlordRallyTriggered = nbt.getBoolean("warlordRallyTriggered");
        exhaustedDuration = nbt.getInt("exhaustedDuration");

        lifeLeechPercentage = nbt.getDouble("lifeLeechPercentage");

        CompoundTag rootTimesTag = nbt.getCompound("LastRootedTimes");
        for (String key : rootTimesTag.getAllKeys()) {
            lastRootedTimes.put(UUID.fromString(key), rootTimesTag.getLong(key));
        }

        steadyBreathingStacks = nbt.getInt("steadyBreathingStacks");
        steadyBreathingIdleTicks = nbt.getInt("steadyBreathingIdleTicks");

        CompoundTag uuidTag = nbt.getCompound("TalentUUIDs");
        for (String key : uuidTag.getAllKeys()) {
            talentUUIDs.put(key, uuidTag.getUUID(key));
        }
    }
}

