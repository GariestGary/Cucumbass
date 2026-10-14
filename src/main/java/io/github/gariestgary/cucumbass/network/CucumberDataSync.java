package io.github.gariestgary.cucumbass.network;

import io.github.gariestgary.cucumbass.data.CucumberPlayerData;
import io.github.gariestgary.cucumbass.data.InstalledCucumber;
import io.github.gariestgary.cucumbass.game.CucumberSlots;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/** Wire format used by the Data Attachment's built-in NeoForge synchronization. */
public final class CucumberDataSync {
    private static final int MAX_SYNCED_CUCUMBERS = 64;
    public static final StreamCodec<RegistryFriendlyByteBuf, CucumberPlayerData> STREAM_CODEC = new StreamCodec<>() {
        @Override public CucumberPlayerData decode(RegistryFriendlyByteBuf buffer) {
            int capacity = buffer.readVarInt();
            int count = buffer.readVarInt();
            List<InstalledCucumber> cucumbers = new ArrayList<>(Math.min(count, MAX_SYNCED_CUCUMBERS));
            for (int index = 0; index < count; index++) {
                ResourceLocation typeId = buffer.readResourceLocation();
                int charges = buffer.readVarInt();
                CompoundTag properties = buffer.readNbt();
                if (index < MAX_SYNCED_CUCUMBERS) cucumbers.add(new InstalledCucumber(typeId, Math.max(1, charges), properties == null ? new CompoundTag() : properties));
            }
            return new CucumberPlayerData(CucumberSlots.of(Math.max(capacity, cucumbers.size()), cucumbers));
        }
        @Override public void encode(RegistryFriendlyByteBuf buffer, CucumberPlayerData data) {
            buffer.writeVarInt(data.slots().capacity());
            buffer.writeVarInt(data.slots().usedSlots());
            for (InstalledCucumber cucumber : data.slots().entries()) {
                buffer.writeResourceLocation(cucumber.typeId());
                buffer.writeVarInt(cucumber.charges());
                buffer.writeNbt(cucumber.properties());
            }
        }
    };
    private CucumberDataSync() { }
}
