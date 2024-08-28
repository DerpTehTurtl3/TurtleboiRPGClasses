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
        if (rpgClass != null) {
            return rpgClass;
        } else {
            return "No Class";
        }
    }

    public void setRpgClass(String className) {
        if (className != null) {
            this.rpgClass = className;
        } else {
            this.rpgClass = "No Class";
        }
    }
    public String getRpgSubclass() {
        if (rpgSubclass != null) {
            return rpgSubclass;
        } else {
            return "No Subclass";
        }
    }
    public void setRpgSubclass(String subclassName) {
        if (subclassName != null) {
            this.rpgSubclass = subclassName;
        } else {
            this.rpgSubclass = "No Subclass";
        }
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
