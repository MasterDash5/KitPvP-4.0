package dashnetwork.kitpvp.listeners;

import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.kitpvp.gui.KitMenu;
import dashnetwork.kitpvp.kit.Kit;
import dashnetwork.kitpvp.utils.SpawnUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().equals(KitMenu.getKitMenu())) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (!SpawnUtils.isInSpawn(player)) {
                MessageUtils.message(player, "&6&l» &7You can only use this in spawn!");
                player.closeInventory();
            } else if (item.hasItemMeta()) {
                String displayName = ColorUtils.strip(item.getItemMeta().getDisplayName());

                if (displayName.equals("Clear Inventory")) {
                    PlayerInventory inventory = player.getInventory();
                    inventory.setArmorContents(null);
                    inventory.clear();

                    player.updateInventory();
                } else {
                    Kit kit = Kit.getKit(displayName);

                    if (kit != null) {
                        kit.loadKit(player, event.getClick().isRightClick());
                        player.closeInventory();
                    }
                }
            }
        }
    }
}