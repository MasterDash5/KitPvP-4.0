package dashnetwork.kitpvp;

import dashnetwork.kitpvp.kit.kits.KitBasic;
import dashnetwork.kitpvp.listeners.*;
import dashnetwork.kitpvp.utils.StatsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class KitPvP extends JavaPlugin {

    // To Do List (In Order)
    // TODO: Add refills on death
    // TODO: Increase killstreak, kill count and death count
    // TODO: Remove dead killstreak
    // TODO: Add more kits
    // TODO: Make the killer be the person who has done the most damage to the killed
    // TODO: Scoreboard

    private static Location spawn;
    private static Vector[] spawnBounds;
    private static KitPvP instance;

    @Override
    public void onEnable() {
        spawn = new Location(Bukkit.getWorld("world"), 203.5D, 240.5D, 235.5D, -90.0F, 0.0F);
        spawnBounds = new Vector[]{new Vector(198, 231, 224), new Vector(226, 247, 245)};
        instance = this;

        StatsUtils.load();

        new KitBasic();

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new BlockListener(), this);
        manager.registerEvents(new CombatListener(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new CreatureSpawnListener(), this);
        manager.registerEvents(new DamageListener(), this);
        manager.registerEvents(new DropListener(), this);
        manager.registerEvents(new HungerListener(), this);
        manager.registerEvents(new InventoryListener(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new NPCListener(), this);
        manager.registerEvents(new ProjectileListener(), this);
        manager.registerEvents(new RespawnListener(), this);
        manager.registerEvents(new SignListener(), this);
        manager.registerEvents(new SoupListener(), this);
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

    public boolean isInSpawn(Entity entity) {
        return isInSpawn(entity.getLocation());
    }

    public boolean isInSpawn(Location location) {
        return location.toVector().isInAABB(spawnBounds[0], spawnBounds[1]);
    }

    public static KitPvP getInstance() {
        return instance;
    }
}