package dashnetwork.kitpvp;

import dashnetwork.kitpvp.listeners.*;
import dashnetwork.kitpvp.utils.StatsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPvP extends JavaPlugin {

    private static KitPvP instance;

    @Override
    public void onEnable() {
        instance = this;

        StatsUtils.load();

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new BlockListener(), this);
        manager.registerEvents(new CreatureSpawnListener(), this);
        manager.registerEvents(new NPCListener(), this);
        manager.registerEvents(new SoupListener(), this);
        manager.registerEvents(new DropListener(), this);
    }

    @Override
    public void onDisable() {
        StatsUtils.save();

        instance = null;
    }

    public Location getSpawn() {
        return new Location(Bukkit.getWorld("world"), 203.5D, 240.5D, 235.5D, -90.0F, 0.0F);
    }

    public static KitPvP getInstance() {
        return instance;
    }
}