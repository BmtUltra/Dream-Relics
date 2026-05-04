package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.config.LootConfig;
import com.bmt.dream_relics.init.DRLootFunctions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.function.Supplier;

public class SetCountFromConfig extends LootItemConditionalFunction {
    public static final MapCodec<SetCountFromConfig> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance)
                    .and(CountConfig.CODEC.fieldOf("config").forGetter(func -> func.countConfig))
                    .apply(instance, SetCountFromConfig::new)
    );

    private final CountConfig countConfig;

    private SetCountFromConfig(List<LootItemCondition> conditions, CountConfig countConfig) {
        super(conditions);
        this.countConfig = countConfig;
    }

    @Override
    public LootItemFunctionType getType() {
        return DRLootFunctions.SET_COUNT_FROM_CONFIG.get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (stack.isEmpty()) {
            return stack;
        }
        int min = countConfig.min.get();
        int max = countConfig.max.get();
        if (min > max) {
            min = max;
        }
        int count = context.getRandom().nextInt(max - min + 1) + min;
        stack.setCount(count);
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> setCount(CountConfig config) {
        return simpleBuilder((conditions) -> new SetCountFromConfig(conditions, config));
    }

    public enum CountConfig {
        ASTRAL_NECKLACE("astral_necklace",
                LootConfig.ASTRAL_NECKLACE_MIN_COUNT,
                LootConfig.ASTRAL_NECKLACE_MAX_COUNT),
        DREAM_TOTEM("dream_totem",
                LootConfig.DREAM_TOTEM_MIN_COUNT,
                LootConfig.DREAM_TOTEM_MAX_COUNT),
        OCEAN_CURRENT_BLESSING("ocean_current_blessing",
                LootConfig.OCEAN_CURRENT_BLESSING_MIN_COUNT,
                LootConfig.OCEAN_CURRENT_BLESSING_MAX_COUNT),
        DARK_WHISPER_RING("dark_whisper_ring",
                LootConfig.DARK_WHISPER_RING_MIN_COUNT,
                LootConfig.DARK_WHISPER_RING_MAX_COUNT),
        VOID_NECKLACE("void_necklace",
                LootConfig.VOID_NECKLACE_MIN_COUNT,
                LootConfig.VOID_NECKLACE_MAX_COUNT),
        MIST_VEIL_RING("mist_veil_ring",
                LootConfig.MIST_VEIL_RING_MIN_COUNT,
                LootConfig.MIST_VEIL_RING_MAX_COUNT),
        ECHO_EARRING("echo_earring",
                LootConfig.ECHO_EARRING_MIN_COUNT,
                LootConfig.ECHO_EARRING_MAX_COUNT),
        PAST_RING("past_ring",
                LootConfig.PAST_RING_MIN_COUNT,
                LootConfig.PAST_RING_MAX_COUNT),
        MEMORY_NECKLACE("memory_necklace",
                LootConfig.MEMORY_NECKLACE_MIN_COUNT,
                LootConfig.MEMORY_NECKLACE_MAX_COUNT),
        MOMENT_STONE("moment_stone",
                LootConfig.MOMENT_STONE_MIN_COUNT,
                LootConfig.MOMENT_STONE_MAX_COUNT),
        HEART_VOICE_PENDANT("heart_voice_pendant",
                LootConfig.HEART_VOICE_PENDANT_MIN_COUNT,
                LootConfig.HEART_VOICE_PENDANT_MAX_COUNT),
        MEMORY_STARDUST("memory_stardust",
                LootConfig.MEMORY_STARDUST_MIN_COUNT,
                LootConfig.MEMORY_STARDUST_MAX_COUNT),
        TIME_HOURGLASS("time_hourglass",
                LootConfig.TIME_HOURGLASS_MIN_COUNT,
                LootConfig.TIME_HOURGLASS_MAX_COUNT),
        TASSEL_RING("tassel_ring",
                LootConfig.TASSEL_RING_MIN_COUNT,
                LootConfig.TASSEL_RING_MAX_COUNT),
        OBSERVE_SELF_EYE("observe_self_eye",
                LootConfig.OBSERVE_SELF_EYE_MIN_COUNT,
                LootConfig.OBSERVE_SELF_EYE_MAX_COUNT),
        ROYAL_CROWN("royal_crown",
                LootConfig.ROYAL_CROWN_MIN_COUNT,
                LootConfig.ROYAL_CROWN_MAX_COUNT),
        PURE_HOLY_GRAIL("pure_holy_grail",
                LootConfig.PURE_HOLY_GRAIL_MIN_COUNT,
                LootConfig.PURE_HOLY_GRAIL_MAX_COUNT),
        ELVEN_BOOTS("elven_boots",
                LootConfig.ELVEN_BOOTS_MIN_COUNT,
                LootConfig.ELVEN_BOOTS_MAX_COUNT),
        DREAM_BALANCE("dream_balance",
                LootConfig.DREAM_BALANCE_MIN_COUNT,
                LootConfig.DREAM_BALANCE_MAX_COUNT),
        YEARS_AMBER("years_amber",
                LootConfig.YEARS_AMBER_MIN_COUNT,
                LootConfig.YEARS_AMBER_MAX_COUNT),
        ICARUS_WINGS("icarus_wings",
                LootConfig.ICARUS_WINGS_MIN_COUNT,
                LootConfig.ICARUS_WINGS_MAX_COUNT),
        ENDLESS_DREAM("endless_dream",
                LootConfig.ENDLESS_DREAM_MIN_COUNT,
                LootConfig.ENDLESS_DREAM_MAX_COUNT),
        AWAKEN_DREAM_BRACELET("awaken_dream_bracelet",
                LootConfig.AWAKEN_DREAM_BRACELET_MIN_COUNT,
                LootConfig.AWAKEN_DREAM_BRACELET_MAX_COUNT),
        ROYAL_LENS("royal_lens",
                LootConfig.ROYAL_LENS_MIN_COUNT,
                LootConfig.ROYAL_LENS_MAX_COUNT),
        RARE_GOLD_BRACELET("rare_gold_bracelet",
                LootConfig.RARE_GOLD_BRACELET_MIN_COUNT,
                LootConfig.RARE_GOLD_BRACELET_MAX_COUNT),
        NIGHTMARE_BOOK("nightmare_book",
                LootConfig.NIGHTMARE_BOOK_MIN_COUNT,
                LootConfig.NIGHTMARE_BOOK_MAX_COUNT);

        public static final MapCodec<CountConfig> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        ExtraCodecs.NON_EMPTY_STRING.fieldOf("config").forGetter(config -> config.name)
                ).apply(instance, CountConfig::byName)
        );

        final String name;
        final Supplier<Integer> min;
        final Supplier<Integer> max;

        CountConfig(String name, Supplier<Integer> min, Supplier<Integer> max) {
            this.name = name;
            this.min = min;
            this.max = max;
        }

        static CountConfig byName(String name) {
            return valueOf(name.toUpperCase(java.util.Locale.ROOT));
        }
    }
}