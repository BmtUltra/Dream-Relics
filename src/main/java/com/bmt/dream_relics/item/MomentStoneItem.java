package com.bmt.dream_relics.item;

import com.bmt.dream_relics.client.DRClient;
import com.bmt.dream_relics.util.FlowStateManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MomentStoneItem extends Item {
    private static final int COOLDOWN_TICKS = 400;
    private static final int FLOW_DURATION_TICKS = 180;

    public MomentStoneItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResult.FAIL;
        }

        BlockState state = level.getBlockState(pos);

        if (!canAccelerate(state)) {
            return InteractionResult.FAIL;
        }

        if (FlowStateManager.isFlowing(level, pos)) {
            return InteractionResult.FAIL;
        }

        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

        FlowStateManager.startFlow(level, pos, FLOW_DURATION_TICKS);

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private boolean canAccelerate(BlockState state) {
        return !state.isAir();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.translatable("tooltip.dream_relics.moment_stone")
                .withStyle(ChatFormatting.GRAY));

        if (level != null && level.isClientSide) {
            Player player = DRClient.getLocalPlayer();
            if (player != null && player.getCooldowns().isOnCooldown(this)) {
                float cooldownPercent = player.getCooldowns().getCooldownPercent(this, 0.0F);
                int remainingTicks = (int) (cooldownPercent * COOLDOWN_TICKS);
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                if (remainingSeconds > 0) {
                    tooltip.add(Component.translatable("item.dream_relics.moment_stone.cooldown", remainingSeconds)
                            .withStyle(ChatFormatting.BLUE));
                }
            }
        }
    }
}