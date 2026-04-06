package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.config.MainConfig;
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
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/bastion_treasure")) && MainConfig.astralNecklaceChance > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.astralNecklaceChance))
                    .add(LootItem.lootTableItem(DRItems.ASTRAL_NECKLACE.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.astralNecklaceMinCount, (float) MainConfig.astralNecklaceMaxCount)))
                    .name(DreamRelics.MODID + ":astral_necklace/bastion_treasure")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 林地府邸箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/woodland_mansion")) && MainConfig.dreamTotemChance > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.dreamTotemChance))
                    .add(LootItem.lootTableItem(DRItems.DREAM_TOTEM.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.dreamTotemMinCount, (float) MainConfig.dreamTotemMaxCount)))
                    .name(DreamRelics.MODID + ":dream_totem/woodland_mansion")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 埋葬的宝藏箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/buried_treasure")) && MainConfig.oceanBlessingBuriedChance > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.oceanBlessingBuriedChance))
                    .add(LootItem.lootTableItem(DRItems.OCEAN_CURRENT_BLESSING.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.oceanBlessingBuriedMinCount, (float) MainConfig.oceanBlessingBuriedMaxCount)))
                    .name(DreamRelics.MODID + ":ocean_current_blessing/buried_treasure")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 远古城市箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/ancient_city"))) {
            boolean hasDarkWhisperRing = MainConfig.darkWhisperRingChance > 0;
            boolean hasVoidNecklace = MainConfig.voidNecklaceChance > 0;
            boolean hasMistVeilRing = MainConfig.mistVeilRingChance > 0;
            boolean hasEchoEarring = MainConfig.echoEarringChance > 0;
            boolean hasPastRing = MainConfig.pastRingChance > 0;
            boolean hasMemoryNecklace = MainConfig.memoryNecklaceChance > 0;

            if (!hasDarkWhisperRing && !hasVoidNecklace && !hasMistVeilRing && !hasEchoEarring && !hasPastRing && !hasMemoryNecklace) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":ancient_city");

            if (hasDarkWhisperRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.DARK_WHISPER_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.darkWhisperRingChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.darkWhisperRingMinCount, (float) MainConfig.darkWhisperRingMaxCount)))
                );
            }

            if (hasVoidNecklace) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.VOID_NECKLACE.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.voidNecklaceChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.voidNecklaceMinCount, (float) MainConfig.voidNecklaceMaxCount)))
                );
            }

            if (hasMistVeilRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MIST_VEIL_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.mistVeilRingChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.mistVeilRingMinCount, (float) MainConfig.mistVeilRingMaxCount)))
                );
            }

            if (hasEchoEarring) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ECHO_EARRING.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.echoEarringChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.echoEarringMinCount, (float) MainConfig.echoEarringMaxCount)))
                );
            }

            if (hasPastRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.PAST_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.pastRingChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.pastRingMinCount, (float) MainConfig.pastRingMaxCount)))
                );
            }

            if (hasMemoryNecklace) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MEMORY_NECKLACE.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.memoryNecklaceChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.memoryNecklaceMinCount, (float) MainConfig.memoryNecklaceMaxCount)))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 末地城箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/end_city_treasure"))) {
            boolean hasMomentStone = MainConfig.momentStoneChance > 0;
            boolean hasHeartVoicePendant = MainConfig.heartVoicePendantChance > 0;
            boolean hasMemoryStardust = MainConfig.memoryStardustChance > 0;
            boolean hasTimeHourglass = MainConfig.timeHourglassChance > 0;
            boolean hasTasselRing = MainConfig.tasselRingChance > 0;

            if (!hasMomentStone && !hasHeartVoicePendant && !hasMemoryStardust && !hasTimeHourglass && !hasTasselRing) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":end_city_treasure");

            if (hasMomentStone) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MOMENT_STONE.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.momentStoneChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.momentStoneMinCount, (float) MainConfig.momentStoneMaxCount)))
                );
            }

            if (hasHeartVoicePendant) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.HEART_VOICE_PENDANT.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.heartVoicePendantChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.heartVoicePendantMinCount, (float) MainConfig.heartVoicePendantMaxCount)))
                );
            }

            if (hasMemoryStardust) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.MEMORY_STARDUST.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.memoryStardustChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.memoryStardustMinCount, (float) MainConfig.memoryStardustMaxCount)))
                );
            }

            if (hasTimeHourglass) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.TIME_HOURGLASS.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.timeHourglassChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.timeHourglassMinCount, (float) MainConfig.timeHourglassMaxCount)))
                );
            }

            if (hasTasselRing) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.TASSEL_RING.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.tasselRingChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.tasselRingMinCount, (float) MainConfig.tasselRingMaxCount)))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 废弃矿井的箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/abandoned_mineshaft"))) {
            boolean hasObserveSelfEye = MainConfig.observeSelfEyeChance > 0;
            boolean hasRoyalCrown = MainConfig.royalCrownChance > 0;

            if (!hasObserveSelfEye && !hasRoyalCrown) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":abandoned_mineshaft");

            if (hasObserveSelfEye) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.OBSERVE_SELF_EYE.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.observeSelfEyeChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.observeSelfEyeMinCount, (float) MainConfig.observeSelfEyeMaxCount)))
                );
            }

            if (hasRoyalCrown) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ROYAL_CROWN.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.royalCrownChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.royalCrownMinCount, (float) MainConfig.royalCrownMaxCount)))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 沙漠金字塔箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/desert_pyramid"))) {
            boolean hasPureHolyGrail = MainConfig.pureHolyGrailChance > 0;
            boolean hasElvenBoots = MainConfig.elvenBootsChance > 0;

            if (!hasPureHolyGrail && !hasElvenBoots) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":desert_pyramid");

            if (hasPureHolyGrail) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.PURE_HOLY_GRAIL.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.pureHolyGrailChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.pureHolyGrailMinCount, (float) MainConfig.pureHolyGrailMaxCount)))
                );
            }

            if (hasElvenBoots) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ELVEN_BOOTS.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.elvenBootsChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.elvenBootsMinCount, (float) MainConfig.elvenBootsMaxCount)))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 遗迹堡垒其他箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/bastion_hoglin_stable"))) {
            boolean hasDreamBalance = MainConfig.dreamBalanceChance > 0;
            boolean hasYearsAmber = MainConfig.yearsAmberChance > 0;
            boolean hasIcarusWings = MainConfig.icarusWingsChance > 0;

            if (!hasDreamBalance && !hasYearsAmber && !hasIcarusWings) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":bastion_hoglin_stable");

            if (hasDreamBalance) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.DREAM_BALANCE.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.dreamBalanceChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.dreamBalanceMinCount, (float) MainConfig.dreamBalanceMaxCount)))
                );
            }

            if (hasYearsAmber) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.YEARS_AMBER.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.yearsAmberChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.yearsAmberMinCount, (float) MainConfig.yearsAmberMaxCount)))
                );
            }

            if (hasIcarusWings) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ICARUS_WINGS.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.icarusWingsChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.icarusWingsMinCount, (float) MainConfig.icarusWingsMaxCount)))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 掠夺者前哨站箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/pillager_outpost"))) {
            boolean hasEndlessDream = MainConfig.endlessDreamChance > 0;
            boolean hasAwakenDreamBracelet = MainConfig.awakenDreamBraceletChance > 0;

            if (!hasEndlessDream && !hasAwakenDreamBracelet) return;

            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .name(DreamRelics.MODID + ":pillager_outpost");

            if (hasEndlessDream) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.ENDLESS_DREAM.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.endlessDreamChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.endlessDreamMinCount, (float) MainConfig.endlessDreamMaxCount)))
                );
            }

            if (hasAwakenDreamBracelet) {
                poolBuilder.add(LootItem.lootTableItem(DRItems.AWAKEN_DREAM_BRACELET.get())
                        .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.awakenDreamBraceletChance))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.awakenDreamBraceletMinCount, (float) MainConfig.awakenDreamBraceletMaxCount)))
                );
            }

            event.getTable().addPool(poolBuilder.build());
        }

        // 废弃传送门箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/ruined_portal")) && MainConfig.royalLensChance > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.royalLensChance))
                    .add(LootItem.lootTableItem(DRItems.ROYAL_LENS.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.royalLensMinCount, (float) MainConfig.royalLensMaxCount)))
                    .name(DreamRelics.MODID + ":royal_lens/ruined_portal")
                    .build();
            event.getTable().addPool(bonusPool);
        }

        // 村庄工具酱箱子
        if (tableId.equals(ResourceLocation.withDefaultNamespace("chests/village/village_toolsmith")) && MainConfig.rareGoldBraceletChance > 0) {
            LootPool bonusPool = LootPool.lootPool()
                    .setRolls(UniformGenerator.between(1.0f, 1.0f))
                    .when(LootItemRandomChanceCondition.randomChance((float) MainConfig.rareGoldBraceletChance))
                    .add(LootItem.lootTableItem(DRItems.RARE_GOLD_BRACELET.get()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between((float) MainConfig.rareGoldBraceletMinCount, (float) MainConfig.rareGoldBraceletMaxCount)))
                    .name(DreamRelics.MODID + ":rare_gold_bracelet/villager_toolsmith")
                    .build();
            event.getTable().addPool(bonusPool);
        }
    }
}
