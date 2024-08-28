package net.turtleboi.turtlerpgclasses.item.weapon;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class AmethystWandItem extends Item {
    public AmethystWandItem(Properties pProperties) {
        super(pProperties
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<net.minecraft.network.chat.Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal("(Work in progress)")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
