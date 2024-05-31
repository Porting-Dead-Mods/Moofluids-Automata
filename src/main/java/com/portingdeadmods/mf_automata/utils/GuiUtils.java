package com.portingdeadmods.mf_automata.utils;

import com.portingdeadmods.mf_automata.MFAutomata;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public final class GuiUtils {
    public static final ResourceLocation BATTERY_SLOT = new ResourceLocation(MFAutomata.MODID, "textures/gui/slots/battery_slot.png");

    public static Font getMCFont() {
        return Minecraft.getInstance().font;
    }

    public static void drawImg(GuiGraphics guiGraphics, ResourceLocation texturePath, int x, int y, int width, int height) {
        guiGraphics.blit(texturePath, x, y, 0, 0, 0, width, height, width, height);
    }

    public static void renderBatterySlot(GuiGraphics guiGraphics, int x, int y) {
        drawImg(guiGraphics, BATTERY_SLOT, x, y, 18, 18);
    }
}
