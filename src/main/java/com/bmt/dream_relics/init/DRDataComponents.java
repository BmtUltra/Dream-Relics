package com.bmt.dream_relics.init;

import com.bmt.dream_relics.DreamRelics;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DRDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, DreamRelics.MODID);

    public static final Supplier<DataComponentType<MemoryStardustContainer>> MEMORY_STARDUST_ITEMS =
            DATA_COMPONENT_TYPES.register("memory_stardust_items",
                    () -> DataComponentType.<MemoryStardustContainer>builder()
                            .persistent(MemoryStardustContainer.CODEC)
                            .networkSynchronized(MemoryStardustContainer.STREAM_CODEC)
                            .build());

    public static final Supplier<DataComponentType<Float>> MEMORY_NECKLACE_STORED_DAMAGE =
            DATA_COMPONENT_TYPES.register("memory_necklace_stored_damage",
                    () -> DataComponentType.<Float>builder()
                            .persistent(Codec.FLOAT)
                            .networkSynchronized(ByteBufCodecs.FLOAT)
                            .build());

    public record MemoryStardustContainer(ItemStackHandler items) {
        private static final int MAX_SIZE = 9;

        public static MemoryStardustContainer of(ItemStackHandler items) {
            ItemStackHandler copy = new ItemStackHandler(MAX_SIZE);
            for (int i = 0; i < Math.min(items.getSlots(), copy.getSlots()); i++) {
                copy.setStackInSlot(i, items.getStackInSlot(i).copy());
            }
            return new MemoryStardustContainer(copy);
        }

        public static final Codec<MemoryStardustContainer> CODEC = ItemStack.OPTIONAL_CODEC.listOf().xmap(
                list -> {
                    ItemStackHandler handler = new ItemStackHandler(MAX_SIZE);
                    for (int i = 0; i < Math.min(list.size(), handler.getSlots()); i++) {
                        handler.setStackInSlot(i, list.get(i));
                    }
                    return new MemoryStardustContainer(handler);
                },
                container -> {
                    ItemStackHandler handler = container.items();
                    List<ItemStack> output = new ArrayList<>();
                    for (int i = 0; i < handler.getSlots(); i++) {
                        output.add(handler.getStackInSlot(i));
                    }
                    return output;
                }
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MemoryStardustContainer> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull MemoryStardustContainer decode(RegistryFriendlyByteBuf buffer) {
                CompoundTag compoundTag = buffer.readNbt();
                ItemStackHandler handler = new ItemStackHandler(MAX_SIZE);
                if (compoundTag != null) {
                    handler.deserializeNBT(buffer.registryAccess(), compoundTag);
                }
                return new MemoryStardustContainer(handler);
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, MemoryStardustContainer value) {
                CompoundTag compoundTag = value.items().serializeNBT(buffer.registryAccess());
                buffer.writeNbt(compoundTag);
            }
        };
    }
}