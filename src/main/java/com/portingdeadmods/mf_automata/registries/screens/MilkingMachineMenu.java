package com.portingdeadmods.mf_automata.registries.screens;

import com.portingdeadmods.mf_automata.api.screens.MFAAbstractContainerMenu;
import com.portingdeadmods.mf_automata.registries.MFAMenus;
import com.portingdeadmods.mf_automata.registries.blockentities.MilkingMachineBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class MilkingMachineMenu extends MFAAbstractContainerMenu<MilkingMachineBlockEntity> {
    public MilkingMachineMenu(int containerId, Inventory inv, MilkingMachineBlockEntity blockEntity) {
        super(MFAMenus.MILKING_MACHINE_MENU.get(), containerId, inv, blockEntity);
        addPlayerInventory(inv, 83 + 17);
        addPlayerHotbar(inv, 141 + 17);
    }

    public MilkingMachineMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, (MilkingMachineBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }
}
