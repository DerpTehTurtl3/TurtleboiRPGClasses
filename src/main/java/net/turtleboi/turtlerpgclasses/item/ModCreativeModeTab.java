package net.turtleboi.turtlerpgclasses.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab TURTLERPGCLASSES_TAB = new CreativeModeTab("turtlerpgclassestab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Items.NETHERITE_INGOT);
        }
    };
}
