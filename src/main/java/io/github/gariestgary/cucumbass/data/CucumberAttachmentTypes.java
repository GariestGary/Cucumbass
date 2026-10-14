package io.github.gariestgary.cucumbass.data;

import io.github.gariestgary.cucumbass.Cucumbass;
import io.github.gariestgary.cucumbass.network.CucumberDataSync;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class CucumberAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Cucumbass.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<CucumberPlayerData>> PLAYER_CUCUMBERS = ATTACHMENTS.register("player_cucumbers", () -> AttachmentType
            .builder(CucumberPlayerData::empty)
            .serialize(new IAttachmentSerializer<CompoundTag, CucumberPlayerData>() {
                @Override public CucumberPlayerData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) { return CucumberPlayerData.load(tag); }
                @Override public CompoundTag write(CucumberPlayerData attachment, HolderLookup.Provider provider) { return attachment.save(); }
            })
            // This targets the owner and every client tracking the player entity.
            .sync((holder, player) -> true, CucumberDataSync.STREAM_CODEC)
            .build());
    private CucumberAttachmentTypes() { }
}
