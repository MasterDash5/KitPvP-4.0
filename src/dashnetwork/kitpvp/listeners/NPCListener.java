package dashnetwork.kitpvp.listeners;

import dashnetwork.core.utils.ColorUtils;
import dashnetwork.kitpvp.gui.KitMenu;
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
        String name = ColorUtils.strip(entity.getName());

        if (entity.hasMetadata("NPC") && name.equals("Kit Selector"))
            player.openInventory(KitMenu.getKitMenu());
    }
}