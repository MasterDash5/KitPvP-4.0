package dashnetwork.kitpvp;

import dashnetwork.kitpvp.commands.CommandSpawn;
import dashnetwork.kitpvp.kit.kits.KitArcher;
import dashnetwork.kitpvp.kit.kits.KitScout;
import dashnetwork.kitpvp.kit.kits.KitSoldier;
import dashnetwork.kitpvp.kit.kits.KitTank;
import dashnetwork.kitpvp.listeners.*;
import dashnetwork.kitpvp.utils.SpawnUtils;
import dashnetwork.kitpvp.utils.StatsUtils;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class KitPvP extends JavaPlugin {

    // To Do List (In Order)
    // TODO: Add more kits
    // TODO: Make the killer be the person who has done the most damage to the killed

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

        getCommand("spawn").setExecutor(new CommandSpawn());
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