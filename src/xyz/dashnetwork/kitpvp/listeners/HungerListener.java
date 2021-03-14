package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

public class HungerListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();

        if (SpawnUtils.isInSpawn(player)) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }
}
