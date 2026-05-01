package com.bmt.dream_relics.mixins.curios;

import com.bmt.dream_relics.common.CombinedEquippedCuriosHandler;
import com.bmt.dream_relics.common.DreamRelicsCurioCompatHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Pseudo
@Mixin(targets = "top.theillusivec4.curios.common.capability.CurioInventoryCapability$CurioInventoryWrapper", remap = false)
public abstract class CurioInventoryWrapperMixin {

    @Shadow(remap = false)
    public abstract Map<String, ICurioStacksHandler> getCurios();

    @Shadow(remap = false)
    @Nullable
    public abstract LivingEntity getWearer();

    @Inject(method = "getEquippedCurios()Lnet/minecraftforge/items/IItemHandlerModifiable;",
            at = @At("RETURN"), cancellable = true, remap = false)
    private void dreamRelics$getEquippedCurios(CallbackInfoReturnable<IItemHandlerModifiable> cir) {
        List<ItemStack> virtualStacks = DreamRelicsCurioCompatHelper.collectVirtualEquippedStacks(this.getCurios());

        if (!virtualStacks.isEmpty()) {
            cir.setReturnValue(new CombinedEquippedCuriosHandler(cir.getReturnValue(), virtualStacks));
        }
    }

    @Inject(method = "findFirstCurio(Ljava/util/function/Predicate;)Ljava/util/Optional;",
            at = @At("RETURN"), cancellable = true, remap = false)
    private void dreamRelics$findFirstCurio(Predicate<ItemStack> filter,
                                            CallbackInfoReturnable<Optional<SlotResult>> cir) {
        if (cir.getReturnValue().isPresent()) {
            return;
        }

        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        DreamRelicsCurioCompatHelper.findFirstStoredCurio(wearer, this.getCurios(), filter)
                .ifPresent(result -> cir.setReturnValue(Optional.of(result)));
    }

    @Inject(method = "findCurios(Ljava/util/function/Predicate;)Ljava/util/List;",
            at = @At("RETURN"), cancellable = true, remap = false)
    private void dreamRelics$findCurios(Predicate<ItemStack> filter,
                                        CallbackInfoReturnable<List<SlotResult>> cir) {
        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        List<SlotResult> merged = new ArrayList<>(cir.getReturnValue());
        merged.addAll(DreamRelicsCurioCompatHelper.findStoredCurios(wearer, this.getCurios(), filter));
        cir.setReturnValue(merged);
    }

    @Inject(method = "findCurios([Ljava/lang/String;)Ljava/util/List;",
            at = @At("RETURN"), cancellable = true, remap = false)
    private void dreamRelics$findCuriosByIdentifiers(String[] identifiers,
                                                     CallbackInfoReturnable<List<SlotResult>> cir) {
        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        List<SlotResult> merged = new ArrayList<>(cir.getReturnValue());
        merged.addAll(DreamRelicsCurioCompatHelper.findStoredCuriosByIdentifiers(wearer, this.getCurios(), identifiers));
        cir.setReturnValue(merged);
    }

    @Inject(method = "findCurio(Ljava/lang/String;I)Ljava/util/Optional;",
            at = @At("RETURN"), cancellable = true, remap = false)
    private void dreamRelics$findCurio(String identifier,
                                       int index,
                                       CallbackInfoReturnable<Optional<SlotResult>> cir) {
        LivingEntity wearer = this.getWearer();
        if (wearer == null) {
            return;
        }

        Optional<SlotResult> original = cir.getReturnValue();

        if (original.isPresent() && !(original.get().stack().getItem() instanceof com.bmt.dream_relics.item.MemoryStardustItem)) {
            return;
        }

        DreamRelicsCurioCompatHelper.findStoredCurioBySlot(wearer, this.getCurios(), identifier, index)
                .ifPresent(result -> cir.setReturnValue(Optional.of(result)));
    }
}
