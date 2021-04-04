package xyz.dashnetwork.kitpvp.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.dashnetwork.core.bukkit.utils.ItemMaker;
import xyz.dashnetwork.kitpvp.kit.Kit;
import xyz.dashnetwork.kitpvp.kit.KitEquipment;

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

        return new KitEquipment(weapon, null, helmet, chestplate, leggings, boots, null, null);
    }
}