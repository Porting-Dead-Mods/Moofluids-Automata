package com.portingdeadmods.mf_automata.registries.blocks;

import com.portingdeadmods.mf_automata.registries.blockentities.ExampleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ExampleBlock extends BaseEntityBlock {
    public ExampleBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ExampleBlockEntity(blockPos, blockState);
    }
}
