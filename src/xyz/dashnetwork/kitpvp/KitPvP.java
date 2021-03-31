package xyz.dashnetwork.kitpvp;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.dashnetwork.kitpvp.commands.CommandBuild;
import xyz.dashnetwork.kitpvp.commands.CommandSpawn;
import xyz.dashnetwork.kitpvp.kit.kits.KitArcher;
import xyz.dashnetwork.kitpvp.kit.kits.KitScout;
import xyz.dashnetwork.kitpvp.kit.kits.KitSoldier;
import xyz.dashnetwork.kitpvp.kit.kits.KitTank;
import xyz.dashnetwork.kitpvp.listeners.*;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;
import xyz.dashnetwork.kitpvp.utils.StatsUtils;

public class KitPvP extends JavaPlugin {

    // To Do List (In Order)
    // TODO: Add more kits
    // TODO: Make the killer be the person who has done the most damage to the killed
    // TODO: Random kit sign

    // Ninja
    // Assassin
    // Swapper
    // Thor
    // Pyro
    // Witch

    private static KitPvP instance;

    @Override
    public void onEnable() {
        instance = this;

        SpawnUtils.startup();
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
        manager.registerEvents(new WeatherListener(), this);
        manager.registerEvents(new FallListener(), this);

        getCommand("spawn").setExecutor(new CommandSpawn());
        getCommand("build").setExecutor(new CommandBuild());
    }

    @Override
    public void onDisable() {
        StatsUtils.save();
        SpawnUtils.stop();

        instance = null;
    }

    public static KitPvP getInstance() {
        return instance;
    }
}