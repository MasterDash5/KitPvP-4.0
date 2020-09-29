package dashnetwork.kitpvp.listeners;

import dashnetwork.core.utils.StringUtils;
import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.api.DuelsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatTimerListener implements Listener {

    private static final Map<UUID, Double> combatTimer = new ConcurrentHashMap<>();

    public CombatTimerListener() {
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

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();

        if (event.getFinalDamage() <= 0.0D || player == null || DuelsAPI.isInDuel(player))
            return;

        Entity damager = event.getDamager();
        UUID playerUuid = player.getUniqueId();
        UUID damagerUuid = damager.getUniqueId();

        if (damager instanceof Player && !DuelsAPI.isInDuel(damagerUuid)) {
            combatTimer.put(playerUuid, 1200D);
            combatTimer.put(damagerUuid, 1200D);
        } else if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;

            if (projectile.getShooter() instanceof Player) {
                Player shooter = (Player) projectile.getShooter();
                UUID shooterUuid = shooter.getUniqueId();

                if (!player.equals(shooter) && !DuelsAPI.isInDuel(shooterUuid)) {
                    combatTimer.put(playerUuid, 1200D);
                    combatTimer.put(shooterUuid, 1200D);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();

        if (combatTimer.containsKey(playerUuid)) {
            // TODO: Fucking Die
        }

        combatTimer.remove(playerUuid);
    }

    public static boolean isInCombat(Player player) {
        return combatTimer.containsKey(player.getUniqueId());
    }

    public static String getCombatTime(Player player) {
        if (!isInCombat(player))
            return null;

        return StringUtils.shortenNumber(combatTimer.get(player.getUniqueId()) / 20.0D, 1);
    }
}