package dashnetwork.kitpvp.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SoupListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        if (player.getGameMode() != GameMode.CREATIVE && event.getAction().name().contains("RIGHT")) {
            ItemStack hand = inventory.getItemInHand();

            if (hand != null && hand.getType() == Material.MUSHROOM_SOUP && useSoup(player))
                inventory.setItemInHand(new ItemStack(Material.BOWL));
        }
    }

    private boolean useSoup(Player player) {
        double health = player.getHealth();
        double maxHealth = player.getMaxHealth();

        if (health < maxHealth) {
            player.setHealth(Math.min(health + 7.0D, maxHealth));
            return true;
        }

        return false;
    }
}