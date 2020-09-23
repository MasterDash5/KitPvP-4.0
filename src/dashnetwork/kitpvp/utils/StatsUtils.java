package dashnetwork.kitpvp.utils;

import dashnetwork.kitpvp.KitPvP;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsUtils {

    private static final File dataFolder;
    private static final File killsFile;
    private static final File deathsFile;
    private static final File killstreaksFile;

    private static Map<UUID, Integer> kills;
    private static Map<UUID, Integer> deaths;
    private static Map<UUID, Integer> killstreaks;

    public static void load() {
        kills = new HashMap<>();
        deaths = new HashMap<>();
        killstreaks = new HashMap<>();

        try {
            if (dataFolder.mkdirs()) {
                kills = readFile(killsFile);
                deaths = readFile(deathsFile);
                killstreaks = readFile(killstreaksFile);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void save() {
        try {
            writeFile(killsFile, kills);
            writeFile(deathsFile, deaths);
            writeFile(killstreaksFile, killstreaks);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static int getKills(Player player) {
        return kills.get(player.getUniqueId());
    }

    public static int getDeaths(Player player) {
        return deaths.get(player.getUniqueId());
    }

    public static int getKDR(Player player) {
        return getKills(player) / getDeaths(player);
    }

    private static void writeFile(File file, Object data) throws IOException {
        if (file.createNewFile()) {
            Yaml yaml = new Yaml();
            FileWriter writer = new FileWriter(file);

            yaml.dump(data, writer);
        }
    }

    private static <T> T readFile(File file) throws IOException {
        if (file.createNewFile()) {
            Yaml yaml = new Yaml();
            FileReader reader = new FileReader(file);

            return (T) yaml.load(reader);
        } else
            return null;
    }

    static {
        dataFolder = KitPvP.getInstance().getDataFolder();

        char slash = (char) (System.getProperty("os.name").startsWith("Windows") ? 92 : 47);
        String path = dataFolder.getPath() + slash;

        killsFile = new File(path + "kills.yml");
        deathsFile = new File(path + "deaths.yml");
        killstreaksFile = new File(path + "killstreaks.yml");
    }
}