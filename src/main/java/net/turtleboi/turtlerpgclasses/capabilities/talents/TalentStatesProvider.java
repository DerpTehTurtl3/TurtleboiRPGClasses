package net.turtleboi.turtlerpgclasses.capabilities.talents;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TalentStatesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<TalentStates> TALENT_STATES = CapabilityManager.get(new CapabilityToken<TalentStates>() { });

    private TalentStates states = null;
    private final LazyOptional<TalentStates> optional = LazyOptional.of(this::giveTalentStates);

    private TalentStates giveTalentStates() {
        if (this.states == null) {
            this.states = new TalentStates();
        }
        return this.states;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TALENT_STATES) {
            return  optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        if (states != null) {
            giveTalentStates().saveNBTData(nbt);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        giveTalentStates().loadNBTData(nbt);
    }
}