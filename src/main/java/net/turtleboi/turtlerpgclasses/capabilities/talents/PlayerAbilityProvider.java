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

public class PlayerAbilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<PlayerAbility> PLAYER_ABILITY = CapabilityManager.get(new CapabilityToken<>() { });

    private PlayerAbility playerAbility = null;
    private final LazyOptional<PlayerAbility> optional = LazyOptional.of(this::createPlayerAbility);

    private PlayerAbility createPlayerAbility() {
        if (this.playerAbility == null) {
            this.playerAbility = new PlayerAbility();
        }
        return this.playerAbility;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_ABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerAbility().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAbility().loadNBTData(nbt);
    }
}
