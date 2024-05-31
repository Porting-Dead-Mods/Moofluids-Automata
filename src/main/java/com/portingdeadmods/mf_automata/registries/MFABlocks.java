package com.portingdeadmods.mf_automata.registries;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.registries.blocks.ExampleBlock;
import com.portingdeadmods.mf_automata.registries.blocks.MilkingMachineBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class MFABlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MFAutomata.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = registerBlockAndItem("example_block",
            () -> new ExampleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<Block> MILKING_MACHINE = registerBlockAndItem("milking_machine",
            () -> new MilkingMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)));

    private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        RegistryObject<Block> toReturn = BLOCKS.register(name, block);
        registerItemFromBlock(name, toReturn);
        return toReturn;
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> void registerItemFromBlock(String name, Supplier<T> block) {
        MFAItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
