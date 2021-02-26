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
 * The current file has been created by Matt Wiggins
 * Date Created: February 19 2021
 * Time Created: 11:23 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class HealCommand extends AbstractCommand {

    public HealCommand() {
        super(CommandType.CONSOLE_OK, "heal");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.setHealth(20);
                Jupiter.getInstance().getLocale().getMessage("heal.self").sendPrefixedMessage(player);
                return ReturnType.SUCCESS;
            }

            if (args.length == 1) {
                Player target = PlayerUtils.findPlayer(args[0]);
                if (target != null) {
                    target.setHealth(20);
                    Jupiter.getInstance().getLocale().getMessage("heal.self").sendPrefixedMessage(target);
                    Jupiter.getInstance().getLocale().getMessage("heal.other").processPlaceholder("player", args[0]).sendPrefixedMessage(sender);
                    return ReturnType.SUCCESS;
                }
                Jupiter.getInstance().getLocale().getMessage("general.playeroffline").processPlaceholder("player", args[0]).sendPrefixedMessage(sender);
                return ReturnType.FAILURE;
            }
            return ReturnType.SUCCESS;
        }

        if (args.length == 0) {
            Jupiter.getInstance().getLocale().getMessage("general.playeronly").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        if (args.length == 1) {
            Player target = PlayerUtils.findPlayer(args[0]);
            if (target != null) {
                target.setHealth(20);
                Jupiter.getInstance().getLocale().getMessage("heal.self").sendPrefixedMessage(target);
                Jupiter.getInstance().getLocale().getMessage("heal.others").processPlaceholder("player", args[0]).sendPrefixedMessage(sender);
                return ReturnType.SUCCESS;
            }
            Jupiter.getInstance().getLocale().getMessage("general.playeroffline").processPlaceholder("player", args[0]).sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }
        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.heal";
    }

    @Override
    public String getSyntax() {
        return "/heal [player]";
    }

    @Override
    public String getDescription() {
        return "Heal yourself or other players";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        return null;
    }
}
