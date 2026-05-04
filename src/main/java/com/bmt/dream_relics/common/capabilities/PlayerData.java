package com.bmt.dream_relics.common.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerData implements INBTSerializable<CompoundTag> {

    public int SocialAnxietyLevel = 0;

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("SocialAnxietyLevel", SocialAnxietyLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        SocialAnxietyLevel = nbt.getInt("SocialAnxietyLevel");
    }
}