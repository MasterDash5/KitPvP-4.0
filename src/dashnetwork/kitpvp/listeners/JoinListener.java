package dashnetwork.kitpvp.listeners;

import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.utils.KitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.teleport(KitPvP.getInstance().getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        KitUtils.refresh(player);
        KitUtils.setSurvival(player);
    }
}