package xyz.dashnetwork.kitpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.dashnetwork.core.bukkit.utils.ItemMaker;
import xyz.dashnetwork.kitpvp.kit.Kit;

public class KitMenu {

    private static Inventory inventory = Bukkit.createInventory(null, 27, "Select a Kit");

    static {
        int slot = 0;

        for (Kit kit : Kit.getKits()) {
            inventory.setItem(slot, kit.getDisplayItem());
            slot++;
        }

        inventory.setItem(inventory.getSize() - 1, new ItemMaker(Material.REDSTONE).name("&cClear Inventory").build());

        ItemStack filler = new ItemMaker(Material.STAINED_GLASS_PANE).name("&6Left Click for Soup, Right Click for Potions").build();
        filler.setDurability((short) 7);

        for (int i = 0; i < inventory.getSize(); i++)
            if (inventory.getItem(i) == null)
                inventory.setItem(i, filler);
    }

    public static Inventory getKitMenu() {
        return inventory;
    }
}