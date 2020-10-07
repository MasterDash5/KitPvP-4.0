package dashnetwork.kitpvp.kit;

import dashnetwork.core.bukkit.utils.ItemMaker;
import dashnetwork.core.bukkit.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class Kit {

    private final ItemStack displayItem;
    private final KitEquipment equipment;
    private final Map<UUID, Boolean> players;

    private static final List<Kit> kits = new ArrayList<>();

    public Kit(ItemMaker displayItem) {
        this.displayItem = displayItem.name("&a" + getName()).build();
        this.equipment = setupEquipment();
        this.players = new HashMap<>();

        kits.add(this);
    }

    public abstract KitEquipment setupEquipment();

    public String getName() {
        return getClass().getName().split("Kit")[1];
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public KitEquipment getEquipment() {
        return equipment;
    }

    public void loadKit(Player player, boolean potions) {
        equipment.loadKit(player, potions);
        MessageUtils.message(player, "&6&l» &7You have been given the &c" + getName() + "&7 kit.");
        addPlayer(player, potions);
    }

    public void addPlayer(Player player, boolean potions) {
        players.put(player.getUniqueId(), potions);
    }

    public boolean isUsingSoup(Player player) {
        return players.getOrDefault(player.getUniqueId(), true);
    }

    public boolean isUsingPotions(Player player) {
        return !isUsingSoup(player);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    public Map<UUID, Boolean> getPlayers() {
        return players;
    }

    public static Kit getKit(String name) {
        for (Kit kit : kits)
            if (kit.getName().equalsIgnoreCase(name))
                return kit;

        return null;
    }

    public static Kit getPlayerKit(Player player) {
        for (Kit kit : kits)
            if (kit.getPlayers().containsKey(player.getUniqueId()))
                return kit;

        return null;
    }

    public static List<Kit> getKits() {
        return kits;
    }
}