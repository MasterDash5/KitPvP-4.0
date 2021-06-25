package xyz.dashnetwork.kitpvp.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dashnetwork.core.bukkit.utils.ItemMaker;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.kit.KitEquipment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KitArcher extends Kit {

    public KitArcher() {
        super(new ItemMaker(Material.BOW));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).unbreakable().build();
        ItemStack bow = new ItemMaker(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 1).enchant(Enchantment.ARROW_INFINITE, 1).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.CHAINMAIL_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.CHAINMAIL_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.CHAINMAIL_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).unbreakable().build();

        Map<Integer, ItemStack> miscellaneousItems = new HashMap<>();
        miscellaneousItems.put(9, new ItemMaker(Material.ARROW).build());

        return new KitEquipment(weapon, bow, helmet, chestplate, leggings, boots, Collections.singletonList(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false)), miscellaneousItems);
    }
}