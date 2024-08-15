package net.turtleboi.turtlerpgclasses.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.turtleboi.turtlerpgclasses.TurtleRPGClasses;
import net.turtleboi.turtlerpgclasses.item.potion.EnergyPotionItem;
import net.turtleboi.turtlerpgclasses.item.potion.ManaPotionItem;
import net.turtleboi.turtlerpgclasses.item.potion.StaminaPotionItem;
import net.turtleboi.turtlerpgclasses.item.weapon.BarbarianAxeItem;
import net.turtleboi.turtlerpgclasses.item.weapon.ExecutionersAxeItem;
import net.turtleboi.turtlerpgclasses.item.weapon.JuggernautHelmetItem;
import net.turtleboi.turtlerpgclasses.item.weapon.PaladinHammerItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TurtleRPGClasses.MOD_ID);

    public static final RegistryObject<Item> BARBARIAN_AXE = ITEMS.register("barbarian_axe",
            () -> new BarbarianAxeItem(Tiers.DIAMOND, 7, -2.4F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> EXECUTIONERS_AXE = ITEMS.register("executioners_axe",
            () -> new ExecutionersAxeItem(Tiers.NETHERITE, 9, -3.0F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> PALADIN_HAMMER = ITEMS.register("paladin_hammer",
            () -> new PaladinHammerItem(Tiers.NETHERITE, 6, -1.8F, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> JUGGERNAUT_HELMET = ITEMS.register("juggernaut_helmet",
            () -> new JuggernautHelmetItem(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB)));

    public static final RegistryObject<Item> STAMINA_POTION = ITEMS.register("stamina_potion",
            () -> new StaminaPotionItem(new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MANA_POTION = ITEMS.register("mana_potion",
            () -> new ManaPotionItem(new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB).stacksTo(1)));

    public static final RegistryObject<Item> ENERGY_POTION = ITEMS.register("energy_potion",
            () -> new EnergyPotionItem(new Item.Properties().tab(ModCreativeModeTab.TURTLERPGCLASSES_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
