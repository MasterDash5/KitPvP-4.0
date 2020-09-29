package dashnetwork.kitpvp.listeners;

import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.kitpvp.kit.Kit;
import dashnetwork.kitpvp.utils.GodDangitDashUtils;
import org.bukkit.GameMode;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.name().contains("BLOCK") && player.getGameMode() != GameMode.CREATIVE) {
            BlockState state = event.getClickedBlock().getState();

            if (state instanceof Sign) {
                Sign sign = (Sign) state;

                String name = getLineText(sign, 0);
                String value = getLineText(sign, 1);

                if (name.equals("[Kit]")) {
                    Kit kit = Kit.getKit(value);

                    if (kit != null) {
                        kit.loadKit(player);
                        MessageUtils.message(player, "&6&l» &7You have been given &c" + kit.getName() + "&7!");
                    } else
                        MessageUtils.message(player, "&6&l» &7This kit sign is broken!");
                }
            }
        }
    }

    private String getLineText(Sign sign, int line) {
        return GodDangitDashUtils.stripColor(sign.getLine(line).replace(" ", ""));
    }
}