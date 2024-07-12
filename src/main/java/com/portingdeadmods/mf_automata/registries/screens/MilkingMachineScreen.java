package com.portingdeadmods.mf_automata.registries.screens;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.api.screens.MFAAbstractContainerScreen;
import com.portingdeadmods.mf_automata.registries.screens.components.EnergyGuiComponent;
import com.portingdeadmods.mf_automata.registries.screens.components.FluidTankGuiComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public class MilkingMachineScreen extends MFAAbstractContainerScreen<MilkingMachineMenu> {
    public MilkingMachineScreen(MilkingMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        this.imageHeight = 181;
        this.inventoryLabelY = imageHeight - 94;
        super.init();
        initComponents(
                new EnergyGuiComponent(new Vector2i(this.leftPos + 10, this.topPos + 16), true),
                new FluidTankGuiComponent(new Vector2i(this.width / 2, this.topPos + 32), FluidTankGuiComponent.TankVariants.SMALL)
        );
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, delta, mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return new ResourceLocation(MFAutomata.MODID, "textures/gui/example_gui.png");
    }
}
