package com.portingdeadmods.mf_automata;

import com.mojang.logging.LogUtils;
import com.portingdeadmods.mf_automata.registries.MFABlockEntities;
import com.portingdeadmods.mf_automata.registries.MFABlocks;
import com.portingdeadmods.mf_automata.registries.MFACreativeTabs;
import com.portingdeadmods.mf_automata.registries.MFAItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MFAutomata.MODID)
public final class MFAutomata {
    public static final String MODID = "mf_automata";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MFAutomata() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MFAItems.ITEMS.register(modEventBus);
        MFABlocks.BLOCKS.register(modEventBus);
        MFABlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        MFACreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MFAConfig.SPEC);
    }
}
