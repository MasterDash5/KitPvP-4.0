package dashnetwork.kitpvp.listeners;

import dashnetwork.kitpvp.KitPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();

        if (KitPvP.getInstance().isInSpawn(player)) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }
}
