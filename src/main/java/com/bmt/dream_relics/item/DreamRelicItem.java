package com.bmt.dream_relics.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class DreamRelicItem extends DreamRelicItemBase implements ICurioItem {
    private final String tooltipKey;

    public DreamRelicItem(Properties properties) {
        this(properties, null);
    }

    public DreamRelicItem(Properties properties, String tooltipKey) {
        super(properties.stacksTo(1));
        this.tooltipKey = tooltipKey;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (tooltipKey != null && !tooltipKey.isEmpty()) {
            tooltip.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
        }
    }
}