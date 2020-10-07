package dashnetwork.kitpvp;

import dashnetwork.kitpvp.commands.CommandSpawn;
import dashnetwork.kitpvp.kit.kits.KitArcher;
import dashnetwork.kitpvp.kit.kits.KitScout;
import dashnetwork.kitpvp.kit.kits.KitSoldier;
import dashnetwork.kitpvp.kit.kits.KitTank;
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
    // TODO: Add more kits
    // TODO: Make the killer be the person who has done the most damage to the killed

    // Soldier
    // Scout
    // Tank
    // Archer

    // Ninja
    // Assassin
    // Swapper
    // Thor
    // Pyro
    // Witch

    private static Location spawn;
    private static Vector[] spawnBounds;
    private static KitPvP instance;

    @Override
    public void onEnable() {
        instance = this;

        spawn = new Location(Bukkit.getWorld("world"), 203.5D, 240.5D, 235.5D, -90.0F, 0.0F);
        spawnBounds = new Vector[]{new Vector(198, 231, 224), new Vector(226, 247, 245)};

        StatsUtils.load();

        new KitSoldier();
        new KitScout();
        new KitTank();
        new KitArcher();

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

        getCommand("spawn").setExecutor(new CommandSpawn());
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