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
public class VoidTeleportToggleCommand extends AbstractCommand {

    public VoidTeleportToggleCommand() {
        super(CommandType.PLAYER_ONLY, "toggle");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            if(!JupiterAPI.voidTeleportAPI().worldExistOnFile(player.getWorld().getName())) {
                JupiterAPI.voidTeleportAPI().addWorld(player.getWorld().getName());
            }

            if (JupiterAPI.voidTeleportAPI().isWorldEnabled(player.getWorld().getName())) {
                JupiterAPI.voidTeleportAPI().toggleWorld(player.getWorld().getName(), false);
                Jupiter.getInstance().getLocale().getMessage("void_teleport.toggle_off").processPlaceholder("world_name", player.getWorld().getName()).sendPrefixedMessage(player);
            } else {
                JupiterAPI.voidTeleportAPI().toggleWorld(player.getWorld().getName(), true);
                Jupiter.getInstance().getLocale().getMessage("void_teleport.toggle_on").processPlaceholder("world_name", player.getWorld().getName()).sendPrefixedMessage(player);
            }
            return ReturnType.SUCCESS;
        }

        if (args.length == 2) {
            if (Bukkit.getWorlds().stream().noneMatch(world -> world.getName().equals(args[1]))) {
                Jupiter.getInstance().getLocale().getMessage("void_teleport.invalid_world").processPlaceholder("world_name", args[1]).sendPrefixedMessage(player);
                return ReturnType.FAILURE;
            }

            if(!JupiterAPI.voidTeleportAPI().worldExistOnFile(args[1])) {
                JupiterAPI.voidTeleportAPI().addWorld(args[1]);
            }

            if (JupiterAPI.voidTeleportAPI().isWorldEnabled(args[1])) {
                JupiterAPI.voidTeleportAPI().toggleWorld(args[1], false);
                Jupiter.getInstance().getLocale().getMessage("void_teleport.toggle_off").processPlaceholder("world_name", args[1]).sendPrefixedMessage(player);
            } else {
                JupiterAPI.voidTeleportAPI().toggleWorld(args[1], true);
                Jupiter.getInstance().getLocale().getMessage("void_teleport.toggle_on").processPlaceholder("world_name", args[1]).sendPrefixedMessage(player);
            }
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
        return "jupiter.cmd.voidteleport.toggle";
    }

    @Override
    public String getSyntax() {
        return "toggle [world]";
    }

    @Override
    public String getDescription() {
        return "Used to enabled/disable void teleport on the world";
    }
}
