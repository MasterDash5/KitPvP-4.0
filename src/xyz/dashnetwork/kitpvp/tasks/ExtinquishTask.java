package xyz.dashnetwork.kitpvp.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ExtinquishTask extends BukkitRunnable {

    private static int ticks = 0;
    private static Map<UUID, Integer> extinguishQueue = new ConcurrentHashMap<>();

    @Override
    public void run() {
        ticks++;

        for (Map.Entry<UUID, Integer> entry : extinguishQueue.entrySet()) {
            UUID uuid = entry.getKey();
            Integer time = entry.getValue();
            Player player = Bukkit.getPlayer(uuid);

            if (player != null && ticks > time + 1)
                player.setFireTicks(0);

            extinguishQueue.remove(uuid);
        }
    }

    public static void addToExtinquishQueue(Player player) {
        extinguishQueue.put(player.getUniqueId(), ticks);
    }
}