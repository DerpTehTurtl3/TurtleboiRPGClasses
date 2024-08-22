package net.turtleboi.turtlerpgclasses.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.entity.weapons.ThrowableDagger;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TurtleRPGClasses.MOD_ID);

    public static final RegistryObject<EntityType<ThrowableDagger>> THROWABLE_DAGGER = ENTITY_TYPES.register("throwable_dagger",
            () -> EntityType.Builder.<ThrowableDagger>of(ThrowableDagger::new, MobCategory.MISC)
                    .sized(0.1F, 0.25F)
                    .build(new ResourceLocation(TurtleRPGClasses.MOD_ID, "throwable_dagger").toString())
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
