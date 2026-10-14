# Cucumbass — technical rules

- Target only Minecraft `1.21.1`, NeoForge `21.1.259`, Java `21`, and ModDevGradle. Do not change these versions without an explicit request.
- Base Java package is `io.github.gariestgary.cucumbass`; mod id is `cucumbass`.
- Keep gameplay authority on the logical server. Client code may display synced state but must not change cucumber state.
- Player cucumber state is stored in `CucumberAttachmentTypes.PLAYER_CUCUMBERS`. Update it immutably with `player.setData(...)`, rather than mutating a retrieved value.
- The attachment is persistent and uses NeoForge built-in synchronization. Its sync predicate intentionally includes the owner and all entity trackers; preserve this when changing data fields.
- Data is not copied on death. Do not enable `copyOnDeath()` unless the gameplay design changes.
- Keep the split between registry, `data`, `game`, `events`, `network`, and `client` packages. Place client-only classes under `client` and guard them with `Dist.CLIENT`.
- Do not add Mixins unless a NeoForge event or public API demonstrably cannot support the feature.
- Developer commands are operator-only and controlled by `enableDebugCommands` in the common config; set it to `false` for production servers.
- Add or update unit tests for pure game rules. Run `gradlew.bat build` before handoff; use a dedicated-server run to check side safety when practical.
- Do not create Git commits unless the user explicitly asks.
