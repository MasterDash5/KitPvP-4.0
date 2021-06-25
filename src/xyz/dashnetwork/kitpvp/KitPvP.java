package xyz.dashnetwork.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.dashnetwork.kitpvp.commands.CommandBuild;
import xyz.dashnetwork.kitpvp.commands.CommandDamageTicks;
import xyz.dashnetwork.kitpvp.commands.CommandOldSpawn;
import xyz.dashnetwork.kitpvp.commands.CommandSpawn;
import xyz.dashnetwork.kitpvp.kit.kits.*;
import xyz.dashnetwork.kitpvp.listeners.*;
import xyz.dashnetwork.kitpvp.tasks.ExperienceTask;
import xyz.dashnetwork.kitpvp.tasks.ExtinquishTask;
import xyz.dashnetwork.kitpvp.utils.KitUtils;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;
import xyz.dashnetwork.kitpvp.utils.StatsUtils;

public class KitPvP extends JavaPlugin {

    private static KitPvP instance;

    @Override
    public void onEnable() {
        instance = this;

        SpawnUtils.startup();
        StatsUtils.load();

        new KitSoldier();
        new KitTank();
        new KitArcher();
        new KitFisherman();
        new KitNinja();

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new BlockListener(), this);
        manager.registerEvents(new DamageListener(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new CreatureSpawnListener(), this);
        manager.registerEvents(new DeathListener(), this);
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
        manager.registerEvents(new FishListener(), this);

        new ExperienceTask().runTaskTimerAsynchronously(this, 0L, 1L);
        new ExtinquishTask().runTaskTimerAsynchronously(this, 0L, 1L);

        getCommand("spawn").setExecutor(new CommandSpawn());
        getCommand("build").setExecutor(new CommandBuild());
        getCommand("oldspawn").setExecutor(new CommandOldSpawn());
        getCommand("damageticks").setExecutor(new CommandDamageTicks());

        for (Player player : Bukkit.getOnlinePlayers()) {
            GameMode gameMode = player.getGameMode();

            if (gameMode != GameMode.CREATIVE && gameMode != GameMode.SPECTATOR) {
                SpawnUtils.teleportToSpawn(player);
                KitUtils.refresh(player);
            }
        }
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