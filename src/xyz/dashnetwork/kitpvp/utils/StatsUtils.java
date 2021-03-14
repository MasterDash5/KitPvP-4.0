package xyz.dashnetwork.kitpvp.utils;

import dashnetwork.core.utils.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.dashnetwork.kitpvp.KitPvP;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsUtils {

    private static final KitPvP plugin = KitPvP.getInstance();

    private static final File folder = plugin.getDataFolder();

    private static final File killsFile = new File(folder, "kills.yml");
    private static final File deathsFile = new File(folder, "deaths.yml");
    private static final File killstreaksFile = new File(folder, "killstreaks.yml");

    private static FileConfiguration killsConfig;
    private static FileConfiguration deathsConfig;
    private static FileConfiguration killstreaksConfig;

    private static final Map<UUID, Integer> kills = new HashMap<>();
    private static final Map<UUID, Integer> deaths = new HashMap<>();
    private static final Map<UUID, Integer> killstreaks = new HashMap<>();

    public static void load() {
        folder.mkdirs();

        try {
            killsFile.createNewFile();
            deathsFile.createNewFile();
            killstreaksFile.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        killsConfig = YamlConfiguration.loadConfiguration(killsFile);
        deathsConfig = YamlConfiguration.loadConfiguration(deathsFile);
        killstreaksConfig = YamlConfiguration.loadConfiguration(killstreaksFile);

        for (Map.Entry<String, Object> entry : killsConfig.getValues(true).entrySet())
            kills.put(UUID.fromString(entry.getKey()), (Integer) entry.getValue());

        for (Map.Entry<String, Object> entry : deathsConfig.getValues(true).entrySet())
            deaths.put(UUID.fromString(entry.getKey()), (Integer) entry.getValue());

        for (Map.Entry<String, Object> entry : killstreaksConfig.getValues(true).entrySet())
            killstreaks.put(UUID.fromString(entry.getKey()), (Integer) entry.getValue());
    }

    public static void save() {
        for (Map.Entry<UUID, Integer> entry : kills.entrySet())
            killsConfig.set(entry.getKey().toString(), entry.getValue());

        for (Map.Entry<UUID, Integer> entry : deaths.entrySet())
            deathsConfig.set(entry.getKey().toString(), entry.getValue());

        for (Map.Entry<UUID, Integer> entry : killstreaks.entrySet())
            killstreaksConfig.set(entry.getKey().toString(), entry.getValue());

        try {
            killsConfig.save(killsFile);
            deathsConfig.save(deathsFile);
            killstreaksConfig.save(killstreaksFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void addKill(Player player) {
        kills.put(player.getUniqueId(), getKills(player) + 1);
        killstreaks.put(player.getUniqueId(), getKillStreak(player) + 1);
    }

    public static void addDeath(Player player) {
        deaths.put(player.getUniqueId(), getDeaths(player) + 1);
        killstreaks.put(player.getUniqueId(), 0);
    }

    public static int getKills(Player player) {
        return kills.getOrDefault(player.getUniqueId(), 0);
    }

    public static int getDeaths(Player player) {
        return deaths.getOrDefault(player.getUniqueId(), 0);
    }

    public static int getKillStreak(Player player) {
        return killstreaks.getOrDefault(player.getUniqueId(), 0);
    }

    public static String getKDR(Player player) {
        double kdr = (double) getKills(player) / (double) getDeaths(player);

        if (Double.isNaN(kdr) || Double.isInfinite(kdr))
            kdr = 0.0;

        return StringUtils.shortenNumber(kdr, 2);
    }
}