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

public class KitScout extends Kit {

    public KitScout() {
        super(new ItemMaker(Material.SUGAR));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.CHAINMAIL_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.CHAINMAIL_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.CHAINMAIL_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();

        return new KitEquipment(weapon, null, helmet, chestplate, leggings, boots, Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, 999999, 1)), null);
    }
}