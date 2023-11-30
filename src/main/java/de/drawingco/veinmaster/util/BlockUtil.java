package de.drawingco.veinmaster.util;

import de.drawingco.veinmaster.VeinMaster;
import de.drawingco.veinmaster.enums.PickaxeType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * VeinMaster
 * Copyright (c) 2023 by @cringekachu
 * https://www.spigotmc.org/members/cringekachu.1920125/
 * info@drawingco.de
 */
public class BlockUtil {
    public static ArrayList<Block> getSurroundingBlocks(VeinMaster plugin, Block origin, ArrayList<Block> blocks) {
        for (BlockFace face : BlockFace.values()) {
            Block blockFace = origin.getRelative(face);

            // REDSTONE_ORE is a special case, as it can be in "GLOWING" state
            if (blockFace.getType().name().replace("GLOWING_", "").equals(origin.getType().name().replace("GLOWING_", ""))) {
                if (!blocks.contains(blockFace)) {
                    blocks.add(blockFace);

                    // Check for max vein size
                    if (blocks.size() >= (int) plugin.configUtil.getAttribute("settings", "maxVeinSize", 64)) {

                        // decrease vein size to maxVeinSize
                        while (blocks.size() >= (int) plugin.configUtil.getAttribute("settings", "maxVeinSize", 64)) {
                            blocks.remove(blocks.size() - 1);
                        }

                        if (plugin.isDebug) {
                            plugin.getLogger().info("Vein size limit reached. Current size: " + blocks.size() + " blocks");
                        }

                        return blocks;
                    }

                    // Recursive call
                    getSurroundingBlocks(plugin, blockFace, blocks);
                }
            }
        }

        return blocks;
    }

    private static final HashMap<Material, PickaxeType> oreAccessibility = new HashMap<>() {{
        put(Material.COAL_ORE, PickaxeType.WOODEN);
        put(Material.DEEPSLATE_COAL_ORE, PickaxeType.WOODEN);
        put(Material.IRON_ORE, PickaxeType.STONE);
        put(Material.DEEPSLATE_IRON_ORE, PickaxeType.STONE);
        put(Material.COPPER_ORE, PickaxeType.STONE);
        put(Material.DEEPSLATE_COPPER_ORE, PickaxeType.STONE);
        put(Material.GOLD_ORE, PickaxeType.IRON);
        put(Material.DEEPSLATE_GOLD_ORE, PickaxeType.IRON);
        put(Material.REDSTONE_ORE, PickaxeType.IRON);
        put(Material.DEEPSLATE_REDSTONE_ORE, PickaxeType.IRON);
        put(Material.LAPIS_ORE, PickaxeType.STONE);
        put(Material.DEEPSLATE_LAPIS_ORE, PickaxeType.STONE);
        put(Material.DIAMOND_ORE, PickaxeType.DIAMOND);
        put(Material.DEEPSLATE_DIAMOND_ORE, PickaxeType.DIAMOND);
        put(Material.EMERALD_ORE, PickaxeType.DIAMOND);
        put(Material.DEEPSLATE_EMERALD_ORE, PickaxeType.DIAMOND);
        put(Material.NETHER_GOLD_ORE, PickaxeType.IRON);
        put(Material.NETHER_QUARTZ_ORE, PickaxeType.IRON);
    }};

    public static boolean isHarvestable(Block block, Material tool) {
        if (oreAccessibility.containsKey(block.getType())) {
            PickaxeType pickaxeType = PickaxeType.getPickaxeType(tool);
            int oreLevel = oreAccessibility.get(block.getType()).getLevel();

            return pickaxeType.getLevel() >= oreLevel;
        }

        return false;
    }
}
