package com.portingdeadmods.mf_automata.api.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity.IOAction;

public record SidedFluidHandler(IFluidHandler innerHandler,
                                IOAction action,
                                List<Integer> tanks) implements IFluidHandler {
    public SidedFluidHandler(IFluidHandler innerHandler, Pair<IOAction, List<Integer>> actionSlotsPair) {
        this(innerHandler, actionSlotsPair != null ? actionSlotsPair.getFirst() : IOAction.NONE, actionSlotsPair != null ? actionSlotsPair.getSecond() : List.of());
    }

    @Override
    public int getTanks() {
        return innerHandler.getTanks();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return innerHandler.getFluidInTank(i);
    }

    @Override
    public int getTankCapacity(int i) {
        return innerHandler.getTankCapacity(i);
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return action == IOAction.INSERT && tanks.contains(i) && innerHandler.isFluidValid(i, fluidStack);
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        return action == IOAction.INSERT ? innerHandler.fill(fluidStack, fluidAction) : 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        return action == IOAction.EXTRACT ? innerHandler.drain(fluidStack, fluidAction) : FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
        return action == IOAction.EXTRACT ? innerHandler.drain(i, fluidAction) : FluidStack.EMPTY;
    }
}
