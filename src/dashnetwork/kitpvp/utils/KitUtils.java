package dashnetwork.kitpvp.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class KitUtils {

    private static final Potion potion = new Potion(PotionType.INSTANT_HEAL, 2).splash();

    public static void refresh(Player player) {
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setSaturation(20.0F);

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

    public static ItemStack getHealingPotion() {
        ItemStack item = new ItemStack(Material.POTION);

        potion.apply(item);

        return item;
    }

    public static boolean isHealingPotion(ItemStack item) {
        return potion.equals(Potion.fromItemStack(item));
    }
}