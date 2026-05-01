package com.bmt.dream_relics.config;

import com.bmt.dream_relics.DreamRelics;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue CHARGE_TIME = BUILDER
            .comment(
                    "Charge time in ticks for Soul Mirror (20 ticks = 1 second)",
                    "灵魂之镜的充能时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("chargeTime", 40, 1, 200);

    public static final ForgeConfigSpec.IntValue COOLDOWN_TIME = BUILDER
            .comment(
                    "Cooldown time in ticks for Soul Mirror (20 ticks = 1 second)",
                    "灵魂之镜的冷却时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("cooldownTime", 4800, 1, 36000);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_TRIGGER_CHANCE = BUILDER
            .comment(
                    "Trigger chance for Echo Earring sonic wave (0.0 - 1.0)",
                    "回响耳坠音波触发的概率（0.0 - 1.0）"
            )
            .defineInRange("echoEarringTriggerChance", 0.30, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_EXTRA_DAMAGE_MULTIPLIER = BUILDER
            .comment(
                    "Extra damage multiplier for Echo Earring sonic wave (e.g., 0.40 = 40% of base damage)",
                    "回响耳坠音波的额外伤害倍率（例如 0.40 = 基础伤害的40%）"
            )
            .defineInRange("echoEarringExtraDamageMultiplier", 0.40, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_RANGE = BUILDER
            .comment(
                    "Range in blocks for Echo Earring sonic wave",
                    "回响耳坠音波的范围（方块）"
            )
            .defineInRange("echoEarringRange", 8.0, 1.0, 64.0);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_WIDTH = BUILDER
            .comment(
                    "Width in blocks for Echo Earring sonic wave hitbox",
                    "回响耳坠音波的判定宽度（方块）"
            )
            .defineInRange("echoEarringWidth", 0.4, 0.1, 5.0);

    public static final ForgeConfigSpec.DoubleValue HEART_VOICE_PENDANT_MAX_DAMAGE_PERCENT = BUILDER
            .comment(
                    "Maximum damage per hit as percentage of max health for Heart Voice Pendant (0.0 - 1.0)",
                    "心声吊坠每次受伤最大伤害占最大生命值的百分比（0.0 - 1.0）"
            )
            .defineInRange("heartVoicePendantMaxDamagePercent", 0.25, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue ENDLESS_DREAM_TRIGGER_CHANCE = BUILDER
            .comment(
                    "Trigger chance for Endless Dream sleep effect on attack (0.0 - 1.0)",
                    "无尽梦攻击时触发睡眠效果的概率（0.0 - 1.0）"
            )
            .defineInRange("endlessDreamTriggerChance", 0.10, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue ENDLESS_DREAM_SLEEP_DURATION = BUILDER
            .comment(
                    "Sleep duration in ticks for Endless Dream (20 ticks = 1 second)",
                    "无尽梦的睡眠持续时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("endlessDreamSleepDuration", 60, 1, 1200);

    public static final ForgeConfigSpec.DoubleValue TASSEL_RING_DAMAGE_MULTIPLIER_MAX = BUILDER
            .comment(
                    "Maximum damage multiplier for Tassel Ring (at 100% enemy health)",
                    "流苏戒指的最大伤害倍率（敌人满血时）"
            )
            .defineInRange("tasselRingDamageMultiplierMax", 0.5, 0.0, 5.0);

    public static final ForgeConfigSpec.DoubleValue ROYAL_CROWN_PET_DAMAGE_REDUCTION = BUILDER
            .comment(
                    "Damage reduction multiplier for pets when Royal Crown is equipped (0.0 - 1.0)",
                    "王室之冠的宠物伤害减免倍率（0.0 - 1.0）"
            )
            .defineInRange("royalCrownPetDamageReduction", 0.6, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue ROYAL_CROWN_PET_DAMAGE_BOOST = BUILDER
            .comment(
                    "Damage boost multiplier for pet attacks when Royal Crown is equipped",
                    "王室之冠的宠物攻击伤害加成倍率"
            )
            .defineInRange("royalCrownPetDamageBoost", 1.2, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue RARE_GOLD_BRACELET_DOUBLE_DROP_CHANCE = BUILDER
            .comment(
                    "Chance to double ore drops for Rare Gold Bracelet (0.0 - 1.0)",
                    "星辰手镯双倍矿物掉落的概率（0.0 - 1.0）"
            )
            .defineInRange("rareGoldBraceletDoubleDropChance", 0.30, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_EFFECT_RANGE = BUILDER
            .comment(
                    "Effect range in blocks for Dream Totem sleep aura",
                    "梦境图腾睡眠光环的效果范围（方块）"
            )
            .defineInRange("dreamTotemEffectRange", 16, 1, 64);

    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_SLEEP_DURATION = BUILDER
            .comment(
                    "Sleep duration in ticks for Dream Totem aura (20 ticks = 1 second)",
                    "梦境图腾光环的睡眠持续时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("dreamTotemSleepDuration", 200, 1, 1200);

    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_COOLDOWN = BUILDER
            .comment(
                    "Cooldown in ticks for Dream Totem (20 ticks = 1 second)",
                    "梦境图腾的冷却时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("dreamTotemCooldown", 1200, 1, 36000);

    public static final ForgeConfigSpec.DoubleValue PURE_HOLY_GRAIL_XP_MULTIPLIER = BUILDER
            .comment(
                    "Experience multiplier for Pure Holy Grail",
                    "纯洁圣杯的经验倍率"
            )
            .defineInRange("pureHolyGrailXpMultiplier", 1.5, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue ROYAL_LENS_CRIT_DAMAGE_MULTIPLIER = BUILDER
            .comment(
                    "Critical hit damage multiplier for Royal Lens (default 2.0 = 2x damage)",
                    "王室镜片的暴击伤害倍率（默认 2.0 = 2倍伤害）"
            )
            .defineInRange("royalLensCritDamageMultiplier", 2.0, 1.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue MEMORY_NECKLACE_MAX_STORED_DAMAGE = BUILDER
            .comment(
                    "Maximum stored damage for Memory Necklace",
                    "记忆项链的最大存储伤害值"
            )
            .defineInRange("memoryNecklaceMaxStoredDamage", 100.0, 1.0, 10000.0);

    public static final ForgeConfigSpec.IntValue PAST_RING_REPAIR_INTERVAL = BUILDER
            .comment(
                    "Repair interval in ticks for Past Ring (20 ticks = 1 second)",
                    "往昔戒指的修复间隔（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("pastRingRepairInterval", 100, 1, 1200);

    public static final ForgeConfigSpec.IntValue PAST_RING_XP_COST_PER_DURABILITY = BUILDER
            .comment(
                    "Experience cost per durability point repaired by Past Ring",
                    "往昔戒指每点耐久修复消耗的经验值"
            )
            .defineInRange("pastRingXpCostPerDurability", 1, 1, 100);

    public static final ForgeConfigSpec.IntValue TIME_HOURGLASS_RADIUS = BUILDER
            .comment(
                    "Radius in blocks for Time Hourglass acceleration effect",
                    "时之沙漏加速效果的范围（方块）"
            )
            .defineInRange("timeHourglassRadius", 8, 1, 32);

    public static final ForgeConfigSpec.DoubleValue TIME_HOURGLASS_ACCELERATION_FACTOR = BUILDER
            .comment(
                    "Maximum acceleration factor for Time Hourglass (at center)",
                    "时之沙漏的最大加速倍率（中心位置）"
            )
            .defineInRange("timeHourglassAccelerationFactor", 2.0, 1.0, 10.0);

    public static final ForgeConfigSpec.IntValue MOMENT_STONE_COOLDOWN = BUILDER
            .comment(
                    "Cooldown in ticks for Moment Stone (20 ticks = 1 second)",
                    "须臾之石的冷却时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("momentStoneCooldown", 400, 1, 36000);

    public static final ForgeConfigSpec.IntValue MOMENT_STONE_FLOW_DURATION = BUILDER
            .comment(
                    "Flow duration in ticks for Moment Stone (20 ticks = 1 second)",
                    "须臾之石的加速持续时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("momentStoneFlowDuration", 180, 1, 36000);

    public static final ForgeConfigSpec.IntValue SLEEPING_STAR_SEED_COOLDOWN = BUILDER
            .comment(
                    "Cooldown in ticks for Sleeping Star Seed (20 ticks = 1 second)",
                    "沉睡星种的冷却时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("sleepingStarSeedCooldown", 1200, 1, 36000);

    public static final ForgeConfigSpec.IntValue SLEEPING_STAR_SEED_SLEEP_DURATION = BUILDER
            .comment(
                    "Sleep duration in ticks for Sleeping Star Seed (20 ticks = 1 second)",
                    "沉睡星种的睡眠持续时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("sleepingStarSeedSleepDuration", 200, 1, 1200);

    public static final ForgeConfigSpec.IntValue DARK_WHISPER_DAGGER_MUTE_DURATION = BUILDER
            .comment(
                    "Mute duration in ticks for Dark Whisper Dagger (20 ticks = 1 second)",
                    "暗黑低语匕首的沉默持续时间（单位：tick，20 tick = 1 秒）"
            )
            .defineInRange("darkWhisperDaggerMuteDuration", 100, 1, 1200);

    public static final ForgeConfigSpec.DoubleValue DARK_WHISPER_DAGGER_DAMAGE_MULTIPLIER = BUILDER
            .comment(
                    "Damage multiplier for muted targets (0.0 - 1.0)",
                    "沉默目标的伤害倍率（0.0 - 1.0）"
            )
            .defineInRange("darkWhisperDaggerDamageMultiplier", 0.5, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue OBSERVE_SELF_EYE_RANGE = BUILDER
            .comment(
                    "Range in blocks for Observe Self Eye glow effect",
                    "观我之瞳发光效果的范围（方块）"
            )
            .defineInRange("observeSelfEyeRange", 16, 1, 64);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int chargeTime;
    public static int cooldownTime;
    public static double echoEarringTriggerChance;
    public static double echoEarringExtraDamageMultiplier;
    public static double echoEarringRange;
    public static double echoEarringWidth;
    public static double heartVoicePendantMaxDamagePercent;
    public static double endlessDreamTriggerChance;
    public static int endlessDreamSleepDuration;
    public static double tasselRingDamageMultiplierMax;
    public static double royalCrownPetDamageReduction;
    public static double royalCrownPetDamageBoost;
    public static double rareGoldBraceletDoubleDropChance;
    public static int dreamTotemEffectRange;
    public static int dreamTotemSleepDuration;
    public static int dreamTotemCooldown;
    public static double pureHolyGrailXpMultiplier;
    public static double royalLensCritDamageMultiplier;
    public static double memoryNecklaceMaxStoredDamage;
    public static int pastRingRepairInterval;
    public static int pastRingXpCostPerDurability;
    public static int timeHourglassRadius;
    public static double timeHourglassAccelerationFactor;
    public static int momentStoneCooldown;
    public static int momentStoneFlowDuration;
    public static int sleepingStarSeedCooldown;
    public static int sleepingStarSeedSleepDuration;
    public static int darkWhisperDaggerMuteDuration;
    public static double darkWhisperDaggerDamageMultiplier;
    public static int observeSelfEyeRange;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        chargeTime = CHARGE_TIME.get();
        cooldownTime = COOLDOWN_TIME.get();
        echoEarringTriggerChance = ECHO_EARRING_TRIGGER_CHANCE.get();
        echoEarringExtraDamageMultiplier = ECHO_EARRING_EXTRA_DAMAGE_MULTIPLIER.get();
        echoEarringRange = ECHO_EARRING_RANGE.get();
        echoEarringWidth = ECHO_EARRING_WIDTH.get();
        heartVoicePendantMaxDamagePercent = HEART_VOICE_PENDANT_MAX_DAMAGE_PERCENT.get();
        endlessDreamTriggerChance = ENDLESS_DREAM_TRIGGER_CHANCE.get();
        endlessDreamSleepDuration = ENDLESS_DREAM_SLEEP_DURATION.get();
        tasselRingDamageMultiplierMax = TASSEL_RING_DAMAGE_MULTIPLIER_MAX.get();
        royalCrownPetDamageReduction = ROYAL_CROWN_PET_DAMAGE_REDUCTION.get();
        royalCrownPetDamageBoost = ROYAL_CROWN_PET_DAMAGE_BOOST.get();
        rareGoldBraceletDoubleDropChance = RARE_GOLD_BRACELET_DOUBLE_DROP_CHANCE.get();
        dreamTotemEffectRange = DREAM_TOTEM_EFFECT_RANGE.get();
        dreamTotemSleepDuration = DREAM_TOTEM_SLEEP_DURATION.get();
        dreamTotemCooldown = DREAM_TOTEM_COOLDOWN.get();
        pureHolyGrailXpMultiplier = PURE_HOLY_GRAIL_XP_MULTIPLIER.get();
        royalLensCritDamageMultiplier = ROYAL_LENS_CRIT_DAMAGE_MULTIPLIER.get();
        memoryNecklaceMaxStoredDamage = MEMORY_NECKLACE_MAX_STORED_DAMAGE.get();
        pastRingRepairInterval = PAST_RING_REPAIR_INTERVAL.get();
        pastRingXpCostPerDurability = PAST_RING_XP_COST_PER_DURABILITY.get();
        timeHourglassRadius = TIME_HOURGLASS_RADIUS.get();
        timeHourglassAccelerationFactor = TIME_HOURGLASS_ACCELERATION_FACTOR.get();
        momentStoneCooldown = MOMENT_STONE_COOLDOWN.get();
        momentStoneFlowDuration = MOMENT_STONE_FLOW_DURATION.get();
        sleepingStarSeedCooldown = SLEEPING_STAR_SEED_COOLDOWN.get();
        sleepingStarSeedSleepDuration = SLEEPING_STAR_SEED_SLEEP_DURATION.get();
        darkWhisperDaggerMuteDuration = DARK_WHISPER_DAGGER_MUTE_DURATION.get();
        darkWhisperDaggerDamageMultiplier = DARK_WHISPER_DAGGER_DAMAGE_MULTIPLIER.get();
        observeSelfEyeRange = OBSERVE_SELF_EYE_RANGE.get();
    }
}