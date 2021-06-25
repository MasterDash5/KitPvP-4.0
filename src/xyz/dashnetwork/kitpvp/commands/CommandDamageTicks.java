package xyz.dashnetwork.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.PermissionType;
import xyz.dashnetwork.core.bukkit.utils.SelectorUtils;
import xyz.dashnetwork.core.bukkit.utils.User;

import java.util.Collection;
import java.util.Objects;

public class CommandDamageTicks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PermissionType.OWNER.toPermission())) {
            MessageUtils.noPermissions(sender);
            return true;
        }

        if (args.length != 1 && args.length != 2) {
            MessageUtils.message(sender, "&6&l» &7Usage: /damageticks <player(s)> [ticks]");
            return true;
        }

        Collection<Player> players = SelectorUtils.getPlayers(sender, args[0]);
        players.removeIf(Objects::isNull);

        if (players.isEmpty()) {
            MessageUtils.noPlayerFound(sender);
            return true;
        }

        if (args.length == 1) {
            for (Player player : players) {
                User user = User.getUser(player);
                String displayName = user.getDisplayName();

                MessageUtils.message(sender, "&6&l» " + displayName + " &7has a damageticks of &6" + player.getMaximumNoDamageTicks() + "&7.");
            }
        }

        if (args.length == 2) {
            int ticks;

            try {
                ticks = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                MessageUtils.sendException(sender, exception);
                return true;
            }

            for (Player player : players) {
                User user = User.getUser(player);
                String displayName = user.getDisplayName();

                player.setNoDamageTicks(ticks);
                player.setMaximumNoDamageTicks(ticks);

                MessageUtils.message(sender, "&6&l» " + displayName + " &7now has a damageticks of &6" + ticks + "&7.");
            }
        }

        return true;
    }
}