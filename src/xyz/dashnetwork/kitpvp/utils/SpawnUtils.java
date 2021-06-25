package xyz.dashnetwork.kitpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class SpawnUtils {

    private static World world;
    private static Location spawn;
    private static Location oldSpawn;
    private static Vector[] spawnBounds;

    public static void startup() {
        world = Bukkit.getWorld("world");
        spawn = new Location(world, 203.5D, 240.5D, 235.5D, -90.0F, 0.0F);
        oldSpawn = new Location(world, 12391.5D, 203.5D, 55.5D, 90.0F, 0.0F);
        spawnBounds = new Vector[]{new Vector(198, 231, 224), new Vector(226, 247, 245)};
    }

    public static void stop() {
        world = null;
        spawn = null;
        oldSpawn = null;
        spawnBounds = null;
    }

    public static World getWorld() {
        return world;
    }

    public static Location getSpawn() {
        return spawn;
    }

    public static Location getOldSpawn() {
        return oldSpawn;
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

    public static void teleportToOldSpawn(Player player) {
        player.teleport(oldSpawn, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
}