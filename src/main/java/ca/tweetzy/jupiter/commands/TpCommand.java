package ca.tweetzy.jupiter.commands;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.jupiter.Jupiter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 19 2021
 * Time Created: 11:37 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class TpCommand extends AbstractCommand {

    public TpCommand() {
        super(CommandType.PLAYER_ONLY, "tp");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length <= 0) return ReturnType.SYNTAX_ERROR;
        Player player = (Player) sender;

        Player target = PlayerUtils.findPlayer(args[0]);
        if (args.length == 1) {
            if (target != null) {
                Jupiter.getInstance().getLocale().getMessage("teleport").processPlaceholder("player", args[0]).sendPrefixedMessage(target);
                player.teleport(target.getLocation());
            } else {
                Jupiter.getInstance().getLocale().getMessage("general.playeroffline").processPlaceholder("player", args[0]).sendPrefixedMessage(sender);
            }
            return ReturnType.SUCCESS;
        }

        if (args.length == 2) {
            if (!player.hasPermission(getPermissionNode() + ".other")) {
                Jupiter.getInstance().getLocale().getMessage("general.nopermission").sendPrefixedMessage(player);
                return ReturnType.FAILURE;
            }

            if (target != null) {
                Player toPlayer = PlayerUtils.findPlayer(args[1]);
                if (toPlayer != null) {
                    Jupiter.getInstance().getLocale().getMessage("teleport").processPlaceholder("player", args[1]).sendPrefixedMessage(target);
                    target.teleport(toPlayer.getLocation());
                } else {
                    Jupiter.getInstance().getLocale().getMessage("general.playeroffline").processPlaceholder("player", args[1]).sendPrefixedMessage(sender);
                }
            } else {
                Jupiter.getInstance().getLocale().getMessage("general.playeroffline").processPlaceholder("player", args[0]).sendPrefixedMessage(sender);
            }
            return ReturnType.SUCCESS;
        }

        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.tp";
    }

    @Override
    public String getSyntax() {
        return "/tp <player> [player]";
    }

    @Override
    public String getDescription() {
        return "Teleport to a specific player";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        return null;
    }
}
