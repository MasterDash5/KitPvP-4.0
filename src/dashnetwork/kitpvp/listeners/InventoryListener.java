package dashnetwork.kitpvp.listeners;

import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.utils.ColorUtils;
import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.gui.KitMenu;
import dashnetwork.kitpvp.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryListener implements Listener {

    private final KitPvP plugin = KitPvP.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().equals(KitMenu.getKitMenu())) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (!plugin.isInSpawn(player)) {
                MessageUtils.message(player, "&6&l» &7You can only use this in spawn!");
                player.closeInventory();
            } else if (item.hasItemMeta()) {
                String displayName = ColorUtils.strip(item.getItemMeta().getDisplayName());
                Kit kit = Kit.getKit(displayName);

                if (kit != null) {
                    kit.loadKit(player, event.getClick().isLeftClick());
                    player.closeInventory();
                    MessageUtils.message(player, "&6&l» &7You have been given the &6" + kit.getName() + "&7 kit.");
                } else if (displayName.equals("Clear Inventory")) {
                    PlayerInventory inventory = player.getInventory();
                    inventory.setArmorContents(null);
                    inventory.clear();

                    player.updateInventory();
                }
            }
        }
    }
}