package ca.tweetzy.jupiter.api.spawner;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.core.utils.items.ItemUtils;
import ca.tweetzy.core.utils.nms.NBTEditor;
import ca.tweetzy.jupiter.helpers.ConfigurationItemHelper;
import ca.tweetzy.jupiter.settings.Settings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 21 2021
 * Time Created: 4:41 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class SpawnerAPI {

    /**
     * Used to update the spawner entity type
     *
     * @param block is target spawner
     * @param type is the mob type
     */
    public void setSpawnerType(Block block, EntityType type) {
        if (block.getType() != XMaterial.SPAWNER.parseMaterial()) return;
        if (!type.isAlive() || !type.isSpawnable() || type.toString().contains("ARMOR")) return;

        BlockState state = block.getState();
        CreatureSpawner spawner = ((CreatureSpawner) state);
        spawner.setSpawnedType(type);
        state.update();
    }

    /**
     * Get the current mob being spawned from a spawner
     *
     * @param block is the target spawner
     * @return the mob type
     */
    public EntityType getSpawnerType(Block block) {
        if (block.getType() != XMaterial.SPAWNER.parseMaterial()) return EntityType.UNKNOWN;
        BlockState state = block.getState();
        CreatureSpawner spawner = ((CreatureSpawner) state);
        return spawner.getSpawnedType();
    }

    /**
     * Get an item stack of a specific spawner, used with the give command,
     * and used when a spawner is mined or exploded
     *
     * @param type is the mob type
     * @return an item stack with the correct mob type
     */
    public ItemStack getSpawnerItem(EntityType type) {
        ItemStack stack = ConfigurationItemHelper.build(Settings.SPAWNER_SYSTEM_SPAWNER_ITEM.getString(), Settings.SPAWNER_SYSTEM_SPAWNER_NAME.getString(), Settings.SPAWNER_SYSTEM_SPAWNER_LORE.getStringList(), new HashMap<String, Object>(){{
            put("%spawner_type%", TextUtils.formatText(type.name().toLowerCase().replace("_", " "), true));
        }});
        stack = NBTEditor.set(stack, type.name(), "JupiterSpawnerItemEntityType");
        return stack;
    }
}
