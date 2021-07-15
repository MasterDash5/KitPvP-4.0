package xyz.dashnetwork.kitpvp.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class ParticleUtils {

    public static void spawnParticle(EnumWrappers.Particle particle, Location location, float floatData, int... intData) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        packet.getParticles().write(0, particle);

        packet.getBooleans().write(0, false); // long distance (256 -> 65536)

        packet.getFloat().write(0, (float) location.getX());
        packet.getFloat().write(1, (float) location.getY());
        packet.getFloat().write(2, (float) location.getZ());

        packet.getFloat().write(3, 0.0F); // offset
        packet.getFloat().write(4, 0.0F); // offset
        packet.getFloat().write(5, 0.0F); // offset

        packet.getFloat().write(6, floatData);

        packet.getIntegers().write(0, 1); // count

        if (intData != null)
            packet.getIntegerArrays().write(0, intData);

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                manager.sendServerPacket(player, packet);
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
    }
}