package net.turtleboi.turtlerpgclasses.client;

public class ClientResourceData {
    private static int stamina;
    private static int maxStamina;
    private static int energy;
    private static int maxEnergy;
    private static int mana;
    private static int maxMana;

    public static int getStamina() {
        return stamina;
    }

    public static void setStamina(int stamina) {
        ClientResourceData.stamina = stamina;
    }

    public static int getMaxStamina() {
        return maxStamina;
    }

    public static void setMaxStamina(int maxStamina) {
        ClientResourceData.maxStamina = maxStamina;
    }

    public static int getEnergy() {
        return energy;
    }

    public static void setEnergy(int energy) {
        ClientResourceData.energy = energy;
    }

    public static int getMaxEnergy() {
        return maxEnergy;
    }

    public static void setMaxEnergy(int maxEnergy) {
        ClientResourceData.maxEnergy = maxEnergy;
    }

    public static int getMana() {
        return mana;
    }

    public static void setMana(int mana) {
        ClientResourceData.mana = mana;
    }

    public static int getMaxMana() {
        return maxMana;
    }

    public static void setMaxMana(int maxMana) {
        ClientResourceData.maxMana = maxMana;
    }
}
