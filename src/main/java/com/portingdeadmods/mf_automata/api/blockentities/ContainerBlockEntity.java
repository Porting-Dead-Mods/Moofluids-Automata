package com.portingdeadmods.mf_automata.api.blockentities;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.mf_automata.api.blocks.ContainerBlock;
import com.portingdeadmods.mf_automata.api.utils.SidedFluidHandler;
import com.portingdeadmods.mf_automata.api.utils.SidedItemHandler;
import com.portingdeadmods.mf_automata.api.utils.ValidationFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class ContainerBlockEntity extends BlockEntity {
    private @Nullable ItemStackHandler itemHandler;
    private @Nullable FluidTank fluidTank;
    private @Nullable EnergyStorage energyStorage;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public ContainerBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    public void drop() {
        getItemHandlerStacks().ifPresent(stacks -> {
            SimpleContainer inventory = new SimpleContainer(stacks);
            Containers.dropContents(this.level, this.worldPosition, inventory);
        });
    }

    /**
     * NOTE: For this to run, override the enableTicking in your blockentity's block class
     */
    public void tick() {
    }

    public Optional<ItemStackHandler> getItemHandler() {
        return Optional.ofNullable(itemHandler);
    }

    public Optional<FluidTank> getFluidHandler() {
        return Optional.ofNullable(fluidTank);
    }

    public Optional<EnergyStorage> getEnergyHandler() {
        return Optional.ofNullable(energyStorage);
    }

    public Optional<ItemStack[]> getItemHandlerStacks() {
        return getItemHandler().map(handler -> {
            ItemStack[] itemStacks = new ItemStack[handler.getSlots()];
            for (int i = 0; i < handler.getSlots(); i++) {
                itemStacks[i] = handler.getStackInSlot(i);
            }
            return itemStacks;
        });
    }

    /**
     * Get the input/output config for the blockenitity.
     * If directions are not defined in the map, they are assumed to be {@link  IOAction#NONE} and do not affect any slot.
     *
     * @return Map of directions that each map to a pair that defines the IOAction as well as the tanks that are affected
     */
    public abstract Map<Direction, Pair<IOAction, List<Integer>>> getItemIO();

    /**
     * Get the input/output config for the blockenitity.
     * If directions are not defined in the map, they are assumed to be {@link  IOAction#NONE} and do not affect any slot.
     *
     * @return Map of directions that each map to a pair that defines the IOAction as well as the tanks that are affected
     */
    public abstract Map<Direction, Pair<IOAction, List<Integer>>> getFluidIO();

    @Override
    public final void load(CompoundTag nbt) {
        super.load(nbt);
        getFluidHandler().ifPresent(fluidTank1 -> fluidTank1.readFromNBT(nbt.getCompound("fluidhandler")));
        getItemHandler().ifPresent(itemStackHandler -> itemStackHandler.deserializeNBT(nbt.getCompound("itemhandler")));
        getEnergyHandler().ifPresent(energyStorage1 -> energyStorage1.deserializeNBT(nbt.get("energyhandler")));
        loadData(nbt);
    }

    @Override
    public final void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        getFluidHandler().ifPresent(fluidTank1 -> nbt.put("fluidhandler", fluidTank1.writeToNBT(new CompoundTag())));
        getItemHandler().ifPresent(itemStackHandler -> nbt.put("itemhandler", itemStackHandler.serializeNBT()));
        getEnergyHandler().ifPresent(energyStorage1 -> nbt.put("energyhandler", energyStorage1.serializeNBT()));
        saveData(nbt);
    }

    protected void loadData(CompoundTag tag) {
    }

    protected void saveData(CompoundTag tag) {
    }

    protected final void addItemHandler(int slots) {
        addItemHandler(slots, (slot, itemStack) -> true);
    }

    protected final void addFluidTank(int capacityInMb) {
        addFluidTank(capacityInMb, 1, fluidStack -> true);
    }

    protected final void addItemHandler(int slots, ValidationFunctions.ItemValid validation) {
        this.itemHandler = new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                onItemsChanged(slot);
                update();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return validation.itemValid(slot, stack);
            }
        };
    }

    protected final void addFluidTank(int capacityInMb, int tanks, ValidationFunctions.FluidValid validation) {
        this.fluidTank = new FluidTank(capacityInMb) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                onFluidsChanged();
                update();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return validation.fluidValid(stack);
            }

            @Override
            public int getTanks() {
                return tanks;
            }
        };
    }

    private void update() {
        if (!level.isClientSide())
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    protected void onItemsChanged(int slot) {
    }

    protected void onFluidsChanged() {
    }

    protected final void addEnergyStorage(int capacity) {
        this.energyStorage = new EnergyStorage(capacity);
    }

    protected final void addEnergyStorage(int capacity, int maxTransfer) {
        this.energyStorage = new EnergyStorage(capacity, maxTransfer);
    }

    protected final void addEnergyStorage(int capacity, int maxTransfer, IOAction action) {
        int extractAmount = 0;
        int insertAmount = 0;
        switch (action) {
            case INSERT -> insertAmount = maxTransfer;
            case EXTRACT -> extractAmount = maxTransfer;
        }
        this.energyStorage = new EnergyStorage(capacity, insertAmount, extractAmount);
    }

    protected final void addEnergyStorage(int capacity, int maxInput, int maxOutput) {
        this.energyStorage = new EnergyStorage(capacity, maxInput, maxOutput);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = itemHandler != null ? LazyOptional.of(() -> itemHandler) : LazyOptional.empty();
        lazyFluidHandler = fluidTank != null ? LazyOptional.of(() -> fluidTank) : LazyOptional.empty();
        lazyEnergyHandler = energyStorage != null ? LazyOptional.of(() -> energyStorage) : LazyOptional.empty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) return lazyFluidHandler.cast();
        else if (cap == ForgeCapabilities.ENERGY) return lazyEnergyHandler.cast();
        else if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        else return super.getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            Map<Direction, Pair<IOAction, List<Integer>>> ioPorts = getItemIO();
            if (ioPorts.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(ContainerBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
                    return LazyOptional.of(() -> new SidedItemHandler(itemHandler, ioPorts.get(side))).cast();
                }

                return switch (localDir) {
                    case NORTH -> LazyOptional.of(() -> new SidedItemHandler(itemHandler, ioPorts.get(side.getOpposite()))).cast();
                    case EAST -> LazyOptional.of(() -> new SidedItemHandler(itemHandler, ioPorts.get(side.getClockWise()))).cast();
                    case SOUTH -> LazyOptional.of(() -> new SidedItemHandler(itemHandler, ioPorts.get(side))).cast();
                    case WEST -> LazyOptional.of(() -> new SidedItemHandler(itemHandler, ioPorts.get(side.getCounterClockWise()))).cast();
                    default -> LazyOptional.empty();
                };
            }
        } else if (cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == null) {
                return lazyFluidHandler.cast();
            }

            Map<Direction, Pair<IOAction, List<Integer>>> ioPorts = getFluidIO();
            if (ioPorts.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(ContainerBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
                    return LazyOptional.of(() -> new SidedFluidHandler(fluidTank, ioPorts.get(side))).cast();
                }

                return switch (localDir) {
                    case NORTH -> LazyOptional.of(() -> new SidedFluidHandler(fluidTank, ioPorts.get(side.getOpposite()))).cast();
                    case EAST -> LazyOptional.of(() -> new SidedFluidHandler(fluidTank, ioPorts.get(side.getClockWise()))).cast();
                    case SOUTH -> LazyOptional.of(() -> new SidedFluidHandler(fluidTank, ioPorts.get(side))).cast();
                    case WEST -> LazyOptional.of(() -> new SidedFluidHandler(fluidTank, ioPorts.get(side.getCounterClockWise()))).cast();
                    default -> LazyOptional.empty();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    public int getEnergyStored() {
        return this.energyStorage.getEnergyStored();
    }

    public int getEnergyCapacity() {
        return this.energyStorage.getMaxEnergyStored();
    }

    public enum IOAction {
        INSERT,
        EXTRACT,
        NONE,
    }
}