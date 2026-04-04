package com.bmt.dream_relics.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class FlawlessGem extends DreamRelicItemBase {

    public FlawlessGem(Properties properties) {
        super(properties.stacksTo(16).rarity(Rarity.RARE));
    }

    public static ItemStack removeNegativeEnchantments(ItemStack itemStack) {
        if (itemStack.isEmpty() || !itemStack.isEnchanted()) {
            return itemStack;
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
        boolean hasNegativeEnchantment = false;

        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (!enchantment.isCurse()) {
                continue;
            }

            hasNegativeEnchantment = true;
            break;
        }

        if (!hasNegativeEnchantment) {
            return itemStack;
        }

        CompoundTag newTag = itemStack.getOrCreateTag();
        ListTag enchantmentList = new ListTag();
        
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();

            if (!enchantment.isCurse()) {
                CompoundTag enchantmentTag = new CompoundTag();
                enchantmentTag.putString("id", EnchantmentHelper.getEnchantmentId(enchantment).toString());
                enchantmentTag.putShort("lvl", (short) level);
                enchantmentList.add(enchantmentTag);
            }
        }

        if (enchantmentList.isEmpty()) {
            newTag.remove("Enchantments");
            newTag.remove("ench");
        } else {
            newTag.put("Enchantments", enchantmentList);
        }
        return itemStack;
    }

    public static ItemStack repairItem(ItemStack itemStack) {
        if (itemStack.isEmpty() || !itemStack.isDamageableItem()) {
            return itemStack;
        }

        itemStack.setDamageValue(0);
        return itemStack;
    }

    public static boolean hasNegativeEnchantments(ItemStack itemStack) {
        if (itemStack.isEmpty() || !itemStack.isEnchanted()) {
            return false;
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (enchantment.isCurse()) {
                return true;
            }
        }
        return false;
    }

    public static boolean needsRepair(ItemStack itemStack) {
        return !itemStack.isEmpty() && 
               itemStack.isDamageableItem() && 
               itemStack.getDamageValue() > 0;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("item.dream_relics.tooltip.item.flawless_gem.restore")
                .withStyle(ChatFormatting.GRAY));
    }
}