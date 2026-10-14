package io.github.gariestgary.cucumbass.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class CucumbassConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue ENABLE_DEBUG_COMMANDS = BUILDER
            .comment("Enables /cucumbass developer commands. Disable this on production servers.")
            .define("enableDebugCommands", true);
    public static final ModConfigSpec SPEC = BUILDER.build();
    private CucumbassConfig() { }
}
