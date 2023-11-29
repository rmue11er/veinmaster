package de.drawingco.veinmaster.event;

import de.drawingco.veinmaster.VeinMaster;
import de.drawingco.veinmaster.util.BlockUtil;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

/**
 * VeinMaster
 * Copyrigth (c) 2020 by @cringekachu
 * https://www.spigotmc.org/members/cringekachu.1920125/
 * info@drawingco.de
 */
public class PlayerOreBreakEvent implements Listener {
    private final VeinMaster plugin;

    public PlayerOreBreakEvent(VeinMaster plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerOreBreakEvent(BlockBreakEvent event) {
        Block b = event.getBlock();

        if (b.getType().name().endsWith("_ORE")) {
            if ((boolean) plugin.configUtil.getAttribute("settings", "disableVeinMining", false)) {
                if (plugin.isDebug) {
                    plugin.getLogger().info("Vein mining disabled globally");
                }

                return;
            }

            if ((boolean) plugin.configUtil.getAttribute("settings", "disableVeinMiningCreative", true)) {
                if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    if (plugin.isDebug) {
                        plugin.getLogger().info("Vein mining disabled for creative players");
                    }

                    return;
                }
            }

            ArrayList<Block> blocks = BlockUtil.getSurroundingBlocks(this.plugin, b, new ArrayList<Block>());

            for (Block block : blocks) {
                block.breakNaturally();
            }
        }
    }
}
