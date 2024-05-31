package com.portingdeadmods.mf_automata.registries.blocks;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.mf_automata.api.blocks.ContainerBlock;
import com.portingdeadmods.mf_automata.registries.MFABlockEntities;
import com.portingdeadmods.mf_automata.registries.blockentities.ExampleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class ExampleBlock extends ContainerBlock {
    public ExampleBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public BlockEntityType<ContainerBlockEntity> getBlockEntityType() {
        return (BlockEntityType<ContainerBlockEntity>) MFABlockEntities.EXAMPLE_BE.get();
    }

    @Override
    public boolean enableTicking() {
        return true;
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        Direction direction = p_60508_.getDirection();
        ContainerBlockEntity blockEntity = (ContainerBlockEntity) p_60504_.getBlockEntity(p_60505_);
        LazyOptional<IItemHandler> capability = blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction);
        MFAutomata.LOGGER.debug("Side: {}, Itemhandler: {}", direction, capability.isPresent() ? capability.orElseThrow(NullPointerException::new) : "Empty");
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ExampleBlockEntity(blockPos, blockState);
    }
}
