package com.bmt.dream_relics.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class DarkWhisperDaggerItem extends SwordItem implements ICurioItem {
    private final String tooltipKey;

    public DarkWhisperDaggerItem(Tier tier, int attackDamageModifier, float attackSpeed, Properties properties) {
        this(tier, attackDamageModifier, attackSpeed, properties, "tooltip.dream_relics.dark_whisper_dagger");
    }

    public DarkWhisperDaggerItem(Tier tier, int attackDamageModifier, float attackSpeed, Properties properties, String tooltipKey) {
        super(tier, properties.stacksTo(1));
        this.tooltipKey = tooltipKey;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (!tooltipKey.isEmpty()) {
            tooltipComponents.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
        }
    }
}