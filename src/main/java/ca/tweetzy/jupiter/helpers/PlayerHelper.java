package ca.tweetzy.jupiter.helpers;

import ca.tweetzy.core.compatibility.ServerVersion;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 25 2021
 * Time Created: 5:03 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class PlayerHelper {

    public static ItemStack getHeldItem(Player player) {
        return ServerVersion.isServerVersionAbove(ServerVersion.V1_8) ? player.getInventory().getItemInHand() : player.getInventory().getItemInMainHand();
    }
}
