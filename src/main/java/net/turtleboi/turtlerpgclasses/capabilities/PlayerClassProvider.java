package net.turtleboi.turtlerpgclasses.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerClassProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerClass> PLAYER_RPGCLASS = CapabilityManager.get(new CapabilityToken<PlayerClass>() { });

    private PlayerClass rpgClass = null;
    private final LazyOptional<PlayerClass> optional = LazyOptional.of(this::giveRPGClass);

    private PlayerClass giveRPGClass() {
        if (this.rpgClass == null) {
            this.rpgClass = new PlayerClass();
        }
        return this.rpgClass;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_RPGCLASS) {
            return  optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        if (rpgClass != null) {
            giveRPGClass().saveNBTData(nbt);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        giveRPGClass().loadNBTData(nbt);
    }
}