package ca.tweetzy.jupiter.settings;

import ca.tweetzy.core.compatibility.XMaterial;
import ca.tweetzy.core.compatibility.XSound;
import ca.tweetzy.core.configuration.Config;
import ca.tweetzy.core.configuration.ConfigSetting;
import ca.tweetzy.jupiter.Jupiter;

import java.util.Arrays;
import java.util.Collections;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 19 2021
 * Time Created: 10:01 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class Settings {

    static final Config config = Jupiter.getInstance().getCoreConfig();

    public static final ConfigSetting LANG = new ConfigSetting(config, "lang", "en_US", "Default language file");
    public static final ConfigSetting UPDATE_CHECKER = new ConfigSetting(config, "update checker", true, "Should Jupiter check for updates?");
    public static final ConfigSetting METRICS = new ConfigSetting(config, "metrics", true, "Should the plugin use metrics?", "It simply allows us to see how many servers", "are currently using the Jupiter plugin.");

    /*
   ========================== PLAYER JOIN/LEAVE ==========================
    */
    public static final ConfigSetting PLAYER_JOIN_MESSAGE_ENABLED = new ConfigSetting(config, "modules.player join.message.enabled", true, "Should the join message module be enabled?");
    public static final ConfigSetting PLAYER_JOIN_MESSAGE = new ConfigSetting(config, "modules.player join.message.message", "&2[&a+&2] &a%player%");
    public static final ConfigSetting PLAYER_QUIT_MESSAGE_ENABLED = new ConfigSetting(config, "modules.player quit.message.enabled", true, "Should the quit message module be enabled?");
    public static final ConfigSetting PLAYER_QUIT_MESSAGE = new ConfigSetting(config, "modules.player quit.message.message", "&4[&c-&4] &c%player%");

    /*
    ========================== CROP TRAMPLE ==========================
     */
    public static final ConfigSetting PLAYER_TRAMPLE_ENABLED = new ConfigSetting(config, "modules.anti crop trample.enabled", true, "Should the anti crop trample module be enabled?");
    public static final ConfigSetting PLAYER_TRAMPLE_IGNORED_GAMEMODES = new ConfigSetting(config, "modules.anti crop trample.ignored gamemodes", Collections.singletonList("CREATIVE"));
    public static final ConfigSetting PLAYER_TRAMPLE_MESSAGE_ENABLED = new ConfigSetting(config, "modules.anti crop trample.chat message.enabled", true, "Should a message be send to the player when they try to trample crops?");
    public static final ConfigSetting PLAYER_TRAMPLE_ACTIONBAR_ENABLED = new ConfigSetting(config, "modules.anti crop trample.action bar message.enabled", true, "Should a actionbar message be send to the player when they try to trample crops?");

    /*
   ========================== VOID TELEPORT ==========================
    */
    public static final ConfigSetting VOID_TP_ENABLED = new ConfigSetting(config, "modules.void teleport.enabled", true, "Should the void teleport module be enabled?");
    public static final ConfigSetting VOID_TP_MESSAGE_ENABLED = new ConfigSetting(config, "modules.void teleport.chat message.enabled", true, "Should a message be sent to the player when they fall?");
    public static final ConfigSetting VOID_TP_ACTIONBAR_ENABLED = new ConfigSetting(config, "modules.void teleport.action bar message.enabled", true, "Should an actionbar message be sent to the player when they fall?");

    /*
    ========================== SPAWNER SETTINGS ==========================
     */
    public static final ConfigSetting SPAWNER_SYSTEM_ENABLED = new ConfigSetting(config, "modules.spawners.enabled", true, "Should the spawner module be enabled?");
    public static final ConfigSetting SPAWNER_SYSTEM_USE_GUI = new ConfigSetting(config, "modules.spawners.use gui", true, "Should the spawner system use a gui when it can?");
    public static final ConfigSetting SPAWNER_SYSTEM_ALLOW_BREAK = new ConfigSetting(config, "modules.spawners.allow players to break", true, "Should players be allowed to mine spawners?");
    public static final ConfigSetting SPAWNER_SYSTEM_ALLOW_PLACE = new ConfigSetting(config, "modules.spawners.allow players to place", true, "Should players be allowed to place spawners?");
    public static final ConfigSetting SPAWNER_SYSTEM_NEED_PERMISSION_TO_BREAK = new ConfigSetting(config, "modules.spawners.need permission to break", true, "Will the player need permission to break the spawner?");
    public static final ConfigSetting SPAWNER_SYSTEM_NEED_PERMISSION_TO_PLACE = new ConfigSetting(config, "modules.spawners.need permission to place", true, "Will the player need permission to place the spawner?");
    public static final ConfigSetting SPAWNER_SYSTEM_REQUIRES_SILK = new ConfigSetting(config, "modules.spawners.silk.needs silk tool to mine", true, "Will the player need a silk enchanted item to mine the spawner?");
    public static final ConfigSetting SPAWNER_SYSTEM_BREAK_IF_SILK_NOT_USED = new ConfigSetting(config, "modules.spawners.silk.break if silk not used", true, "Should the spawner break and not drop itself if an item without silk touch is used?");
    public static final ConfigSetting SPAWNER_SYSTEM_ALLOWED_SILK_TOOLS = new ConfigSetting(config, "modules.spawners.silk.allowed tools", Arrays.asList(
            "WOOD_PICKAXE",
            "STONE_PICKAXE",
            "IRON_PICKAXE",
            "GOLD_PICKAXE",
            "DIAMOND_PICKAXE",
            "NETHERITE_PICKAXE"
    ), "A list of tools that are allowed to break spawners using silk touch");
    public static final ConfigSetting SPAWNER_SYSTEM_SPAWNER_ITEM = new ConfigSetting(config, "modules.spawners.spawner.item", "MOB_SPAWNER", "The Material type for the spawner itemstack");
    public static final ConfigSetting SPAWNER_SYSTEM_SPAWNER_NAME = new ConfigSetting(config, "modules.spawners.spawner.name", "&6%spawner_type% &7Spawner", "The display name for the spawner itemstack");
    public static final ConfigSetting SPAWNER_SYSTEM_SPAWNER_LORE = new ConfigSetting(config, "modules.spawners.spawner.lore", Arrays.asList(
            "&7Place spawner on ground to activate",
            "&7Spawner Type: &e%spawner_type%"
    ), "The lore for the spawner itemstack");

    /*
   ========================== GUI SETTINGS ==========================
    */
    public static final ConfigSetting GUI_BACK_BTN_ITEM = new ConfigSetting(config, "gui.global items.back button.item", "ARROW", "Settings for the back button");
    public static final ConfigSetting GUI_BACK_BTN_NAME = new ConfigSetting(config, "gui.global items.back button.name", "&e<< Back");
    public static final ConfigSetting GUI_BACK_BTN_LORE = new ConfigSetting(config, "gui.global items.back button.lore", Arrays.asList("&7Click the button to go", "&7back to the previous page."));

    public static final ConfigSetting GUI_CLOSE_BTN_ITEM = new ConfigSetting(config, "gui.global items.close button.item", "BARRIER", "Settings for the close button");
    public static final ConfigSetting GUI_CLOSE_BTN_NAME = new ConfigSetting(config, "gui.global items.close button.name", "&cClose");
    public static final ConfigSetting GUI_CLOSE_BTN_LORE = new ConfigSetting(config, "gui.global items.close button.lore", Collections.singletonList("&7Click to close this menu."));

    public static final ConfigSetting GUI_NEXT_BTN_ITEM = new ConfigSetting(config, "gui.global items.next button.item", "ARROW", "Settings for the next button");
    public static final ConfigSetting GUI_NEXT_BTN_NAME = new ConfigSetting(config, "gui.global items.next button.name", "&eNext >>");
    public static final ConfigSetting GUI_NEXT_BTN_LORE = new ConfigSetting(config, "gui.global items.next button.lore", Arrays.asList("&7Click the button to go", "&7to the next page."));

    /*  ===============================
     *         SPAWNER LIST GUI
     *  ===============================*/
    public static final ConfigSetting GUI_SPAWNER_LIST_TITLE = new ConfigSetting(config, "gui.spawner list.title", "&eListing Spawners");
    public static final ConfigSetting GUI_SPAWNER_LIST_USE_BORDER = new ConfigSetting(config, "gui.spawner list.border.use border", true);
    public static final ConfigSetting GUI_SPAWNER_LIST_BORDER_ITEM = new ConfigSetting(config, "gui.spawner list.border.item", "YELLOW_STAINED_GLASS_PANE");
    public static final ConfigSetting GUI_SPAWNER_LIST_BORDER_NAME = new ConfigSetting(config, "gui.spawner list.border.name", "");
    public static final ConfigSetting GUI_SPAWNER_LIST_BORDER_LORE = new ConfigSetting(config, "gui.spawner list.border.lore", Collections.singletonList(""));


    /*
   ========================== Chat Management ==========================
    */
    public static final ConfigSetting CHAT_MANAGEMENT_CHAT_MESSAGE_ENABLED = new ConfigSetting(config, "modules.chat management.chat message.enabled", true, "Should a message be send to the player when chat is disabled?");
    public static final ConfigSetting CHAT_MANAGEMENT_ACTIONBAR_MESSAGE_ENABLED = new ConfigSetting(config, "modules.chat management.action bar message.enabled", true, "Should a actionbar message be send to the player when chat is disabled?");

    /*
  ========================== Sounds ==========================
   */
    public static final ConfigSetting SOUNDS_NAVIGATE_GUI_PAGES = new ConfigSetting(config, "sounds.navigated between gui pages", XSound.ENTITY_BAT_TAKEOFF.parseSound().name());
    public static final ConfigSetting SOUNDS_NOT_ENOUGH_MONEY = new ConfigSetting(config, "sounds.not enough money", XSound.ENTITY_ITEM_BREAK.parseSound().name());

    public static void setup() {
        config.load();
        config.setAutoremove(true).setAutosave(true);
        config.saveChanges();
    }
}
