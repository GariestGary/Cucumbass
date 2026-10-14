package io.github.gariestgary.cucumbass.events;

import io.github.gariestgary.cucumbass.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public final class ModContentEvents {
    private ModContentEvents() { }

    public static void addCreativeItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.BASIC_CUCUMBER);
        }
    }
}

