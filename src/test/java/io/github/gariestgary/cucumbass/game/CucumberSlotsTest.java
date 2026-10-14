package io.github.gariestgary.cucumbass.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CucumberSlotsTest {
    @Test
    void cannotAddMoreCucumbersThanCapacity() {
        CucumberSlots<String> slots = CucumberSlots.<String>empty(2).tryAdd("basic").orElseThrow().tryAdd("basic").orElseThrow();
        assertEquals(0, slots.availableSlots());
        assertTrue(slots.tryAdd("basic").isEmpty());
    }

    @Test
    void removesOnlyOneMatchingCucumber() {
        CucumberSlots<String> slots = CucumberSlots.of(3, java.util.List.of("basic", "basic", "other"));
        CucumberSlots<String> afterUse = slots.removeFirst("basic"::equals).orElseThrow();
        assertEquals(java.util.List.of("basic", "other"), afterUse.entries());
    }
}
