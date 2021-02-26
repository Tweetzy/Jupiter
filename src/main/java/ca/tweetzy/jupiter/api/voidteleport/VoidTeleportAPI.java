package ca.tweetzy.jupiter.api.voidteleport;

import ca.tweetzy.core.utils.Serialize;
import ca.tweetzy.jupiter.Jupiter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 20 2021
 * Time Created: 12:04 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class VoidTeleportAPI {

    /**
     * Used to add a world to the configuration file
     *
     * @param world is the world name being added
     * @return true if the world was added to the file
     */
    public boolean addWorld(String world) {
        try {
            Bukkit.getServer().getScheduler().runTaskAsynchronously(Jupiter.getInstance(), () -> {
                Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".name", world);
                Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".enabled", true);
                Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".void type", VoidTeleportType.DEATH.name());
                Jupiter.getInstance().getDataFile().save();
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Used to remove a world from the configuration file
     *
     * @param world is the world being added
     * @return true if the world was removed from the file
     */
    public boolean removeWorld(String world) {
        try {
            Bukkit.getServer().getScheduler().runTaskAsynchronously(Jupiter.getInstance(), () -> {
                Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".name", null);
                Jupiter.getInstance().getDataFile().save();
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Used to toggle a world from the void teleport list
     *
     * @param world is the world being targeted
     * @param enabled is whether the world should be set to enabled or not
     */
    public void toggleWorld(String world, boolean enabled) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Jupiter.getInstance(), () -> {
            Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".name", world);
            Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".enabled", enabled);
            Jupiter.getInstance().getDataFile().save();
        });
    }

    /**
     * Used to set the spawn location of a world
     *
     * @param world is the name of the world being targeted
     */
    public void setSpawn(String world, Location location) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Jupiter.getInstance(), () -> {
            Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".name", world);
            Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".spawn location", Serialize.location(location));
            Jupiter.getInstance().getDataFile().save();
        });
    }

    /**
     * Used to set the void mode of a world
     *
     * @param world is the name of the world being targeted
     * @param type is the void teleport type
     */
    public void setMode(String world, VoidTeleportType type) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(Jupiter.getInstance(), () -> {
            Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".name", world);
            Jupiter.getInstance().getDataFile().set("void teleport.worlds." + world + ".void type", type.name());
            Jupiter.getInstance().getDataFile().save();
        });
    }

    /**
     * Used to check if a world exists on file
     *
     * @param world is the world name being checked
     * @return true if the world is in the data file
     */
    public boolean worldExistOnFile(String world) {
        if (Jupiter.getInstance().getDataFile().getConfigurationSection("void teleport.worlds") != null) {
            return Jupiter.getInstance().getDataFile().getConfigurationSection("void teleport.worlds").getKeys(false).stream().anyMatch(key -> key.equals(world));
        }
        return false;
    }

    /**
     * Used to check if a world is enabled
     *
     * @param world is the world being checked
     * @return true if its enabled
     */
    public boolean isWorldEnabled(String world) {
        if (!worldExistOnFile(world)) return false;
        return Jupiter.getInstance().getDataFile().getBoolean("void teleport.worlds." + world + ".enabled");
    }

    /**
     * Get the void type for a world
     *
     * @param world is the world being checked
     * @return the VoidTeleportType
     */
    public VoidTeleportType getWorldVoidType(String world) {
        return VoidTeleportType.valueOf(Jupiter.getInstance().getDataFile().getString("void teleport.worlds." + world + ".void type").toUpperCase());
    }

    /**
     * Check whether the world has a spawn set
     *
     * @param world is the world name
     * @return true if a spawn point is set
     */
    public boolean worldSpawnSet(String world) {
        return Jupiter.getInstance().getDataFile().contains("void teleport.worlds." + world + ".spawn location");
    }

    /**
     * Used to get the spawn location of a world
     *
     * @param world is the world name
     * @return the void spawn location
     */
    public Location getWorldSpawn(String world) {
        return Serialize.deserializeLocation(Jupiter.getInstance().getDataFile().getString("void teleport.worlds." + world + ".spawn location"));
    }
}
