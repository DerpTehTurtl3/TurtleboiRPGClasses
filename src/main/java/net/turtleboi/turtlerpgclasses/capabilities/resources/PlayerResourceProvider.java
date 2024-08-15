package net.turtleboi.turtlerpgclasses.capabilities.resources;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerResourceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerResource> PLAYER_RESOURCE = CapabilityManager.get(new CapabilityToken<PlayerResource>() { });

    private PlayerResource playerResource = null;
    private final LazyOptional<PlayerResource> optional = LazyOptional.of(this::createPlayerResource);

    private final Player player;

    public PlayerResourceProvider(Player player) {
        this.player = player;
    }

    private PlayerResource createPlayerResource() {
        if (this.playerResource == null) {
            this.playerResource = new PlayerResource(player);
        }
        return this.playerResource;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_RESOURCE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerResource().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerResource().loadNBTData(nbt);
    }
}
