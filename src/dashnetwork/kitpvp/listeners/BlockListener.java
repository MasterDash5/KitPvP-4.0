package dashnetwork.kitpvp.listeners;

import dashnetwork.core.bukkit.utils.User;
import dashnetwork.core.utils.LazyUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        User user = User.getUser(event.getPlayer());

        if (!user.isAdmin() && event.getAction() == Action.PHYSICAL) {
            Material type = event.getClickedBlock().getType();

            if (!LazyUtils.anyContains(type.name(), "PLATE", "TRIPWIRE"))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (!user.isAdmin() || player.getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (!user.isAdmin() || player.getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);

        event.getBlock().getDrops().clear();
        event.setExpToDrop(0);
    }
}