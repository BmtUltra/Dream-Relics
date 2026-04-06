package com.bmt.dream_relics.item;

import com.bmt.dream_relics.init.DRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class MemoryNecklaceItem extends DreamRelicItem {
    private static final String TAG_STORED_DAMAGE = "StoredDamage";
    private static final float MAX_STORED_DAMAGE = 100.0f;
    
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
        float newStored = Math.min(currentStored + damageAmount, MAX_STORED_DAMAGE);
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
        
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getFloat(TAG_STORED_DAMAGE);
    }

    public static void setStoredDamage(ItemStack stack, float damage) {
        if (stack.isEmpty() || !(stack.getItem() instanceof MemoryNecklaceItem)) {
            return;
        }
        
        CompoundTag tag = stack.getOrCreateTag();
        tag.putFloat(TAG_STORED_DAMAGE, damage);
    }

    public static boolean isEquipped(Player player) {
        return CuriosApi.getCuriosInventory(player).resolve()
                .map(inventory -> inventory.findFirstCurio(DRItems.MEMORY_NECKLACE.get()).isPresent())
                .orElse(false);
    }

    public static ItemStack getMemoryNecklace(Player player) {
        return CuriosApi.getCuriosInventory(player).resolve()
                .flatMap(inventory -> inventory.findFirstCurio(DRItems.MEMORY_NECKLACE.get()))
                .map(SlotResult::stack)
                .orElse(ItemStack.EMPTY);
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        float storedDamage = getStoredDamage(stack);
        if (storedDamage > 0) {
            tooltip.add(Component.translatable("tooltip.dream_relics.memory_necklace.stored_damage", 
                    String.format("%.1f", storedDamage))
                    .withStyle(ChatFormatting.AQUA));
        }
    }
}