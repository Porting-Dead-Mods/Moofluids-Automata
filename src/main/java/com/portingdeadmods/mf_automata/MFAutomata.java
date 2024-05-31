package com.portingdeadmods.mf_automata;

import com.mojang.logging.LogUtils;
import com.portingdeadmods.mf_automata.registries.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(MFAutomata.MODID)
public final class MFAutomata {
    public static final String MODID = "mf_automata";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MFAutomata() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MFABlocks.BLOCKS.register(modEventBus);
        MFAItems.ITEMS.register(modEventBus);
        MFACreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        MFABlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        MFAMenus.MENUS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MFAConfig.SPEC);
    }
}
