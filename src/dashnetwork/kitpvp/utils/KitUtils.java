package dashnetwork.kitpvp.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class KitUtils {

    public static void refresh(Player player) {
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        player.getInventory().clear();
    }

    public static void setSurvival(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);
    }
}