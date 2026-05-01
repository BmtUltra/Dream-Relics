package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.loot.ConfigValueChance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DRLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS =
            DeferredRegister.create(BuiltInRegistries.LOOT_CONDITION_TYPE.key(), DreamRelics.MODID);

    public static final RegistryObject<LootItemConditionType> CONFIG_VALUE_CHANCE =
            LOOT_CONDITIONS.register("config_value_chance",
                    () -> new LootItemConditionType(new ConfigValueChance.Serializer()));

    public static void register(IEventBus bus) {
        LOOT_CONDITIONS.register(bus);
    }
}