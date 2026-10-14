package io.github.gariestgary.cucumbass;

import com.mojang.logging.LogUtils;
import io.github.gariestgary.cucumbass.config.CucumbassConfig;
import io.github.gariestgary.cucumbass.data.CucumberAttachmentTypes;
import io.github.gariestgary.cucumbass.events.ModContentEvents;
import io.github.gariestgary.cucumbass.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(Cucumbass.MOD_ID)
public final class Cucumbass {
    public static final String MOD_ID = "cucumbass";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Cucumbass(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modEventBus);
        modEventBus.addListener(ModContentEvents::addCreativeItems);
        CucumberAttachmentTypes.ATTACHMENTS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, CucumbassConfig.SPEC);
    }
}

