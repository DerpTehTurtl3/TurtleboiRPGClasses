package net.turtleboi.turtlerpgclasses.capabilities.resources;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;

import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AutoRegisterCapability
public class PlayerResource {
    private int stamina, energy, mana;
    private boolean staminaActive, energyActive, manaActive;
    private static final int MIN_RESOURCE = 0;
    private double staminaBuffer = 0.0, energyBuffer = 0.0, manaBuffer = 0.0;

    private final Player player;

    public PlayerResource(Player player) {
        this.player = player;
        this.stamina = getMaxStamina();
        this.energy = getMaxEnergy();
        this.mana = getMaxMana();
    }

    public int getStamina() {
        return stamina;
    }
    public void addStamina(int add) {
        this.stamina = Math.min(stamina + add, getMaxStamina());
    }
    public void subStamina(int sub) {
        this.stamina = Math.max(stamina - sub, MIN_RESOURCE);
    }
    public void setStamina(int stamina) {
        this.stamina = Math.min(stamina, getMaxStamina());
    }
    public int getMaxStamina() {
        return (int) player.getAttributeValue(ModAttributes.MAX_STAMINA.get());
    }
    public void setStaminaActive(boolean active) {
        this.staminaActive = active;
    }
    public boolean isStaminaActive() {
        return this.staminaActive;
    }
    public double getStaminaRecharge() {
        return player.getAttributeValue(ModAttributes.STAMINA_RECHARGE.get());
    }

    public int getEnergy() {
        return energy;
    }
    public void addEnergy(int add) {
        this.energy = Math.min(energy + add, getMaxEnergy());
    }
    public void subEnergy(int sub) {
        this.energy = Math.max(energy - sub, MIN_RESOURCE);
    }
    public void setEnergy(int energy) {
        this.energy = Math.min(energy, getMaxEnergy());
    }
    public int getMaxEnergy() {
        return (int) player.getAttributeValue(ModAttributes.MAX_ENERGY.get());
    }
    public void setEnergyActive(boolean active) {
        this.energyActive = active;
    }
    public boolean isEnergyActive() {
        return this.energyActive;
    }
    public double getEnergyRecharge() {
        return player.getAttributeValue(ModAttributes.ENERGY_RECHARGE.get());
    }

    public int getMana() {
        return mana;
    }
    public void addMana(int add) {
        this.mana = Math.min(mana + add, getMaxMana());
    }
    public void subMana(int sub) {
        this.mana = Math.max(mana - sub, MIN_RESOURCE);
    }
    public void setMana(int mana) {
        this.mana = Math.min(mana, getMaxMana());
    }
    public int getMaxMana() {
        return (int) player.getAttributeValue(ModAttributes.MAX_MANA.get());
    }
    public void setManaActive(boolean active) {
        this.manaActive = active;
    }
    public boolean isManaActive() {
        return this.manaActive;
    }
    public double getManaRecharge() {
        return player.getAttributeValue(ModAttributes.MANA_RECHARGE.get());
    }

    public void resetResources() {
        this.stamina = this.energy = this.mana = 0;
        this.staminaActive = this.energyActive = this.manaActive = false;
    }

    public void copyFrom(PlayerResource source) {
        this.stamina = source.stamina;
        this.energy = source.energy;
        this.mana = source.mana;
        this.staminaActive = source.staminaActive;
        this.energyActive = source.energyActive;
        this.manaActive = source.manaActive;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("stamina", stamina);
        nbt.putInt("energy", energy);
        nbt.putInt("mana", mana);
        nbt.putBoolean("staminaActive", staminaActive);
        nbt.putBoolean("energyActive", energyActive);
        nbt.putBoolean("manaActive", manaActive);
    }
    public void loadNBTData(CompoundTag nbt) {
        stamina = nbt.getInt("stamina");
        energy = nbt.getInt("energy");
        mana = nbt.getInt("mana");
        staminaActive = nbt.getBoolean("staminaActive");
        energyActive = nbt.getBoolean("energyActive");
        manaActive = nbt.getBoolean("manaActive");
    }

    public void updateRechargeRates(double deltaTime) {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        if (staminaActive) {
            double staminaRegenRate = getStaminaRecharge();
            staminaBuffer += getStaminaRecharge() * deltaTime;
            while (staminaBuffer >= 1.0) {
                addStamina(1);
                //System.out.println("[" + currentTime.format(formatter) + "] Adding stamina! Current regeneration is " + staminaRegenRate + " stamina per second");
                staminaBuffer = 0.0;
            }
            this.stamina = Math.min(this.stamina, getMaxStamina());
        }
        if (energyActive) {
            double energyRechargeRate = getEnergyRecharge();
            energyBuffer += getEnergyRecharge() * deltaTime;
            while (energyBuffer >= 1.0) {
                addEnergy(1);
                //System.out.println("[" + currentTime.format(formatter) + "] Adding energy! Current regeneration is " + energyRechargeRate + " energy per second");
                energyBuffer = 0.0;
            }
            this.energy = Math.min(this.energy, getMaxEnergy());
        }
        if (manaActive) {
            double manaRechargeRate = getManaRecharge();
            manaBuffer += getManaRecharge() * deltaTime;
            while (manaBuffer >= 1.0) {
                addMana(1);
                //System.out.println("[" + currentTime.format(formatter) + "] Adding mana! Current regeneration is " + manaRechargeRate + " mana per second");
                manaBuffer = 0.0;
            }
            this.mana = Math.min(this.mana, getMaxMana());
        }
    }
}