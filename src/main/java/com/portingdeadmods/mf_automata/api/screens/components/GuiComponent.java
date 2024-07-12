package com.portingdeadmods.mf_automata.api.screens.components;

import com.portingdeadmods.mf_automata.api.screens.MFAAbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

public abstract class GuiComponent {
    public final @NotNull Vector2i position;
    protected @Nullable MFAAbstractContainerScreen<?> screen;

    public GuiComponent(@NotNull Vector2i position) {
        this.position = position;
    }

    public void init(@NotNull MFAAbstractContainerScreen<?> screen) {
        this.screen = screen;
        onInit();
    }

    protected void onInit() {
    }

    public final boolean isShiftKeyDown() {
        return Screen.hasShiftDown();
    }

    public abstract int textureWidth();

    public abstract int textureHeight();

    public abstract void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta);

    public void renderInBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
    }
}
