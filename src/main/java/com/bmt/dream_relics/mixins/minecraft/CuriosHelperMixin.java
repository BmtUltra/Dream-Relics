package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.item.MemoryStardustItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.CuriosHelper;

@Mixin(value = CuriosHelper.class, remap = false)
public abstract class CuriosHelperMixin {

    private static final int MAX_DEPTH = 8;

    @Inject(
            method = "getEquippedCurios(Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraftforge/common/util/LazyOptional;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$getEquippedCurios(LivingEntity livingEntity,
                                               CallbackInfoReturnable<LazyOptional<IItemHandlerModifiable>> cir) {
        LazyOptional<IItemHandlerModifiable> result = CuriosApi.getCuriosInventory(livingEntity)
                .lazyMap(handler -> new CombinedEquippedCuriosHandler(livingEntity, handler.getEquippedCurios()));
        cir.setReturnValue(result);
    }

    @Inject(
            method = "findFirstCurio(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findFirstCurioByItem(LivingEntity livingEntity,
                                                  Item item,
                                                  CallbackInfoReturnable<Optional<SlotResult>> cir) {
        cir.setReturnValue(findFirstCurioExtended(livingEntity, stack -> stack.getItem() == item));
    }

    @Inject(
            method = "findFirstCurio(Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findFirstCurioByPredicate(LivingEntity livingEntity,
                                                       Predicate<ItemStack> filter,
                                                       CallbackInfoReturnable<Optional<SlotResult>> cir) {
        cir.setReturnValue(findFirstCurioExtended(livingEntity, filter));
    }

    @Inject(
            method = "findCurios(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findCuriosByItem(LivingEntity livingEntity,
                                              Item item,
                                              CallbackInfoReturnable<List<SlotResult>> cir) {
        cir.setReturnValue(findCuriosExtended(livingEntity, stack -> stack.getItem() == item));
    }

    @Inject(
            method = "findCurios(Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findCuriosByPredicate(LivingEntity livingEntity,
                                                   Predicate<ItemStack> filter,
                                                   CallbackInfoReturnable<List<SlotResult>> cir) {
        cir.setReturnValue(findCuriosExtended(livingEntity, filter));
    }

    @Inject(
            method = "findCurios(Lnet/minecraft/world/entity/LivingEntity;[Ljava/lang/String;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findCuriosByIdentifiers(LivingEntity livingEntity,
                                                     String[] identifiers,
                                                     CallbackInfoReturnable<List<SlotResult>> cir) {
        cir.setReturnValue(findCuriosByIdentifiersExtended(livingEntity, identifiers));
    }

    @Inject(
            method = "findCurio(Lnet/minecraft/world/entity/LivingEntity;Ljava/lang/String;I)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findCurio(LivingEntity livingEntity,
                                       String identifier,
                                       int index,
                                       CallbackInfoReturnable<Optional<SlotResult>> cir) {
        cir.setReturnValue(findCurioExtended(livingEntity, identifier, index));
    }

    @Inject(
            method = "findEquippedCurio(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findEquippedCurioByItem(Item item,
                                                     LivingEntity livingEntity,
                                                     CallbackInfoReturnable<Optional<ImmutableTriple<String, Integer, ItemStack>>> cir) {
        cir.setReturnValue(findEquippedCurioExtended(livingEntity, stack -> stack.getItem() == item));
    }

    @Inject(
            method = "findEquippedCurio(Ljava/util/function/Predicate;Lnet/minecraft/world/entity/LivingEntity;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dreamRelics$findEquippedCurioByPredicate(Predicate<ItemStack> filter,
                                                          LivingEntity livingEntity,
                                                          CallbackInfoReturnable<Optional<ImmutableTriple<String, Integer, ItemStack>>> cir) {
        cir.setReturnValue(findEquippedCurioExtended(livingEntity, filter));
    }

    private static Optional<SlotResult> findFirstCurioExtended(LivingEntity livingEntity,
                                                               Predicate<ItemStack> filter) {
        List<SlotResult> results = findCuriosExtended(livingEntity, filter);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    private static List<SlotResult> findCuriosExtended(LivingEntity livingEntity,
                                                       Predicate<ItemStack> filter) {
        List<SlotResult> results = new ArrayList<>();

        CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
            Map<String, Integer> baseCounts = getBaseSlotCounts(handler.getCurios());
            Map<String, Integer> nextVirtualIndex = new HashMap<>();
            Map<String, ICurioStacksHandler> curios = handler.getCurios();

            for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                String identifier = entry.getKey();
                IDynamicStackHandler stacks = entry.getValue().getStacks();

                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack equipped = stacks.getStackInSlot(i);

                    if (!equipped.isEmpty() && filter.test(equipped)) {
                        results.add(new SlotResult(new SlotContext(identifier, livingEntity, i, false, true), equipped));
                    }
                }
            }

            for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                String parentIdentifier = entry.getKey();
                IDynamicStackHandler stacks = entry.getValue().getStacks();

                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack equipped = stacks.getStackInSlot(i);
                    collectStoredCuriosByFilter(equipped, parentIdentifier, filter, results, nextVirtualIndex, baseCounts, livingEntity, 0);
                }
            }
        });

        return results;
    }

    private static List<SlotResult> findCuriosByIdentifiersExtended(LivingEntity livingEntity,
                                                                    String[] identifiers) {
        if (identifiers == null || identifiers.length == 0) {
            return Collections.emptyList();
        }

        Set<String> identifierSet = new LinkedHashSet<>();
        Collections.addAll(identifierSet, identifiers);
        List<SlotResult> results = new ArrayList<>();

        CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
            Map<String, ICurioStacksHandler> curios = handler.getCurios();
            Map<String, Integer> nextVirtualIndex = new HashMap<>();
            Map<String, Integer> baseCounts = getBaseSlotCounts(curios);

            for (String identifier : identifierSet) {
                ICurioStacksHandler stacksHandler = curios.get(identifier);

                if (stacksHandler == null) {
                    continue;
                }

                IDynamicStackHandler stacks = stacksHandler.getStacks();

                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack stack = stacks.getStackInSlot(i);

                    if (!stack.isEmpty()) {
                        results.add(new SlotResult(new SlotContext(identifier, livingEntity, i, false, true), stack));
                    }
                }
            }

            for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                IDynamicStackHandler stacks = entry.getValue().getStacks();

                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack equipped = stacks.getStackInSlot(i);
                    collectStoredCuriosByIdentifiers(equipped, identifierSet, results, nextVirtualIndex, baseCounts, livingEntity, 0);
                }
            }
        });

        return results;
    }

    private static Optional<SlotResult> findCurioExtended(LivingEntity livingEntity,
                                                          String identifier,
                                                          int index) {
        if (index < 0) {
            return Optional.empty();
        }

        return CuriosApi.getCuriosInventory(livingEntity).map(handler -> {
            Map<String, ICurioStacksHandler> curios = handler.getCurios();
            ICurioStacksHandler stacksHandler = curios.get(identifier);
            int realSlotCount = 0;

            if (stacksHandler != null) {
                IDynamicStackHandler stacks = stacksHandler.getStacks();
                realSlotCount = stacks.getSlots();

                if (index < realSlotCount) {
                    ItemStack stack = stacks.getStackInSlot(index);
                    return stack.isEmpty() ? Optional.<SlotResult>empty()
                            : Optional.of(new SlotResult(new SlotContext(identifier, livingEntity, index, false, true), stack));
                }
            }

            int targetVirtualIndex = index - realSlotCount;

            if (targetVirtualIndex < 0) {
                return Optional.<SlotResult>empty();
            }

            List<ItemStack> virtualStacks = new ArrayList<>();

            for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                IDynamicStackHandler stacks = entry.getValue().getStacks();

                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack equipped = stacks.getStackInSlot(i);
                    collectStoredStacksForIdentifier(equipped, identifier, virtualStacks, 0);
                }
            }

            if (targetVirtualIndex >= virtualStacks.size()) {
                return Optional.<SlotResult>empty();
            }

            int resolvedIndex = realSlotCount + targetVirtualIndex;
            ItemStack stack = virtualStacks.get(targetVirtualIndex);
            return Optional.of(new SlotResult(new SlotContext(identifier, livingEntity, resolvedIndex, false, true), stack));
        }).orElse(Optional.empty());
    }

    private static Optional<ImmutableTriple<String, Integer, ItemStack>> findEquippedCurioExtended(LivingEntity livingEntity,
                                                                                                    Predicate<ItemStack> filter) {
        Optional<SlotResult> slotResult = findFirstCurioExtended(livingEntity, filter);

        if (slotResult.isEmpty()) {
            return Optional.empty();
        }

        SlotContext slotContext = slotResult.get().slotContext();
        return Optional.of(new ImmutableTriple<>(slotContext.identifier(), slotContext.index(), slotResult.get().stack()));
    }

    private static void collectStoredCuriosByFilter(ItemStack container,
                                                    String parentIdentifier,
                                                    Predicate<ItemStack> filter,
                                                    List<SlotResult> results,
                                                    Map<String, Integer> nextVirtualIndex,
                                                    Map<String, Integer> baseCounts,
                                                    LivingEntity livingEntity,
                                                    int depth) {
        if (depth >= MAX_DEPTH || !(container.getItem() instanceof MemoryStardustItem) || !MemoryStardustItem.hasItems(container)) {
            return;
        }

        ItemStackHandler storedItems = MemoryStardustItem.getItems(container);

        for (int i = 0; i < storedItems.getSlots(); i++) {
            ItemStack stored = storedItems.getStackInSlot(i);

            if (stored.isEmpty()) {
                continue;
            }

            if (filter.test(stored)) {
                String identifier = resolvePreferredIdentifier(stored, parentIdentifier);
                int resolvedIndex = nextVirtualIndex(identifier, nextVirtualIndex, baseCounts);
                results.add(new SlotResult(new SlotContext(identifier, livingEntity, resolvedIndex, false, true), stored));
            }

            collectStoredCuriosByFilter(stored, parentIdentifier, filter, results, nextVirtualIndex, baseCounts, livingEntity, depth + 1);
        }
    }

    private static void collectStoredCuriosByIdentifiers(ItemStack container,
                                                         Set<String> identifiers,
                                                         List<SlotResult> results,
                                                         Map<String, Integer> nextVirtualIndex,
                                                         Map<String, Integer> baseCounts,
                                                         LivingEntity livingEntity,
                                                         int depth) {
        if (depth >= MAX_DEPTH || !(container.getItem() instanceof MemoryStardustItem) || !MemoryStardustItem.hasItems(container)) {
            return;
        }

        ItemStackHandler storedItems = MemoryStardustItem.getItems(container);

        for (int i = 0; i < storedItems.getSlots(); i++) {
            ItemStack stored = storedItems.getStackInSlot(i);

            if (stored.isEmpty()) {
                continue;
            }

            Set<String> stackIdentifiers = getCurioIdentifiers(stored);

            for (String identifier : identifiers) {
                if (stackIdentifiers.contains(identifier)) {
                    int resolvedIndex = nextVirtualIndex(identifier, nextVirtualIndex, baseCounts);
                    results.add(new SlotResult(new SlotContext(identifier, livingEntity, resolvedIndex, false, true), stored));
                }
            }

            collectStoredCuriosByIdentifiers(stored, identifiers, results, nextVirtualIndex, baseCounts, livingEntity, depth + 1);
        }
    }

    private static void collectStoredStacksForIdentifier(ItemStack container,
                                                         String identifier,
                                                         List<ItemStack> stacks,
                                                         int depth) {
        if (depth >= MAX_DEPTH || !(container.getItem() instanceof MemoryStardustItem) || !MemoryStardustItem.hasItems(container)) {
            return;
        }

        ItemStackHandler storedItems = MemoryStardustItem.getItems(container);

        for (int i = 0; i < storedItems.getSlots(); i++) {
            ItemStack stored = storedItems.getStackInSlot(i);

            if (stored.isEmpty()) {
                continue;
            }

            if (getCurioIdentifiers(stored).contains(identifier)) {
                stacks.add(stored);
            }

            collectStoredStacksForIdentifier(stored, identifier, stacks, depth + 1);
        }
    }

    private static List<ItemStack> collectAllStoredStacks(LivingEntity livingEntity) {
        List<ItemStack> stacks = new ArrayList<>();

        CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
            for (Map.Entry<String, ICurioStacksHandler> entry : handler.getCurios().entrySet()) {
                IDynamicStackHandler equipped = entry.getValue().getStacks();

                for (int i = 0; i < equipped.getSlots(); i++) {
                    ItemStack stack = equipped.getStackInSlot(i);
                    collectStoredStacksLinear(stack, stacks, 0);
                }
            }
        });

        return stacks;
    }

    private static void collectStoredStacksLinear(ItemStack container,
                                                  List<ItemStack> stacks,
                                                  int depth) {
        if (depth >= MAX_DEPTH || !(container.getItem() instanceof MemoryStardustItem) || !MemoryStardustItem.hasItems(container)) {
            return;
        }

        ItemStackHandler storedItems = MemoryStardustItem.getItems(container);

        for (int i = 0; i < storedItems.getSlots(); i++) {
            ItemStack stored = storedItems.getStackInSlot(i);

            if (stored.isEmpty()) {
                continue;
            }

            stacks.add(stored);
            collectStoredStacksLinear(stored, stacks, depth + 1);
        }
    }

    private static Map<String, Integer> getBaseSlotCounts(Map<String, ICurioStacksHandler> curios) {
        Map<String, Integer> result = new HashMap<>();

        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getStacks().getSlots());
        }

        return result;
    }

    private static int nextVirtualIndex(String identifier,
                                        Map<String, Integer> nextVirtualIndex,
                                        Map<String, Integer> baseCounts) {
        int next = nextVirtualIndex.getOrDefault(identifier, baseCounts.getOrDefault(identifier, 0));
        nextVirtualIndex.put(identifier, next + 1);
        return next;
    }

    private static String resolvePreferredIdentifier(ItemStack stack,
                                                     String fallbackIdentifier) {
        Set<String> identifiers = getCurioIdentifiers(stack);

        if (identifiers.isEmpty()) {
            return fallbackIdentifier;
        }

        List<String> ordered = new ArrayList<>(identifiers);
        Collections.sort(ordered);
        return ordered.get(0);
    }

    private static Set<String> getCurioIdentifiers(ItemStack stack) {
        return CuriosApi.getItemStackSlots(stack, FMLLoader.getDist() == Dist.CLIENT).keySet();
    }

    private static final class CombinedEquippedCuriosHandler implements IItemHandlerModifiable {

        private final LivingEntity livingEntity;
        private final IItemHandlerModifiable delegate;

        private CombinedEquippedCuriosHandler(LivingEntity livingEntity,
                                              IItemHandlerModifiable delegate) {
            this.livingEntity = livingEntity;
            this.delegate = delegate;
        }

        @Override
        public int getSlots() {
            return this.delegate.getSlots() + collectAllStoredStacks(this.livingEntity).size();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            if (slot < 0) {
                return ItemStack.EMPTY;
            }

            if (slot < this.delegate.getSlots()) {
                return this.delegate.getStackInSlot(slot);
            }

            int virtualIndex = slot - this.delegate.getSlots();
            List<ItemStack> virtualStacks = collectAllStoredStacks(this.livingEntity);
            return virtualIndex >= virtualStacks.size() ? ItemStack.EMPTY : virtualStacks.get(virtualIndex);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot,
                                             @NotNull ItemStack stack,
                                             boolean simulate) {
            if (slot < 0) {
                return stack;
            }

            if (slot < this.delegate.getSlots()) {
                return this.delegate.insertItem(slot, stack, simulate);
            }

            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot,
                                              int amount,
                                              boolean simulate) {
            if (slot < 0) {
                return ItemStack.EMPTY;
            }

            if (slot < this.delegate.getSlots()) {
                return this.delegate.extractItem(slot, amount, simulate);
            }

            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            if (slot < 0) {
                return 0;
            }

            if (slot < this.delegate.getSlots()) {
                return this.delegate.getSlotLimit(slot);
            }

            ItemStack stack = this.getStackInSlot(slot);
            return stack.isEmpty() ? 64 : stack.getMaxStackSize();
        }

        @Override
        public boolean isItemValid(int slot,
                                   @NotNull ItemStack stack) {
            if (slot < 0) {
                return false;
            }

            if (slot < this.delegate.getSlots()) {
                return this.delegate.isItemValid(slot, stack);
            }

            return false;
        }

        @Override
        public void setStackInSlot(int slot,
                                   @NotNull ItemStack stack) {
            if (slot < 0) {
                return;
            }

            if (slot < this.delegate.getSlots()) {
                this.delegate.setStackInSlot(slot, stack);
            }
        }
    }
}
