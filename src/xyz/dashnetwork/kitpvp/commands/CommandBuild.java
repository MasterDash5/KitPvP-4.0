package xyz.dashnetwork.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.User;
import xyz.dashnetwork.kitpvp.listeners.CombatListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandBuild implements CommandExecutor {

    private static final List<UUID> builders = new ArrayList<>();

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

        if (CombatListener.isInCombat(player)) {
            MessageUtils.message(player, "&6&l» &7You can't build while in combat!");
            return true;
        }

        if (builders.contains(uuid))
            builders.remove(uuid);
        else
            builders.add(uuid);

        MessageUtils.message(player, "&6&l» &7You can " + (builders.contains(uuid) ? "now" : "no longer") + " build");

        return true;
    }

    public static boolean canBuild(Player player) {
        return builders.contains(player.getUniqueId());
    }
}