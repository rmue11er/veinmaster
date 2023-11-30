package de.drawingco.veinmaster.util;

import de.drawingco.veinmaster.VeinMaster;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * VeinMaster
 * Copyright (c) 2023 by @cringekachu
 * https://www.spigotmc.org/members/cringekachu.1920125/
 * info@drawingco.de
 */
public class ConfigUtil {
    private VeinMaster plugin;
    private File dataFolder;
    public ConfigUtil(VeinMaster plugin) {
        this.plugin = plugin;
        this.dataFolder = plugin.getDataFolder();
        this.plugin.getLogger().info("ConfigUtil loaded");
    }

    private HashMap<String, YamlConfiguration> configset = new HashMap<>();


    public Object getAttribute(String configName, String attribute, Object defaults) {
        YamlConfiguration config = getConfig(configName);

        if(!config.contains(attribute)) {
            config.set(attribute, defaults);
            this.plugin.getLogger().info("Added default value for " + attribute + " ("+defaults.toString().toUpperCase()+") in " + configName + ".yml");
            saveConfig(configName);
            return defaults;
        } else {
            return config.get(attribute);
        }
    }

    public void shutdown() {
        for (String configName : configset.keySet()) {
            saveConfig(configName);
        }
    }

    private YamlConfiguration getConfig(String name) {
        if (configset.containsKey(name)) {
            return configset.get(name);
        } else {
            if (createConfigFile(name)) {
                createConfigFile(name);
            }
            return configset.get(name);
        }
    }

    private boolean createConfigFile(String name) {
        if (!dataFolder.exists()) {

            if (dataFolder.mkdirs()) {
                this.plugin.getLogger().info("Created directory " + plugin.getDataFolder().getAbsolutePath());
            } else {
                this.plugin.getLogger().warning("Could not create directory " + plugin.getDataFolder().getAbsolutePath());
                return false;
            }
        }

        // Set up the configuration file
        File configFile = new File(dataFolder, name+".yml");

        // Load the configuration file
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        configset.put(name, config);

        // If the file doesn't exist, create it with default values
        if (!configFile.exists()) {
            config.options().copyDefaults(true);
            saveConfig(name);
        }

        return true;
    }

    private void saveConfig(String name) {
        try {
           getConfig(name).save(new File(dataFolder, name+".yml"));
        } catch (IOException e) {
            if(plugin.isDebug) {
                e.printStackTrace();
            } else {
                plugin.getLogger().warning("Could not save config file " + name + ".yml");
            }
        }
    }
}
