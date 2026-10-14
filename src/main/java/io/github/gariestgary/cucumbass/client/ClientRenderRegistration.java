package io.github.gariestgary.cucumbass.client;

import io.github.gariestgary.cucumbass.Cucumbass;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = Cucumbass.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientRenderRegistration {
    private static final ResourceLocation HUD_LAYER = ResourceLocation.fromNamespaceAndPath(Cucumbass.MOD_ID, "cucumber_count");
    private ClientRenderRegistration() { }

    @SubscribeEvent public static void registerModelLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PlayerCucumberRenderLayer.MODEL_LAYER, PlayerCucumberRenderLayer::createLayer);
    }

    @SubscribeEvent public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        ModelPart cucumber = event.getEntityModels().bakeLayer(PlayerCucumberRenderLayer.MODEL_LAYER).getChild("cucumber");
        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            if (renderer != null) renderer.addLayer(new PlayerCucumberRenderLayer(renderer, cucumber));
        }
    }

    @SubscribeEvent public static void registerHudLayer(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.HOTBAR, HUD_LAYER, CucumberHudLayer::render);
    }
}
