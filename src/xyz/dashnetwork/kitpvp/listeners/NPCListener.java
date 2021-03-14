package xyz.dashnetwork.kitpvp.listeners;

import dashnetwork.core.utils.ColorUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import xyz.dashnetwork.kitpvp.gui.KitMenu;

public class NPCListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        String name = ColorUtils.strip(entity.getName());

        if (entity.hasMetadata("NPC") && name.equals("Kit Selector"))
            player.openInventory(KitMenu.getKitMenu());
    }
}