package xyz.dashnetwork.kitpvp.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import xyz.dashnetwork.core.bukkit.utils.ItemMaker;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.kit.KitEquipment;

public class KitSoldier extends Kit {

    public KitSoldier() {
        super(new ItemMaker(Material.IRON_CHESTPLATE));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.IRON_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();

        return new KitEquipment(weapon, null, helmet, chestplate, leggings, boots, null, null);
    }
}