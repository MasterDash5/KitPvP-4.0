package dashnetwork.kitpvp.gui;

import dashnetwork.core.bukkit.utils.ItemMaker;
import dashnetwork.kitpvp.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitMenu {

    private static final Inventory inventory = Bukkit.createInventory(null, 18, "Select a Kit");

    static {
        int slot = 0;

        for (Kit kit : Kit.getKits()) {
            inventory.setItem(slot, kit.getDisplayItem());
            slot++;
        }

        inventory.setItem(17, new ItemMaker(Material.REDSTONE).name("&cClear Inventory").build());

        ItemStack filler = new ItemMaker(Material.STAINED_GLASS_PANE).name(" ").build();
        filler.setDurability((short) 7);

        for (int i = 0; i < inventory.getSize(); i++)
            if (inventory.getItem(i) == null)
                inventory.setItem(i, filler);
    }

    public static Inventory getKitMenu() {
        return inventory;
    }
}