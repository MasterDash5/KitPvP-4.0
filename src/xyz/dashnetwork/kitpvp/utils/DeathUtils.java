package xyz.dashnetwork.kitpvp.utils;

import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import xyz.dashnetwork.core.bukkit.utils.MessageUtils;
import xyz.dashnetwork.core.bukkit.utils.User;
import xyz.dashnetwork.core.bukkit.utils.VanishUtils;
import xyz.dashnetwork.core.utils.MessageBuilder;
import xyz.dashnetwork.kitpvp.kit.Kit;

public class DeathUtils {

    public static void death(Player player, Player killer) {
        Kit kit = Kit.getPlayerKit(player);

        if (killer != null) {
            StatsUtils.addKill(killer);

            deathMessage(player, killer);

            if (kit != null && SpawnUtils.isInSpawn(player))
                refill(killer);
        }

        StatsUtils.addDeath(player);

        SpawnUtils.teleportToSpawn(player);

        KitUtils.setSurvival(player);
        KitUtils.refresh(player);

        if (kit != null) {
            boolean potions = kit.getPlayers().getOrDefault(player.getUniqueId(), false);

            kit.loadKit(player, potions);
        }
    }

    public static void deathMessage(Player player, Player killer) {
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
            builder.append(" &7ate shit by ");
        else
            builder.append(" &7has been slain by ");

        builder.append(User.getUser(killer).getDisplayName()).hoverEvent(HoverEvent.Action.SHOW_TEXT, "&6" + killer.getName());
        builder.append("&7.");

        for (Player online : Bukkit.getOnlinePlayers())
            if (VanishUtils.canSee(online, player) && VanishUtils.canSee(online, killer))
                MessageUtils.message(online, builder.build());
    }

    public static void refill(Player player) {
        Kit kit = Kit.getPlayerKit(player);
        ItemStack refillItem = kit != null && kit.isUsingPotions(player) ? KitUtils.getPotion() : KitUtils.getSoup();
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < 9; i++)
            inventory.addItem(refillItem);

        for (ItemStack item : inventory.getContents())
            if (item != null && item.getType() == Material.COOKED_BEEF)
                item.setAmount(64);
    }
}