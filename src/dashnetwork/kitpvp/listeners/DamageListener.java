package dashnetwork.kitpvp.listeners;

import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.api.DuelsAPI;
import dashnetwork.kitpvp.utils.KitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player damager = (Player) event.getDamager();

        if (damager != null && event.getFinalDamage() > 0.0D && !DuelsAPI.isInDuel(damager))
            KitUtils.setSurvival(damager);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            event.setCancelled(true);

        if (KitPvP.getInstance().isInSpawn(event.getEntity().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.setDroppedExp(0);
    }
}