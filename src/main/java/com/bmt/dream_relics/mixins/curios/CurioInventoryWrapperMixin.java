package com.bmt.dream_relics.mixins.curios;

import com.bmt.dream_relics.item.MemoryStardustItem;
import com.bmt.dream_relics.util.CombinedEquipped;
import com.bmt.dream_relics.util.CurioCompatHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.common.capability.CurioInventoryCapability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Mixin(CurioInventoryCapability.class)
public abstract class CurioInventoryWrapperMixin {

    @Shadow
    public abstract Map<String, ICurioStacksHandler> getCurios();

    @Shadow
    @Nullable
    public abstract LivingEntity getWearer();

    @Inject(method = "getEquippedCurios()Lnet/neoforged/neoforge/items/IItemHandlerModifiable;",
            at = @At("RETURN"), cancellable = true)
    private void dreamRelics$getEquippedCurios(CallbackInfoReturnable<IItemHandlerModifiable> cir) {
        List<ItemStack> virtualStacks = CurioCompatHelper.collectVirtualEquippedStacks(this.getCurios());

        if (!virtualStacks.isEmpty()) {
            cir.setReturnValue(new CombinedEquipped(cir.getReturnValue(), virtualStacks));
        }
    }

    @Inject(method = "findFirstCurio(Ljava/util/function/Predicate;ZLjava/lang/String;)Ljava/util/Optional;",
            at = @At("RETURN"), cancellable = true)
    private void dreamRelics$findFirstCurio(Predicate<ItemStack> filter, boolean includeInactive, String cacheKey, CallbackInfoReturnable<Optional<SlotResult>> cir) {
        if (cir.getReturnValue().isPresent()) {
            return;
        }

        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        CurioCompatHelper.findFirstStoredCurio(wearer, this.getCurios(), filter)
                .ifPresent(result -> cir.setReturnValue(Optional.of(result)));
    }

    @Inject(method = "findCurios(Ljava/util/function/Predicate;ZLjava/lang/String;)Ljava/util/List;",
            at = @At("RETURN"), cancellable = true)
    private void dreamRelics$findCurios(Predicate<ItemStack> filter, boolean includeInactive, String cacheKey, CallbackInfoReturnable<List<SlotResult>> cir) {
        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        List<SlotResult> merged = new ArrayList<>(cir.getReturnValue());
        merged.addAll(CurioCompatHelper.findStoredCurios(wearer, this.getCurios(), filter));
        cir.setReturnValue(merged);
    }

    @Inject(method = "findCurios(Z[Ljava/lang/String;)Ljava/util/List;",
            at = @At("RETURN"), cancellable = true)
    private void dreamRelics$findCuriosByIdentifiers(boolean includeInactive, String[] identifiers, CallbackInfoReturnable<List<SlotResult>> cir) {
        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        List<SlotResult> merged = new ArrayList<>(cir.getReturnValue());
        merged.addAll(CurioCompatHelper.findStoredCuriosByIdentifiers(wearer, this.getCurios(), identifiers));
        cir.setReturnValue(merged);
    }

    @Inject(method = "findCurio(Ljava/lang/String;IZ)Ljava/util/Optional;",
            at = @At("RETURN"), cancellable = true)
    private void dreamRelics$findCurio(String identifier, int index, boolean includeInactive, CallbackInfoReturnable<Optional<SlotResult>> cir) {
        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        Optional<SlotResult> original = cir.getReturnValue();

        if (original.isPresent() && !(original.get().stack().getItem() instanceof MemoryStardustItem)) {
            return;
        }

        CurioCompatHelper.findStoredCurioBySlot(wearer, this.getCurios(), identifier, index)
                .ifPresent(result -> cir.setReturnValue(Optional.of(result)));
    }
}