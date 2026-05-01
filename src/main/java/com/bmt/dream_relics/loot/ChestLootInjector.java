package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.config.LootConfig;
import com.bmt.dream_relics.init.DRItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID)
public class ChestLootInjector {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation tableId = event.getName();

        // 堡垒遗迹藏宝藏箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/bastion_treasure")) && LootConfig.ASTRAL_NECKLACE_CHANCE.get() > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance(LootConfig.ASTRAL_NECKLACE_CHANCE.get().floatValue()))
                    .add(LootItem.lootTableItem(DRItems.ASTRAL_NECKLACE.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.ASTRAL_NECKLACE_MIN_COUNT.get().floatValue(), LootConfig.ASTRAL_NECKLACE_MAX_COUNT.get().floatValue())))
                    .name(DreamRelics.MODID + ":astral_necklace/bastion_treasure")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 林地府邸箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/woodland_mansion")) && LootConfig.DREAM_TOTEM_CHANCE.get() > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance(LootConfig.DREAM_TOTEM_CHANCE.get().floatValue()))
                    .add(LootItem.lootTableItem(DRItems.DREAM_TOTEM.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.DREAM_TOTEM_MIN_COUNT.get().floatValue(), LootConfig.DREAM_TOTEM_MAX_COUNT.get().floatValue())))
                    .name(DreamRelics.MODID + ":dream_totem/woodland_mansion")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 埋葬的宝藏箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/buried_treasure")) && LootConfig.OCEAN_CURRENT_BLESSING_CHANCE.get() > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance(LootConfig.OCEAN_CURRENT_BLESSING_CHANCE.get().floatValue()))
                    .add(LootItem.lootTableItem(DRItems.OCEAN_CURRENT_BLESSING.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.OCEAN_CURRENT_BLESSING_MIN_COUNT.get().floatValue(), LootConfig.OCEAN_CURRENT_BLESSING_MAX_COUNT.get().floatValue())))
                    .name(DreamRelics.MODID + ":ocean_current_blessing/buried_treasure")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 远古城市箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/ancient_city"))) {
            boolean hasDarkWhisperRing = LootConfig.DARK_WHISPER_RING_CHANCE.get() > 0;
            boolean hasVoidNecklace = LootConfig.VOID_NECKLACE_CHANCE.get() > 0;
            boolean hasMistVeilRing = LootConfig.MIST_VEIL_RING_CHANCE.get() > 0;
            boolean hasEchoEarring = LootConfig.ECHO_EARRING_CHANCE.get() > 0;
            boolean hasPastRing = LootConfig.PAST_RING_CHANCE.get() > 0;
            boolean hasMemoryNecklace = LootConfig.MEMORY_NECKLACE_CHANCE.get() > 0;

            if (!hasDarkWhisperRing && !hasVoidNecklace && !hasMistVeilRing && !hasEchoEarring && !hasPastRing && !hasMemoryNecklace) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":ancient_city");

            if (hasDarkWhisperRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.DARK_WHISPER_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.DARK_WHISPER_RING_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.DARK_WHISPER_RING_MIN_COUNT.get().floatValue(), LootConfig.DARK_WHISPER_RING_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasVoidNecklace) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.VOID_NECKLACE.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.VOID_NECKLACE_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.VOID_NECKLACE_MIN_COUNT.get().floatValue(), LootConfig.VOID_NECKLACE_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasMistVeilRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MIST_VEIL_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.MIST_VEIL_RING_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.MIST_VEIL_RING_MIN_COUNT.get().floatValue(), LootConfig.MIST_VEIL_RING_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasEchoEarring) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ECHO_EARRING.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.ECHO_EARRING_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.ECHO_EARRING_MIN_COUNT.get().floatValue(), LootConfig.ECHO_EARRING_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasPastRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.PAST_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.PAST_RING_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.PAST_RING_MIN_COUNT.get().floatValue(), LootConfig.PAST_RING_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasMemoryNecklace) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MEMORY_NECKLACE.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.MEMORY_NECKLACE_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.MEMORY_NECKLACE_MIN_COUNT.get().floatValue(), LootConfig.MEMORY_NECKLACE_MAX_COUNT.get().floatValue())))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 末地城箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/end_city_treasure"))) {
            boolean hasMomentStone = LootConfig.MOMENT_STONE_CHANCE.get() > 0;
            boolean hasHeartVoicePendant = LootConfig.HEART_VOICE_PENDANT_CHANCE.get() > 0;
            boolean hasMemoryStardust = LootConfig.MEMORY_STARDUST_CHANCE.get() > 0;
            boolean hasTimeHourglass = LootConfig.TIME_HOURGLASS_CHANCE.get() > 0;
            boolean hasTasselRing = LootConfig.TASSEL_RING_CHANCE.get() > 0;

            if (!hasMomentStone && !hasHeartVoicePendant && !hasMemoryStardust && !hasTimeHourglass && !hasTasselRing) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":end_city_treasure");

            if (hasMomentStone) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MOMENT_STONE.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.MOMENT_STONE_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.MOMENT_STONE_MIN_COUNT.get().floatValue(), LootConfig.MOMENT_STONE_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasHeartVoicePendant) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.HEART_VOICE_PENDANT.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.HEART_VOICE_PENDANT_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.HEART_VOICE_PENDANT_MIN_COUNT.get().floatValue(), LootConfig.HEART_VOICE_PENDANT_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasMemoryStardust) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MEMORY_STARDUST.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.MEMORY_STARDUST_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.MEMORY_STARDUST_MIN_COUNT.get().floatValue(), LootConfig.MEMORY_STARDUST_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasTimeHourglass) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.TIME_HOURGLASS.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.TIME_HOURGLASS_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.TIME_HOURGLASS_MIN_COUNT.get().floatValue(), LootConfig.TIME_HOURGLASS_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasTasselRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.TASSEL_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.TASSEL_RING_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.TASSEL_RING_MIN_COUNT.get().floatValue(), LootConfig.TASSEL_RING_MAX_COUNT.get().floatValue())))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 废弃矿井的箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/abandoned_mineshaft"))) {
            boolean hasObserveSelfEye = LootConfig.OBSERVE_SELF_EYE_CHANCE.get() > 0;
            boolean hasRoyalCrown = LootConfig.ROYAL_CROWN_CHANCE.get() > 0;

            if (!hasObserveSelfEye && !hasRoyalCrown) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":abandoned_mineshaft");

            if (hasObserveSelfEye) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.OBSERVE_SELF_EYE.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.OBSERVE_SELF_EYE_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.OBSERVE_SELF_EYE_MIN_COUNT.get().floatValue(), LootConfig.OBSERVE_SELF_EYE_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasRoyalCrown) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ROYAL_CROWN.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.ROYAL_CROWN_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.ROYAL_CROWN_MIN_COUNT.get().floatValue(), LootConfig.ROYAL_CROWN_MAX_COUNT.get().floatValue())))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 沙漠金字塔箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/desert_pyramid"))) {
            boolean hasPureHolyGrail = LootConfig.PURE_HOLY_GRAIL_CHANCE.get() > 0;
            boolean hasElvenBoots = LootConfig.ELVEN_BOOTS_CHANCE.get() > 0;

            if (!hasPureHolyGrail && !hasElvenBoots) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":desert_pyramid");

            if (hasPureHolyGrail) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.PURE_HOLY_GRAIL.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.PURE_HOLY_GRAIL_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.PURE_HOLY_GRAIL_MIN_COUNT.get().floatValue(), LootConfig.PURE_HOLY_GRAIL_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasElvenBoots) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ELVEN_BOOTS.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.ELVEN_BOOTS_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.ELVEN_BOOTS_MIN_COUNT.get().floatValue(), LootConfig.ELVEN_BOOTS_MAX_COUNT.get().floatValue())))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 遗迹堡垒其他箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/bastion_hoglin_stable"))) {
            boolean hasDreamBalance = LootConfig.DREAM_BALANCE_CHANCE.get() > 0;
            boolean hasYearsAmber = LootConfig.YEARS_AMBER_CHANCE.get() > 0;
            boolean hasIcarusWings = LootConfig.ICARUS_WINGS_CHANCE.get() > 0;

            if (!hasDreamBalance && !hasYearsAmber && !hasIcarusWings) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":bastion_hoglin_stable");

            if (hasDreamBalance) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.DREAM_BALANCE.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.DREAM_BALANCE_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.DREAM_BALANCE_MIN_COUNT.get().floatValue(), LootConfig.DREAM_BALANCE_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasYearsAmber) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.YEARS_AMBER.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.YEARS_AMBER_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.YEARS_AMBER_MIN_COUNT.get().floatValue(), LootConfig.YEARS_AMBER_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasIcarusWings) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ICARUS_WINGS.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.ICARUS_WINGS_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.ICARUS_WINGS_MIN_COUNT.get().floatValue(), LootConfig.ICARUS_WINGS_MAX_COUNT.get().floatValue())))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 掠夺者前哨站箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/pillager_outpost"))) {
            boolean hasEndlessDream = LootConfig.ENDLESS_DREAM_CHANCE.get() > 0;
            boolean hasAwakenDreamBracelet = LootConfig.AWAKEN_DREAM_BRACELET_CHANCE.get() > 0;

            if (!hasEndlessDream && !hasAwakenDreamBracelet) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":pillager_outpost");

            if (hasEndlessDream) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ENDLESS_DREAM.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.ENDLESS_DREAM_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.ENDLESS_DREAM_MIN_COUNT.get().floatValue(), LootConfig.ENDLESS_DREAM_MAX_COUNT.get().floatValue())))
                );
            }

            if (hasAwakenDreamBracelet) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.AWAKEN_DREAM_BRACELET.get())
                        .when(LootItemRandomChanceCondition.randomChance(LootConfig.AWAKEN_DREAM_BRACELET_CHANCE.get().floatValue()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.AWAKEN_DREAM_BRACELET_MIN_COUNT.get().floatValue(), LootConfig.AWAKEN_DREAM_BRACELET_MAX_COUNT.get().floatValue())))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 废弃传送门箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/ruined_portal")) && LootConfig.ROYAL_LENS_CHANCE.get() > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance(LootConfig.ROYAL_LENS_CHANCE.get().floatValue()))
                    .add(LootItem.lootTableItem(DRItems.ROYAL_LENS.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.ROYAL_LENS_MIN_COUNT.get().floatValue(), LootConfig.ROYAL_LENS_MAX_COUNT.get().floatValue())))
                    .name(DreamRelics.MODID + ":royal_lens/ruined_portal")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 村庄工具酱箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/village/village_toolsmith")) && LootConfig.RARE_GOLD_BRACELET_CHANCE.get() > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance(LootConfig.RARE_GOLD_BRACELET_CHANCE.get().floatValue()))
                    .add(LootItem.lootTableItem(DRItems.RARE_GOLD_BRACELET.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(LootConfig.RARE_GOLD_BRACELET_MIN_COUNT.get().floatValue(), LootConfig.RARE_GOLD_BRACELET_MAX_COUNT.get().floatValue())))
                    .name(DreamRelics.MODID + ":rare_gold_bracelet/villager_toolsmith")
                    .build();
            event.getTable().addPool(bonusPool);
        }
    }
}