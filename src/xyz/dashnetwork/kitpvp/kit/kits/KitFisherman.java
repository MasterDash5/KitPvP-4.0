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

public class KitFisherman extends Kit {

    public KitFisherman() {
        super(new ItemMaker(Material.FISHING_ROD));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.WOOD_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).unbreakable().build();
        ItemStack rod = new ItemMaker(Material.FISHING_ROD).enchant(Enchantment.LURE, 5).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.CHAINMAIL_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.LEATHER_CHESTPLATE).color(Color.BLUE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.LEATHER_LEGGINGS).color(Color.BLUE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).unbreakable().build();

        return new KitEquipment(weapon, rod, helmet, chestplate, leggings, boots, Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false)), null);
    }
}