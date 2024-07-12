package com.portingdeadmods.mf_automata.registries.screens.components;

import com.portingdeadmods.mf_automata.MFAutomata;
import com.portingdeadmods.mf_automata.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.mf_automata.api.screens.FluidTankRenderer;
import com.portingdeadmods.mf_automata.api.screens.components.TooltipGuiComponent;
import com.portingdeadmods.mf_automata.utils.GuiUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import java.util.List;

public class FluidTankGuiComponent extends TooltipGuiComponent {
    private static final ResourceLocation SMALL_TANK = new ResourceLocation(MFAutomata.MODID, "textures/gui/sprites/small_tank.png");
    private static final ResourceLocation NORMAL_TANK = new ResourceLocation(MFAutomata.MODID, "textures/gui/sprites/normal_tank.png");
    private static final ResourceLocation LARGE_TANK = new ResourceLocation(MFAutomata.MODID, "textures/gui/sprites/large_tank.png");

    private final TankVariants variant;
    private FluidTankRenderer renderer;

    public FluidTankGuiComponent(@NotNull Vector2i position, TankVariants variant) {
        super(position);
        this.variant = variant;
    }

    @Override
    protected void onInit() {
        super.onInit();
        // Need to initialize it here, since in the constructor we can't access the screen yet
        this.renderer = new FluidTankRenderer(screen.getMenu().getBlockEntity().getFluidCapacity(), true, textureWidth()-2, textureHeight()-2);
    }

    @Override
    public List<Component> getTooltip() {
        ContainerBlockEntity blockEntity = this.screen.getMenu().getBlockEntity();
        return renderer.getTooltip(blockEntity.getFluid());
    }

    @Override
    public int textureWidth() {
        return variant.textureWidth;
    }

    @Override
    public int textureHeight() {
        return variant.textureHeight;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderer.render(guiGraphics.pose(), position.x + 1, position.y + 1, screen.getMenu().getBlockEntity().getFluid());
    }

    @Override
    public void renderInBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.renderInBackground(guiGraphics, mouseX, mouseY, delta);
        GuiUtils.drawImg(guiGraphics, variant.location, position.x, position.y, textureWidth(), textureHeight());
    }

    public enum TankVariants {
        SMALL(18, 54, SMALL_TANK),
        NORMAL(36, 54, NORMAL_TANK),
        LARGE(54, 54, LARGE_TANK);

        final int textureWidth;
        final int textureHeight;
        final ResourceLocation location;

        TankVariants(int textureWidth, int textureHeight, ResourceLocation location) {
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
            this.location = location;
        }
    }
}
