package ca.tweetzy.jupiter.listeners;

import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.jupiter.api.JupiterAPI;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: February 19 2021
 * Time Created: 11:23 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Settings.PLAYER_JOIN_MESSAGE_ENABLED.getBoolean()) {
            player.sendMessage(TextUtils.formatText(Settings.PLAYER_JOIN_MESSAGE.getString()).replace("%player%", player.getName()));
        }
    }
}
