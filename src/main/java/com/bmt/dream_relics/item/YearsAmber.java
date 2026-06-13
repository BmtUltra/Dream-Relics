package com.bmt.dream_relics.item;

import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.util.YearsAmberDurabilityTracker;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;

public class YearsAmber extends DreamRelicItem {
    public YearsAmber(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
        tooltipComponents.add(Component.translatable("item.dream_relics.years_amber.show"));
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

        Holder<Enchantment> efficiencyEnchantment = player.level()
                .registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(Enchantments.EFFICIENCY);

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.isEmpty() || itemStack == old) continue;

            if (itemStack.isCorrectToolForDrops(blockState)) {
                float newSpeed = itemStack.getDestroySpeed(blockState);
                if (newSpeed > speed) {
                    int efficiency = getEnchantmentLevelStatic(itemStack, efficiencyEnchantment);
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

    public static int getEnchantmentLevelStatic(ItemStack stack, Holder<Enchantment> enchantment) {
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments != null) {
            return enchantments.getLevel(enchantment);
        }
        return 0;
    }

    @Nullable
    public static Pair<Float, ItemStack> findBestCorrectToolFromTracker(Player player, ItemStack old) {
        BlockState blockState = YearsAmberDurabilityTracker.getBlock();
        if (blockState == null) {
            return null;
        }
        return findBestCorrectTool(player, old, blockState);
    }
}