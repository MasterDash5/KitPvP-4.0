package dashnetwork.kitpvp.listeners;

import dashnetwork.kitpvp.api.DuelsAPI;
import dashnetwork.kitpvp.utils.KitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player damager = (Player) event.getDamager();

        if (damager != null && event.getFinalDamage() > 0.0D && !DuelsAPI.isInDuel(damager))
            KitUtils.setSurvival(damager);
    }
}