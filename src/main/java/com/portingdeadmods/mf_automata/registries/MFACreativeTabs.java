package com.portingdeadmods.mf_automata.registries;

import com.portingdeadmods.mf_automata.MFAutomata;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class MFACreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MFAutomata.MODID);

    static {
        CREATIVE_MODE_TABS.register("mfa_tab", () -> CreativeModeTab.builder()
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon(() -> MFAItems.EXAMPLE_ITEM.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.accept(MFAItems.EXAMPLE_ITEM.get());
                    output.accept(MFABlocks.MILKING_MACHINE.get());
                    output.accept(MFABlocks.EXAMPLE_BLOCK.get());
                }).build());
    }
}
