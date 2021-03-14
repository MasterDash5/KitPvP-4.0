package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import xyz.dashnetwork.kitpvp.api.DuelsAPI;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

public class ProjectileListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        event.getEntity().remove();
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (event.getCause() != TeleportCause.ENDER_PEARL || DuelsAPI.isInDuel(player))
            return;

        if ((SpawnUtils.isInSpawn(from) && !SpawnUtils.isInSpawn(to)) || (!SpawnUtils.isInSpawn(from) && SpawnUtils.isInSpawn(to)))
            event.setCancelled(true);
    }
}