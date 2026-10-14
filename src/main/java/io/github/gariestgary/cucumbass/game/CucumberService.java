package io.github.gariestgary.cucumbass.game;

import io.github.gariestgary.cucumbass.data.CucumberAttachmentTypes;
import io.github.gariestgary.cucumbass.data.CucumberPlayerData;
import io.github.gariestgary.cucumbass.data.InstalledCucumber;
import net.minecraft.server.level.ServerPlayer;

/** Server-only state transitions for installing and consuming cucumbers. */
public final class CucumberService {
    private CucumberService() { }

    public static InstallationResult installBasic(ServerPlayer target) {
        CucumberPlayerData current = target.getData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get());
        return current.tryInstall(InstalledCucumber.basic())
                .map(updated -> {
                    target.setData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get(), updated);
                    return InstallationResult.INSTALLED;
                })
                .orElse(InstallationResult.NO_AVAILABLE_SLOT);
    }

    public static boolean consumeBasicForPositiveDamage(ServerPlayer player) {
        CucumberPlayerData current = player.getData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get());
        return current.consumeFirst(cucumber -> cucumber.typeId().equals(InstalledCucumber.BASIC_TYPE))
                .map(updated -> {
                    player.setData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get(), updated);
                    return true;
                })
                .orElse(false);
    }

    public static void clear(ServerPlayer player) {
        CucumberPlayerData current = player.getData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get());
        player.setData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get(), new CucumberPlayerData(CucumberSlots.empty(current.slots().capacity())));
    }
}

