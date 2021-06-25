package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.kitpvp.gui.KitMenu;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

import java.util.Collection;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && clickedInventory.equals(KitMenu.getKitMenu())) {
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

                    Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();

                    if (!activePotionEffects.isEmpty())
                        for (PotionEffect effect : activePotionEffects)
                            player.removePotionEffect(effect.getType());

                    player.updateInventory();

                    Kit kit = Kit.getPlayerKit(player);

                    if (kit != null)
                        kit.removePlayer(player);
                } else {
                    Kit kit = Kit.getKit(displayName);

                    if (kit != null) {
                        kit.loadKit(player, event.getClick().isLeftClick());
                        player.closeInventory();
                    }
                }
            }
        }
    }
}