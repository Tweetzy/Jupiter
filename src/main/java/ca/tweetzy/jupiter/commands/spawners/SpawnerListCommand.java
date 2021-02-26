package ca.tweetzy.jupiter.commands.spawners;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.guis.SpawnerListGUI;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 21 2021
 * Time Created: 3:55 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class SpawnerListCommand extends AbstractCommand {

    public SpawnerListCommand() {
        super(CommandType.CONSOLE_OK, "list");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) {
            sendAvailableSpawners(sender);
            return ReturnType.SUCCESS;
        }

        Player player = (Player) sender;
        if (Settings.SPAWNER_SYSTEM_USE_GUI.getBoolean()) {
            player.openInventory(new SpawnerListGUI().getInventory());
        } else {
            sendAvailableSpawners(player);
        }
        return ReturnType.SUCCESS;
    }

    private void sendAvailableSpawners(CommandSender sender) {
        StringBuilder builder = new StringBuilder();
        for (EntityType types : EntityType.values()) {
            if (types.isSpawnable() && types.isAlive() && !types.toString().contains("ARMOR")) {
                builder.append(types.name().toUpperCase().replace(" ", "_")).append("&7, &6");
            }
        }
        Jupiter.getInstance().getLocale().newMessage("&6" + builder.toString()).sendPrefixedMessage(sender);
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.spawner.list";
    }

    @Override
    public String getSyntax() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists all the available mob types";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
