package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FallListener implements Listener {

    private List<UUID> falling = new ArrayList<>();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (SpawnUtils.isInSpawn(from) && !SpawnUtils.isInSpawn(to) && !player.isOnGround())
            falling.add(player.getUniqueId());
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        UUID uuid = entity.getUniqueId();

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && entity instanceof Player && falling.contains(uuid)) {
            event.setCancelled(true);
            falling.remove(uuid);
        }
    }
}