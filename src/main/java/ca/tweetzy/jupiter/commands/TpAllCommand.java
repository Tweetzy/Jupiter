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
 * Time Created: 11:23 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class TpAllCommand extends AbstractCommand {

    public TpAllCommand() {
        super(CommandType.PLAYER_ONLY, "tpall");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            Jupiter.getInstance().getLocale().getMessage("teleportall.self").sendPrefixedMessage(player);
            Bukkit.getOnlinePlayers().stream().filter(p -> !p.getUniqueId().equals(player.getUniqueId())).forEach(p -> {
                p.teleport(player.getLocation());
                Jupiter.getInstance().getLocale().getMessage("teleportall.others").processPlaceholder("player", player.getName()).sendPrefixedMessage(p);
            });
            return ReturnType.SUCCESS;
        }

        if (args.length == 1) {
            Player target = PlayerUtils.findPlayer(args[0]);
            if (target != null) {
                Jupiter.getInstance().getLocale().getMessage("teleportall.other").processPlaceholder("player", target.getName()).sendPrefixedMessage(player);
                Bukkit.getOnlinePlayers().stream().filter(p -> (!p.getUniqueId().equals(player.getUniqueId()) && !p.getUniqueId().equals(target.getUniqueId()))).forEach(p -> {
                    p.teleport(target.getLocation());
                    Jupiter.getInstance().getLocale().getMessage("teleportall.others").processPlaceholder("player", target.getName()).sendPrefixedMessage(p);
                });
            } else {
                Jupiter.getInstance().getLocale().getMessage("general.playeroffline").processPlaceholder("player", args[0]).sendPrefixedMessage(player);
            }
        }

        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.tpall";
    }

    @Override
    public String getSyntax() {
        return "/tpall [player]";
    }

    @Override
    public String getDescription() {
        return "Teleport all players to a specific player";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        return null;
    }
}
