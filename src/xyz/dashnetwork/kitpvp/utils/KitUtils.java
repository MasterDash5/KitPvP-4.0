package xyz.dashnetwork.kitpvp.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import xyz.dashnetwork.core.bukkit.utils.ItemMaker;

public class KitUtils {

    private static ItemStack soup = new ItemMaker(Material.MUSHROOM_SOUP).name("&fMushroom Soup").build();
    private static ItemStack potion = new Potion(PotionType.INSTANT_HEAL, 2).splash().toItemStack(1);

    public static void refresh(Player player) {
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        player.setHealth(20.0D);
        player.setMaxHealth(20.0D);

        player.setMaximumNoDamageTicks(20);
        player.setNoDamageTicks(20);

        player.setExp(0.0F);
        player.setLevel(0);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        PlayerInventory inventory = player.getInventory();
        inventory.setArmorContents(null);
        inventory.clear();

        player.updateInventory();
    }

    public static void setSurvival(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);
    }

    public static ItemStack getPotion() {
        return potion;
    }

    public static ItemStack getSoup() {
        return soup;
    }
}