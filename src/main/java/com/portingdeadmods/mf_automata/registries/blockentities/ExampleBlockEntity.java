package com.portingdeadmods.mf_automata.registries.blockentities;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.mf_automata.registries.MFABlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public class ExampleBlockEntity extends ContainerBlockEntity {
    public ExampleBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(MFABlockEntities.EXAMPLE_BE.get(), p_155229_, p_155230_);
        addEnergyStorage(1000);
        addFluidTank(69);
        addItemHandler(2);
    }

    @Override
    public void tick() {
        MFAutomata.LOGGER.debug("AAAAAAA i am ticking");
    }

    @Override
    public Map<Direction, Pair<IOAction, List<Integer>>> getItemIO() {
        return Map.of(
                Direction.UP, Pair.of(IOAction.INSERT, List.of(0)),
                Direction.DOWN, Pair.of(IOAction.EXTRACT, List.of(0, 1)),
                Direction.EAST, Pair.of(IOAction.INSERT, List.of(0)),
                Direction.WEST, Pair.of(IOAction.EXTRACT, List.of(0, 1))
        );
    }

    @Override
    public Map<Direction, Pair<IOAction, List<Integer>>> getFluidIO() {
        return Map.of(
                Direction.UP, Pair.of(IOAction.INSERT, List.of(0)),
                Direction.DOWN, Pair.of(IOAction.EXTRACT, List.of(0))
        );
    }
}
