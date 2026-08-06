package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.config.LootConfig;
import com.bmt.dream_relics.init.DRItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ConfigLootModifier extends LootModifier {
    public static final MapCodec<ConfigLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            codecStart(instance)
                    .and(ResourceLocation.CODEC.listOf().fieldOf("entries").forGetter(m -> m.entries))
                    .apply(instance, ConfigLootModifier::new)
    );

    private final List<ResourceLocation> entries;

    protected ConfigLootModifier(LootItemCondition[] conditionsIn, List<ResourceLocation> entries) {
        super(conditionsIn);
        this.entries = entries;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (ResourceLocation entryId : entries) {
            EntryConfig config = EntryConfig.byName(entryId.getPath());

            double chance = 0;
            if (config != null) {
                chance = config.chance.get();
            }
            if (context.getRandom().nextDouble() < chance) {
                ItemStack stack = null;
                if (config != null) {
                    stack = new ItemStack(config.itemSupplier.get());
                }
                int min = 0;
                if (config != null) {
                    min = config.minCount.get();
                }
                int max = 0;
                if (config != null) {
                    max = config.maxCount.get();
                }
                if (min > max) min = max;
                int count = context.getRandom().nextInt(max - min + 1) + min;
                if (stack != null) {
                    stack.setCount(count);
                }
                generatedLoot.add(stack);
            }
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    public enum EntryConfig {
        ASTRAL_NECKLACE("astral_necklace",
                DRItems.ASTRAL_NECKLACE,
                LootConfig.ASTRAL_NECKLACE_CHANCE,
                LootConfig.ASTRAL_NECKLACE_MIN_COUNT,
                LootConfig.ASTRAL_NECKLACE_MAX_COUNT),

        DREAM_TOTEM("dream_totem",
                DRItems.DREAM_TOTEM,
                LootConfig.DREAM_TOTEM_CHANCE,
                LootConfig.DREAM_TOTEM_MIN_COUNT,
                LootConfig.DREAM_TOTEM_MAX_COUNT),

        OCEAN_CURRENT_BLESSING("ocean_current_blessing",
                DRItems.OCEAN_CURRENT_BLESSING,
                LootConfig.OCEAN_CURRENT_BLESSING_CHANCE,
                LootConfig.OCEAN_CURRENT_BLESSING_MIN_COUNT,
                LootConfig.OCEAN_CURRENT_BLESSING_MAX_COUNT),

        MIST_VEIL_RING("mist_veil_ring",
                DRItems.MIST_VEIL_RING,
                LootConfig.MIST_VEIL_RING_CHANCE,
                LootConfig.MIST_VEIL_RING_MIN_COUNT,
                LootConfig.MIST_VEIL_RING_MAX_COUNT),

        ECHO_EARRING("echo_earring",
                DRItems.ECHO_EARRING,
                LootConfig.ECHO_EARRING_CHANCE,
                LootConfig.ECHO_EARRING_MIN_COUNT,
                LootConfig.ECHO_EARRING_MAX_COUNT),

        PAST_RING("past_ring",
                DRItems.PAST_RING,
                LootConfig.PAST_RING_CHANCE,
                LootConfig.PAST_RING_MIN_COUNT,
                LootConfig.PAST_RING_MAX_COUNT),

        MEMORY_NECKLACE("memory_necklace",
                DRItems.MEMORY_NECKLACE,
                LootConfig.MEMORY_NECKLACE_CHANCE,
                LootConfig.MEMORY_NECKLACE_MIN_COUNT,
                LootConfig.MEMORY_NECKLACE_MAX_COUNT),

        MOMENT_STONE("moment_stone",
                DRItems.MOMENT_STONE,
                LootConfig.MOMENT_STONE_CHANCE,
                LootConfig.MOMENT_STONE_MIN_COUNT,
                LootConfig.MOMENT_STONE_MAX_COUNT),

        HEART_VOICE_PENDANT("heart_voice_pendant",
                DRItems.HEART_VOICE_PENDANT,
                LootConfig.HEART_VOICE_PENDANT_CHANCE,
                LootConfig.HEART_VOICE_PENDANT_MIN_COUNT,
                LootConfig.HEART_VOICE_PENDANT_MAX_COUNT),

        MEMORY_STARDUST("memory_stardust",
                DRItems.MEMORY_STARDUST,
                LootConfig.MEMORY_STARDUST_CHANCE,
                LootConfig.MEMORY_STARDUST_MIN_COUNT,
                LootConfig.MEMORY_STARDUST_MAX_COUNT),

        TIME_HOURGLASS("time_hourglass",
                DRItems.TIME_HOURGLASS,
                LootConfig.TIME_HOURGLASS_CHANCE,
                LootConfig.TIME_HOURGLASS_MIN_COUNT,
                LootConfig.TIME_HOURGLASS_MAX_COUNT),

        TASSEL_RING("tassel_ring",
                DRItems.TASSEL_RING,
                LootConfig.TASSEL_RING_CHANCE,
                LootConfig.TASSEL_RING_MIN_COUNT,
                LootConfig.TASSEL_RING_MAX_COUNT),

        OBSERVE_SELF_EYE("observe_self_eye",
                DRItems.OBSERVE_SELF_EYE,
                LootConfig.OBSERVE_SELF_EYE_CHANCE,
                LootConfig.OBSERVE_SELF_EYE_MIN_COUNT,
                LootConfig.OBSERVE_SELF_EYE_MAX_COUNT),

        ROYAL_CROWN("royal_crown",
                DRItems.ROYAL_CROWN,
                LootConfig.ROYAL_CROWN_CHANCE,
                LootConfig.ROYAL_CROWN_MIN_COUNT,
                LootConfig.ROYAL_CROWN_MAX_COUNT),

        PURE_HOLY_GRAIL("pure_holy_grail",
                DRItems.PURE_HOLY_GRAIL,
                LootConfig.PURE_HOLY_GRAIL_CHANCE,
                LootConfig.PURE_HOLY_GRAIL_MIN_COUNT,
                LootConfig.PURE_HOLY_GRAIL_MAX_COUNT),

        ELVEN_BOOTS("elven_boots",
                DRItems.ELVEN_BOOTS,
                LootConfig.ELVEN_BOOTS_CHANCE,
                LootConfig.ELVEN_BOOTS_MIN_COUNT,
                LootConfig.ELVEN_BOOTS_MAX_COUNT),

        DREAM_BALANCE("dream_balance",
                DRItems.DREAM_BALANCE,
                LootConfig.DREAM_BALANCE_CHANCE,
                LootConfig.DREAM_BALANCE_MIN_COUNT,
                LootConfig.DREAM_BALANCE_MAX_COUNT),

        YEARS_AMBER("years_amber",
                DRItems.YEARS_AMBER,
                LootConfig.YEARS_AMBER_CHANCE,
                LootConfig.YEARS_AMBER_MIN_COUNT,
                LootConfig.YEARS_AMBER_MAX_COUNT),

        ICARUS_WINGS("icarus_wings",
                DRItems.ICARUS_WINGS,
                LootConfig.ICARUS_WINGS_CHANCE,
                LootConfig.ICARUS_WINGS_MIN_COUNT,
                LootConfig.ICARUS_WINGS_MAX_COUNT),

        ENDLESS_DREAM("endless_dream",
                DRItems.ENDLESS_DREAM,
                LootConfig.ENDLESS_DREAM_CHANCE,
                LootConfig.ENDLESS_DREAM_MIN_COUNT,
                LootConfig.ENDLESS_DREAM_MAX_COUNT),

        AWAKEN_DREAM_BRACELET("awaken_dream_bracelet",
                DRItems.AWAKEN_DREAM_BRACELET,
                LootConfig.AWAKEN_DREAM_BRACELET_CHANCE,
                LootConfig.AWAKEN_DREAM_BRACELET_MIN_COUNT,
                LootConfig.AWAKEN_DREAM_BRACELET_MAX_COUNT),

        ROYAL_LENS("royal_lens",
                DRItems.ROYAL_LENS,
                LootConfig.ROYAL_LENS_CHANCE,
                LootConfig.ROYAL_LENS_MIN_COUNT,
                LootConfig.ROYAL_LENS_MAX_COUNT),

        RARE_GOLD_BRACELET("rare_gold_bracelet",
                DRItems.RARE_GOLD_BRACELET,
                LootConfig.RARE_GOLD_BRACELET_CHANCE,
                LootConfig.RARE_GOLD_BRACELET_MIN_COUNT,
                LootConfig.RARE_GOLD_BRACELET_MAX_COUNT);

        final String name;
        final Supplier<? extends Item> itemSupplier;
        final Supplier<Double> chance;
        final Supplier<Integer> minCount;
        final Supplier<Integer> maxCount;

        EntryConfig(String name,
                    Supplier<? extends Item> itemSupplier,
                    Supplier<Double> chance,
                    Supplier<Integer> minCount,
                    Supplier<Integer> maxCount) {
            this.name = name;
            this.itemSupplier = itemSupplier;
            this.chance = chance;
            this.minCount = minCount;
            this.maxCount = maxCount;
        }

        static @Nullable EntryConfig byName(String name) {
            try {
                return valueOf(name.toUpperCase(java.util.Locale.ROOT));
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}
