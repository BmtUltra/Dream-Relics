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
            .comment("Charge time in ticks for Soul Mirror (20 ticks = 1 second)")
            .defineInRange("chargeTime", 40, 1, 200);

    public static final ForgeConfigSpec.IntValue COOLDOWN_TIME = BUILDER
            .comment("Cooldown time in ticks for Soul Mirror (20 ticks = 1 second)")
            .defineInRange("cooldownTime", 4800, 1, 36000);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_TRIGGER_CHANCE = BUILDER
            .comment("Trigger chance for Echo Earring sonic wave (0.0 - 1.0)")
            .defineInRange("echoEarringTriggerChance", 0.30, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_EXTRA_DAMAGE_MULTIPLIER = BUILDER
            .comment("Extra damage multiplier for Echo Earring sonic wave (e.g., 0.40 = 40% of base damage)")
            .defineInRange("echoEarringExtraDamageMultiplier", 0.40, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_RANGE = BUILDER
            .comment("Range in blocks for Echo Earring sonic wave")
            .defineInRange("echoEarringRange", 8.0, 1.0, 64.0);

    public static final ForgeConfigSpec.DoubleValue ECHO_EARRING_WIDTH = BUILDER
            .comment("Width in blocks for Echo Earring sonic wave hitbox")
            .defineInRange("echoEarringWidth", 0.4, 0.1, 5.0);

    public static final ForgeConfigSpec.DoubleValue HEART_VOICE_PENDANT_MAX_DAMAGE_PERCENT = BUILDER
            .comment("Maximum damage per hit as percentage of max health for Heart Voice Pendant (0.0 - 1.0)")
            .defineInRange("heartVoicePendantMaxDamagePercent", 0.25, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue ENDLESS_DREAM_TRIGGER_CHANCE = BUILDER
            .comment("Trigger chance for Endless Dream sleep effect on attack (0.0 - 1.0)")
            .defineInRange("endlessDreamTriggerChance", 0.10, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue ENDLESS_DREAM_SLEEP_DURATION = BUILDER
            .comment("Sleep duration in ticks for Endless Dream (20 ticks = 1 second)")
            .defineInRange("endlessDreamSleepDuration", 60, 1, 1200);

    public static final ForgeConfigSpec.DoubleValue TASSEL_RING_DAMAGE_MULTIPLIER_MAX = BUILDER
            .comment("Maximum damage multiplier for Tassel Ring (at 100% enemy health)")
            .defineInRange("tasselRingDamageMultiplierMax", 0.5, 0.0, 5.0);

    public static final ForgeConfigSpec.DoubleValue ROYAL_CROWN_PET_DAMAGE_REDUCTION = BUILDER
            .comment("Damage reduction multiplier for pets when Royal Crown is equipped (0.0 - 1.0)")
            .defineInRange("royalCrownPetDamageReduction", 0.6, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue ROYAL_CROWN_PET_DAMAGE_BOOST = BUILDER
            .comment("Damage boost multiplier for pet attacks when Royal Crown is equipped")
            .defineInRange("royalCrownPetDamageBoost", 1.2, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue RARE_GOLD_BRACELET_DOUBLE_DROP_CHANCE = BUILDER
            .comment("Chance to double ore drops for Rare Gold Bracelet (0.0 - 1.0)")
            .defineInRange("rareGoldBraceletDoubleDropChance", 0.30, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_EFFECT_RANGE = BUILDER
            .comment("Effect range in blocks for Dream Totem sleep aura")
            .defineInRange("dreamTotemEffectRange", 16, 1, 64);

    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_SLEEP_DURATION = BUILDER
            .comment("Sleep duration in ticks for Dream Totem aura (20 ticks = 1 second)")
            .defineInRange("dreamTotemSleepDuration", 200, 1, 1200);

    public static final ForgeConfigSpec.IntValue DREAM_TOTEM_COOLDOWN = BUILDER
            .comment("Cooldown in ticks for Dream Totem (20 ticks = 1 second)")
            .defineInRange("dreamTotemCooldown", 1200, 1, 36000);

    public static final ForgeConfigSpec.DoubleValue PURE_HOLY_GRAIL_XP_MULTIPLIER = BUILDER
            .comment("Experience multiplier for Pure Holy Grail")
            .defineInRange("pureHolyGrailXpMultiplier", 1.5, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue ROYAL_LENS_CRIT_DAMAGE_MULTIPLIER = BUILDER
            .comment("Critical hit damage multiplier for Royal Lens (default 2.0 = 2x damage)")
            .defineInRange("royalLensCritDamageMultiplier", 2.0, 1.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue MEMORY_NECKLACE_MAX_STORED_DAMAGE = BUILDER
            .comment("Maximum stored damage for Memory Necklace")
            .defineInRange("memoryNecklaceMaxStoredDamage", 100.0, 1.0, 10000.0);

    public static final ForgeConfigSpec.IntValue PAST_RING_REPAIR_INTERVAL = BUILDER
            .comment("Repair interval in ticks for Past Ring (20 ticks = 1 second)")
            .defineInRange("pastRingRepairInterval", 100, 1, 1200);

    public static final ForgeConfigSpec.IntValue PAST_RING_XP_COST_PER_DURABILITY = BUILDER
            .comment("Experience cost per durability point repaired by Past Ring")
            .defineInRange("pastRingXpCostPerDurability", 1, 1, 100);

    public static final ForgeConfigSpec.IntValue TIME_HOURGLASS_RADIUS = BUILDER
            .comment("Radius in blocks for Time Hourglass acceleration effect")
            .defineInRange("timeHourglassRadius", 8, 1, 32);

    public static final ForgeConfigSpec.DoubleValue TIME_HOURGLASS_ACCELERATION_FACTOR = BUILDER
            .comment("Maximum acceleration factor for Time Hourglass (at center)")
            .defineInRange("timeHourglassAccelerationFactor", 2.0, 1.0, 10.0);

    public static final ForgeConfigSpec.IntValue MOMENT_STONE_COOLDOWN = BUILDER
            .comment("Cooldown in ticks for Moment Stone (20 ticks = 1 second)")
            .defineInRange("momentStoneCooldown", 400, 1, 36000);

    public static final ForgeConfigSpec.IntValue MOMENT_STONE_FLOW_DURATION = BUILDER
            .comment("Flow duration in ticks for Moment Stone (20 ticks = 1 second)")
            .defineInRange("momentStoneFlowDuration", 180, 1, 36000);

    public static final ForgeConfigSpec.IntValue SLEEPING_STAR_SEED_COOLDOWN = BUILDER
            .comment("Cooldown in ticks for Sleeping Star Seed (20 ticks = 1 second)")
            .defineInRange("sleepingStarSeedCooldown", 1200, 1, 36000);

    public static final ForgeConfigSpec.IntValue SLEEPING_STAR_SEED_SLEEP_DURATION = BUILDER
            .comment("Sleep duration in ticks for Sleeping Star Seed (20 ticks = 1 second)")
            .defineInRange("sleepingStarSeedSleepDuration", 200, 1, 1200);

    public static final ForgeConfigSpec.IntValue DARK_WHISPER_DAGGER_MUTE_DURATION = BUILDER
            .comment("Mute duration in ticks for Dark Whisper Dagger (20 ticks = 1 second)")
            .defineInRange("darkWhisperDaggerMuteDuration", 100, 1, 1200);

    public static final ForgeConfigSpec.DoubleValue DARK_WHISPER_DAGGER_DAMAGE_MULTIPLIER = BUILDER
            .comment("Damage multiplier for muted targets (0.0 - 1.0)")
            .defineInRange("darkWhisperDaggerDamageMultiplier", 0.5, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue OBSERVE_SELF_EYE_RANGE = BUILDER
            .comment("Range in blocks for Observe Self Eye glow effect")
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