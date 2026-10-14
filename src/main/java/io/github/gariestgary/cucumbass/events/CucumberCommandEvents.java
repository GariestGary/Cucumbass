package io.github.gariestgary.cucumbass.events;

import com.mojang.brigadier.CommandDispatcher;
import io.github.gariestgary.cucumbass.Cucumbass;
import io.github.gariestgary.cucumbass.config.CucumbassConfig;
import io.github.gariestgary.cucumbass.data.CucumberAttachmentTypes;
import io.github.gariestgary.cucumbass.data.CucumberPlayerData;
import io.github.gariestgary.cucumbass.game.CucumberService;
import io.github.gariestgary.cucumbass.game.InstallationResult;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Cucumbass.MOD_ID)
public final class CucumberCommandEvents {
    private CucumberCommandEvents() { }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("cucumbass")
                .requires(source -> source.hasPermission(2) && CucumbassConfig.ENABLE_DEBUG_COMMANDS.get())
                .then(Commands.literal("status").executes(context -> status(context.getSource())))
                .then(Commands.literal("debug")
                        .then(Commands.literal("insert").executes(context -> insert(context.getSource())))
                        .then(Commands.literal("clear").executes(context -> clear(context.getSource())))));
    }

    private static int status(CommandSourceStack source) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        CucumberPlayerData data = player.getData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get());
        String installed = data.slots().entries().isEmpty() ? "-" : data.slots().entries().stream()
                .map(cucumber -> cucumber.typeId().getPath() + " x" + cucumber.charges()).collect(Collectors.joining(", "));
        source.sendSuccess(() -> Component.translatable("command.cucumbass.status", data.slots().usedSlots(), data.slots().capacity(), data.availableSlots(), installed), false);
        return data.slots().usedSlots();
    }

    private static int insert(CommandSourceStack source) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        if (CucumberService.installBasic(player) == InstallationResult.NO_AVAILABLE_SLOT) {
            source.sendFailure(Component.translatable("command.cucumbass.debug.no_available_slot"));
            return 0;
        }
        source.sendSuccess(() -> Component.translatable("command.cucumbass.debug.inserted"), false);
        return 1;
    }

    private static int clear(CommandSourceStack source) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        CucumberService.clear(source.getPlayerOrException());
        source.sendSuccess(() -> Component.translatable("command.cucumbass.debug.cleared"), false);
        return 1;
    }
}
