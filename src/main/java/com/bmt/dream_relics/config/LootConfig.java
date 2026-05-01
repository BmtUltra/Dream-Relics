package com.bmt.dream_relics.config;

import com.bmt.dream_relics.DreamRelics;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.DoubleValue ASTRAL_NECKLACE_CHANCE;
    public static final ForgeConfigSpec.IntValue ASTRAL_NECKLACE_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue ASTRAL_NECKLACE_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue DREAM_TOTEM_CHANCE;
    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue OCEAN_CURRENT_BLESSING_CHANCE;
    public static final ForgeConfigSpec.IntValue OCEAN_CURRENT_BLESSING_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue OCEAN_CURRENT_BLESSING_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue DARK_WHISPER_RING_CHANCE;
    public static final ForgeConfigSpec.IntValue DARK_WHISPER_RING_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue DARK_WHISPER_RING_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue VOID_NECKLACE_CHANCE;
    public static final ForgeConfigSpec.IntValue VOID_NECKLACE_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue VOID_NECKLACE_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue MIST_VEIL_RING_CHANCE;
    public static final ForgeConfigSpec.IntValue MIST_VEIL_RING_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue MIST_VEIL_RING_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_CHANCE;
    public static final ForgeConfigSpec.IntValue ECHO_EARRING_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue ECHO_EARRING_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue PAST_RING_CHANCE;
    public static final ForgeConfigSpec.IntValue PAST_RING_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue PAST_RING_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue MEMORY_NECKLACE_CHANCE;
    public static final ForgeConfigSpec.IntValue MEMORY_NECKLACE_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue MEMORY_NECKLACE_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue MOMENT_STONE_CHANCE;
    public static final ForgeConfigSpec.IntValue MOMENT_STONE_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue MOMENT_STONE_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue HEART_VOICE_PENDANT_CHANCE;
    public static final ForgeConfigSpec.IntValue HEART_VOICE_PENDANT_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue HEART_VOICE_PENDANT_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue MEMORY_STARDUST_CHANCE;
    public static final ForgeConfigSpec.IntValue MEMORY_STARDUST_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue MEMORY_STARDUST_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue TIME_HOURGLASS_CHANCE;
    public static final ForgeConfigSpec.IntValue TIME_HOURGLASS_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue TIME_HOURGLASS_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue TASSEL_RING_CHANCE;
    public static final ForgeConfigSpec.IntValue TASSEL_RING_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue TASSEL_RING_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue OBSERVE_SELF_EYE_CHANCE;
    public static final ForgeConfigSpec.IntValue OBSERVE_SELF_EYE_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue OBSERVE_SELF_EYE_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue ROYAL_CROWN_CHANCE;
    public static final ForgeConfigSpec.IntValue ROYAL_CROWN_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue ROYAL_CROWN_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue PURE_HOLY_GRAIL_CHANCE;
    public static final ForgeConfigSpec.IntValue PURE_HOLY_GRAIL_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue PURE_HOLY_GRAIL_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue ELVEN_BOOTS_CHANCE;
    public static final ForgeConfigSpec.IntValue ELVEN_BOOTS_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue ELVEN_BOOTS_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue DREAM_BALANCE_CHANCE;
    public static final ForgeConfigSpec.IntValue DREAM_BALANCE_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue DREAM_BALANCE_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue YEARS_AMBER_CHANCE;
    public static final ForgeConfigSpec.IntValue YEARS_AMBER_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue YEARS_AMBER_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue ICARUS_WINGS_CHANCE;
    public static final ForgeConfigSpec.IntValue ICARUS_WINGS_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue ICARUS_WINGS_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue ENDLESS_DREAM_CHANCE;
    public static final ForgeConfigSpec.IntValue ENDLESS_DREAM_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue ENDLESS_DREAM_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue AWAKEN_DREAM_BRACELET_CHANCE;
    public static final ForgeConfigSpec.IntValue AWAKEN_DREAM_BRACELET_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue AWAKEN_DREAM_BRACELET_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue ROYAL_LENS_CHANCE;
    public static final ForgeConfigSpec.IntValue ROYAL_LENS_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue ROYAL_LENS_MAX_COUNT;
    public static final ForgeConfigSpec.DoubleValue RARE_GOLD_BRACELET_CHANCE;
    public static final ForgeConfigSpec.IntValue RARE_GOLD_BRACELET_MIN_COUNT;
    public static final ForgeConfigSpec.IntValue RARE_GOLD_BRACELET_MAX_COUNT;

    static {
        BUILDER.push("Loot Settings");

        BUILDER.push("Astral Necklace Settings");
        ASTRAL_NECKLACE_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Astral Necklace in bastion treasure chests (0.0 - 1.0)",
                        "堡垒遗迹藏宝藏箱子中星界项链的生成几率（0.0 - 1.0）"
                )
                .defineInRange("AstralNecklaceChance", 0.015, 0.0, 1.0);
        ASTRAL_NECKLACE_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Astral Necklace",
                        "星界项链的最小生成数量"
                )
                .defineInRange("AstralNecklaceMinCount", 1, 1, 64);
        ASTRAL_NECKLACE_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Astral Necklace",
                        "星界项链的最大生成数量"
                )
                .defineInRange("AstralNecklaceMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Dream Totem Settings");
        DREAM_TOTEM_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Dream Totem in woodland mansion chests (0.0 - 1.0)",
                        "林地府邸箱子中梦境图腾的生成几率（0.0 - 1.0）"
                )
                .defineInRange("DreamTotemChance", 0.015, 0.0, 1.0);
        DREAM_TOTEM_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Dream Totem",
                        "梦境图腾的最小生成数量"
                )
                .defineInRange("DreamTotemMinCount", 1, 1, 64);
        DREAM_TOTEM_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Dream Totem",
                        "梦境图腾的最大生成数量"
                )
                .defineInRange("DreamTotemMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Ocean Current Blessing Settings");
        OCEAN_CURRENT_BLESSING_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Ocean Current Blessing in buried treasure chests (0.0 - 1.0)",
                        "埋葬的宝藏箱子中洋流眷顾的生成几率（0.0 - 1.0）"
                )
                .defineInRange("OceanCurrentBlessingChance", 0.015, 0.0, 1.0);
        OCEAN_CURRENT_BLESSING_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Ocean Current Blessing",
                        "洋流眷顾的最小生成数量"
                )
                .defineInRange("OceanCurrentBlessingMinCount", 1, 1, 64);
        OCEAN_CURRENT_BLESSING_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Ocean Current Blessing",
                        "洋流眷顾的最大生成数量"
                )
                .defineInRange("OceanCurrentBlessingMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Dark Whisper Ring Settings");
        DARK_WHISPER_RING_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Dark Whisper Ring in ancient city chests (0.0 - 1.0); use average probability",
                        "远古城市箱子中暗黑低语戒指的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("darkWhisperRingChance", 0.09, 0.0, 1.0);
        DARK_WHISPER_RING_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Dark Whisper Ring",
                        "暗黑低语戒指的最小生成数量"
                )
                .defineInRange("darkWhisperRingMinCount", 1, 1, 64);
        DARK_WHISPER_RING_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Dark Whisper Ring",
                        "暗黑低语戒指的最大生成数量"
                )
                .defineInRange("darkWhisperRingMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Void Necklace Settings");
        VOID_NECKLACE_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Void Necklace in ancient city chests (0.0 - 1.0); use average probability",
                        "远古城市箱子中虚空项链的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("voidNecklaceChance", 0.09, 0.0, 1.0);
        VOID_NECKLACE_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Void Necklace",
                        "虚空项链的最小生成数量"
                )
                .defineInRange("voidNecklaceMinCount", 1, 1, 64);
        VOID_NECKLACE_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Void Necklace",
                        "虚空项链的最大生成数量"
                )
                .defineInRange("voidNecklaceMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Mist Veil Ring Settings");
        MIST_VEIL_RING_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Mist Veil Ring in ancient city chests (0.0 - 1.0); use average probability",
                        "远古城市箱子中雾幔纱戒指的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("mistVeilRingChance", 0.09, 0.0, 1.0);
        MIST_VEIL_RING_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Mist Veil Ring",
                        "雾幔纱戒指的最小生成数量"
                )
                .defineInRange("mistVeilRingMinCount", 1, 1, 64);
        MIST_VEIL_RING_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Mist Veil Ring",
                        "雾幔纱戒指的最大生成数量"
                )
                .defineInRange("mistVeilRingMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Echo Earring Settings");
        ECHO_EARRING_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Echo Earring in ancient city chests (0.0 - 1.0); use average probability",
                        "远古城市箱子中回响耳坠的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("echoEarringChance", 0.09, 0.0, 1.0);
        ECHO_EARRING_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Echo Earring",
                        "回响耳坠的最小生成数量"
                )
                .defineInRange("echoEarringMinCount", 1, 1, 64);
        ECHO_EARRING_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Echo Earring",
                        "回响耳坠的最大生成数量"
                )
                .defineInRange("echoEarringMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Past Ring Settings");
        PAST_RING_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Past Ring in ancient city chests (0.0 - 1.0); use average probability",
                        "远古城市箱子中往昔戒指的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("pastRingChance", 0.09, 0.0, 1.0);
        PAST_RING_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Past Ring",
                        "往昔戒指的最小生成数量"
                )
                .defineInRange("pastRingMinCount", 1, 1, 64);
        PAST_RING_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Past Ring",
                        "往昔戒指的最大生成数量"
                )
                .defineInRange("pastRingMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Memory Necklace Settings");
        MEMORY_NECKLACE_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Memory Necklace in ancient city chests (0.0 - 1.0); use average probability",
                        "远古城市箱子中记忆项链的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("memoryNecklaceChance", 0.09, 0.0, 1.0);
        MEMORY_NECKLACE_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Memory Necklace",
                        "记忆项链的最小生成数量"
                )
                .defineInRange("memoryNecklaceMinCount", 1, 1, 64);
        MEMORY_NECKLACE_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Memory Necklace",
                        "记忆项链的最大生成数量"
                )
                .defineInRange("memoryNecklaceMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Moment Stone Settings");
        MOMENT_STONE_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Moment Stone in end city treasure chests (0.0 - 1.0); use average probability",
                        "末地城宝箱中须臾之石的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("momentStoneChance", 0.075, 0.0, 1.0);
        MOMENT_STONE_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Moment Stone",
                        "须臾之石的最小生成数量"
                )
                .defineInRange("momentStoneMinCount", 1, 1, 64);
        MOMENT_STONE_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Moment Stone",
                        "须臾之石的最大生成数量"
                )
                .defineInRange("momentStoneMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Heart Voice Pendant Settings");
        HEART_VOICE_PENDANT_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Heart Voice Pendant in end city treasure chests (0.0 - 1.0); use average probability",
                        "末地城宝箱中心声吊坠的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("heartVoicePendantChance", 0.075, 0.0, 1.0);
        HEART_VOICE_PENDANT_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Heart Voice Pendant",
                        "心声吊坠的最小生成数量"
                )
                .defineInRange("heartVoicePendantMinCount", 1, 1, 64);
        HEART_VOICE_PENDANT_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Heart Voice Pendant",
                        "心声吊坠的最大生成数量"
                )
                .defineInRange("heartVoicePendantMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Memory Stardust Settings");
        MEMORY_STARDUST_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Memory Stardust in end city treasure chests (0.0 - 1.0); use average probability",
                        "末地城宝箱中记忆星尘的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("memoryStardustChance", 0.075, 0.0, 1.0);
        MEMORY_STARDUST_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Memory Stardust",
                        "记忆星尘的最小生成数量"
                )
                .defineInRange("memoryStardustMinCount", 1, 1, 64);
        MEMORY_STARDUST_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Memory Stardust",
                        "记忆星尘的最大生成数量"
                )
                .defineInRange("memoryStardustMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Time Hourglass Settings");
        TIME_HOURGLASS_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Time Hourglass in end city treasure chests (0.0 - 1.0); use average probability",
                        "末地城宝箱中时之沙漏的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("timeHourglassChance", 0.075, 0.0, 1.0);
        TIME_HOURGLASS_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Time Hourglass",
                        "时之沙漏的最小生成数量"
                )
                .defineInRange("timeHourglassMinCount", 1, 1, 64);
        TIME_HOURGLASS_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Time Hourglass",
                        "时之沙漏的最大生成数量"
                )
                .defineInRange("timeHourglassMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Tassel Ring Settings");
        TASSEL_RING_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Tassel Ring in end city treasure chests (0.0 - 1.0); use average probability",
                        "末地城宝箱中流苏戒指的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("tasselRingChance", 0.075, 0.0, 1.0);
        TASSEL_RING_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Tassel Ring",
                        "流苏戒指的最小生成数量"
                )
                .defineInRange("tasselRingMinCount", 1, 1, 64);
        TASSEL_RING_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Tassel Ring",
                        "流苏戒指的最大生成数量"
                )
                .defineInRange("tasselRingMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Observe Self Eye Settings");
        OBSERVE_SELF_EYE_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Observe Self Eye in abandoned mineshaft chests (0.0 - 1.0); use average probability",
                        "废弃矿井箱子中观我之瞳的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("observeSelfEyeChance", 0.03, 0.0, 1.0);
        OBSERVE_SELF_EYE_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Observe Self Eye",
                        "观我之瞳的最小生成数量"
                )
                .defineInRange("observeSelfEyeMinCount", 1, 1, 64);
        OBSERVE_SELF_EYE_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Observe Self Eye",
                        "观我之瞳的最大生成数量"
                )
                .defineInRange("observeSelfEyeMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Royal Crown Settings");
        ROYAL_CROWN_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Royal Crown in abandoned mineshaft chests (0.0 - 1.0); use average probability",
                        "废弃矿井箱子中王室之冠的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("royalCrownChance", 0.03, 0.0, 1.0);
        ROYAL_CROWN_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Royal Crown",
                        "王室之冠的最小生成数量"
                )
                .defineInRange("royalCrownMinCount", 1, 1, 64);
        ROYAL_CROWN_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Royal Crown",
                        "王室之冠的最大生成数量"
                )
                .defineInRange("royalCrownMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Pure Holy Grail Settings");
        PURE_HOLY_GRAIL_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Pure Holy Grail in desert pyramid chests (0.0 - 1.0); use average probability",
                        "沙漠神殿箱子中纯洁圣杯的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("pureHolyGrailChance", 0.03, 0.0, 1.0);
        PURE_HOLY_GRAIL_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Pure Holy Grail",
                        "纯洁圣杯的最小生成数量"
                )
                .defineInRange("pureHolyGrailMinCount", 1, 1, 64);
        PURE_HOLY_GRAIL_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Pure Holy Grail",
                        "纯洁圣杯的最大生成数量"
                )
                .defineInRange("pureHolyGrailMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Elven Boots Settings");
        ELVEN_BOOTS_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Elven Boots in desert pyramid chests (0.0 - 1.0); use average probability",
                        "沙漠神殿箱子中精灵之靴的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("elvenBootsChance", 0.03, 0.0, 1.0);
        ELVEN_BOOTS_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Elven Boots",
                        "精灵之靴的最小生成数量"
                )
                .defineInRange("elvenBootsMinCount", 1, 1, 64);
        ELVEN_BOOTS_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Elven Boots",
                        "精灵之靴的最大生成数量"
                )
                .defineInRange("elvenBootsMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Dream Balance Settings");
        DREAM_BALANCE_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Dream Balance in bastion hoglin stable chests (0.0 - 1.0); use average probability",
                        "遗迹堡垒其他箱子中梦境图腾的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("dreamBalanceChance", 0.045, 0.0, 1.0);
        DREAM_BALANCE_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Dream Balance",
                        "梦境图腾的最小生成数量"
                )
                .defineInRange("dreamBalanceMinCount", 1, 1, 64);
        DREAM_BALANCE_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Dream Balance",
                        "梦境图腾的最大生成数量"
                )
                .defineInRange("dreamBalanceMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Years Amber Settings");
        YEARS_AMBER_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Years Amber in bastion hoglin stable chests (0.0 - 1.0); use average probability",
                        "遗迹堡垒其他箱子中岁月琥珀的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("yearsAmberChance", 0.045, 0.0, 1.0);
        YEARS_AMBER_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Years Amber",
                        "岁月琥珀的最小生成数量"
                )
                .defineInRange("yearsAmberMinCount", 1, 1, 64);
        YEARS_AMBER_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Years Amber",
                        "岁月琥珀的最大生成数量"
                )
                .defineInRange("yearsAmberMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Icarus Wings Settings");
        ICARUS_WINGS_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Icarus Wings in bastion hoglin stable chests (0.0 - 1.0); use average probability",
                        "遗迹堡垒其他箱子中伊卡洛斯之翼的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("icarusWingsChance", 0.045, 0.0, 1.0);
        ICARUS_WINGS_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Icarus Wings",
                        "伊卡洛斯之翼的最小生成数量"
                )
                .defineInRange("icarusWingsMinCount", 1, 1, 64);
        ICARUS_WINGS_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Icarus Wings",
                        "伊卡洛斯之翼的最大生成数量"
                )
                .defineInRange("icarusWingsMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Endless Dream Settings");
        ENDLESS_DREAM_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Endless Dream in pillager outpost chests (0.0 - 1.0); use average probability",
                        "掠夺者前哨站箱子中无尽梦的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("endlessDreamChance", 0.03, 0.0, 1.0);
        ENDLESS_DREAM_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Endless Dream",
                        "无尽梦的最小生成数量"
                )
                .defineInRange("endlessDreamMinCount", 1, 1, 64);
        ENDLESS_DREAM_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Endless Dream",
                        "无尽梦的最大生成数量"
                )
                .defineInRange("endlessDreamMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Awaken Dream Bracelet Settings");
        AWAKEN_DREAM_BRACELET_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Awaken Dream Bracelet in pillager outpost chests (0.0 - 1.0); use average probability",
                        "掠夺者前哨站箱子中醒梦手镯的生成几率（0.0 - 1.0）；使用平均概率"
                )
                .defineInRange("awakenDreamBraceletChance", 0.03, 0.0, 1.0);
        AWAKEN_DREAM_BRACELET_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Awaken Dream Bracelet",
                        "醒梦手镯的最小生成数量"
                )
                .defineInRange("awakenDreamBraceletMinCount", 1, 1, 64);
        AWAKEN_DREAM_BRACELET_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Awaken Dream Bracelet",
                        "醒梦手镯的最大生成数量"
                )
                .defineInRange("awakenDreamBraceletMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Royal Lens Settings");
        ROYAL_LENS_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Royal Lens in ruined portal chests (0.0 - 1.0)",
                        "废弃传送门箱子中王室镜片的生成几率（0.0 - 1.0）"
                )
                .defineInRange("royalLensChance", 0.01, 0.0, 1.0);
        ROYAL_LENS_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Royal Lens",
                        "王室镜片的最小生成数量"
                )
                .defineInRange("royalLensMinCount", 1, 1, 64);
        ROYAL_LENS_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Royal Lens",
                        "王室镜片的最大生成数量"
                )
                .defineInRange("royalLensMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.push("Rare Gold Bracelet Settings");
        RARE_GOLD_BRACELET_CHANCE = BUILDER
                .comment(
                        "Spawn chance for Rare Gold Bracelet in villager toolsmith chests (0.0 - 1.0)",
                        "村庄工具匠箱子中星辰手镯的生成几率（0.0 - 1.0）"
                )
                .defineInRange("rareGoldBraceletChance", 0.01, 0.0, 1.0);
        RARE_GOLD_BRACELET_MIN_COUNT = BUILDER
                .comment(
                        "Spawn count for Rare Gold Bracelet",
                        "星辰手镯的最小生成数量"
                )
                .defineInRange("rareGoldBraceletMinCount", 1, 1, 64);
        RARE_GOLD_BRACELET_MAX_COUNT = BUILDER
                .comment(
                        "Spawn count for Rare Gold Bracelet",
                        "星辰手镯的最大生成数量"
                )
                .defineInRange("rareGoldBraceletMaxCount", 1, 1, 64);
        BUILDER.pop();

        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}