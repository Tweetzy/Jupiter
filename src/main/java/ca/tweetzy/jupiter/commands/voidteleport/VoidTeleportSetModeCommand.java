package ca.tweetzy.jupiter.commands.voidteleport;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import ca.tweetzy.jupiter.api.voidteleport.VoidTeleportType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 20 2021
 * Time Created: 11:33 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class VoidTeleportSetModeCommand extends AbstractCommand {

    public VoidTeleportSetModeCommand() {
        super(CommandType.PLAYER_ONLY, "setmode");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length < 1) return ReturnType.SYNTAX_ERROR;

        Player player = (Player) sender;

        if (args.length == 1) {
            switch (args[0].toUpperCase()) {
                case "DEATH":
                case "SPAWN":
                    JupiterAPI.voidTeleportAPI().setMode(player.getWorld().getName(), VoidTeleportType.valueOf(args[0].toUpperCase()));
                    Jupiter.getInstance().getLocale().getMessage("void_teleport.set_mode")
                            .processPlaceholder("void_type", VoidTeleportType.valueOf(args[0].toUpperCase()).getType())
                            .processPlaceholder("world_name", player.getWorld().getName())
                            .sendPrefixedMessage(player);
                    return ReturnType.SUCCESS;
                default:
                    Jupiter.getInstance().getLocale().getMessage("void_teleport.invalid_mode").sendPrefixedMessage(player);
                    return ReturnType.FAILURE;
            }
        }

        if (args.length == 2) {
            switch (args[0].toUpperCase()) {
                case "DEATH":
                case "SPAWN":
                    if (Bukkit.getWorlds().stream().noneMatch(world -> world.getName().equals(args[1]))) {
                        Jupiter.getInstance().getLocale().getMessage("void_teleport.invalid_world").processPlaceholder("world_name", args[1]).sendPrefixedMessage(player);
                        return ReturnType.FAILURE;
                    }

                    JupiterAPI.voidTeleportAPI().setMode(args[1], VoidTeleportType.valueOf(args[0].toUpperCase()));
                    Jupiter.getInstance().getLocale().getMessage("void_teleport.set_mode")
                            .processPlaceholder("void_type", VoidTeleportType.valueOf(args[0].toUpperCase()).getType())
                            .processPlaceholder("world_name", args[1])
                            .sendPrefixedMessage(player);
                    return ReturnType.SUCCESS;
                default:
                    Jupiter.getInstance().getLocale().getMessage("void_teleport.invalid_mode").sendPrefixedMessage(player);
                    return ReturnType.FAILURE;
            }
        }

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Arrays.asList("DEATH", "SPAWN");
        if (args.length == 2) return Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.voidteleport.setmode";
    }

    @Override
    public String getSyntax() {
        return "setmode <mode> [world]";
    }

    @Override
    public String getDescription() {
        return "Used to to set the void mode for the world";
    }
}
