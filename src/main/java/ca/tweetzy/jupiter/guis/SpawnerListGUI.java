package ca.tweetzy.jupiter.guis;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.inventory.TInventory;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.jupiter.api.JupiterAPI;
import ca.tweetzy.jupiter.helpers.ConfigurationItemHelper;
import ca.tweetzy.jupiter.managers.SoundManager;
import ca.tweetzy.jupiter.settings.Settings;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 21 2021
 * Time Created: 4:29 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class SpawnerListGUI extends TInventory {

    private final List<List<EntityType>> items;

    public SpawnerListGUI() {
        this.items = Lists.partition(Arrays.stream(EntityType.values()).filter(type -> type.isSpawnable() && type.isAlive() && !type.toString().contains("ARMOR")).sorted().collect(Collectors.toList()), 28);
        setTitle(Settings.GUI_SPAWNER_LIST_TITLE.getString());
        setPage(1);
        setRows(6);
    }

    @Override
    public void onClick(InventoryClickEvent e, int slot) {
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);
        switch (slot) {
            case 48:
                if (getPage() > 1) {
                    p.openInventory(setPage(getPage() - 1).getInventory());
                    SoundManager.getInstance().playSound(p, Settings.SOUNDS_NAVIGATE_GUI_PAGES.getString(), 1f, 1f);
                }
                break;
            case 50:
                if (getPage() < this.items.size()) {
                    p.openInventory(setPage(getPage() + 1).getInventory());
                    SoundManager.getInstance().playSound(p, Settings.SOUNDS_NAVIGATE_GUI_PAGES.getString(), 1f, 1f);
                }
                break;
            default:
                ItemStack clickedItem = e.getCurrentItem();
                if (clickedItem.getType() == XMaterial.AIR.parseMaterial()) return;
                if (!NBTEditor.contains(clickedItem, "JupiterSpawnerItemEntityType")) return;
                if (!p.hasPermission("jupiter.spawners.takefromlistgui")) return;

                if (e.getClick() == ClickType.SHIFT_LEFT)  {
                    EntityType type = EntityType.valueOf(NBTEditor.getString(clickedItem, "JupiterSpawnerItemEntityType"));
                    PlayerUtils.giveItem(p, JupiterAPI.spawnerAPI().getSpawnerItem(type));
                }

                break;
        }
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle());

        if (Settings.GUI_SPAWNER_LIST_USE_BORDER.getBoolean()) {
            fillRows(inventory, ConfigurationItemHelper.build(Settings.GUI_SPAWNER_LIST_BORDER_ITEM.getString(), Settings.GUI_SPAWNER_LIST_BORDER_NAME.getString(), Settings.GUI_SPAWNER_LIST_BORDER_LORE.getStringList(), null), 1, 6);
            multiMirrorFill(inventory, ConfigurationItemHelper.build(Settings.GUI_SPAWNER_LIST_BORDER_ITEM.getString(), Settings.GUI_SPAWNER_LIST_BORDER_NAME.getString(), Settings.GUI_SPAWNER_LIST_BORDER_LORE.getStringList(), null), 9, 18, 27, 36);
        }

        inventory.setItem(48, ConfigurationItemHelper.build(Settings.GUI_BACK_BTN_ITEM.getString(), Settings.GUI_BACK_BTN_NAME.getString(), Settings.GUI_BACK_BTN_LORE.getStringList(), null));
        inventory.setItem(50, ConfigurationItemHelper.build(Settings.GUI_NEXT_BTN_ITEM.getString(), Settings.GUI_NEXT_BTN_NAME.getString(), Settings.GUI_NEXT_BTN_LORE.getStringList(), null));

        // fill in the spawners
        if (items.size() != 0) {
            items.get(getPage() - 1).forEach(entity -> inventory.setItem(inventory.firstEmpty(), JupiterAPI.spawnerAPI().getSpawnerItem(entity)));
        }

        return inventory;
    }
}
