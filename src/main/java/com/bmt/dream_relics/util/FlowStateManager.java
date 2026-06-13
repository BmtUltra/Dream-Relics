package com.bmt.dream_relics.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

public class FlowStateManager {
    private static final String FLOW_BLOCKS_KEY = "FlowBlocks";
    private static final String BLOCK_POS_KEY = "BlockPos";
    private static final String REMAINING_TICKS_KEY = "RemainingTicks";
    private static final String DURATION_TICKS_KEY = "DurationTicks";

    public static void startFlow(Level level, BlockPos pos, int durationTicks) {
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            FlowSavedData savedData = getSavedData(serverLevel);
            CompoundTag flowData = new CompoundTag();

            flowData.put(BLOCK_POS_KEY, NbtUtils.writeBlockPos(pos));
            flowData.putInt(REMAINING_TICKS_KEY, durationTicks);
            flowData.putInt(DURATION_TICKS_KEY, durationTicks);

            ListTag flowList = savedData.getFlowList();
            flowList.add(flowData);
            savedData.setFlowList(flowList);
            savedData.setDirty();

            level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE,
                    SoundSource.PLAYERS, 1.0F, 1.5F);

            generateInitialParticles(serverLevel, pos);
        }
    }

    private static void generateInitialParticles(ServerLevel serverLevel, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            double offsetX = (serverLevel.random.nextDouble() - 0.5) * 1.5;
            double offsetY = (serverLevel.random.nextDouble() - 0.5) * 1.5;
            double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 1.5;

            double particleX = pos.getX() + 0.5 + offsetX;
            double particleY = pos.getY() + 0.5 + offsetY;
            double particleZ = pos.getZ() + 0.5 + offsetZ;

            serverLevel.sendParticles(ParticleTypes.WAX_OFF,
                    particleX, particleY, particleZ,
                    1, 0.1, 0.1, 0.1, 0.02);
        }
    }

    public static void updateFlowStates(Level level) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        FlowSavedData savedData = getSavedData(serverLevel);
        ListTag flowList = savedData.getFlowList();

        if (flowList.isEmpty()) {
            return;
        }

        ListTag updatedList = new ListTag();

        for (int i = 0; i < flowList.size(); i++) {
            CompoundTag flowData = flowList.getCompound(i);
            BlockPos pos = NbtUtils.readBlockPos(flowData, BLOCK_POS_KEY).orElse(BlockPos.ZERO);
            int remainingTicks = flowData.getInt(REMAINING_TICKS_KEY);

            if (remainingTicks > 0) {
                accelerateBlockTick(level, pos);

                remainingTicks--;
                flowData.putInt(REMAINING_TICKS_KEY, remainingTicks);

                if (remainingTicks % 20 == 0) {
                    generateUpdateParticles(level, pos);
                }

                if (remainingTicks > 0) {
                    updatedList.add(flowData);
                }
            }
        }
        savedData.setFlowList(updatedList);
        savedData.setDirty();
    }

    private static void accelerateBlockTick(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity != null) {
            accelerateBlockEntity(level, pos, state, blockEntity);
        } else if (state.isRandomlyTicking()) {
            for (int i = 0; i < 10; i++) {
                state.randomTick((ServerLevel) level, pos, level.random);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void accelerateBlockEntity(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (state.getBlock() instanceof EntityBlock entityBlock) {
            BlockEntityTicker<BlockEntity> ticker = (BlockEntityTicker<BlockEntity>)
                    entityBlock.getTicker(level, state, blockEntity.getType());

            if (ticker != null && !blockEntity.isRemoved()) {
                for (int i = 0; i < 10; i++) {
                    if (blockEntity.isRemoved()) {
                        break;
                    }
                    ticker.tick(level, pos, state, blockEntity);
                }
            }
        }
    }

    private static void generateUpdateParticles(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 2; i++) {
                double offsetX = (serverLevel.random.nextDouble() - 0.5) * 1.2;
                double offsetY = (serverLevel.random.nextDouble() - 0.5) * 1.2;
                double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 1.2;

                double particleX = pos.getX() + 0.5 + offsetX;
                double particleY = pos.getY() + 0.5 + offsetY;
                double particleZ = pos.getZ() + 0.5 + offsetZ;

                serverLevel.sendParticles(ParticleTypes.WAX_OFF,
                        particleX, particleY, particleZ,
                        1, 0.08, 0.08, 0.08, 0.015);
            }
        }
    }

    public static boolean isFlowing(Level level, BlockPos pos) {
        if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        FlowSavedData savedData = getSavedData(serverLevel);
        ListTag flowList = savedData.getFlowList();

        for (int i = 0; i < flowList.size(); i++) {
            CompoundTag flowData = flowList.getCompound(i);
            BlockPos storedPos = NbtUtils.readBlockPos(flowData, BLOCK_POS_KEY).orElse(BlockPos.ZERO);
            if (storedPos.equals(pos)) {
                return flowData.getInt(REMAINING_TICKS_KEY) > 0;
            }
        }
        return false;
    }

    private static FlowSavedData getSavedData(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(
                        FlowSavedData::new,
                        FlowSavedData::load
                ),
                "dream_relics_flow_data"
        );
    }

    public static class FlowSavedData extends SavedData {
        private ListTag flowList = new ListTag();

        public FlowSavedData() {
        }

        public static FlowSavedData load(CompoundTag tag, HolderLookup.Provider provider) {
            FlowSavedData data = new FlowSavedData();
            if (tag.contains(FLOW_BLOCKS_KEY)) {
                data.flowList = tag.getList(FLOW_BLOCKS_KEY, 10);
            }
            return data;
        }

        @Override
        public @NotNull CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
            if (!flowList.isEmpty()) {
                tag.put(FLOW_BLOCKS_KEY, flowList);
            }
            return tag;
        }

        public ListTag getFlowList() {
            return flowList;
        }

        public void setFlowList(ListTag flowList) {
            this.flowList = flowList;
        }
    }
}