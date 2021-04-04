package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.utils.KitUtils;

import java.util.Map;

public class FishListener implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();

        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Kit kit = Kit.getPlayerKit(player);
            ItemStack item = kit != null && kit.isUsingPotions(player) ? KitUtils.getPotion() : KitUtils.getSoup();
            PlayerInventory inventory = player.getInventory();
            Map<Integer, ItemStack> leftover = inventory.addItem(item);

            if (!leftover.isEmpty()) {
                leftover.clear();
                MessageUtils.message(player, "&6&l» &cYour inventory is full!");
            } else
                MessageUtils.message(player, "&6&l» &7You fished up a &6" + ColorUtils.strip(item.getItemMeta().getDisplayName()) + "&7!");

            event.getCaught().remove();
            event.setExpToDrop(0);
        }
    }
}