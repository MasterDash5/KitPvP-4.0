package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import xyz.dashnetwork.kitpvp.utils.KitUtils;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        event.setRespawnLocation(SpawnUtils.getSpawn());

        KitUtils.refresh(player);
        KitUtils.setSurvival(player);
    }
}