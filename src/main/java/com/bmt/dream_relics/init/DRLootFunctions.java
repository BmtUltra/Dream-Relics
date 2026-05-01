package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.loot.ReplaceWithLootTableFunction;
import com.bmt.dream_relics.loot.SetCountFromConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DRLootFunctions {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS =
            DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE.key(), DreamRelics.MODID);

    public static final RegistryObject<LootItemFunctionType> SET_COUNT_FROM_CONFIG =
            LOOT_FUNCTIONS.register("set_count_from_config",
                    () -> new LootItemFunctionType(new SetCountFromConfig.Serializer()));

    public static final RegistryObject<LootItemFunctionType> REPLACE_WITH_LOOT_TABLE =
            LOOT_FUNCTIONS.register("replace_with_loot_table",
                    () -> new LootItemFunctionType(new ReplaceWithLootTableFunction.Serializer()));

    public static void register(IEventBus bus) {
        LOOT_FUNCTIONS.register(bus);
    }
}