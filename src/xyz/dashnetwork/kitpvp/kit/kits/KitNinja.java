package xyz.dashnetwork.kitpvp.kit.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dashnetwork.core.bukkit.utils.ItemMaker;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.kit.KitEquipment;

import java.util.Collections;

public class KitNinja extends Kit {

    public KitNinja() {
        super(new ItemMaker(Material.LEATHER_CHESTPLATE).color(Color.BLACK));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.GOLD_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.LEATHER_HELMET).color(Color.BLACK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.LEATHER_CHESTPLATE).color(Color.BLACK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.LEATHER_LEGGINGS).color(Color.BLACK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.LEATHER_BOOTS).color(Color.BLACK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).unbreakable().build();

        return new KitEquipment(weapon, null, helmet, chestplate, leggings, boots, Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false)), null);
    }
}