package net.turtleboi.turtlerpgclasses.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerClass {
    private String rpgClass;
    private String rpgSubclass;
    public PlayerClass() {
        this.rpgClass = "No Class";
        this.rpgSubclass = "No Subclass";
    }
    public String getRpgClass() {
        return rpgClass;
    }
    public void setRpgClass(String rpgClass) {
        this.rpgClass = rpgClass;
    }
    public String getRpgSubclass() {
        return rpgSubclass;
    }
    public void setRpgSubclass(String rpgSubclass) {
        this.rpgSubclass = rpgSubclass;
    }
    public void copyFrom(PlayerClass source) {
        this.rpgClass = source.rpgClass;
        this.rpgSubclass = source.rpgSubclass;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("class", rpgClass);
        nbt.putString("subclass", rpgSubclass);
    }
    public void loadNBTData(CompoundTag nbt) {
        rpgClass = nbt.getString("class");
        rpgSubclass = nbt.getString("subclass");
    }
}
