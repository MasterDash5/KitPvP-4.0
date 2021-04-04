package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.dashnetwork.core.utils.StringUtils;
import xyz.dashnetwork.kitpvp.KitPvP;
import xyz.dashnetwork.kitpvp.api.DuelsAPI;
import xyz.dashnetwork.kitpvp.utils.DeathUtils;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatListener implements Listener {

    private static final Map<UUID, Double> combatTimer = new ConcurrentHashMap<>();
    private static final Map<UUID, UUID> combatTarget = new ConcurrentHashMap<>();

    public CombatListener() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Map.Entry<UUID, Double> entry : combatTimer.entrySet()) {
                    UUID uuid = entry.getKey();
                    Player player = Bukkit.getPlayer(uuid);
                    double ticks = entry.getValue();

                    if (player == null || ticks <= 0) {
                        combatTimer.remove(uuid);
                        continue;
                    }

                    combatTimer.put(uuid, --ticks);
                }
            }

        }.runTaskTimerAsynchronously(KitPvP.getInstance(), 0L, 1L);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof EnderCrystal || event.getEntity() instanceof EnderCrystal) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getEntity();
        Entity damager = event.getDamager();
        double finalDamage = event.getFinalDamage();

        if ((finalDamage <= 0.0D && !(damager instanceof FishHook)) || player == null || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR || DuelsAPI.isInDuel(player))
            return;

        UUID playerUuid = player.getUniqueId();
        UUID damagerUuid = null;

        if (damager instanceof Player)
            damagerUuid = damager.getUniqueId();
        else if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;

            if (projectile.getShooter() instanceof Player) {
                Player shooter = (Player) projectile.getShooter();

                if (!player.equals(shooter))
                    damagerUuid = shooter.getUniqueId();
            }
        }

        if (damagerUuid != null && !DuelsAPI.isInDuel(damagerUuid)) {
            if (SpawnUtils.isInSpawn(player) || SpawnUtils.isInSpawn(Bukkit.getPlayer(damagerUuid))) {
                event.setCancelled(true);
                return;
            }

            combatTimer.put(playerUuid, 600D);
            combatTimer.put(damagerUuid, 600D);

            combatTarget.put(playerUuid, damagerUuid);
            combatTarget.put(damagerUuid, playerUuid);
        }

        if (player.getHealth() - finalDamage <= 0.0D) {
            event.setCancelled(true);

            DeathUtils.death(player, damagerUuid == null ? Bukkit.getPlayer(combatTarget.get(playerUuid)) : Bukkit.getPlayer(damagerUuid));

            combatTimer.remove(playerUuid);
            combatTarget.remove(playerUuid);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof EnderCrystal) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getEntity();
        double finalDamage = event.getFinalDamage();

        if (finalDamage <= 0.0D || player == null || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR || DuelsAPI.isInDuel(player))
            return;

        if (player.getHealth() - finalDamage <= 0.0D && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            event.setCancelled(true);

            UUID playerUuid = player.getUniqueId();

            DeathUtils.death(player, Bukkit.getPlayer(combatTarget.get(playerUuid)));

            combatTimer.remove(playerUuid);
            combatTarget.remove(playerUuid);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();

        if (combatTimer.containsKey(playerUuid))
            DeathUtils.death(player, Bukkit.getPlayer(combatTarget.get(playerUuid)));

        combatTimer.remove(playerUuid);
        combatTarget.remove(playerUuid);
    }

    public static String getCombatTime(Player player) {
        if (!isInCombat(player))
            return "0.0s";

        return StringUtils.shortenNumber(combatTimer.get(player.getUniqueId()) / 20.0D, 1) + "s";
    }

    public static boolean isInCombat(Player player) {
        return combatTimer.containsKey(player.getUniqueId());
    }
}