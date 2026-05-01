package com.bmt.dream_relics.item;

import com.bmt.dream_relics.init.DRItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class YearsAmber extends DreamRelicItem {
    public YearsAmber(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.dream_relics.years_amber.show"));
    }

    @Nullable
    public static Pair<Float, ItemStack> findBestCorrectTool(Player player, ItemStack old, BlockState blockState) {
        boolean hasYearsAmber = CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.isEquipped(DRItems.YEARS_AMBER.get()))
                .orElse(false);

        if (!hasYearsAmber) {
            return null;
        }

        float oldSpeed = old.getDestroySpeed(blockState);
        float speed = 0;
        ItemStack newItem = null;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.isEmpty() || itemStack == old) continue;

            if (itemStack.isCorrectToolForDrops(blockState)) {
                float newSpeed = itemStack.getDestroySpeed(blockState);
                if (newSpeed > speed) {
                    int efficiency = itemStack.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY);
                    if (efficiency > 0) {
                        speed = newSpeed + efficiency * efficiency + 1;
                    } else {
                        speed = newSpeed;
                    }
                    newItem = itemStack;
                }
            }
        }

        if (speed > oldSpeed && newItem != null) {
            return Pair.of(speed, newItem);
        }
        return null;
    }
}