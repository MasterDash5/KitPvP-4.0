package xyz.dashnetwork.kitpvp.kit.kits;

import dashnetwork.core.bukkit.utils.ItemMaker;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.kit.KitEquipment;

import java.util.Collections;

public class KitTank extends Kit {

    public KitTank() {
        super(new ItemMaker(Material.OBSIDIAN));
    }

    @Override
    public KitEquipment setupEquipment() {
        ItemStack weapon = new ItemMaker(Material.DIAMOND_SWORD).unbreakable().build();
        ItemStack helmet = new ItemMaker(Material.DIAMOND_HELMET).unbreakable().build();
        ItemStack chestplate = new ItemMaker(Material.DIAMOND_CHESTPLATE).unbreakable().build();
        ItemStack leggings = new ItemMaker(Material.DIAMOND_LEGGINGS).unbreakable().build();
        ItemStack boots = new ItemMaker(Material.DIAMOND_BOOTS).unbreakable().build();

        return new KitEquipment(weapon, null, helmet, chestplate, leggings, boots, Collections.singletonList(new PotionEffect(PotionEffectType.SLOW, 999999, 1)), null);
    }
}