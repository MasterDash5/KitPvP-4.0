package xyz.dashnetwork.kitpvp.api.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DuelsMatchRemoveEvent extends Event {

    private static HandlerList handlers = new HandlerList();
    private OfflinePlayer player;

    public DuelsMatchRemoveEvent(OfflinePlayer player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}