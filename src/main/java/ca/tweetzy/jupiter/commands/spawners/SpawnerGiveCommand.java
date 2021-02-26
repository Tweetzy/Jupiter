package ca.tweetzy.jupiter.commands.spawners;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.NumberUtils;
import ca.tweetzy.core.utils.PlayerUtils;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 21 2021
 * Time Created: 3:51 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class SpawnerGiveCommand extends AbstractCommand {

    public SpawnerGiveCommand() {
        super(CommandType.CONSOLE_OK, "give");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length < 2) return ReturnType.SYNTAX_ERROR;

        Player target = PlayerUtils.findPlayer(args[0]);

        if (target == null) {
            Jupiter.getInstance().getLocale().getMessage("general.playeroffline").processPlaceholder("player", args[0]).sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        if (Arrays.stream(EntityType.values()).filter(type -> type.isSpawnable() && type.isAlive() && !type.toString().contains("ARMOR")).map(EntityType::name).noneMatch(name -> name.equalsIgnoreCase(args[1]))) {
            Jupiter.getInstance().getLocale().getMessage("spawners.invalid_mob_type").processPlaceholder("mob_type", args[1]).sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        if (args.length == 2) {
            PlayerUtils.giveItem(target, JupiterAPI.spawnerAPI().getSpawnerItem(EntityType.valueOf(args[1].toUpperCase())));
            sendGiveReceiveMessages(sender, target, args[1], "1");
            return ReturnType.SUCCESS;
        }

        if (args.length == 3 && !NumberUtils.isInt(args[2])) {
            Jupiter.getInstance().getLocale().getMessage("general.notanumber").processPlaceholder("value", args[2]).sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        for (int i = 0; i < Integer.parseInt(args[2]); i++) PlayerUtils.giveItem(target, JupiterAPI.spawnerAPI().getSpawnerItem(EntityType.valueOf(args[1].toUpperCase())));
        sendGiveReceiveMessages(sender, target, args[1], args[2]);
        return ReturnType.SUCCESS;
    }

    private void sendGiveReceiveMessages(CommandSender sender, Player player, String mob, String amount) {
        Jupiter.getInstance().getLocale().getMessage("spawners.give_spawner")
                .processPlaceholder("player", player.getName())
                .processPlaceholder("amount", amount)
                .processPlaceholder("mob_type", TextUtils.formatText(mob.toLowerCase().replace("_", " "),true))
                .sendPrefixedMessage(sender);

        Jupiter.getInstance().getLocale().getMessage("spawners.receive_spawner")
                .processPlaceholder("amount", amount)
                .processPlaceholder("mob_type", TextUtils.formatText(mob.toLowerCase().replace("_", " "),true))
                .sendPrefixedMessage(player);
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        if (args.length == 2) return Arrays.stream(EntityType.values()).filter(type -> type.isSpawnable() && type.isAlive() && !type.toString().contains("ARMOR")).map(EntityType::name).sorted().collect(Collectors.toList());
        if (args.length == 3) return Arrays.asList("1", "2", "3", "4", "5");
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.spawner.give";
    }

    @Override
    public String getSyntax() {
        return "give <player> <entity> [#]";
    }

    @Override
    public String getDescription() {
        return "Used to give a player a mob spawner";
    }
}
