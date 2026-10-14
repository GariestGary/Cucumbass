package io.github.gariestgary.cucumbass.registry;

import io.github.gariestgary.cucumbass.Cucumbass;
import io.github.gariestgary.cucumbass.item.BasicCucumberItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Cucumbass.MOD_ID);
    public static final DeferredItem<Item> BASIC_CUCUMBER = ITEMS.registerItem("basic_cucumber", BasicCucumberItem::new);
    private ModItems() { }
}
