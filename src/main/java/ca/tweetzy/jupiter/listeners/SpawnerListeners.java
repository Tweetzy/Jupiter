package ca.tweetzy.jupiter.listeners;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.ItemUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import ca.tweetzy.jupiter.events.SpawnerBreakEvent;
import ca.tweetzy.jupiter.events.SpawnerPlaceEvent;
import ca.tweetzy.jupiter.helpers.PlayerHelper;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 21 2021
 * Time Created: 9:11 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class SpawnerListeners  implements Listener {

    @EventHandler
    public void onSpawnerPlace(BlockPlaceEvent e) {
        Material spawnerMaterial = XMaterial.matchXMaterial(Settings.SPAWNER_SYSTEM_SPAWNER_ITEM.getString()).get().parseMaterial();
        Player player = e.getPlayer();

        if (e.getBlock().getType() != spawnerMaterial) return;
        if (PlayerHelper.getHeldItem(player).getType() != spawnerMaterial) return;
        if (!NBTEditor.contains(PlayerHelper.getHeldItem(player), "JupiterSpawnerItemEntityType")) return;

        // grab the entity type
        EntityType spawnerType = EntityType.valueOf(NBTEditor.getString(PlayerHelper.getHeldItem(player), "JupiterSpawnerItemEntityType"));

        if (!Settings.SPAWNER_SYSTEM_ALLOW_PLACE.getBoolean()) {
            Jupiter.getInstance().getLocale().getMessage("spawners.not_allowed_place").processPlaceholder("mob_type", TextUtils.formatText(spawnerType.name().toLowerCase().replace("_", " "), true)).sendPrefixedMessage(player);
            e.setCancelled(true);
        }

        if (Settings.SPAWNER_SYSTEM_NEED_PERMISSION_TO_PLACE.getBoolean() && !player.hasPermission("jupiter.spawners.place.*") || !player.hasPermission("jupiter.spawners.place." + spawnerType.name().toLowerCase().replace("_", ""))) {
            Jupiter.getInstance().getLocale().getMessage("spawners.not_allowed_place").processPlaceholder("mob_type", TextUtils.formatText(spawnerType.name().toLowerCase().replace("_", " "), true)).sendPrefixedMessage(player);
            e.setCancelled(true);
        }

        // place spawner and change type
        Block block = e.getBlockPlaced();

        SpawnerPlaceEvent spawnerPlaceEvent = new SpawnerPlaceEvent(player, block, spawnerType);
        Bukkit.getPluginManager().callEvent(spawnerPlaceEvent);
        if (spawnerPlaceEvent.isCancelled()) {
            e.setCancelled(true);
            return;
        }

        if (block.getType() != XMaterial.SPAWNER.parseMaterial()) {
            block.setType(XMaterial.SPAWNER.parseMaterial());
        }

        JupiterAPI.spawnerAPI().setSpawnerType(block, spawnerType);
        Jupiter.getInstance().getLocale().getMessage("spawners.placed_spawner").processPlaceholder("mob_type", TextUtils.formatText(spawnerType.name().toLowerCase().replace("_", " "), true)).sendPrefixedMessage(player);
    }

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (e.getBlock().getType() != XMaterial.SPAWNER.parseMaterial()) return;
        if (!Settings.SPAWNER_SYSTEM_ALLOW_BREAK.getBoolean()) return;

        EntityType spawnerType = JupiterAPI.spawnerAPI().getSpawnerType(e.getBlock());
        ItemStack heldItem = PlayerHelper.getHeldItem(player);

        if (Settings.SPAWNER_SYSTEM_NEED_PERMISSION_TO_BREAK.getBoolean() && !player.hasPermission("jupiter.spawners.break.*") || !player.hasPermission("jupiter.spawners.break." + spawnerType.name().toLowerCase().replace("_", ""))) {
            Jupiter.getInstance().getLocale().getMessage("spawners.not_allowed_mine").processPlaceholder("mob_type", TextUtils.formatText(spawnerType.name().toLowerCase().replace("_", " "), true)).sendPrefixedMessage(player);
            e.setCancelled(true);
        }

        if (Settings.SPAWNER_SYSTEM_REQUIRES_SILK.getBoolean()) {
            if (!Settings.SPAWNER_SYSTEM_ALLOWED_SILK_TOOLS.getStringList().contains(heldItem.getType().name())) {
                Jupiter.getInstance().getLocale().getMessage("spawners.invalid_mine_tool").sendPrefixedMessage(player);
                e.setCancelled(true);
            }

            // check if item has silk touch on it
           if (!heldItem.hasItemMeta() && Settings.SPAWNER_SYSTEM_BREAK_IF_SILK_NOT_USED.getBoolean()) {
               e.setDropItems(false);
               e.setExpToDrop(0);
           } else {
               if (heldItem.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                    dropSpawner(player, spawnerType, e);
               }
           }
        } else {
            dropSpawner(player, spawnerType, e);
        }
    }

    private void dropSpawner(Player player, EntityType spawnerType, BlockBreakEvent e) {
        SpawnerBreakEvent spawnerBreakEvent = new SpawnerBreakEvent(player, e.getBlock(), spawnerType);
        Bukkit.getPluginManager().callEvent(spawnerBreakEvent);
        if (spawnerBreakEvent.isCancelled()) {
            e.setExpToDrop(0);
            e.setDropItems(false);
            e.setCancelled(true);
            return;
        }

        e.setDropItems(false);
        e.setExpToDrop(0);
        player.getWorld().dropItemNaturally(e.getBlock().getLocation(), JupiterAPI.spawnerAPI().getSpawnerItem(spawnerType));
        Jupiter.getInstance().getLocale().getMessage("spawners.mined_spawner").processPlaceholder("mob_type", TextUtils.formatText(spawnerType.name().toLowerCase().replace("_", " "), true)).sendPrefixedMessage(player);
    }
}
