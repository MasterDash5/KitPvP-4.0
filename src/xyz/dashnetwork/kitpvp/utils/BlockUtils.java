package xyz.dashnetwork.kitpvp.utils;

import org.bukkit.Material;

public class BlockUtils {

    public static boolean isBlockInteractable(Material material) {
        String name = material.name();

        if (name.contains("GATE") || name.contains("CHEST") || name.contains("DOOR") || name.contains("BUTTON")
                || name.contains("DIODE") || name.contains("COMPARATOR") || name.contains("DAYLIGHT_DETECTOR"))
            return true;

        switch (material) {
            case WORKBENCH:
            case FURNACE:
            case BURNING_FURNACE:
            case BED:
            case BED_BLOCK:
            case HOPPER:
            case ANVIL:
            case BREWING_STAND:
            case LEVER:
            case DROPPER:
            case BEACON:
            case DISPENSER:
            case NOTE_BLOCK:
            case ENCHANTMENT_TABLE:
            case JUKEBOX:
            case ITEM_FRAME:
                return true;
            default:
                return false;
        }
    }
}