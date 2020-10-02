package dashnetwork.kitpvp.kit.kits;

import dashnetwork.core.bukkit.utils.ItemMaker;
import dashnetwork.kitpvp.kit.Kit;
import dashnetwork.kitpvp.kit.KitEquipment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class KitBasic extends Kit {

    public KitBasic() {
        super(new ItemMaker(Material.IRON_CHESTPLATE).lore("&5The Basic Kit").build());
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).build();
        ItemStack helmet = new ItemMaker(Material.IRON_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();

        return new KitEquipment(weapon, null, helmet, chestplate, leggings, boots, null, null);
    }
}