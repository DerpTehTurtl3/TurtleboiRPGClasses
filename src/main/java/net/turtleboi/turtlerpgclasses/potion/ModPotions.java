package net.turtleboi.turtlerpgclasses.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.effect.ModEffects;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, TurtleRPGClasses.MOD_ID);

    public static final RegistryObject<Potion> LESSER_ENERGY_POTION = POTIONS.register("lesser_energy_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.ENERGY_RESTORE.get(), 0, 0)));

    public static final RegistryObject<Potion> ENERGY_POTION = POTIONS.register("energy_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.ENERGY_RESTORE.get(), 0, 1)));

    public static final RegistryObject<Potion> GREATER_ENERGY_POTION = POTIONS.register("greater_energy_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.ENERGY_RESTORE.get(), 0, 2)));

    public static final RegistryObject<Potion> LESSER_MANA_POTION = POTIONS.register("lesser_mana_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.MANA_RESTORE.get(), 0, 0)));

    public static final RegistryObject<Potion> MANA_POTION = POTIONS.register("mana_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.MANA_RESTORE.get(), 0, 1)));

    public static final RegistryObject<Potion> GREATER_MANA_POTION = POTIONS.register("greater_mana_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.MANA_RESTORE.get(), 0, 2)));

    public static final RegistryObject<Potion> LESSER_STAMINA_POTION = POTIONS.register("lesser_stamina_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.STAMINA_RESTORE.get(), 0, 0)));

    public static final RegistryObject<Potion> STAMINA_POTION = POTIONS.register("stamina_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.STAMINA_RESTORE.get(), 0, 1)));

    public static final RegistryObject<Potion> GREATER_STAMINA_POTION = POTIONS.register("greater_stamina_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.STAMINA_RESTORE.get(), 0, 2)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
