package ca.tweetzy.jupiter.commands.chat;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: February 21 2021
 * Time Created: 6:26 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ChatToggleCommand extends AbstractCommand {

    public ChatToggleCommand() {
        super(CommandType.CONSOLE_OK, "toggle");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length == 0) {
            if (JupiterAPI.chatAPI().isChatEnabled()) {
                JupiterAPI.chatAPI().toggleChat(false);
                Bukkit.getOnlinePlayers().forEach(player -> {
                    Jupiter.getInstance().getLocale().getMessage("chat_management.chat_toggled").processPlaceholder("value", "off").sendPrefixedMessage(player);
                });
                return ReturnType.SUCCESS;
            }
            JupiterAPI.chatAPI().toggleChat(true);
            Bukkit.getOnlinePlayers().forEach(player -> {
                Jupiter.getInstance().getLocale().getMessage("chat_management.chat_toggled").processPlaceholder("value", "on").sendPrefixedMessage(player);
            });
        }

        if (args.length == 1) {
            switch (args[0].toUpperCase()) {
                case "ON":
                    JupiterAPI.chatAPI().toggleChat(true);
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        Jupiter.getInstance().getLocale().getMessage("chat_management.chat_toggled").processPlaceholder("value", "on").sendPrefixedMessage(player);
                    });
                    break;

                case "OFF":
                    JupiterAPI.chatAPI().toggleChat(false);
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        Jupiter.getInstance().getLocale().getMessage("chat_management.chat_toggled").processPlaceholder("value", "off").sendPrefixedMessage(player);
                    });
                    break;
            }
        }
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) Arrays.asList("ON", "OFF");
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.chat.toggle";
    }

    @Override
    public String getSyntax() {
        return "toggle [on|off]";
    }

    @Override
    public String getDescription() {
        return "Toggles the chat on or off";
    }
}
