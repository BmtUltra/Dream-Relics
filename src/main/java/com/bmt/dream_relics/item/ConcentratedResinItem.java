package com.bmt.dream_relics.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ConcentratedResinItem extends Item {
    public static final int MAX_USES = 4;

    public ConcentratedResinItem(Properties properties) {
        super(properties.durability(MAX_USES));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
        tooltipComponents.add(Component.translatable("tooltip.dream_relics.concentrated_resin").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int max = stack.getMaxDamage();
        return Math.max(1, Math.round((max - stack.getDamageValue()) * 13.0F / max));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xFFFFFFFF;
    }
}
