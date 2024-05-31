package com.portingdeadmods.mf_automata.api.utils;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity.IOAction;

public record SidedItemHandler(IItemHandlerModifiable innerHandler,
                               ContainerBlockEntity.IOAction action,
                               List<Integer> slots) implements IItemHandlerModifiable {
    public SidedItemHandler(IItemHandlerModifiable innerHandler, Pair<ContainerBlockEntity.IOAction, List<Integer>> actionSlotsPair) {
        this(innerHandler, actionSlotsPair != null ? actionSlotsPair.getFirst() : IOAction.NONE, actionSlotsPair != null ? actionSlotsPair.getSecond() : List.of());
    }

    @Override
    public void setStackInSlot(int i, @NotNull ItemStack itemStack) {
        innerHandler.setStackInSlot(i, itemStack);
    }

    @Override
    public int getSlots() {
        return innerHandler.getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        return innerHandler.getStackInSlot(i);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack itemStack, boolean simulate) {
        return action == IOAction.INSERT && slots.contains(slot) ? innerHandler.insertItem(slot, itemStack, simulate) : itemStack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return action == IOAction.EXTRACT && slots.contains(slot) ? innerHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int i) {
        return innerHandler.getSlotLimit(i);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack itemStack) {
        return action == IOAction.INSERT && slots.contains(slot) && innerHandler.isItemValid(slot, itemStack);
    }
}
