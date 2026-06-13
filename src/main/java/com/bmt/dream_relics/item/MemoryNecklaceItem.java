package com.bmt.dream_relics.item;

import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.init.DRDataComponents;
import com.bmt.dream_relics.init.DRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class MemoryNecklaceItem extends DreamRelicItem {

    public MemoryNecklaceItem(Properties properties) {
        super(properties, "tooltip.dream_relics.memory_necklace");
    }

    public static void recordDamage(Player player, float damageAmount) {
        if (!isEquipped(player)) {
            return;
        }

        ItemStack necklace = getMemoryNecklace(player);
        if (necklace.isEmpty()) {
            return;
        }
        float currentStored = getStoredDamage(necklace);
        float newStored = Math.min(currentStored + damageAmount, (float) CommonConfig.memoryNecklaceMaxStoredDamage);
        setStoredDamage(necklace, newStored);
    }

    public static float applyStoredDamage(Player player, float baseDamage) {
        if (!isEquipped(player)) {
            return baseDamage;
        }

        ItemStack necklace = getMemoryNecklace(player);
        if (necklace.isEmpty()) {
            return baseDamage;
        }

        float storedDamage = getStoredDamage(necklace);

        if (storedDamage > 0) {
            float totalDamage = baseDamage + storedDamage;
            setStoredDamage(necklace, 0);
            return totalDamage;
        }

        return baseDamage;
    }

    public static float getStoredDamage(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof MemoryNecklaceItem)) {
            return 0;
        }
        return stack.getOrDefault(DRDataComponents.MEMORY_NECKLACE_STORED_DAMAGE, 0.0f);
    }

    public static void setStoredDamage(ItemStack stack, float damage) {
        if (stack.isEmpty() || !(stack.getItem() instanceof MemoryNecklaceItem)) {
            return;
        }
        stack.set(DRDataComponents.MEMORY_NECKLACE_STORED_DAMAGE, damage);
    }

    public static boolean isEquipped(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(inventory -> inventory.findFirstCurio(DRItems.MEMORY_NECKLACE.get()).isPresent())
                .orElse(false);
    }

    public static ItemStack getMemoryNecklace(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .flatMap(inventory -> inventory.findFirstCurio(DRItems.MEMORY_NECKLACE.get()))
                .map(SlotResult::stack)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipComponents, flag);

        float storedDamage = getStoredDamage(stack);
        if (storedDamage > 0) {
            tooltipComponents.add(Component.translatable("tooltip.dream_relics.memory_necklace.stored_damage",
                            String.format("%.1f", storedDamage))
                    .withStyle(ChatFormatting.AQUA));
        }
    }
}