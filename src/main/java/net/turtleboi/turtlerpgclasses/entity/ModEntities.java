package net.turtleboi.turtlerpgclasses.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.entity.abilities.SanctuaryDomeEntity;
import net.turtleboi.turtlerpgclasses.entity.abilities.UnleashFuryEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TurtleRPGClasses.MOD_ID);

    public static final RegistryObject<EntityType<UnleashFuryEntity>> UNLEASH_FURY =
            ENTITY_TYPES.register("unleash_fury" , () -> EntityType.Builder.of(UnleashFuryEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.1F)
                    .build("unleash_fury"));
    public static final RegistryObject<EntityType<SanctuaryDomeEntity>> SANCTUARY_DOME =
            ENTITY_TYPES.register("sanctuary_dome" , () -> EntityType.Builder.of(SanctuaryDomeEntity::new, MobCategory.MISC)
                    .sized(5F, 5F)
                    .build("sanctuary_dome"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
