package dashnetwork.kitpvp.kit.kits;

import dashnetwork.core.bukkit.utils.ItemMaker;
import dashnetwork.kitpvp.kit.Kit;
import dashnetwork.kitpvp.kit.KitEquipment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitArcher extends Kit {

    public KitArcher() {
        super(new ItemMaker(Material.BOW));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.STONE_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).unbreakable().build();
        ItemStack bow = new ItemMaker(Material.BOW).name("&aArcher's Bow").enchant(Enchantment.ARROW_INFINITE, 1).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.IRON_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).unbreakable().build();

        Map<Integer, ItemStack> miscellaneousItems = new HashMap<>();
        miscellaneousItems.put(9, new ItemMaker(Material.ARROW).name("&aArcher's Arrow").build());

        return new KitEquipment(weapon, bow, helmet, chestplate, leggings, boots, null, miscellaneousItems);
    }
}