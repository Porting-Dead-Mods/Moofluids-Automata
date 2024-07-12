package com.portingdeadmods.mf_automata.registries.blockentities;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.mf_automata.MFAConfig;
import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.mf_automata.api.blocks.ContainerBlock;
import com.portingdeadmods.mf_automata.registries.MFABlockEntities;
import com.portingdeadmods.mf_automata.registries.screens.MilkingMachineMenu;
import com.portingdeadmods.moofluids.MFConfig;
import com.portingdeadmods.moofluids.Utils;
import com.portingdeadmods.moofluids.entity.FluidCow;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class MilkingMachineBlockEntity extends ContainerBlockEntity implements MenuProvider {
    public MilkingMachineBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(MFABlockEntities.MILKING_MACHINE.get(), p_155229_, p_155230_);
        addFluidTank(8000, 3, fluidStack -> !MFConfig.fluidBlacklist.contains(Utils.idFromFluid(fluidStack.getFluid())));
        addEnergyStorage(8000, 128, IOAction.INSERT);
        addItemHandler(1);
    }

    @Override
    public void tick() {
        if (level instanceof ServerLevel serverLevel) {
            super.tick();
            BlockState state = level.getBlockState(worldPosition);
            Direction facing = state.getValue(ContainerBlock.FACING);
            BlockPos offset = worldPosition.offset(facing.getOpposite().getNormal());
            List<FluidCow> fluidCows = serverLevel.getEntities(null, new AABB(offset)).stream()
                    .filter(entity -> entity instanceof FluidCow)
                    .map(entity -> (FluidCow) entity).toList();

            getEnergyHandler().get().receiveEnergy(1, false);

            if (fluidCows.isEmpty())
                return;

            for (FluidCow fluidCow : fluidCows) {
                EnergyStorage energyStorage = getEnergyHandler().get();
                FluidTank fluidTank = getFluidHandler().get();
                FluidStack fluidStack = new FluidStack(fluidCow.getFluid(), 1000);
                if (fluidCow.canBeMilked() && energyStorage.getEnergyStored() >= MFAConfig.milkingMachineEnergyUsage && fluidTank.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) == 1000) {
                    fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                    energyStorage.extractEnergy(MFAConfig.milkingMachineEnergyUsage, false);
                    fluidCow.setCanBeMilked(false);
                }
            }
        }
    }

    @Override
    public Map<Direction, Pair<IOAction, List<Integer>>> getItemIO() {
        return Map.of();
    }

    @Override
    public Map<Direction, Pair<IOAction, List<Integer>>> getFluidIO() {
        return Map.of();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MilkingMachineMenu(i, inventory, this);
    }
}
