package dashnetwork.kitpvp.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KitEquipment {

    private final ItemStack weapon;
    private final ItemStack abilityItem;

    private final ItemStack helmet;
    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;

    private final List<PotionEffect> potionEffects;

    private final Map<Integer, ItemStack> miscellaneousItems;

    public KitEquipment(ItemStack weapon, ItemStack abilityItem, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, List<PotionEffect> potionEffects, Map<Integer, ItemStack> miscellaneousItems) {
        this.weapon = weapon;
        this.abilityItem = abilityItem;

        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;

        this.potionEffects = potionEffects;

        this.miscellaneousItems = miscellaneousItems;
    }

    public ItemStack getWeapon() {
        return weapon;
    }

    public ItemStack getAbilityItem() {
        return abilityItem;
    }

    public ItemStack[] getArmorContents() {
        return new ItemStack[]{helmet, chestplate, leggings, boots};
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public Map<Integer, ItemStack> getMiscellaneousItems() {
        return miscellaneousItems;
    }

    public void loadKit(Player player) {
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setSaturation(20.0F);

        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setArmorContents(getArmorContents());

        if (weapon != null)
            inventory.addItem(weapon);

        if (abilityItem != null)
            inventory.addItem(abilityItem);

        if (miscellaneousItems != null)
            for (Map.Entry<Integer, ItemStack> entry : miscellaneousItems.entrySet())
                inventory.setItem(entry.getKey(), entry.getValue());

        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();

        if (!activePotionEffects.isEmpty())
            for (PotionEffect effect : activePotionEffects)
                player.removePotionEffect(effect.getType());

        if (potionEffects != null)
            for (PotionEffect effect : potionEffects)
                player.addPotionEffect(effect);

        player.updateInventory();
    }
}