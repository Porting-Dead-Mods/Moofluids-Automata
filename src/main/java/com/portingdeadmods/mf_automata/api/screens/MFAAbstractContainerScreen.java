package com.portingdeadmods.mf_automata.api.screens;

import com.portingdeadmods.mf_automata.api.screens.components.GuiComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class MFAAbstractContainerScreen<T extends MFAAbstractContainerMenu<?>> extends AbstractContainerScreen<T> {
    private GuiComponent[] components;

    public MFAAbstractContainerScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    public final void initComponents(GuiComponent... components) {
        this.components = components;
        for (GuiComponent component : this.components) {
            component.init(this);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if (components == null) return;

        for (GuiComponent component : components) {
            component.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(getBackgroundTexture(), x, y, 0, 0, imageWidth, imageHeight);

        if (components == null) return;

        for (GuiComponent component : components) {
            component.renderInBackground(guiGraphics, mouseX, mouseY, delta);
        }
    }

    public abstract @NotNull ResourceLocation getBackgroundTexture();
}
