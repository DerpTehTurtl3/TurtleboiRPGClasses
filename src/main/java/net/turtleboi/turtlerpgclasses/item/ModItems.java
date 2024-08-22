package net.turtleboi.turtlerpgclasses.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.item.weapon.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TurtleRPGClasses.MOD_ID);

    public static final RegistryObject<Item> WOODEN_DAGGER = ITEMS.register("wooden_dagger",
            () -> new WoodenDaggerItem(Tiers.WOOD, 1, -1.6F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> STONE_DAGGER = ITEMS.register("stone_dagger",
            () -> new StoneDaggerItem(Tiers.STONE, 1, -1.6F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> IRON_DAGGER = ITEMS.register("iron_dagger",
            () -> new IronDaggerItem(Tiers.IRON, 1, -1.6F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> GOLDEN_DAGGER = ITEMS.register("golden_dagger",
            () -> new GoldenDaggerItem(Tiers.GOLD, 1, -1.6F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> DIAMOND_DAGGER = ITEMS.register("diamond_dagger",
            () -> new DiamondDaggerItem(Tiers.DIAMOND, 1, -1.6F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> NETHERITE_DAGGER = ITEMS.register("netherite_dagger",
            () -> new NetheriteDaggerItem(Tiers.NETHERITE, 1, -1.6F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
