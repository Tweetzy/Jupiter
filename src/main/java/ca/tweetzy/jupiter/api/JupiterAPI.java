package ca.tweetzy.jupiter.api;

import ca.tweetzy.jupiter.api.chat.ChatAPI;
import ca.tweetzy.jupiter.api.spawner.SpawnerAPI;
import ca.tweetzy.jupiter.api.voidteleport.VoidTeleportAPI;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 20 2021
 * Time Created: 1:53 a.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class JupiterAPI {

    private JupiterAPI(){}

    private static VoidTeleportAPI voidTeleportAPI;
    private static SpawnerAPI spawnerAPI;
    private static ChatAPI chatAPI;

    public static VoidTeleportAPI voidTeleportAPI() {
        if (voidTeleportAPI == null) {
            voidTeleportAPI = new VoidTeleportAPI();
        }
        return voidTeleportAPI;
    }

    public static SpawnerAPI spawnerAPI() {
        if (spawnerAPI == null) {
            spawnerAPI = new SpawnerAPI();
        }
        return spawnerAPI;
    }

    public static ChatAPI chatAPI() {
        if (chatAPI == null) {
            chatAPI = new ChatAPI();
        }
        return chatAPI;
    }
}

