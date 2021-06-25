package xyz.dashnetwork.kitpvp.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtinquishTask extends BukkitRunnable {

    private static int ticks = 0;
    private static Map<Player, Integer> extinguishQueue = new ConcurrentHashMap<>();

    @Override
    public void run() {
        ticks++;

        for (Map.Entry<Player, Integer> entry : extinguishQueue.entrySet()) {
            Player player = entry.getKey();
            Integer time = entry.getValue();

            if (ticks >= time + 2) {
                player.setFireTicks(0);
                extinguishQueue.remove(player);
            }
        }
    }

    public static void addToExtinquishQueue(Player player) {
        extinguishQueue.put(player, ticks);
    }
}
