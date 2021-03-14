package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.utils.LazyUtils;
import xyz.dashnetwork.kitpvp.gui.KitMenu;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        if (CombatListener.isInCombat(player) && message.equalsIgnoreCase("/kill")) {
            event.setCancelled(true);
            MessageUtils.message(player, "&6&l» &7You can't do that while in combat!");
            return;
        }

        if ((LazyUtils.anyEquals(message, "/kit", "/kits") || message.startsWith("/kit ") || message.startsWith("/kits "))) {
            event.setCancelled(true);

            if (SpawnUtils.isInSpawn(player))
                player.openInventory(KitMenu.getKitMenu());
            else
                MessageUtils.message(player, "&6&l» &7You can only use this in spawn!");
        }
    }
}