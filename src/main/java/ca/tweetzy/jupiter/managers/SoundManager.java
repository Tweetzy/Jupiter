package ca.tweetzy.jupiter.managers;

import ca.tweetzy.core.compatibility.XSound;
import ca.tweetzy.jupiter.Jupiter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 21 2021
 * Time Created: 9:13 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class SoundManager {

    private static SoundManager instance;

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private SoundManager(){}

    public void playSound(Player player, String sound, float volume, float pitch) {
        player.playSound(player.getLocation(), XSound.matchXSound(sound).get().parseSound(), volume, pitch);
    }

    public void playSound(Player[] players, String sound, float volume, float pitch) {
        Arrays.stream(players).forEach(p -> p.playSound(p.getLocation(), XSound.matchXSound(sound).get().parseSound(), volume, pitch));
    }

    public void playSound(Player player, String sound, float volume, float pitch, int delay) {
        Bukkit.getServer().getScheduler().runTaskLater(Jupiter.getInstance(), () -> playSound(player, sound, volume, pitch), delay);
    }
}
