package com.portingdeadmods.mf_automata.registries.blocks;

import com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.mf_automata.api.blocks.ContainerBlock;
import com.portingdeadmods.mf_automata.registries.MFABlockEntities;
import com.portingdeadmods.mf_automata.registries.blockentities.MilkingMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class MilkingMachineBlock extends ContainerBlock {
    public MilkingMachineBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<ContainerBlockEntity> getBlockEntityType() {
        return (BlockEntityType<ContainerBlockEntity>) MFABlockEntities.MILKING_MACHINE.get();
    }

    @Override
    public boolean enableTicking() {
        return true;
    }

    @Override
    public @NotNull InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (p_60504_.isClientSide()) return InteractionResult.FAIL;

        BlockEntity blockEntity = p_60504_.getBlockEntity(p_60505_);
        if (blockEntity instanceof MilkingMachineBlockEntity milkingMachineBlockEntity) {
            NetworkHooks.openScreen((ServerPlayer) p_60506_, milkingMachineBlockEntity, p_60505_);
        }
        return InteractionResult.SUCCESS;
    }
}
