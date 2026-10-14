package io.github.gariestgary.cucumbass.data;

import io.github.gariestgary.cucumbass.game.CucumberSlots;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

/** Persistent, server-owned state for a single player. */
public record CucumberPlayerData(CucumberSlots<InstalledCucumber> slots) {
    public static final int INITIAL_SLOT_CAPACITY = 2;

    public CucumberPlayerData {
        if (slots == null) throw new IllegalArgumentException("slots must not be null");
    }

    public static CucumberPlayerData empty() { return new CucumberPlayerData(CucumberSlots.empty(INITIAL_SLOT_CAPACITY)); }
    public int availableSlots() { return slots.availableSlots(); }
    public Optional<CucumberPlayerData> tryInstall(InstalledCucumber cucumber) { return slots.tryAdd(cucumber).map(CucumberPlayerData::new); }
    public Optional<CucumberPlayerData> consumeFirst(Predicate<InstalledCucumber> predicate) { return slots.removeFirst(predicate).map(CucumberPlayerData::new); }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("SlotCapacity", slots.capacity());
        ListTag installed = new ListTag();
        for (InstalledCucumber cucumber : slots.entries()) installed.add(cucumber.save());
        tag.put("Installed", installed);
        return tag;
    }

    public static CucumberPlayerData load(CompoundTag tag) {
        int capacity = Math.max(0, tag.getInt("SlotCapacity"));
        if (capacity == 0) capacity = INITIAL_SLOT_CAPACITY;
        ListTag installed = tag.getList("Installed", CompoundTag.TAG_COMPOUND);
        List<InstalledCucumber> cucumbers = installed.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast)
                .map(InstalledCucumber::load).limit(capacity).toList();
        return new CucumberPlayerData(CucumberSlots.of(capacity, cucumbers));
    }
}
