package com.bmt.dream_relics.util;

import net.minecraft.world.level.block.state.BlockState;

public class YearsAmberDurabilityTracker {
    private static final ThreadLocal<BlockState> CURRENT_BLOCK = new ThreadLocal<>();

    public static void setBlock(BlockState state) {
        CURRENT_BLOCK.set(state);
    }

    public static BlockState getBlock() {
        return CURRENT_BLOCK.get();
    }

    public static void clear() {
        CURRENT_BLOCK.remove();
    }
}
