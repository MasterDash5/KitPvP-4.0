package dashnetwork.kitpvp.listeners;

import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.api.DuelsAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class ProjectileListener implements Listener {

    private final KitPvP plugin = KitPvP.getInstance();

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        Player shooter = (Player) projectile.getShooter();

        if ((shooter != null && plugin.isInSpawn(shooter)) || plugin.isInSpawn(projectile.getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        Player shooter = (Player) projectile.getShooter();

        if ((shooter != null && plugin.isInSpawn(shooter)) || plugin.isInSpawn(projectile.getLocation()))
            projectile.remove();
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (event.getCause() != TeleportCause.ENDER_PEARL || DuelsAPI.isInDuel(player))
            return;

        if ((plugin.isInSpawn(from) && !plugin.isInSpawn(to)) || (!plugin.isInSpawn(from) && plugin.isInSpawn(to)))
            event.setCancelled(true);
    }
}