package dashnetwork.kitpvp.utils;

import dashnetwork.core.bukkit.utils.MessageUtils;
import dashnetwork.core.bukkit.utils.PermissionType;
import dashnetwork.core.bukkit.utils.User;
import dashnetwork.core.utils.MessageBuilder;
import dashnetwork.kitpvp.KitPvP;
import dashnetwork.kitpvp.kit.Kit;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DeathUtils {

    public static void death(Player player, Player killer) {
        player.teleport(KitPvP.getInstance().getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);

        Kit kit = Kit.getPlayerKit(player);

        if (kit != null)
            kit.removePlayer(player);

        KitUtils.refresh(player);
        KitUtils.setSurvival(player);

        if (killer != null) {
            StatsUtils.addKill(killer);
            StatsUtils.addDeath(player);

            deathMessage(player, killer);
            addSoup(killer);
        }
    }

    private static void deathMessage(Player player, Player killer) {
        EntityDamageEvent.DamageCause lastCause = player.getLastDamageCause() == null ? null : player.getLastDamageCause().getCause();
        MessageBuilder builder = new MessageBuilder();

        builder.append("&6&l» ");
        builder.append(User.getUser(player).getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + player.getName());

        if (lastCause == EntityDamageEvent.DamageCause.FIRE || lastCause == EntityDamageEvent.DamageCause.FIRE_TICK)
            builder.append(" &7was burnt to death by ");
        else if (lastCause == EntityDamageEvent.DamageCause.LAVA)
            builder.append(" &7tried to swim in lava to escape ");
        else if (lastCause == EntityDamageEvent.DamageCause.DROWNING)
            builder.append(" &7drowned to escape ");
        else if (lastCause == EntityDamageEvent.DamageCause.STARVATION)
            builder.append(" &7starved to death whilst fighting ");
        else if (lastCause == EntityDamageEvent.DamageCause.THORNS)
            builder.append(" &7died trying to hurt ");
        else if (lastCause == EntityDamageEvent.DamageCause.PROJECTILE)
            builder.append(" &7was shot by ");
        else if (lastCause == EntityDamageEvent.DamageCause.CUSTOM)
            builder.append(" &7got shit on by ");
        else
            builder.append(" &7has been slain by ");

        builder.append(User.getUser(killer).getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + killer.getName());
        builder.append("&7.");

        MessageUtils.broadcast(PermissionType.NONE, builder.build());
    }

    private static void addSoup(Player player) {
        Kit kit = Kit.getPlayerKit(player);
        ItemStack item = kit != null && !kit.isUsingSoup(player) ? KitUtils.getHealingPotion() : new ItemStack(Material.MUSHROOM_SOUP);
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < 9; i++)
            inventory.addItem(item);
    }
}