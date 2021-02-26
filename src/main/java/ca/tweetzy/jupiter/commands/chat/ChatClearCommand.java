package ca.tweetzy.jupiter.commands.chat;

import ca.tweetzy.core.commands.AbstractCommand;
import ca.tweetzy.jupiter.Jupiter;
import ca.tweetzy.jupiter.api.JupiterAPI;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * The current file has been created by Matt Wiggins
 * Date Created: February 21 2021
 * Time Created: 6:19 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class ChatClearCommand extends AbstractCommand {

    public ChatClearCommand() {
        super(CommandType.CONSOLE_OK, "clear");
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length == 0) {
            JupiterAPI.chatAPI().clearChat();
            Jupiter.getInstance().getLocale().getMessage("chat_management.chat_cleared_reason_self").sendPrefixedMessage(sender);
            return ReturnType.SUCCESS;
        }

        if (args.length == 1) {
            JupiterAPI.chatAPI().clearChat(args[1]);
            Jupiter.getInstance().getLocale().getMessage("chat_management.chat_cleared_reason_self").sendPrefixedMessage(sender);
            return ReturnType.SUCCESS;
        }

        return ReturnType.SUCCESS;
    }

    @Override
    public String getPermissionNode() {
        return "jupiter.cmd.chat.clear";
    }

    @Override
    public String getSyntax() {
        return "clear [reason]";
    }

    @Override
    public String getDescription() {
        return "Clears the chat";
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) return Arrays.asList("[reason]");
        return null;
    }
}
