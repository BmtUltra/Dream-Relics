package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.DreamRelics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID)
public class ChestLootInjector {

    private static final String LOOT_TABLE_PREFIX = DreamRelics.MODID + ":chests/";

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation tableId = event.getName();

        // 地牢
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/simple_dungeon"),
                LOOT_TABLE_PREFIX + "inject/simple_dungeon");

        // 废弃矿井
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/abandoned_mineshaft"),
                LOOT_TABLE_PREFIX + "inject/abandoned_mineshaft");

        // 沙漠神殿
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/desert_pyramid"),
                LOOT_TABLE_PREFIX + "inject/desert_pyramid");

        // 丛林神庙
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/jungle_temple"),
                LOOT_TABLE_PREFIX + "inject/jungle_temple");

        // 要塞
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/stronghold_corridor"),
                LOOT_TABLE_PREFIX + "inject/stronghold_corridor");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/stronghold_crossing"),
                LOOT_TABLE_PREFIX + "inject/stronghold_crossing");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/stronghold_library"),
                LOOT_TABLE_PREFIX + "inject/stronghold_library");

        // 林地府邸
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/woodland_mansion"),
                LOOT_TABLE_PREFIX + "inject/woodland_mansion");

        // 掠夺者前哨站
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/pillager_outpost"),
                LOOT_TABLE_PREFIX + "inject/pillager_outpost");

        // 埋藏的宝藏
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/buried_treasure"),
                LOOT_TABLE_PREFIX + "inject/buried_treasure");

        // 沉船
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/shipwreck_treasure"),
                LOOT_TABLE_PREFIX + "inject/shipwreck_treasure");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/shipwreck_supply"),
                LOOT_TABLE_PREFIX + "inject/shipwreck_supply");

        // 水下遗迹
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/underwater_ruin_big"),
                LOOT_TABLE_PREFIX + "inject/underwater_ruin_big");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/underwater_ruin_small"),
                LOOT_TABLE_PREFIX + "inject/underwater_ruin_small");

        // 村庄
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/village/village_toolsmith"),
                LOOT_TABLE_PREFIX + "inject/village_toolsmith");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/village/village_weaponsmith"),
                LOOT_TABLE_PREFIX + "inject/village_weaponsmith");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/village/village_temple"),
                LOOT_TABLE_PREFIX + "inject/village_temple");

        // 废弃传送门
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/ruined_portal"),
                LOOT_TABLE_PREFIX + "inject/ruined_portal");

        // 下界要塞
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/nether_bridge"),
                LOOT_TABLE_PREFIX + "inject/nether_bridge");

        // 堡垒遗迹
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/bastion_treasure"),
                LOOT_TABLE_PREFIX + "inject/bastion_treasure");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/bastion_hoglin_stable"),
                LOOT_TABLE_PREFIX + "inject/bastion_hoglin_stable");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/bastion_bridge"),
                LOOT_TABLE_PREFIX + "inject/bastion_bridge");
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/bastion_other"),
                LOOT_TABLE_PREFIX + "inject/bastion_other");

        // 末地城
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/end_city_treasure"),
                LOOT_TABLE_PREFIX + "inject/end_city_treasure");

        // 远古城市
        injectLootTable(event, tableId,
                ResourceLocation.withDefaultNamespace("chests/ancient_city"),
                LOOT_TABLE_PREFIX + "inject/ancient_city");
    }

    private static void injectLootTable(LootTableLoadEvent event, ResourceLocation currentTable,
                                        ResourceLocation targetTable, String lootTableRef) {
        if (!currentTable.equals(targetTable)) return;

        LootPool bonusPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0f, 1.0f))
                .add(LootTableReference.lootTableReference(ResourceLocation.parse(lootTableRef)))
                .name(lootTableRef.replace(':', '/'))
                .build();
        event.getTable().addPool(bonusPool);
    }
}