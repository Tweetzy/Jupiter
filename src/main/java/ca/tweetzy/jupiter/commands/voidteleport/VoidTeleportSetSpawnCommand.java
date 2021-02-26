package ca.tweetzy.jupiter.commands.voidteleport;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 20 2021
 * Time Created: 11:33 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class VoidTeleportSetSpawnCommand extends AbstractCommand {

    public VoidTeleportSetSpawnCommand() {
        super(CommandType.PLAYER_ONLY, "setspawn");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            JupiterAPI.voidTeleportAPI().setSpawn(player.getWorld().getName(), player.getLocation());
            Jupiter.getInstance().getLocale().getMessage("void_teleport.set_spawn")
                    .processPlaceholder("world_name", player.getWorld().getName())
                    .sendPrefixedMessage(player);
            return ReturnType.SUCCESS;
        }

        if (args.length == 2) {
            if (Bukkit.getWorlds().stream().noneMatch(world -> world.getName().equals(args[1]))) {
                Jupiter.getInstance().getLocale().getMessage("void_teleport.invalid_world").processPlaceholder("world_name", args[1]).sendPrefixedMessage(player);
                return ReturnType.FAILURE;
            }

            JupiterAPI.voidTeleportAPI().setSpawn(args[1], player.getLocation());
            Jupiter.getInstance().getLocale().getMessage("void_teleport.set_spawn")
                    .processPlaceholder("world_name", args[1])
                    .sendPrefixedMessage(player);
            return ReturnType.SUCCESS;
        }

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.voidteleport.setspawn";
    }

    @Override
    public String getSyntax() {
        return "setspawn [world]";
    }

    @Override
    public String getDescription() {
        return "Used to the set the void spawn for the world";
    }
}
