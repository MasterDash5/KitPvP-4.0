package dashnetwork.kitpvp.kit.kits;

import dashnetwork.core.bukkit.utils.ItemMaker;
import dashnetwork.kitpvp.kit.Kit;
import dashnetwork.kitpvp.kit.KitEquipment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class KitTank extends Kit {

    public KitTank() {
        super(new ItemMaker(Material.OBSIDIAN));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();

        return new KitEquipment(weapon, null, helmet, chestplate, leggings, boots, Collections.singletonList(new PotionEffect(PotionEffectType.SLOW, 999999, 1)), null);
    }
}