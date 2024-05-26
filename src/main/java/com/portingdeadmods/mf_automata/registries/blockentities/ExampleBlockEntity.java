package com.portingdeadmods.mf_automata.registries.blockentities;

import com.portingdeadmods.mf_automata.registries.MFABlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleBlockEntity extends BlockEntity {
    public ExampleBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(MFABlockEntities.EXAMPLE_BE.get(), p_155229_, p_155230_);
    }
}
