package com.portingdeadmods.mf_automata.api.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public final class ValidationFunctions {
    @FunctionalInterface
    public interface ItemValid {
        boolean itemValid(int slot, ItemStack itemStack);
    }

    @FunctionalInterface
    public interface FluidValid {
        boolean fluidValid(FluidStack fluidStack);
    }
}