package com.bmt.dream_relics.integration;

import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Map;
import java.util.Optional;


public class CuriosCompat {

    public static Map<String, ICurioStacksHandler> getCurios(LivingEntity entity) {
        Optional<ICuriosItemHandler> curiosOpt = CuriosApi.getCuriosInventory(entity);
        if (curiosOpt.isPresent()) {
            return curiosOpt.get().getCurios();
        }
        return Map.of();
    }
}
