package ca.tweetzy.jupiter.events;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The current file has been created by Kiran Hart
 * Date Created: February 21 2021
 * Time Created: 8:52 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public class SpawnerBreakEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Player player;
    private Block block;
    private EntityType entityType;

    public SpawnerBreakEvent(Player player, Block block, EntityType entityType) {
        this.player = player;
        this.block = block;
        this.entityType = entityType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
