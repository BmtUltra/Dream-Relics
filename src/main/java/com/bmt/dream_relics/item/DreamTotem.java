package com.bmt.dream_relics.item;

import com.bmt.dream_relics.config.CommonConfig;
import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.util.SleepStateManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;

public class DreamTotem extends DreamRelicItemBase implements ICurioItem {

    public DreamTotem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public static boolean tryAutoActivate(Player player, Level level) {
        if (player.getCooldowns().isOnCooldown(DRItems.DREAM_TOTEM.get())) {
            return false;
        }

        Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
        if (optional.isPresent()) {
            ICuriosItemHandler handler = optional.get();
            List<SlotResult> results = handler.findCurios(DRItems.DREAM_TOTEM.get());
            if (!results.isEmpty()) {
                return activateTotemForDeathPrevention(player, level);
            }
        }

        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() == DRItems.DREAM_TOTEM.get()) {
                return activateTotemForDeathPrevention(player, level);
            }
        }
        return false;
    }

    private static boolean activateTotemForDeathPrevention(Player player, Level level) {
        player.getCooldowns().addCooldown(DRItems.DREAM_TOTEM.get(), CommonConfig.dreamTotemCooldown);

        player.setHealth(2.0F);
        player.removeAllEffects();
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 2));

        BlockPos pos = player.blockPosition();
        AABB area = new AABB(
                pos.getX() - CommonConfig.dreamTotemEffectRange, pos.getY() - CommonConfig.dreamTotemEffectRange, pos.getZ() - CommonConfig.dreamTotemEffectRange,
                pos.getX() + CommonConfig.dreamTotemEffectRange, pos.getY() + CommonConfig.dreamTotemEffectRange, pos.getZ() + CommonConfig.dreamTotemEffectRange
        );

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity instanceof Mob) {
                SleepStateManager.setSleeping(entity, CommonConfig.dreamTotemSleepDuration);
            }
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

        level.broadcastEntityEvent(player, (byte)66);
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("item.dream_relics.tooltip.dream_totem.auto_trigger")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
    }
}