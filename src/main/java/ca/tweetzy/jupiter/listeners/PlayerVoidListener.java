package ca.tweetzy.jupiter.listeners;

import ca.tweetzy.core.utils.nms.ActionBar;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 20 2021
 * Time Created: 11:15 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerVoidListener implements Listener {

    @EventHandler
    public void onPlayerTakeDamageFromVoid(EntityDamageEvent e) {
        if(!Settings.VOID_TP_ENABLED.getBoolean()) return;
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.VOID && e.getEntity().getLocation().getBlockY() >= 0) return;

        Player player = (Player) e.getEntity();
        if (!JupiterAPI.voidTeleportAPI().isWorldEnabled(player.getWorld().getName())) return;

        switch (JupiterAPI.voidTeleportAPI().getWorldVoidType(player.getWorld().getName())) {
            case DEATH:
                player.setHealth(0);
                player.setFoodLevel(0);
                break;
            case SPAWN:
                if (JupiterAPI.voidTeleportAPI().worldSpawnSet(player.getWorld().getName())) {
                    e.setCancelled(true);
                    player.teleport(JupiterAPI.voidTeleportAPI().getWorldSpawn(player.getWorld().getName()));

                    // messages void_actionbar_message
                    if (Settings.VOID_TP_MESSAGE_ENABLED.getBoolean()) {
                        Jupiter.getInstance().getLocale().getMessage("void_teleport.void_chat_message").sendPrefixedMessage(player);
                    }

                    if (Settings.VOID_TP_ACTIONBAR_ENABLED.getBoolean()) {
                        ActionBar.send(player, Jupiter.getInstance().getLocale().getMessage("void_teleport.void_actionbar_message").getMessage());
                    }
                }
                break;
        }
    }
}
