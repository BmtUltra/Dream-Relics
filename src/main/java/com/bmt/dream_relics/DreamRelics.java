package com.bmt.dream_relics;

import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.config.LootConfig;
import com.bmt.dream_relics.init.DRCreativeTabs;
import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.init.DRLootConditions;
import com.bmt.dream_relics.init.DRLootFunctions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DreamRelics.MODID)
public class DreamRelics {
    public static final String MODID = "dream_relics";

    public DreamRelics(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        DRItems.ITEMS.register(modEventBus);
        DRCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DRLootConditions.register(modEventBus);
        DRLootFunctions.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC,"dream_relics/dream_relics-common.toml");
        context.registerConfig(ModConfig.Type.COMMON, LootConfig.SPEC, "dream_relics/dream_relics-loot.toml");
    }

    public static ResourceLocation id(String s) {
        return ResourceLocation.fromNamespaceAndPath(MODID, s);
    }
}