package dashnetwork.kitpvp.api;

import dashnetwork.kitpvp.api.events.DuelsMatchRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DuelsAPI {

    private static final List<UUID> inDuel = new ArrayList<>();

    @Deprecated
    public static void addInDuel(UUID uuid) {
        if (!inDuel.contains(uuid))
            inDuel.add(uuid);
    }

    @Deprecated
    public static void removeInDuel(UUID uuid) {
        inDuel.remove(uuid);
    }

    public static void addInDuel(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();

        if (!inDuel.contains(uuid))
            inDuel.add(uuid);
    }

    public static void removeInDuel(OfflinePlayer player) {
        inDuel.remove(player.getUniqueId());

        Bukkit.getServer().getPluginManager().callEvent(new DuelsMatchRemoveEvent(player));
    }

    public static boolean isInDuel(UUID uuid) {
        return inDuel.contains(uuid);
    }

    public static boolean isInDuel(OfflinePlayer player) {
        return inDuel.contains(player.getUniqueId());
    }
}