package xyz.dashnetwork.kitpvp.listeners;

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
import org.bukkit.inventory.ItemStack;
import xyz.dashnetwork.core.utils.LazyUtils;
import xyz.dashnetwork.kitpvp.commands.CommandBuild;
import xyz.dashnetwork.kitpvp.utils.BlockUtils;
import xyz.dashnetwork.kitpvp.utils.KitUtils;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack hand = player.getItemInHand();
        boolean rightClick = action.name().contains("RIGHT");
        boolean inSpawn = SpawnUtils.isInSpawn(player);

        if (CommandBuild.canBuild(player))
            return;

        if (inSpawn && rightClick && hand.isSimilar(KitUtils.getPotion())) {
            event.setUseItemInHand(Event.Result.DENY);
            player.updateInventory();
        }

        if (!event.hasBlock())
            return;

        Block block = event.getClickedBlock();
        Material type = block.getType();

        if (rightClick && BlockUtils.isBlockInteractable(type))
            event.setUseInteractedBlock(Event.Result.DENY);

        if (action == Action.PHYSICAL && !LazyUtils.anyContains(type.name(), "PLATE", "TRIPWIRE"))
            event.setUseInteractedBlock(Event.Result.DENY);

        if (type == Material.DRAGON_EGG)
            event.setUseInteractedBlock(Event.Result.DENY);

        if (action == Action.LEFT_CLICK_BLOCK) {
            Block blockAbove = block.getLocation().add(0, 1, 0).getBlock();

            if (blockAbove.getType() == Material.FIRE)
                event.setUseInteractedBlock(Event.Result.DENY);
        }
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