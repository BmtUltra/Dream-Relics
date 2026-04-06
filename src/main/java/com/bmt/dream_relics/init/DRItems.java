package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.item.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DRItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DreamRelics.MODID);
    public static final RegistryObject<Item> SOUL_MIRROR = ITEMS.register("soul_mirror",
            () -> new SoulMirrorItem(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_TOTEM = ITEMS.register("dream_totem",
            () -> new DreamTotem(new Item.Properties()));
    public static final RegistryObject<Item> MOMENT_STONE = ITEMS.register("moment_stone",
            () -> new MomentStoneItem(new Item.Properties()));
    public static final RegistryObject<Item> ENDLESS_DREAM = ITEMS.register("endless_dream",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.endless_dream"));
    public static final RegistryObject<Item> YEARS_AMBER = ITEMS.register("years_amber",
            () -> new YearsAmber(new Item.Properties()));
    public static final RegistryObject<Item> MEMORY_STARDUST = ITEMS.register("memory_stardust",
            () -> new MemoryStardustItem(new Item.Properties()));
    public static final RegistryObject<Item> TIME_HOURGLASS = ITEMS.register("time_hourglass",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.time_hourglass"));
    public static final RegistryObject<Item> ASTRAL_NECKLACE = ITEMS.register("astral_necklace",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.astral_necklace"));
    public static final RegistryObject<Item> TASSEL_RING = ITEMS.register("tassel_ring",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.tassel_ring"));
    public static final RegistryObject<Item> OBSERVE_SELF_EYE = ITEMS.register("observe_self_eye",
            () -> new DreamRelicItem(new Item.Properties(),"tooltip.dream_relics.observe_self_eye"));
    public static final RegistryObject<Item> DREAM_BALANCE = ITEMS.register("dream_balance",
            () -> new DreamRelicItem(new Item.Properties(),"tooltip.dream_relics.dream_balance"));
    public static final RegistryObject<Item> PAST_RING = ITEMS.register("past_ring",
            () -> new PastRingItem(new Item.Properties()));
    public static final RegistryObject<Item> ECHO_EARRING = ITEMS.register("echo_earring",
            () -> new DreamRelicItem(new Item.Properties(),"tooltip.dream_relics.echo_earring"));
    public static final RegistryObject<Item> MEMORY_NECKLACE = ITEMS.register("memory_necklace",
            () -> new MemoryNecklaceItem(new Item.Properties()));
    public static final RegistryObject<Item> SLEEPING_STAR_SEED = ITEMS.register("sleeping_star_seed",
            () -> new SleepingStarSeedItem(new Item.Properties()));
    public static final RegistryObject<Item> LIMINAL_KEY = ITEMS.register("liminal_key",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.liminal_key"));
    public static final RegistryObject<Item> HEART_VOICE_PENDANT = ITEMS.register("heart_voice_pendant",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.heart_voice_pendant"));
    public static final RegistryObject<Item> AWAKEN_DREAM_BRACELET = ITEMS.register("awaken_dream_bracelet",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.awaken_dream_bracelet"));
    public static final RegistryObject<Item> MIST_VEIL_RING = ITEMS.register("mist_veil_ring",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.mist_veil_ring"));
    public static final RegistryObject<Item> NIGHTMARE_BOOK = ITEMS.register("nightmare_book",
            () -> new NightmareBook(new Item.Properties()));
    public static final RegistryObject<Item> VOID_NECKLACE = ITEMS.register("void_necklace",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.void_necklace"));
    public static final RegistryObject<Item> DARK_WHISPER_RING = ITEMS.register("dark_whisper_ring",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.dark_whisper_ring"));
    public static final RegistryObject<Item> DARK_WHISPER_DAGGER = ITEMS.register("dark_whisper_dagger",
            () -> new DarkWhisperDaggerItem(Tiers.IRON,1, -2.0F, new Item.Properties()));
    public static final RegistryObject<Item> FLAWLESS_GEM = ITEMS.register("flawless_gem",
            () -> new FlawlessGem(new Item.Properties()));
    public static final RegistryObject<Item> ROYAL_LENS = ITEMS.register("royal_lens",
            () -> new DreamRelicItem(new Item.Properties().rarity(Rarity.COMMON), "tooltip.dream_relics.royal_lens"));
    public static final RegistryObject<Item> ROYAL_CROWN = ITEMS.register("royal_crown",
            () -> new DreamRelicItem(new Item.Properties().rarity(Rarity.COMMON), "tooltip.dream_relics.royal_crown"));
    public static final RegistryObject<Item> OCEAN_CURRENT_BLESSING = ITEMS.register("ocean_current_blessing",
            () -> new DreamRelicItem(new Item.Properties().rarity(Rarity.COMMON), "tooltip.dream_relics.ocean_current_blessing"));
    public static final RegistryObject<Item> ICARUS_WINGS = ITEMS.register("icarus_wings",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.icarus_wings"));
    public static final RegistryObject<Item> PURE_HOLY_GRAIL = ITEMS.register("pure_holy_grail",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.pure_holy_grail"));
    public static final RegistryObject<Item> ELVEN_BOOTS = ITEMS.register("elven_boots",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.elven_boots"));
    public static final RegistryObject<Item> RARE_GOLD_BRACELET = ITEMS.register("rare_gold_bracelet",
            () -> new DreamRelicItem(new Item.Properties(), "tooltip.dream_relics.rare_gold_bracelet"));
}