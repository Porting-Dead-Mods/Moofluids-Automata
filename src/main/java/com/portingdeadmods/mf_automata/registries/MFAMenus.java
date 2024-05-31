package com.portingdeadmods.mf_automata.registries;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.api.screens.MFAAbstractContainerMenu;
import com.portingdeadmods.mf_automata.registries.screens.MilkingMachineMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class MFAMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MFAutomata.MODID);
    public static final RegistryObject<MenuType<MilkingMachineMenu>> MILKING_MACHINE_MENU =
            registerMenuType("milking_machine_menu", MilkingMachineMenu::new);

    private static <T extends MFAAbstractContainerMenu<?>> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
}
