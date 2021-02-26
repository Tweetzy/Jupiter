package ca.tweetzy.jupiter.commands.spawners;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.utils.TextUtils;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import ca.tweetzy.jupiter.events.SpawnerChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
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
public class SpawnerSetCommand extends AbstractCommand {

    public SpawnerSetCommand() {
        super(CommandType.PLAYER_ONLY, "set");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length != 1) return ReturnType.SYNTAX_ERROR;
        Player player = (Player) sender;

        Block targetedBlock = player.getTargetBlock(null, 25);
        if (targetedBlock.getType() != XMaterial.SPAWNER.parseMaterial()) {
            Jupiter.getInstance().getLocale().getMessage("spawners.not_a_spawner").sendPrefixedMessage(player);
            return ReturnType.FAILURE;
        }

        if (Arrays.stream(EntityType.values()).filter(type -> type.isSpawnable() && type.isAlive() && !type.toString().contains("ARMOR")).map(EntityType::name).noneMatch(name -> name.equalsIgnoreCase(args[0]))) {
            Jupiter.getInstance().getLocale().getMessage("spawners.invalid_mob_type").processPlaceholder("mob_type", args[0]).sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }



        EntityType original = JupiterAPI.spawnerAPI().getSpawnerType(targetedBlock);

        SpawnerChangeEvent spawnerChangeEvent = new SpawnerChangeEvent(player, targetedBlock, original, EntityType.valueOf(args[0].toUpperCase()));
        Bukkit.getPluginManager().callEvent(spawnerChangeEvent);

        if (spawnerChangeEvent.isCancelled()) {
            return ReturnType.FAILURE;
        }

        JupiterAPI.spawnerAPI().setSpawnerType(targetedBlock, EntityType.valueOf(args[0].toUpperCase()));
        Jupiter.getInstance().getLocale().getMessage("spawners.changed_spawner_mob")
                .processPlaceholder("spawner_type", TextUtils.formatText(original.name().toLowerCase().replace("_", " "), true))
                .processPlaceholder("mob_type", TextUtils.formatText(args[0].toLowerCase().replace("_", " "), true))
                .sendPrefixedMessage(player);

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Arrays.stream(EntityType.values()).filter(type -> type.isSpawnable() && type.isAlive() && !type.toString().contains("ARMOR")).map(EntityType::name).sorted().collect(Collectors.toList());
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.spawners.set";
    }

    @Override
    public String getSyntax() {
        return "set <entity>";
    }

    @Override
    public String getDescription() {
        return "Used to set the entity type of a spawner";
    }
}
