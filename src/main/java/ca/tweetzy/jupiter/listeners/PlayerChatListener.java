package ca.tweetzy.jupiter.listeners;

import ca.tweetzy.core.utils.nms.ActionBar;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: February 21 2021
 * Time Created: 7:54 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(JupiterAPI.chatAPI().canBypassChat(event.getPlayer())) return;

        Player player = event.getPlayer();
        if (!JupiterAPI.chatAPI().isChatEnabled()) {
            event.setCancelled(true);

            if (Settings.CHAT_MANAGEMENT_CHAT_MESSAGE_ENABLED.getBoolean()) {
                Jupiter.getInstance().getLocale().getMessage("chat_management.cannot_talk").sendPrefixedMessage(player);
            }

            if (Settings.CHAT_MANAGEMENT_ACTIONBAR_MESSAGE_ENABLED.getBoolean()) {
                ActionBar.send(player, Jupiter.getInstance().getLocale().getMessage("chat_management.cannot_talk").getMessage());
            }
        }
    }
}
