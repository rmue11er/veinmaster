package de.drawingco.veinmaster.enums;

import org.bukkit.Material;

/**
 * VeinMaster
 * Copyright (c) 2023 by @cringekachu
 * https://www.spigotmc.org/members/cringekachu.1920125/
 * info@drawingco.de
 */
public enum PickaxeType {
    HAND(-1),
    WOODEN(0),
    STONE(1),
    IRON(2),
    DIAMOND(3),
    NETHERITE(4);

    private int level;

    PickaxeType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static PickaxeType getPickaxeType(Material material) {
        if(material == Material.WOODEN_PICKAXE) {
            return PickaxeType.WOODEN;
        } else if(material == Material.STONE_PICKAXE) {
            return PickaxeType.STONE;
        } else if(material == Material.IRON_PICKAXE) {
            return PickaxeType.IRON;
        } else if(material == Material.DIAMOND_PICKAXE) {
            return PickaxeType.DIAMOND;
        } else if(material == Material.NETHERITE_PICKAXE) {
            return PickaxeType.NETHERITE;
        } else {
            return PickaxeType.HAND;
        }
    }
}