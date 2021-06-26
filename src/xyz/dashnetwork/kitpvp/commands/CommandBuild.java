package xyz.dashnetwork.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandBuild implements CommandExecutor {

    private static List<UUID> builders = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;
        User user = User.getUser(player);
        UUID uuid = player.getUniqueId();

        if (!user.isAdmin()) {
            MessageUtils.noPermissions(player);
            return true;
        }

        if (!builders.contains(uuid)) {
            builders.add(uuid);
            MessageUtils.message(player, "&6&l» &7You can now build.");
        } else {
            removeFromBuild(player);
            MessageUtils.message(player, "&6&l» &7You can no longer build.");
        }

        return true;
    }

    public static boolean canBuild(Player player) {
        return builders.contains(player.getUniqueId());
    }

    public static void removeFromBuild(Player player) {
        builders.remove(player.getUniqueId());
    }
}