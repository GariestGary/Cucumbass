package io.github.gariestgary.cucumbass.client;

import io.github.gariestgary.cucumbass.data.CucumberAttachmentTypes;
import io.github.gariestgary.cucumbass.registry.ModItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/** Compact local-player HUD readout of the synchronized attachment. */
public final class CucumberHudLayer {
    private CucumberHudLayer() { }

    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui || minecraft.player == null || minecraft.level == null) return;
        int installed = minecraft.player.getData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get()).slots().usedSlots();
        if (installed == 0) return;
        int x = graphics.guiWidth() / 2 + 96;
        int y = graphics.guiHeight() - 42;
        graphics.renderItem(new ItemStack(ModItems.BASIC_CUCUMBER.get()), x, y);
        graphics.drawString(minecraft.font, Component.literal("×" + installed), x + 18, y + 4, 0xFFFFFF, true);
    }
}
