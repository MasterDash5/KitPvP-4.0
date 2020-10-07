package dashnetwork.kitpvp.listeners;

import dashnetwork.kitpvp.utils.KitUtils;
import dashnetwork.kitpvp.utils.SpawnUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        event.setRespawnLocation(SpawnUtils.getSpawn());

        KitUtils.refresh(player);
        KitUtils.setSurvival(player);
    }
}