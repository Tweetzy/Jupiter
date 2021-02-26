package ca.tweetzy.jupiter.listeners;

import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: February 19 2021
 * Time Created: 11:23 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Settings.PLAYER_QUIT_MESSAGE_ENABLED.getBoolean()) {
            player.sendMessage(TextUtils.formatText(Settings.PLAYER_QUIT_MESSAGE.getString()).replace("%player%", player.getName()));
        }
    }
}
