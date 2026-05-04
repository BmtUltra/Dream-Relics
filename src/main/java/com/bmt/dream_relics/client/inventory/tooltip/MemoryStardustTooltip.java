package com.bmt.dream_relics.client.inventory.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class MemoryStardustTooltip implements ClientTooltipComponent {
    private final Component component;

    public MemoryStardustTooltip(Component component) {
        this.component = component;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics) {
        ItemStackHandler items = component.getItems();

        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack relic = items.getStackInSlot(i);
            if (!relic.isEmpty()) {
                int posX = x + 18 * i;

                guiGraphics.renderItem(relic, posX, y);
                guiGraphics.renderItemDecorations(font, relic, posX, y);
            }
        }
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return 18 * 9;
    }

    public static class Component implements TooltipComponent {
        private final ItemStackHandler items;

        public Component(ItemStackHandler items) {
            this.items = items;
        }

        public ItemStackHandler getItems() {
            return items;
        }
    }
}