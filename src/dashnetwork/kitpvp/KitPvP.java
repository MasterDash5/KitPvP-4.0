package dashnetwork.kitpvp;

import dashnetwork.kitpvp.listeners.*;
import dashnetwork.kitpvp.utils.StatsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class KitPvP extends JavaPlugin {

    private static Location spawn;
    private static Vector[] spawnBounds;
    private static KitPvP instance;

    @Override
    public void onEnable() {
        spawn = new Location(Bukkit.getWorld("world"), 203.5D, 240.5D, 235.5D, -90.0F, 0.0F);
        spawnBounds = new Vector[]{new Vector(), new Vector()}; // TODO: Add Spawn Bounds
        instance = this;

        StatsUtils.load();

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new BlockListener(), this);
        manager.registerEvents(new CreatureSpawnListener(), this);
        manager.registerEvents(new NPCListener(), this);
        manager.registerEvents(new SoupListener(), this);
        manager.registerEvents(new DropListener(), this);
        manager.registerEvents(new CombatTimerListener(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new ProjectileListener(), this);
    }

    @Override
    public void onDisable() {
        StatsUtils.save();

        spawn = null;
        spawnBounds = null;
        instance = null;
    }

    public Location getSpawn() {
        return spawn;
    }

    public boolean isInSpawn(Player player) {
        return isInSpawn(player.getLocation());
    }

    public boolean isInSpawn(Location location) {
        return location.toVector().isInAABB(spawnBounds[0], spawnBounds[1]);
    }

    public static KitPvP getInstance() {
        return instance;
    }
}