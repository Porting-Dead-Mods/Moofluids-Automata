package com.portingdeadmods.mf_automata;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = MFAutomata.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class MFAConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MILKING_MACHINE_ENERGY_CAPACITY = BUILDER
            .comment("Amount of energy that can be stored in the milking machine")
            .defineInRange("milkingMachineEnergyCapacity", 8000, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue MILKING_MACHINE_ENERGY_USAGE = BUILDER
            .comment("Amount of energy used by the milking machine for each operation")
            .defineInRange("milkingMachineEnergyUsage", 20, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int milkingMachineEnergyUsage;
    public static int milkingMachineEnergyCapacity;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        milkingMachineEnergyUsage = MILKING_MACHINE_ENERGY_USAGE.get();
        milkingMachineEnergyCapacity = MILKING_MACHINE_ENERGY_CAPACITY.get();
    }
}
