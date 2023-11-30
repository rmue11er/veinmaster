package de.drawingco.veinmaster;

import de.drawingco.veinmaster.event.PlayerOreBreakEvent;
import de.drawingco.veinmaster.util.ConfigUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * VeinMaster
 * Copyright (c) 2023 by @cringekachu
 * https://www.spigotmc.org/members/cringekachu.1920125/
 * info@drawingco.de
 */
public class VeinMaster extends JavaPlugin {
    public ConfigUtil configUtil;
    public boolean isDebug;
    private Metrics metrics;
    @Override
    public void onEnable() {
        // register ore break event
        getServer().getPluginManager().registerEvents(new PlayerOreBreakEvent(this), this);

        // load config
        this.configUtil = new ConfigUtil(this);
        isDebug = (boolean) configUtil.getAttribute("settings", "debug", false);

        if(isDebug) {
            getLogger().info("Debug mode enabled");
        }

        // Lets add some metrics
        metrics = new Metrics(this, 20420);

        getLogger().info("VeinMaster startup finished.");
    }

    @Override
    public void onDisable() {
        configUtil.shutdown();
        metrics.shutdown();
        getLogger().info("VeinMaster disabled.");
    }
}
