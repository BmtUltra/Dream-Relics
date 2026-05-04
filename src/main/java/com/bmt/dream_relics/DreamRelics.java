package com.bmt.dream_relics;

import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.config.LootConfig;
import com.bmt.dream_relics.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(DreamRelics.MODID)
public class DreamRelics {
    public static final String MODID = "dream_relics";

    public DreamRelics(IEventBus modEventBus, ModContainer modContainer) {
        DRItems.ITEMS.register(modEventBus);
        DRCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DRDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        DRLootConditions.register(modEventBus);
        DRLootFunctions.register(modEventBus);
        DRCapabilities.ATTACHMENT_TYPES.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "dream_relics/dream_relics-common.toml");
        modContainer.registerConfig(ModConfig.Type.COMMON, LootConfig.SPEC, "dream_relics/dream_relics-loot.toml");
    }
}