package dashnetwork.kitpvp.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Kit {

    private final ItemStack displayItem;
    private final KitEquipment equipment;
    private final List<UUID> players;

    private static final List<Kit> kits = new ArrayList<>();

    public Kit(ItemStack displayItem) {
        this.displayItem = displayItem;
        this.equipment = setupEquipment();
        this.players = new ArrayList<>();

        kits.add(this);
    }

    public abstract KitEquipment setupEquipment();

    public String getName() {
        return getClass().getName().split("Kit")[0];
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public KitEquipment getEquipment() {
        return equipment;
    }

    public void loadKit(Player player) {
        equipment.loadKit(player);
        addPlayer(player);
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    public List<UUID> getPlayers() {
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
            if (kit.getPlayers().contains(player.getUniqueId()))
                return kit;

        return null;
    }

    public static List<Kit> getKits() {
        return kits;
    }
}