package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.loot.ReplaceWithLootTableFunction;
import com.bmt.dream_relics.loot.SetCountFromConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DRLootFunctions {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS =
            DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, DreamRelics.MODID);

    public static final Supplier<LootItemFunctionType<ReplaceWithLootTableFunction>> REPLACE_WITH_LOOT_TABLE =
            LOOT_FUNCTIONS.register("replace_with_loot_table",
                    () -> new LootItemFunctionType<>(ReplaceWithLootTableFunction.CODEC));

    public static final Supplier<LootItemFunctionType<SetCountFromConfig>> SET_COUNT_FROM_CONFIG =
            LOOT_FUNCTIONS.register("set_count_from_config",
                    () -> new LootItemFunctionType<>(SetCountFromConfig.CODEC));

    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        LOOT_FUNCTIONS.register(modEventBus);
    }
}