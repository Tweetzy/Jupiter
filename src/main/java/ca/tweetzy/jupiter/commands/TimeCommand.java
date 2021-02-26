package ca.tweetzy.jupiter.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.jupiter.Jupiter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: February 19 2021
 * Time Created: 11:23 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class TimeCommand extends AbstractCommand {

    public TimeCommand() {
        super(CommandType.PLAYER_ONLY, "time");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        Player player = (Player) sender;

        if (args.length == 1) {
            switch (args[0].toUpperCase()) {
                case "DAY":
                    player.getLocation().getWorld().setTime(0);
                    Jupiter.getInstance().getLocale().getMessage("time.timeset").processPlaceholder("value", "day").sendPrefixedMessage(player);
                    break;

                case "NIGHT":
                    player.getLocation().getWorld().setTime(14000);
                    Jupiter.getInstance().getLocale().getMessage("time.timeset").processPlaceholder("value", "night").sendPrefixedMessage(player);
                    break;

                default:
                    if (NumberUtils.isInt(args[0])) {
                        player.getLocation().getWorld().setTime(Integer.parseInt(args[0]));
                        Jupiter.getInstance().getLocale().getMessage("time.timeset").processPlaceholder("value", args[0]).sendPrefixedMessage(player);
                    }
                    break;
            }
            return ReturnType.SUCCESS;
        }

        if (args.length == 2) {
            if (Bukkit.getWorlds().stream().noneMatch(world -> world.getName().equals(args[1]))) {
                Jupiter.getInstance().getLocale().getMessage("time.invalid_world").processPlaceholder("world_name", args[1]).sendPrefixedMessage(player);
                return ReturnType.FAILURE;
            }

            switch (args[0].toUpperCase()) {
                case "DAY":
                    player.getLocation().getWorld().setTime(0);
                    Jupiter.getInstance().getLocale().getMessage("time.timeset").processPlaceholder("time", "day").sendPrefixedMessage(player);
                    break;

                case "NIGHT":
                    player.getLocation().getWorld().setTime(14000);
                    Jupiter.getInstance().getLocale().getMessage("time.timeset").processPlaceholder("time", "night").sendPrefixedMessage(player);
                    break;

                default:
                    if (NumberUtils.isInt(args[0])) {
                        player.getLocation().getWorld().setTime(Integer.parseInt(args[0]));
                        Jupiter.getInstance().getLocale().getMessage("time.timeset").processPlaceholder("time", args[0]).sendPrefixedMessage(player);
                    }
                    break;
            }
            return ReturnType.SUCCESS;
        }

        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.time";
    }

    @Override
    public String getSyntax() {
        return "/time <day|night|#> [world]";
    }

    @Override
    public String getDescription() {
        return "Set the time of day";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Arrays.asList("DAY", "NIGHT");
        if (args.length == 2) return Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
        return null;
    }
}
