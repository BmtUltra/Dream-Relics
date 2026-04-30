package com.bmt.dream_relics.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class DarkWhisperDaggerItem extends SwordItem implements ICurioItem {
    private final String tooltipKey;

    public DarkWhisperDaggerItem(Tier tier, int attackDamageModifier, float attackSpeed, Properties properties) {
        this(tier, attackDamageModifier, attackSpeed, properties, "tooltip.dream_relics.dark_whisper_dagger");
    }

    public DarkWhisperDaggerItem(Tier tier, int attackDamageModifier, float attackSpeed, Properties properties, String tooltipKey) {
        super(tier, attackDamageModifier, attackSpeed, properties.stacksTo(1));
        this.tooltipKey = tooltipKey;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        if (!tooltipKey.isEmpty()) {
            tooltip.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
        }
    }
}