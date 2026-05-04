package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.loot.ConfigValueChance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class DRLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS =
            DeferredRegister.create(BuiltInRegistries.LOOT_CONDITION_TYPE.key(), DreamRelics.MODID);

    public static final Supplier<LootItemConditionType> CONFIG_VALUE_CHANCE =
            LOOT_CONDITIONS.register("config_value_chance",
                    () -> new LootItemConditionType(ConfigValueChance.CODEC));

    public static void register(IEventBus bus) {
        LOOT_CONDITIONS.register(bus);
    }
}