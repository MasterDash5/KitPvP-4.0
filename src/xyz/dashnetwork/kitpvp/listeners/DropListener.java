package xyz.dashnetwork.kitpvp.listeners;

import dashnetwork.core.utils.LazyUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        Material material = item.getItemStack().getType();

        if (player.getGameMode() != GameMode.CREATIVE && !LazyUtils.anyEquals(material, Material.GLASS_BOTTLE, Material.BOWL))
            event.setCancelled(true);

        item.remove();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        event.getDrops().clear();
        event.setDroppedExp(0);
    }
}