package io.github.gariestgary.cucumbass.events;

import io.github.gariestgary.cucumbass.Cucumbass;
import io.github.gariestgary.cucumbass.game.CucumberService;
import io.github.gariestgary.cucumbass.game.InstallationResult;
import io.github.gariestgary.cucumbass.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Cucumbass.MOD_ID)
public final class CucumberGameplayEvents {
    private CucumberGameplayEvents() { }

    @SubscribeEvent
    public static void installOnPlayer(PlayerInteractEvent.EntityInteract event) {
        if (event.getLevel().isClientSide() || event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!(event.getEntity() instanceof ServerPlayer installer) || !(event.getTarget() instanceof ServerPlayer target)) return;
        if (!event.getItemStack().is(ModItems.BASIC_CUCUMBER.get())) return;

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        if (installer == target) return;

        InstallationResult result = CucumberService.installBasic(target);
        if (result == InstallationResult.NO_AVAILABLE_SLOT) return;

        if (!installer.getAbilities().instabuild) event.getItemStack().shrink(1);
    }

    @SubscribeEvent
    public static void reduceNextPositiveDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide() || !(event.getEntity() instanceof ServerPlayer player)) return;
        if (event.getNewDamage() <= 0.0F || !CucumberService.consumeBasicForPositiveDamage(player)) return;
        event.setNewDamage(event.getNewDamage() * 0.5F);
    }
}
