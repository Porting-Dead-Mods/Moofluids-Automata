package com.portingdeadmods.mf_automata.api.blocks;

import com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ContainerBlock extends BaseEntityBlock {
    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ContainerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        return defaultBlockState().setValue(FACING, p_49820_.getPlayer().getDirection());
    }

    public abstract BlockEntityType<ContainerBlockEntity> getBlockEntityType();

    public abstract boolean enableTicking();

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return getBlockEntityType().create(blockPos, blockState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide() || !enableTicking()) return null;

        return createTickerHelper(blockEntityType, getBlockEntityType(),
                (level1, pos1, state1, entity1) -> entity1.tick());
    }

    @Override
    public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        if (!p_60515_.is(p_60518_.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ContainerBlockEntity containerBlockEntity) {
                containerBlockEntity.drop();
            }
        }
        super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
    }
}
