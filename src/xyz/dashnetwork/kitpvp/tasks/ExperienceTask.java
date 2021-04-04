package xyz.dashnetwork.kitpvp.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dashnetwork.kitpvp.utils.KitUtils;

public class ExperienceTask extends BukkitRunnable {

    private ItemStack potion = KitUtils.getPotion();
    private ItemStack soup = KitUtils.getSoup();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerInventory inventory = player.getInventory();

            if (inventory.contains(potion))
                player.setLevel(inventory.all(potion).size());
            else if (inventory.contains(soup))
                player.setLevel(inventory.all(soup).size());
            else
                player.setLevel(0);
        }
    }
}