package ca.tweetzy.jupiter.api.voidteleport;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 20 2021
 * Time Created: 12:10 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public enum VoidTeleportType {

    DEATH("Death"),
    SPAWN("Spawn");

    private final String type;

    VoidTeleportType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
