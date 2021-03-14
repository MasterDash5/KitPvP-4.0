package xyz.dashnetwork.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.kitpvp.utils.SpawnUtils;

public class CommandSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            SpawnUtils.teleportToSpawn(player);
            MessageUtils.message(player, "&6&l» &7Teleporting...");
        }

        return true;
    }
}