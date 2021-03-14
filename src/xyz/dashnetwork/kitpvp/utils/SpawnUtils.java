package xyz.dashnetwork.kitpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class SpawnUtils {

    private static Location spawn;
    private static Vector[] spawnBounds;

    public static void startup() {
        spawn = new Location(Bukkit.getWorld("world"), 203.5D, 240.5D, 235.5D, -90.0F, 0.0F);
        spawnBounds = new Vector[]{new Vector(198, 231, 224), new Vector(226, 247, 245)};
    }

    public static void stop() {
        spawn = null;
        spawnBounds = null;
    }

    public static Location getSpawn() {
        return spawn;
    }

    public static boolean isInSpawn(Entity entity) {
        return isInSpawn(entity.getLocation());
    }

    public static boolean isInSpawn(Location location) {
        return location.toVector().isInAABB(spawnBounds[0], spawnBounds[1]);
    }

    public static void teleportToSpawn(Player player) {
        player.teleport(spawn, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
}