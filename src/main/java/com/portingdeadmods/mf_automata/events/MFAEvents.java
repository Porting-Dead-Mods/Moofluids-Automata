package com.portingdeadmods.mf_automata.events;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.registries.MFAMenus;
import com.portingdeadmods.mf_automata.registries.screens.MilkingMachineScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class MFAEvents {
    @Mod.EventBusSubscriber(modid = MFAutomata.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ClientBus {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MFAMenus.MILKING_MACHINE_MENU.get(), MilkingMachineScreen::new);
        }
    }
}
