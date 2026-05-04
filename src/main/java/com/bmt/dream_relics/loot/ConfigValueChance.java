package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.config.LootConfig;
import com.bmt.dream_relics.init.DRLootConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Locale;
import java.util.function.Supplier;

public record ConfigValueChance(ChanceConfig chanceConfig) implements LootItemCondition {

    public static final MapCodec<ConfigValueChance> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ChanceConfig.CODEC.fieldOf("config").forGetter(ConfigValueChance::chanceConfig)
            ).apply(instance, ConfigValueChance::new)
    );

    @Override
    public LootItemConditionType getType() {
        return DRLootConditions.CONFIG_VALUE_CHANCE.get();
    }

    @Override
    public boolean test(LootContext context) {
        return context.getRandom().nextDouble() < this.chanceConfig.value.get();
    }

    public static LootItemCondition.Builder astralNecklaceChance() {
        return () -> new ConfigValueChance(ChanceConfig.ASTRAL_NECKLACE);
    }

    public static LootItemCondition.Builder dreamTotemChance() {
        return () -> new ConfigValueChance(ChanceConfig.DREAM_TOTEM);
    }

    public static LootItemCondition.Builder oceanCurrentBlessingChance() {
        return () -> new ConfigValueChance(ChanceConfig.OCEAN_CURRENT_BLESSING);
    }

    public static LootItemCondition.Builder darkWhisperRingChance() {
        return () -> new ConfigValueChance(ChanceConfig.DARK_WHISPER_RING);
    }

    public static LootItemCondition.Builder voidNecklaceChance() {
        return () -> new ConfigValueChance(ChanceConfig.VOID_NECKLACE);
    }

    public static LootItemCondition.Builder mistVeilRingChance() {
        return () -> new ConfigValueChance(ChanceConfig.MIST_VEIL_RING);
    }

    public static LootItemCondition.Builder echoEarringChance() {
        return () -> new ConfigValueChance(ChanceConfig.ECHO_EARRING);
    }

    public static LootItemCondition.Builder pastRingChance() {
        return () -> new ConfigValueChance(ChanceConfig.PAST_RING);
    }

    public static LootItemCondition.Builder memoryNecklaceChance() {
        return () -> new ConfigValueChance(ChanceConfig.MEMORY_NECKLACE);
    }

    public static LootItemCondition.Builder momentStoneChance() {
        return () -> new ConfigValueChance(ChanceConfig.MOMENT_STONE);
    }

    public static LootItemCondition.Builder heartVoicePendantChance() {
        return () -> new ConfigValueChance(ChanceConfig.HEART_VOICE_PENDANT);
    }

    public static LootItemCondition.Builder memoryStardustChance() {
        return () -> new ConfigValueChance(ChanceConfig.MEMORY_STARDUST);
    }

    public static LootItemCondition.Builder timeHourglassChance() {
        return () -> new ConfigValueChance(ChanceConfig.TIME_HOURGLASS);
    }

    public static LootItemCondition.Builder tasselRingChance() {
        return () -> new ConfigValueChance(ChanceConfig.TASSEL_RING);
    }

    public static LootItemCondition.Builder observeSelfEyeChance() {
        return () -> new ConfigValueChance(ChanceConfig.OBSERVE_SELF_EYE);
    }

    public static LootItemCondition.Builder royalCrownChance() {
        return () -> new ConfigValueChance(ChanceConfig.ROYAL_CROWN);
    }

    public static LootItemCondition.Builder pureHolyGrailChance() {
        return () -> new ConfigValueChance(ChanceConfig.PURE_HOLY_GRAIL);
    }

    public static LootItemCondition.Builder elvenBootsChance() {
        return () -> new ConfigValueChance(ChanceConfig.ELVEN_BOOTS);
    }

    public static LootItemCondition.Builder dreamBalanceChance() {
        return () -> new ConfigValueChance(ChanceConfig.DREAM_BALANCE);
    }

    public static LootItemCondition.Builder yearsAmberChance() {
        return () -> new ConfigValueChance(ChanceConfig.YEARS_AMBER);
    }

    public static LootItemCondition.Builder icarusWingsChance() {
        return () -> new ConfigValueChance(ChanceConfig.ICARUS_WINGS);
    }

    public static LootItemCondition.Builder endlessDreamChance() {
        return () -> new ConfigValueChance(ChanceConfig.ENDLESS_DREAM);
    }

    public static LootItemCondition.Builder awakenDreamBraceletChance() {
        return () -> new ConfigValueChance(ChanceConfig.AWAKEN_DREAM_BRACELET);
    }

    public static LootItemCondition.Builder royalLensChance() {
        return () -> new ConfigValueChance(ChanceConfig.ROYAL_LENS);
    }

    public static LootItemCondition.Builder rareGoldBraceletChance() {
        return () -> new ConfigValueChance(ChanceConfig.RARE_GOLD_BRACELET);
    }

    public static LootItemCondition.Builder nightmareBookChance() {
        return () -> new ConfigValueChance(ChanceConfig.NIGHTMARE_BOOK);
    }

    public enum ChanceConfig {
        ASTRAL_NECKLACE("astral_necklace", LootConfig.ASTRAL_NECKLACE_CHANCE),
        DREAM_TOTEM("dream_totem", LootConfig.DREAM_TOTEM_CHANCE),
        OCEAN_CURRENT_BLESSING("ocean_current_blessing", LootConfig.OCEAN_CURRENT_BLESSING_CHANCE),
        DARK_WHISPER_RING("dark_whisper_ring", LootConfig.DARK_WHISPER_RING_CHANCE),
        VOID_NECKLACE("void_necklace", LootConfig.VOID_NECKLACE_CHANCE),
        MIST_VEIL_RING("mist_veil_ring", LootConfig.MIST_VEIL_RING_CHANCE),
        ECHO_EARRING("echo_earring", LootConfig.ECHO_EARRING_CHANCE),
        PAST_RING("past_ring", LootConfig.PAST_RING_CHANCE),
        MEMORY_NECKLACE("memory_necklace", LootConfig.MEMORY_NECKLACE_CHANCE),
        MOMENT_STONE("moment_stone", LootConfig.MOMENT_STONE_CHANCE),
        HEART_VOICE_PENDANT("heart_voice_pendant", LootConfig.HEART_VOICE_PENDANT_CHANCE),
        MEMORY_STARDUST("memory_stardust", LootConfig.MEMORY_STARDUST_CHANCE),
        TIME_HOURGLASS("time_hourglass", LootConfig.TIME_HOURGLASS_CHANCE),
        TASSEL_RING("tassel_ring", LootConfig.TASSEL_RING_CHANCE),
        OBSERVE_SELF_EYE("observe_self_eye", LootConfig.OBSERVE_SELF_EYE_CHANCE),
        ROYAL_CROWN("royal_crown", LootConfig.ROYAL_CROWN_CHANCE),
        PURE_HOLY_GRAIL("pure_holy_grail", LootConfig.PURE_HOLY_GRAIL_CHANCE),
        ELVEN_BOOTS("elven_boots", LootConfig.ELVEN_BOOTS_CHANCE),
        DREAM_BALANCE("dream_balance", LootConfig.DREAM_BALANCE_CHANCE),
        YEARS_AMBER("years_amber", LootConfig.YEARS_AMBER_CHANCE),
        ICARUS_WINGS("icarus_wings", LootConfig.ICARUS_WINGS_CHANCE),
        ENDLESS_DREAM("endless_dream", LootConfig.ENDLESS_DREAM_CHANCE),
        AWAKEN_DREAM_BRACELET("awaken_dream_bracelet", LootConfig.AWAKEN_DREAM_BRACELET_CHANCE),
        ROYAL_LENS("royal_lens", LootConfig.ROYAL_LENS_CHANCE),
        RARE_GOLD_BRACELET("rare_gold_bracelet", LootConfig.RARE_GOLD_BRACELET_CHANCE),
        NIGHTMARE_BOOK("nightmare_book", LootConfig.NIGHTMARE_BOOK_CHANCE);

        public static final MapCodec<ChanceConfig> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        net.minecraft.util.ExtraCodecs.NON_EMPTY_STRING.fieldOf("config").forGetter(config -> config.name)
                ).apply(instance, ChanceConfig::byName)
        );

        final String name;
        final Supplier<Double> value;

        ChanceConfig(String name, Supplier<Double> value) {
            this.name = name;
            this.value = value;
        }

        static ChanceConfig byName(String name) {
            return valueOf(name.toUpperCase(Locale.ROOT));
        }
    }
}