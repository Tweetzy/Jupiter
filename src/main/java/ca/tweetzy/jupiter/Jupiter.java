package ca.tweetzy.jupiter;

import ca.tweetzy.core.TweetyCore;
import ca.tweetzy.core.TweetyPlugin;
import ca.tweetzy.core.commands.CommandManager;
import ca.tweetzy.core.compatibility.ServerVersion;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.utils.Metrics;
import ca.tweetzy.jupiter.commands.HealCommand;
import ca.tweetzy.jupiter.commands.TimeCommand;
import ca.tweetzy.jupiter.commands.TpAllCommand;
import ca.tweetzy.jupiter.commands.TpCommand;
import ca.tweetzy.jupiter.commands.chat.ChatClearCommand;
import ca.tweetzy.jupiter.commands.chat.ChatToggleCommand;
import ca.tweetzy.jupiter.commands.spawners.SpawnerGiveCommand;
import ca.tweetzy.jupiter.commands.spawners.SpawnerListCommand;
import ca.tweetzy.jupiter.commands.spawners.SpawnerSetCommand;
import ca.tweetzy.jupiter.commands.voidteleport.VoidTeleportSetModeCommand;
import ca.tweetzy.jupiter.commands.voidteleport.VoidTeleportSetSpawnCommand;
import ca.tweetzy.jupiter.commands.voidteleport.VoidTeleportToggleCommand;
import ca.tweetzy.jupiter.listeners.*;
import ca.tweetzy.jupiter.settings.Settings;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 19 2021
 * Time Created: 7:17 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class Jupiter extends TweetyPlugin {

    private static Jupiter instance;
    private CommandManager commandManager;
    private Metrics metrics;

    private final Config dataFile = new Config(this, "data.yml");

    @Override
    public void onPluginLoad() {
        instance = this;
    }

    @Override
    public void onPluginEnable() {
        TweetyCore.registerPlugin(this, 10, "BROWN_STAINED_GLASS");
        TweetyCore.initEvents(this);

        // Check the server version
        if (ServerVersion.isServerVersionAtOrBelow(ServerVersion.V1_7)) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Settings
        Settings.setup();

        // Locale
        setLocale(Settings.LANG.getString(), false);

        // Data Files
        this.dataFile.load();

        // setup commands
        this.commandManager = new CommandManager(this);

        // Commands with sub commands
        this.commandManager.addMainCommand("voidteleport").addSubCommands(
                new VoidTeleportSetSpawnCommand(),
                new VoidTeleportSetModeCommand(),
                new VoidTeleportToggleCommand()
        );

        this.commandManager.addMainCommand("spawner").addSubCommands(
                new SpawnerListCommand(),
                new SpawnerGiveCommand(),
                new SpawnerSetCommand()
        );

        this.commandManager.addMainCommand("chat").addSubCommands(
                new ChatClearCommand(),
                new ChatToggleCommand()
        );

        // Commands without sub commands
        this.commandManager.addCommands(
                new HealCommand(),
                new TpAllCommand(),
                new TpCommand(),
                new TimeCommand()
        );

        // Metrics
        if (Settings.METRICS.getBoolean()) {
            // start metrics
            this.metrics = new Metrics(this, 10488);
        }

        // Listeners
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerTrampleListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerVoidListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SpawnerListeners(), this);
    }

    @Override
    public void onPluginDisable() {

    }

    @Override
    public void onConfigReload() {

    }

    @Override
    public List<Config> getExtraConfig() {
        return null;
    }

    public static Jupiter getInstance() {
        return instance;
    }

    public Config getDataFile() {
        return dataFile;
    }
}
