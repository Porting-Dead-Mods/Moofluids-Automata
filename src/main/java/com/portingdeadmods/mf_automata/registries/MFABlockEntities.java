package com.portingdeadmods.mf_automata.registries;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.registries.blockentities.ExampleBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class MFABlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MFAutomata.MODID);

    public static final RegistryObject<BlockEntityType<?>> EXAMPLE_BE = registerBlockEntity("example_be",
            BlockEntityType.Builder.of(ExampleBlockEntity::new, MFABlocks.EXAMPLE_BLOCK.get()));

    private static RegistryObject<BlockEntityType<?>> registerBlockEntity(String name, BlockEntityType.Builder<?> builder) {
        return BLOCK_ENTITY_TYPES.register(name, () -> builder.build(null));
    }
}
