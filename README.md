# chuchu-herdr

A [chuchu](https://github.com/jossephus/chuchu) plugin for [herdr](https://github.com/herdrdev/herdr), the terminal multiplexer for coding agents.

- **Session persistence:** herdr appears next to tmux and zmx when you add a host.
- **Native splits** (per host, on by default): chuchu draws herdr's workspaces, tabs and split panes with its own terminals instead of showing herdr's TUI.
  - Tap a pane to focus it; tap the focused pane to type.
  - The `+` menu splits right or down, closes the pane, or opens a new tab.
  - "take over" appears when another client (e.g. herdr on your desktop) holds a pane.
- **Switcher home:** the `⌂ herdr` chip shows agents that need you (done or blocked) first, then every workspace with its agent counts. Workspaces expand to jump straight to a tab or an agent.
- **Notifications:** when an agent finishes or needs input while chuchu is in the background. You can turn this off in chuchu › settings › plugins.

Everything runs over the tab's own SSH connection (extra exec channels), so there's no second login and nothing to install on the server besides herdr.

## Install

1. Install a chuchu build with the plugin API (chuchu PR #121, plugin API v1).
2. Build and install this plugin (below), or install a release APK.
3. In chuchu: settings › plugins › enable **herdr**, review the signing certificate, restart.
4. Edit a host, set session persistence to **herdr**. Under plugins, **native splits** chooses chuchu's splits (on) or herdr's own interface (off).

herdr must already be running a session on the host.

## Build

```sh
./gradlew assembleDebug
adb install -r build/outputs/apk/debug/chuchu-herdr-debug.apk
```

The plugin API comes from JitPack (`com.github.jossephus:chuchu`, pinned in `gradle/libs.versions.toml`). To build against a local chuchu checkout while changing both:

```sh
./gradlew assembleDebug -PchuchuDir=../chuchu
```

Keep Kotlin and the Compose BOM at or below what the targeted chuchu uses: chuchu provides the Compose runtime at runtime.

## Credits

The herdr integration was written by [@salemsayed](https://github.com/salemsayed) in [chuchu#69](https://github.com/jossephus/chuchu/pull/69): the protocol code, stream handling, split layout and switcher home, and most of the tests. This repo rebuilds it as a standalone plugin on chuchu's plugin API.

## License

MIT, see [LICENSE](LICENSE).
