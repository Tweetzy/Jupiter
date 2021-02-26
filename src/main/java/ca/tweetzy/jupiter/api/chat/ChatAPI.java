package ca.tweetzy.jupiter.api.chat;

import ca.tweetzy.core.utils.Serialize;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *  The current file has been created by Matt Wiggins
 *  Date Created: February 21 2021
 *  Time Created: 6:16 PM
 *  Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */public class ChatAPI {

    /**
     * Used to check if chat is enabled
     *
     * @return true if its enabled
     */
    public boolean isChatEnabled() {
        return Jupiter.getInstance().getDataFile().getBoolean("chat management.chat.enabled");
    }

    /**
     * Used to toggle the server chat
     *
     * @param value is the toggle value
     */
    public void toggleChat(boolean value) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Jupiter.getInstance(), () -> {
            Jupiter.getInstance().getDataFile().set("chat management.chat.enabled", value);
            Jupiter.getInstance().getDataFile().save();
        });
    }

    /**
     * Used to check if player can bypass the chat clear/toggle
     *
     * @param player is the player being checked
     * @return true if player can bypass
     */
    public boolean canBypassChat(Player player) {
        return player.hasPermission("jupiter.cmd.chat.bypass");
    }

    /**
     * Used to clear the chat of all online players
     */
    public void clearChat() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!canBypassChat(player)) {
                for (int i = 0; i < 1000; i++) player.sendMessage("");
            }
            Jupiter.getInstance().getLocale().getMessage("chat_management.chat_cleared").sendPrefixedMessage(player);
        });
    }

    /**
     * Used to clear the chat of all online players
     *
     * @param reason is the reason the sender cleared the chat
     */
    public void clearChat(String reason) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!canBypassChat(player)) {
                for (int i = 0; i < 1000; i++) player.sendMessage("");
            }
            Jupiter.getInstance().getLocale().getMessage("chat_management.chat_cleared_reason").processPlaceholder("reason", reason).sendPrefixedMessage(player);
        });
    }
}
