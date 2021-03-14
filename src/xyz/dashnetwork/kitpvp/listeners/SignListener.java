package xyz.dashnetwork.kitpvp.listeners;

import org.bukkit.GameMode;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.kitpvp.kit.Kit;

public class SignListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String action = event.getAction().name();

        if (action.contains("BLOCK") && player.getGameMode() != GameMode.CREATIVE) {
            BlockState state = event.getClickedBlock().getState();

            if (!(state instanceof Sign))
                return;

            Sign sign = (Sign) state;

            String name = getLineText(sign, 0);
            String value = getLineText(sign, 1);

            if (name.equals("[Kit]")) {
                Kit kit = Kit.getKit(value);

                if (kit != null)
                    kit.loadKit(player, action.contains("RIGHT"));
                else
                    MessageUtils.message(player, "&6&l» &7This kit sign is broken!");
            }
        }
    }

    private String getLineText(Sign sign, int line) {
        return ColorUtils.strip(sign.getLine(line).replace(" ", ""));
    }
}