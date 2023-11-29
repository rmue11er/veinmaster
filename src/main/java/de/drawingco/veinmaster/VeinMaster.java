package de.drawingco.veinmaster;

import de.drawingco.veinmaster.event.PlayerOreBreakEvent;
import de.drawingco.veinmaster.util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * VeinMaster
 * Copyrigth (c) 2020 by @cringekachu
 * https://www.spigotmc.org/members/cringekachu.1920125/
 * info@drawingco.de
 */
public class VeinMaster extends JavaPlugin {
    public ConfigUtil configUtil;
    public boolean isDebug;
    @Override
    public void onEnable() {
        // register event
        getServer().getPluginManager().registerEvents(new PlayerOreBreakEvent(this), this);

        // load config
        this.configUtil = new ConfigUtil(this);
        isDebug = (boolean) configUtil.getAttribute("settings", "debug", false);

        if(isDebug) {
            getLogger().info("Debug mode enabled");
        }
    }

    @Override
    public void onDisable() {
        configUtil.shutdown();
        getLogger().info("VeinMaster disabled. Unloaded all configs.");
    }
}
