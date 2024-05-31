package com.portingdeadmods.mf_automata.registries;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.registries.blockentities.ExampleBlockEntity;
import com.portingdeadmods.mf_automata.registries.blockentities.MilkingMachineBlockEntity;
import com.portingdeadmods.mf_automata.registries.blocks.MilkingMachineBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class MFABlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MFAutomata.MODID);

    public static final RegistryObject<BlockEntityType<?>> EXAMPLE_BE = BLOCK_ENTITY_TYPES.register("example_be",
            () -> BlockEntityType.Builder.of(ExampleBlockEntity::new, MFABlocks.EXAMPLE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> MILKING_MACHINE = BLOCK_ENTITY_TYPES.register("milking_machine",
            () -> BlockEntityType.Builder.of(MilkingMachineBlockEntity::new, MFABlocks.MILKING_MACHINE.get()).build(null));
}
