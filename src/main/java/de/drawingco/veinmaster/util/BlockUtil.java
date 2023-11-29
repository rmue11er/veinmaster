package de.drawingco.veinmaster.util;

import de.drawingco.veinmaster.VeinMaster;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;

/**
 * VeinMaster
 * Copyrigth (c) 2020 by @cringekachu
 * https://www.spigotmc.org/members/cringekachu.1920125/
 * info@drawingco.de
 */
public class BlockUtil {
    public static ArrayList<Block> getSurroundingBlocks(VeinMaster plugin, Block origin, ArrayList<Block> blocks) {
        for (BlockFace face : BlockFace.values()) {
            Block blockFace = origin.getRelative(face);

            // REDSTONE_ORE is a special case, as it can be in "GLOWING" state
            if (blockFace.getType().name().replace("GLOWING_", "").equals(origin.getType().name().replace("GLOWING_", ""))) {
                if(!blocks.contains(blockFace)) {
                    blocks.add(blockFace);
                    plugin.getLogger().info("Added block " + blockFace.getType().name() + " to vein");

                    // Check for max vein size
                    if(blocks.size() >= (int) plugin.configUtil.getAttribute("settings", "maxVeinSize", 64)) {

                        // decrease vein size to maxVeinSize
                        while(blocks.size() >= (int) plugin.configUtil.getAttribute("settings", "maxVeinSize", 64)) {
                            blocks.remove(blocks.size()-1);
                        }

                        if(plugin.isDebug) {
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
}
