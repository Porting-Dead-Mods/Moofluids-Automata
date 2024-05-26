package com.portingdeadmods.mf_automata.registries;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.registries.blocks.ExampleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class MFABlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MFAutomata.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = registerBlockAndItem("example_block", new ExampleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    private static RegistryObject<Block> registerBlock(String name, Block block) {
        return BLOCKS.register(name, () -> block);
    }

    private static RegistryObject<Block> registerBlockAndItem(String name, Block block) {
        RegistryObject<Block> toReturn = registerBlock(name, block);
        MFAItems.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties()));
        return toReturn;
    }
}
