package xyz.dashnetwork.kitpvp.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.dashnetwork.core.bukkit.events.UserPacketEvent;
import xyz.dashnetwork.core.utils.ColorUtils;
import xyz.dashnetwork.kitpvp.gui.KitMenu;

public class NPCListener implements Listener {

    @EventHandler
    public void onUserPacket(UserPacketEvent event) {
        PacketContainer packet = event.getPacket();

        if (!packet.getType().equals(PacketType.Play.Client.USE_ENTITY))
            return;

        Player player = event.getPlayer();

        if (event.getPacketEvent().isPlayerTemporary())
            return;

        Entity entity = packet.getEntityModifier(player.getWorld()).read(0);

        if (entity == null)
            return;

        String name = ColorUtils.strip(entity.getName());

        if (entity.hasMetadata("NPC") && name.equals("Kit Selector"))
            player.openInventory(KitMenu.getKitMenu());
    }
}