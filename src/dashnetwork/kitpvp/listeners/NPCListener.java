package dashnetwork.kitpvp.listeners;

import dashnetwork.kitpvp.gui.KitMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        String name = ChatColor.stripColor(entity.getName());

        if (entity.hasMetadata("NPC") && name.equals("Kit Selector")) {
            player.openInventory(KitMenu.getKitMenu());
        }
    }
}