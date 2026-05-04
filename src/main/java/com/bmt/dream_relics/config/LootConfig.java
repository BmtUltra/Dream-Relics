package com.bmt.dream_relics.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class LootConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue ASTRAL_NECKLACE_CHANCE = BUILDER
            .comment("Spawn chance for Astral Necklace in bastion treasure chests (0.0 - 1.0)")
            .defineInRange("AstralNecklaceChance", 0.015, 0.0, 1.0);

    public static final ModConfigSpec.IntValue ASTRAL_NECKLACE_MIN_COUNT = BUILDER
            .comment("Spawn count for Astral Necklace")
            .defineInRange("AstralNecklaceMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue ASTRAL_NECKLACE_MAX_COUNT = BUILDER
            .comment("Spawn count for Astral Necklace")
            .defineInRange("AstralNecklaceMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue DREAM_TOTEM_CHANCE = BUILDER
            .comment("Spawn chance for Dream Totem in woodland mansion chests (0.0 - 1.0)")
            .defineInRange("DreamTotemChance", 0.015, 0.0, 1.0);

    public static final ModConfigSpec.IntValue DREAM_TOTEM_MIN_COUNT = BUILDER
            .comment("Spawn count for Dream Totem")
            .defineInRange("DreamTotemMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue DREAM_TOTEM_MAX_COUNT = BUILDER
            .comment("Spawn count for Dream Totem")
            .defineInRange("DreamTotemMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue OCEAN_CURRENT_BLESSING_CHANCE = BUILDER
            .comment("Spawn chance for Ocean Current Blessing in buried treasure chests (0.0 - 1.0)")
            .defineInRange("OceanCurrentBlessingChance", 0.015, 0.0, 1.0);

    public static final ModConfigSpec.IntValue OCEAN_CURRENT_BLESSING_MIN_COUNT = BUILDER
            .comment("Spawn count for Ocean Current Blessing")
            .defineInRange("OceanCurrentBlessingMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue OCEAN_CURRENT_BLESSING_MAX_COUNT = BUILDER
            .comment("Spawn count for Ocean Current Blessing")
            .defineInRange("OceanCurrentBlessingMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue DARK_WHISPER_RING_CHANCE = BUILDER
            .comment("Spawn chance for Dark Whisper Ring in ancient city chests (0.0 - 1.0)")
            .defineInRange("darkWhisperRingChance", 0.09, 0.0, 1.0);

    public static final ModConfigSpec.IntValue DARK_WHISPER_RING_MIN_COUNT = BUILDER
            .comment("Spawn count for Dark Whisper Ring")
            .defineInRange("darkWhisperRingMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue DARK_WHISPER_RING_MAX_COUNT = BUILDER
            .comment("Spawn count for Dark Whisper Ring")
            .defineInRange("darkWhisperRingMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue VOID_NECKLACE_CHANCE = BUILDER
            .comment("Spawn chance for Void Necklace in ancient city chests (0.0 - 1.0)")
            .defineInRange("voidNecklaceChance", 0.09, 0.0, 1.0);

    public static final ModConfigSpec.IntValue VOID_NECKLACE_MIN_COUNT = BUILDER
            .comment("Spawn count for Void Necklace")
            .defineInRange("voidNecklaceMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue VOID_NECKLACE_MAX_COUNT = BUILDER
            .comment("Spawn count for Void Necklace")
            .defineInRange("voidNecklaceMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue MIST_VEIL_RING_CHANCE = BUILDER
            .comment("Spawn chance for Mist Veil Ring in ancient city chests (0.0 - 1.0)")
            .defineInRange("mistVeilRingChance", 0.09, 0.0, 1.0);

    public static final ModConfigSpec.IntValue MIST_VEIL_RING_MIN_COUNT = BUILDER
            .comment("Spawn count for Mist Veil Ring")
            .defineInRange("mistVeilRingMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue MIST_VEIL_RING_MAX_COUNT = BUILDER
            .comment("Spawn count for Mist Veil Ring")
            .defineInRange("mistVeilRingMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue ECHO_EARRING_CHANCE = BUILDER
            .comment("Spawn chance for Echo Earring in ancient city chests (0.0 - 1.0)")
            .defineInRange("echoEarringChance", 0.09, 0.0, 1.0);

    public static final ModConfigSpec.IntValue ECHO_EARRING_MIN_COUNT = BUILDER
            .comment("Spawn count for Echo Earring")
            .defineInRange("echoEarringMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue ECHO_EARRING_MAX_COUNT = BUILDER
            .comment("Spawn count for Echo Earring")
            .defineInRange("echoEarringMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue PAST_RING_CHANCE = BUILDER
            .comment("Spawn chance for Past Ring in ancient city chests (0.0 - 1.0)")
            .defineInRange("pastRingChance", 0.09, 0.0, 1.0);

    public static final ModConfigSpec.IntValue PAST_RING_MIN_COUNT = BUILDER
            .comment("Spawn count for Past Ring")
            .defineInRange("pastRingMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue PAST_RING_MAX_COUNT = BUILDER
            .comment("Spawn count for Past Ring")
            .defineInRange("pastRingMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue MEMORY_NECKLACE_CHANCE = BUILDER
            .comment("Spawn chance for Memory Necklace in ancient city chests (0.0 - 1.0)")
            .defineInRange("memoryNecklaceChance", 0.09, 0.0, 1.0);

    public static final ModConfigSpec.IntValue MEMORY_NECKLACE_MIN_COUNT = BUILDER
            .comment("Spawn count for Memory Necklace")
            .defineInRange("memoryNecklaceMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue MEMORY_NECKLACE_MAX_COUNT = BUILDER
            .comment("Spawn count for Memory Necklace")
            .defineInRange("memoryNecklaceMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue MOMENT_STONE_CHANCE = BUILDER
            .comment("Spawn chance for Moment Stone in end city treasure chests (0.0 - 1.0)")
            .defineInRange("momentStoneChance", 0.075, 0.0, 1.0);

    public static final ModConfigSpec.IntValue MOMENT_STONE_MIN_COUNT = BUILDER
            .comment("Spawn count for Moment Stone")
            .defineInRange("momentStoneMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue MOMENT_STONE_MAX_COUNT = BUILDER
            .comment("Spawn count for Moment Stone")
            .defineInRange("momentStoneMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue HEART_VOICE_PENDANT_CHANCE = BUILDER
            .comment("Spawn chance for Heart Voice Pendant in end city treasure chests (0.0 - 1.0)")
            .defineInRange("heartVoicePendantChance", 0.075, 0.0, 1.0);

    public static final ModConfigSpec.IntValue HEART_VOICE_PENDANT_MIN_COUNT = BUILDER
            .comment("Spawn count for Heart Voice Pendant")
            .defineInRange("heartVoicePendantMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue HEART_VOICE_PENDANT_MAX_COUNT = BUILDER
            .comment("Spawn count for Heart Voice Pendant")
            .defineInRange("heartVoicePendantMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue MEMORY_STARDUST_CHANCE = BUILDER
            .comment("Spawn chance for Memory Stardust in end city treasure chests (0.0 - 1.0)")
            .defineInRange("memoryStardustChance", 0.075, 0.0, 1.0);

    public static final ModConfigSpec.IntValue MEMORY_STARDUST_MIN_COUNT = BUILDER
            .comment("Spawn count for Memory Stardust")
            .defineInRange("memoryStardustMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue MEMORY_STARDUST_MAX_COUNT = BUILDER
            .comment("Spawn count for Memory Stardust")
            .defineInRange("memoryStardustMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue TIME_HOURGLASS_CHANCE = BUILDER
            .comment("Spawn chance for Time Hourglass in end city treasure chests (0.0 - 1.0)")
            .defineInRange("timeHourglassChance", 0.075, 0.0, 1.0);

    public static final ModConfigSpec.IntValue TIME_HOURGLASS_MIN_COUNT = BUILDER
            .comment("Spawn count for Time Hourglass")
            .defineInRange("timeHourglassMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue TIME_HOURGLASS_MAX_COUNT = BUILDER
            .comment("Spawn count for Time Hourglass")
            .defineInRange("timeHourglassMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue TASSEL_RING_CHANCE = BUILDER
            .comment("Spawn chance for Tassel Ring in end city treasure chests (0.0 - 1.0)")
            .defineInRange("tasselRingChance", 0.075, 0.0, 1.0);

    public static final ModConfigSpec.IntValue TASSEL_RING_MIN_COUNT = BUILDER
            .comment("Spawn count for Tassel Ring")
            .defineInRange("tasselRingMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue TASSEL_RING_MAX_COUNT = BUILDER
            .comment("Spawn count for Tassel Ring")
            .defineInRange("tasselRingMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue OBSERVE_SELF_EYE_CHANCE = BUILDER
            .comment("Spawn chance for Observe Self Eye in abandoned mineshaft chests (0.0 - 1.0)")
            .defineInRange("observeSelfEyeChance", 0.03, 0.0, 1.0);

    public static final ModConfigSpec.IntValue OBSERVE_SELF_EYE_MIN_COUNT = BUILDER
            .comment("Spawn count for Observe Self Eye")
            .defineInRange("observeSelfEyeMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue OBSERVE_SELF_EYE_MAX_COUNT = BUILDER
            .comment("Spawn count for Observe Self Eye")
            .defineInRange("observeSelfEyeMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue ROYAL_CROWN_CHANCE = BUILDER
            .comment("Spawn chance for Royal Crown in abandoned mineshaft chests (0.0 - 1.0)")
            .defineInRange("royalCrownChance", 0.03, 0.0, 1.0);

    public static final ModConfigSpec.IntValue ROYAL_CROWN_MIN_COUNT = BUILDER
            .comment("Spawn count for Royal Crown")
            .defineInRange("royalCrownMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue ROYAL_CROWN_MAX_COUNT = BUILDER
            .comment("Spawn count for Royal Crown")
            .defineInRange("royalCrownMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue PURE_HOLY_GRAIL_CHANCE = BUILDER
            .comment("Spawn chance for Pure Holy Grail in desert pyramid chests (0.0 - 1.0)")
            .defineInRange("pureHolyGrailChance", 0.03, 0.0, 1.0);

    public static final ModConfigSpec.IntValue PURE_HOLY_GRAIL_MIN_COUNT = BUILDER
            .comment("Spawn count for Pure Holy Grail")
            .defineInRange("pureHolyGrailMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue PURE_HOLY_GRAIL_MAX_COUNT = BUILDER
            .comment("Spawn count for Pure Holy Grail")
            .defineInRange("pureHolyGrailMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue ELVEN_BOOTS_CHANCE = BUILDER
            .comment("Spawn chance for Elven Boots in desert pyramid chests (0.0 - 1.0)")
            .defineInRange("elvenBootsChance", 0.03, 0.0, 1.0);

    public static final ModConfigSpec.IntValue ELVEN_BOOTS_MIN_COUNT = BUILDER
            .comment("Spawn count for Elven Boots")
            .defineInRange("elvenBootsMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue ELVEN_BOOTS_MAX_COUNT = BUILDER
            .comment("Spawn count for Elven Boots")
            .defineInRange("elvenBootsMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue DREAM_BALANCE_CHANCE = BUILDER
            .comment("Spawn chance for Dream Balance in bastion hoglin stable chests (0.0 - 1.0)")
            .defineInRange("dreamBalanceChance", 0.045, 0.0, 1.0);

    public static final ModConfigSpec.IntValue DREAM_BALANCE_MIN_COUNT = BUILDER
            .comment("Spawn count for Dream Balance")
            .defineInRange("dreamBalanceMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue DREAM_BALANCE_MAX_COUNT = BUILDER
            .comment("Spawn count for Dream Balance")
            .defineInRange("dreamBalanceMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue YEARS_AMBER_CHANCE = BUILDER
            .comment("Spawn chance for Years Amber in bastion hoglin stable chests (0.0 - 1.0)")
            .defineInRange("yearsAmberChance", 0.045, 0.0, 1.0);

    public static final ModConfigSpec.IntValue YEARS_AMBER_MIN_COUNT = BUILDER
            .comment("Spawn count for Years Amber")
            .defineInRange("yearsAmberMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue YEARS_AMBER_MAX_COUNT = BUILDER
            .comment("Spawn count for Years Amber")
            .defineInRange("yearsAmberMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue ICARUS_WINGS_CHANCE = BUILDER
            .comment("Spawn chance for Icarus Wings in bastion hoglin stable chests (0.0 - 1.0)")
            .defineInRange("icarusWingsChance", 0.045, 0.0, 1.0);

    public static final ModConfigSpec.IntValue ICARUS_WINGS_MIN_COUNT = BUILDER
            .comment("Spawn count for Icarus Wings")
            .defineInRange("icarusWingsMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue ICARUS_WINGS_MAX_COUNT = BUILDER
            .comment("Spawn count for Icarus Wings")
            .defineInRange("icarusWingsMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue ENDLESS_DREAM_CHANCE = BUILDER
            .comment("Spawn chance for Endless Dream in pillager outpost chests (0.0 - 1.0)")
            .defineInRange("endlessDreamChance", 0.03, 0.0, 1.0);

    public static final ModConfigSpec.IntValue ENDLESS_DREAM_MIN_COUNT = BUILDER
            .comment("Spawn count for Endless Dream")
            .defineInRange("endlessDreamMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue ENDLESS_DREAM_MAX_COUNT = BUILDER
            .comment("Spawn count for Endless Dream")
            .defineInRange("endlessDreamMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue AWAKEN_DREAM_BRACELET_CHANCE = BUILDER
            .comment("Spawn chance for Awaken Dream Bracelet in pillager outpost chests (0.0 - 1.0)")
            .defineInRange("awakenDreamBraceletChance", 0.03, 0.0, 1.0);

    public static final ModConfigSpec.IntValue AWAKEN_DREAM_BRACELET_MIN_COUNT = BUILDER
            .comment("Spawn count for Awaken Dream Bracelet")
            .defineInRange("awakenDreamBraceletMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue AWAKEN_DREAM_BRACELET_MAX_COUNT = BUILDER
            .comment("Spawn count for Awaken Dream Bracelet")
            .defineInRange("awakenDreamBraceletMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue ROYAL_LENS_CHANCE = BUILDER
            .comment("Spawn chance for Royal Lens in ruined portal chests (0.0 - 1.0)")
            .defineInRange("royalLensChance", 0.01, 0.0, 1.0);

    public static final ModConfigSpec.IntValue ROYAL_LENS_MIN_COUNT = BUILDER
            .comment("Spawn count for Royal Lens")
            .defineInRange("royalLensMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue ROYAL_LENS_MAX_COUNT = BUILDER
            .comment("Spawn count for Royal Lens")
            .defineInRange("royalLensMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue RARE_GOLD_BRACELET_CHANCE = BUILDER
            .comment("Spawn chance for Rare Gold Bracelet in villager toolsmith chests (0.0 - 1.0)")
            .defineInRange("rareGoldBraceletChance", 0.01, 0.0, 1.0);

    public static final ModConfigSpec.IntValue RARE_GOLD_BRACELET_MIN_COUNT = BUILDER
            .comment("Spawn count for Rare Gold Bracelet")
            .defineInRange("rareGoldBraceletMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue RARE_GOLD_BRACELET_MAX_COUNT = BUILDER
            .comment("Spawn count for Rare Gold Bracelet")
            .defineInRange("rareGoldBraceletMaxCount", 1, 1, 64);

    public static final ModConfigSpec.DoubleValue NIGHTMARE_BOOK_CHANCE = BUILDER
            .comment("Spawn chance for Nightmare Book in stronghold library chests (0.0 - 1.0)")
            .defineInRange("NightmareBookChance", 0.05, 0.0, 1.0);

    public static final ModConfigSpec.IntValue NIGHTMARE_BOOK_MIN_COUNT = BUILDER
            .comment("Spawn count for Nightmare Book")
            .defineInRange("NightmareBookMinCount", 1, 1, 64);

    public static final ModConfigSpec.IntValue NIGHTMARE_BOOK_MAX_COUNT = BUILDER
            .comment("Spawn count for Nightmare Book")
            .defineInRange("NightmareBookMaxCount", 1, 1, 64);

    public static final ModConfigSpec SPEC = BUILDER.build();
}