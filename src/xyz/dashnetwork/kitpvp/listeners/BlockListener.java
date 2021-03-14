package xyz.dashnetwork.kitpvp.listeners;

import dashnetwork.core.utils.LazyUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.dashnetwork.kitpvp.commands.CommandBuild;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if (!CommandBuild.canBuild(player) && SpawnUtils.isInSpawn(player) && action.name().contains("RIGHT") && (block == null || (block.getType() != Material.SIGN && block.getType() != Material.WALL_SIGN))) {
            event.setCancelled(true);
            player.updateInventory();
        }

        if (!CommandBuild.canBuild(player) && action == Action.RIGHT_CLICK_BLOCK && (block == null || (block.getType() != Material.SIGN && block.getType() != Material.WALL_SIGN))) {
            event.setUseInteractedBlock(Event.Result.DENY);
            player.updateInventory();
        }

        if (!CommandBuild.canBuild(player) && action == Action.LEFT_CLICK_BLOCK && block != null) {
            Block blockAbove = block.getLocation().add(0, 1, 0).getBlock();

            if (blockAbove.getType() == Material.FIRE)
                event.setCancelled(true);
        }

        if (!CommandBuild.canBuild(player) && action == Action.PHYSICAL) {
            Material type = event.getClickedBlock().getType();

            if (!LazyUtils.anyContains(type.name(), "PLATE", "TRIPWIRE"))
                event.setUseInteractedBlock(Event.Result.DENY);
        }

        if (block != null && block.getType() == Material.DRAGON_EGG)
            event.setUseInteractedBlock(Event.Result.DENY);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!CommandBuild.canBuild(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!CommandBuild.canBuild(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (!CommandBuild.canBuild(player) && (entity instanceof ItemFrame || entity instanceof Painting))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Entity entity = event.getEntity();

            if (!CommandBuild.canBuild(player) && (entity instanceof ItemFrame || entity instanceof Painting))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        Player player = (Player) event.getRemover();

        if (player != null && !CommandBuild.canBuild(player))
            event.setCancelled(true);
    }
}