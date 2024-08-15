package net.turtleboi.turtlerpgclasses.capabilities.resources;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.turtleboi.turtlerpgclasses.init.ModAttributes;

@AutoRegisterCapability
public class PlayerResource {
    private int stamina, energy, mana;
    private boolean staminaActive, energyActive, manaActive;
    private static final int MIN_RESOURCE = 0;
    private double staminaRecharge = 0.1, energyRecharge = 2.0, manaRecharge = 0.33; // Resources per second Stamina 1/10s Energy 2/1s Mana 1/3s
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
        this.stamina = stamina;
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
        return staminaRecharge;
    }
    public void setStaminaRecharge(double staminaRecharge) {
        this.staminaRecharge = staminaRecharge;
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
        this.energy = energy;
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
        return energyRecharge;
    }
    public void setEnergyRecharge(double energyRecharge) {
        this.energyRecharge = energyRecharge;
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
        this.mana = mana;
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
        return manaRecharge;
    }
    public void setManaRecharge(double manaRecharge) {
        this.manaRecharge = manaRecharge;
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
        this.staminaRecharge = source.staminaRecharge;
        this.energyRecharge = source.energyRecharge;
        this.manaRecharge = source.manaRecharge;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("stamina", stamina);
        nbt.putInt("energy", energy);
        nbt.putInt("mana", mana);
        nbt.putBoolean("staminaActive", staminaActive);
        nbt.putBoolean("energyActive", energyActive);
        nbt.putBoolean("manaActive", manaActive);
        nbt.putDouble("staminaRecharge", staminaRecharge);
        nbt.putDouble("energyRecharge", energyRecharge);
        nbt.putDouble("manaRecharge", manaRecharge);
    }
    public void loadNBTData(CompoundTag nbt) {
        stamina = nbt.getInt("stamina");
        energy = nbt.getInt("energy");
        mana = nbt.getInt("mana");
        staminaActive = nbt.getBoolean("staminaActive");
        energyActive = nbt.getBoolean("energyActive");
        manaActive = nbt.getBoolean("manaActive");
        staminaRecharge = nbt.getDouble("staminaRecharge");
        energyRecharge = nbt.getDouble("energyRecharge");
        manaRecharge = nbt.getDouble("manaRecharge");
    }

    public void updateRechargeRates(double deltaTime) {
        if (staminaActive) {
            staminaBuffer += staminaRecharge * deltaTime;
            while (staminaBuffer >= 1.0) {
                addStamina(1);
                staminaBuffer -= 1.0;
            }
        }
        if (energyActive) {
            energyBuffer += energyRecharge * deltaTime;
            while (energyBuffer >= 1.0) {
                addEnergy(1);
                energyBuffer -= 1.0;
            }
        }
        if (manaActive) {
            manaBuffer += manaRecharge * deltaTime;
            while (manaBuffer >= 1.0) {
                addMana(1);
                manaBuffer -= 1.0;
            }
        }
    }
}