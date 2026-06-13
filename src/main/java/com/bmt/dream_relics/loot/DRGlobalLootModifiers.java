package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.DreamRelics;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DRGlobalLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, DreamRelics.MODID);

    static {
        GLM_SERIALIZERS.register(
                "config_loot_modifier",
                () -> ConfigLootModifier.CODEC
        );
    }

    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        GLM_SERIALIZERS.register(modEventBus);
    }
}
