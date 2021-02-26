package ca.tweetzy.jupiter.listeners;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.nms.ActionBar;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: February 21 2021
 * Time Created: 2:37 AM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerTrampleListener implements Listener {

    @EventHandler
    public void onPlayerTrampleCrop(PlayerInteractEvent event) {
        if (!Settings.PLAYER_TRAMPLE_ENABLED.getBoolean()) return;
        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == XMaterial.FARMLAND.parseMaterial()) {
            Settings.PLAYER_TRAMPLE_IGNORED_GAMEMODES.getStringList().forEach(gamemode -> { if (player.getGameMode() == GameMode.valueOf(gamemode.toUpperCase())) return; });
            event.setCancelled(true);

            if (Settings.PLAYER_TRAMPLE_MESSAGE_ENABLED.getBoolean()) {
                Jupiter.getInstance().getLocale().getMessage("anti_crop_trample.chat_message").sendPrefixedMessage(player);
            }

            if (Settings.PLAYER_TRAMPLE_ACTIONBAR_ENABLED.getBoolean()) {
                ActionBar.send(player, Jupiter.getInstance().getLocale().getMessage("anti_crop_trample.chat_message").getMessage());
            }
        }
    }
}
