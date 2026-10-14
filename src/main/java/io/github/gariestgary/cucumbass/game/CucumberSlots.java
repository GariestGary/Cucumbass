package io.github.gariestgary.cucumbass.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/** Immutable bounded collection. Its capacity rules are independent from Minecraft APIs and unit-tested. */
public final class CucumberSlots<T> {
    private final int capacity;
    private final List<T> entries;

    private CucumberSlots(int capacity, List<T> entries) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must not be negative");
        if (entries.size() > capacity) throw new IllegalArgumentException("entries cannot exceed capacity");
        this.capacity = capacity;
        this.entries = List.copyOf(entries);
    }

    public static <T> CucumberSlots<T> of(int capacity, List<T> entries) { return new CucumberSlots<>(capacity, entries); }
    public static <T> CucumberSlots<T> empty(int capacity) { return new CucumberSlots<>(capacity, List.of()); }
    public int capacity() { return capacity; }
    public int usedSlots() { return entries.size(); }
    public int availableSlots() { return capacity - entries.size(); }
    public List<T> entries() { return entries; }

    public Optional<CucumberSlots<T>> tryAdd(T entry) {
        Objects.requireNonNull(entry, "entry");
        if (entries.size() == capacity) return Optional.empty();
        List<T> updated = new ArrayList<>(entries);
        updated.add(entry);
        return Optional.of(new CucumberSlots<>(capacity, updated));
    }

    public Optional<CucumberSlots<T>> removeFirst(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        List<T> updated = new ArrayList<>(entries);
        for (int index = 0; index < updated.size(); index++) {
            if (predicate.test(updated.get(index))) {
                updated.remove(index);
                return Optional.of(new CucumberSlots<>(capacity, updated));
            }
        }
        return Optional.empty();
    }
}
