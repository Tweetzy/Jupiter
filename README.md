#      Jupiter
[![Jupiter Build](https://github.com/kiranhart/Jupiter/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/kiranhart/Jupiter/actions/workflows/maven.yml) [![](https://jitpack.io/v/kiranhart/Jupiter.svg)](https://jitpack.io/#kiranhart/Jupiter)

Jupiter is an all-in-one server core, it's an attempt to reduce the need of downloading a variety of plugins to reach a goal. Jupiter is bundled with many features that most servers are looking for. 

<h3 align=left>Available Commands</h3>

| Commands | Description |
| --- | ---|
| `/heal [player]` | Used to heal yourself or another player |
| `/time  <day/night/#> [world]` | Used to set the current time |
| `/tp <player> [toPlayer]` | Used to teleport yourself to a player or player to player |
| `/tpall [player]` | Used to teleport all players to you or another player |
| `/voidtp` | Main command for the void teleport settings |
| `/spawner` | Main command for the spawner system |


<h3 align=left>Available Developer Events</h3>

| Custom Events | Description |
| --- | --- |
| `PlayerCompleteCaptchaEvent` | Called when a player completes a captcha challenge |
| `PlayerFailCaptchaEvent` | Called when a player fails to complete a captcha challenge |
| `VaultOpenEvent` | Called when a player opens a /pv vault |
| `VaultCloseEvent` | Called when a player closes a /pv vault |
| `VaultRenameEvent` | Called when a player renames a /pv vault |
| `SpawnerBreakEvent` | Called when a player breaks a mob spawner |
| `SpawnerPlaceEvent` | Called when a player places a mob spawner |
| `SpawnerChangeEvent` | Called when a player changes the mob type of a spawner |

<h3 align=left>Completion List</h3>

| Feature | Done By | Status |
| --- | --- | --- |
| Join / Leave Messages | Matt | ✅ |
| Void Fall Protection | Kiran | ✅ |
| Anti Trample | Matt | ✅ |
| Chat Management | Matt | ✅ |
| Fishing Rewards | Matt | ❌ |
| Spawner System | Kiran | ✅ |
| Captcha |  | ❌ |
| Report System |  | ❌ |
| Player Message Search |  | ❌ |
| Discord Bot |  | ❌ |