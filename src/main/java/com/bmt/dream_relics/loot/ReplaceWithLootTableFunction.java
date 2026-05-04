package com.bmt.dream_relics.loot;

import com.bmt.dream_relics.init.DRLootFunctions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public class ReplaceWithLootTableFunction extends LootItemConditionalFunction {
    public static final MapCodec<ReplaceWithLootTableFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance)
                    .and(ResourceLocation.CODEC.fieldOf("loot_table").forGetter(func -> func.lootTable))
                    .apply(instance, ReplaceWithLootTableFunction::new)
    );

    private final ResourceLocation lootTable;

    public ReplaceWithLootTableFunction(List<LootItemCondition> predicates, ResourceLocation lootTable) {
        super(predicates);
        this.lootTable = lootTable;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return DRLootFunctions.REPLACE_WITH_LOOT_TABLE.get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext lootContext) {
        if (stack.isEmpty()) {
            return stack;
        }

        LootTable table = lootContext.getLevel().getServer().reloadableRegistries().getLootTable(
                net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.LOOT_TABLE, this.lootTable));
        ObjectArrayList<ItemStack> loot = new ObjectArrayList<>();
        table.getRandomItemsRaw(lootContext, loot::add);

        if (loot.isEmpty()) {
            return ItemStack.EMPTY;
        }

        return loot.getFirst();
    }

    public static Builder<?> replaceWithLootTable(ResourceLocation lootTable) {
        return simpleBuilder((conditions) -> new ReplaceWithLootTableFunction(conditions, lootTable));
    }
}