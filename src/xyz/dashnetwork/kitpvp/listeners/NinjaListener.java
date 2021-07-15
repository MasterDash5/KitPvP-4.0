package xyz.dashnetwork.kitpvp.listeners;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dashnetwork.kitpvp.KitPvP;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.utils.ParticleUtils;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;
import xyz.dashnetwork.kitpvp.utils.Tuple;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NinjaListener implements Listener {

    private Map<UUID, UUID> shurikens = new ConcurrentHashMap<>();

    public NinjaListener() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Map.Entry<UUID, UUID> entry : shurikens.entrySet()) {
                    UUID key = entry.getKey();
                    Player thrower = Bukkit.getPlayer(key);
                    Item shuriken = null;

                    for (Item item : SpawnUtils.getWorld().getEntitiesByClass(Item.class))
                        if (item.getUniqueId().equals(entry.getValue()))
                            shuriken = item;

                    if (thrower != null && shuriken != null) {
                        Location location = shuriken.getLocation();
                        Material material = location.getBlock().getType();

                        if (location.distanceSquared(thrower.getEyeLocation()) > 0.0D)
                            ParticleUtils.spawnParticle(EnumWrappers.Particle.FIREWORKS_SPARK, location, 0.0F);

                        Tuple<Player, Double> tuple = getClosestPlayer(location);

                        Player closestPlayer = tuple.getA();
                        double distance = tuple.getB();

                        if (distance <= 0.64D && !closestPlayer.equals(thrower)) {
                            closestPlayer.damage(10.0D, thrower);
                            thrower.playSound(thrower.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);

                            shuriken.remove();
                            shurikens.remove(key);
                        }

                        if (shuriken.isOnGround() || material != Material.AIR) {
                            shuriken.remove();
                            shurikens.remove(key);
                        }
                    }

                    if (shuriken == null) {
                        shurikens.remove(key);
                        return;
                    }

                    if (thrower == null) {
                        shuriken.remove();
                        shurikens.remove(key);
                    }
                }
            }

        }.runTaskTimerAsynchronously(KitPvP.getInstance(), 0L, 1L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Kit kit = Kit.getPlayerKit(player);
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (kit == null || item == null)
            return;

        ItemStack kitItem = kit.getEquipment().getKitItem();

        if (!item.isSimilar(kitItem))
            return;

        if (kit.getName().equals("Ninja") && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            Location location = player.getEyeLocation();

            if (!shurikens.containsKey(player.getUniqueId())) {
                World world = player.getWorld();
                Item shuriken = world.dropItem(location, kitItem);

                shuriken.setPickupDelay(Integer.MAX_VALUE);
                shuriken.setVelocity(location.getDirection());

                player.playSound(location, Sound.SHOOT_ARROW, 1.0F, 1.0F);

                shurikens.put(player.getUniqueId(), shuriken.getUniqueId());
            }
        }
    }

    private Tuple<Player, Double> getClosestPlayer(Location location) {
        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        for (Player player : Bukkit.getOnlinePlayers()) {
            double distance = player.getLocation().distanceSquared(location);

            if (distance < closestDistance) {
                closestDistance = distance;
                closestPlayer = player;
            }
        }

        return new Tuple<>(closestPlayer, closestDistance);
    }
}