package com.bmt.dream_relics.common.capabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

public class CombinedEquippedCuriosHandler implements IItemHandlerModifiable {

    private final IItemHandlerModifiable delegate;
    private final List<ItemStack> virtualStacks;

    public CombinedEquippedCuriosHandler(IItemHandlerModifiable delegate, List<ItemStack> virtualStacks) {
        this.delegate = delegate;
        this.virtualStacks = virtualStacks;
    }

    @Override
    public int getSlots() {
        return this.delegate.getSlots() + this.virtualStacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < this.delegate.getSlots()) {
            return this.delegate.getStackInSlot(slot);
        }

        int virtualIndex = slot - this.delegate.getSlots();

        if (virtualIndex >= 0 && virtualIndex < this.virtualStacks.size()) {
            return this.virtualStacks.get(virtualIndex);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot < this.delegate.getSlots()) {
            return this.delegate.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot < this.delegate.getSlots()) {
            return this.delegate.extractItem(slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot < this.delegate.getSlots()) {
            return this.delegate.getSlotLimit(slot);
        }
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (slot < this.delegate.getSlots()) {
            return this.delegate.isItemValid(slot, stack);
        }
        return false;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (slot < this.delegate.getSlots()) {
            this.delegate.setStackInSlot(slot, stack);
        }
    }
}
