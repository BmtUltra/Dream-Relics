package com.bmt.dream_relics.item;

import com.bmt.dream_relics.init.DRItems;
import com.bmt.dream_relics.util.SleepStateManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class DreamTotem extends DreamRelicItemBase implements ICurioItem {

    private static final String DREAM_ESSENCE_KEY = "DreamEssence";
    private static final int MAX_ESSENCE = 4;
    private static final int EFFECT_RANGE = 16;
    private static final int SLEEP_DURATION = 200;

    public DreamTotem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.COMMON));
    }

    public static int getDreamEssence(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(DREAM_ESSENCE_KEY);
    }

    private static void setDreamEssence(ItemStack stack, int amount) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(DREAM_ESSENCE_KEY, Math.min(amount, MAX_ESSENCE));
    }

    public static void addDreamEssence(ItemStack stack) {
        int current = getDreamEssence(stack);
        if (current < MAX_ESSENCE) {
            setDreamEssence(stack, current + 1);
        }
    }

    private static void consumeDreamEssence(ItemStack stack) {
        int current = getDreamEssence(stack);
        if (current > 0) {
            setDreamEssence(stack, current - 1);
        }
    }

    public static boolean tryAutoActivate(Player player, Level level) {

        LazyOptional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
        if (optional.isPresent()) {
            ICuriosItemHandler handler = optional.orElseThrow(NullPointerException::new);
            List<SlotResult> results = handler.findCurios(DRItems.DREAM_TOTEM.get());
            if (!results.isEmpty()) {
                ItemStack totem = results.get(0).stack();
                return activateTotemForDeathPrevention(totem, player, level);
            }
        }

        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() == DRItems.DREAM_TOTEM.get()) {
                return activateTotemForDeathPrevention(stack, player, level);
            }
        }
        return false;
    }

    private static boolean activateTotemForDeathPrevention(ItemStack stack, Player player, Level level) {
        if (getDreamEssence(stack) > 0) {

            consumeDreamEssence(stack);

            player.setHealth(2.0F);
            player.removeAllEffects();
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));

            BlockPos pos = player.blockPosition();
            AABB area = new AABB(
                    pos.getX() - EFFECT_RANGE, pos.getY() - EFFECT_RANGE, pos.getZ() - EFFECT_RANGE,
                    pos.getX() + EFFECT_RANGE, pos.getY() + EFFECT_RANGE, pos.getZ() + EFFECT_RANGE
            );

            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
                if (entity instanceof Mob) {
                    SleepStateManager.setSleeping(entity, SLEEP_DURATION);
                }
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

            level.broadcastEntityEvent(player, (byte)35);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.translatable("item.dream_relics.tooltip.dream_totem.restore")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.dream_relics.tooltip.dream_totem.auto_trigger")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
    }
}