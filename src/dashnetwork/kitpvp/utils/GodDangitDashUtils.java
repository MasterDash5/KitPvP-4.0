package dashnetwork.kitpvp.utils;

import org.bukkit.ChatColor;

public class GodDangitDashUtils {

    public static String stripColor(String string) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string));
    }
}