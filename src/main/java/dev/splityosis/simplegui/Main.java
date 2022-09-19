package dev.splityosis.simplegui;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        if (!new File(this.getDataFolder(), "config.yml").exists())
            saveDefaultConfig();

        Util.log("&9SimpleGUIs &bloaded &9"+loadGUIsFromConfig()+" GUIs.");
        getCommand("simplegui").setExecutor(new SimpleGuiCMD());
        getCommand("simplegui").setTabCompleter(new GUIsTabCompleter());
        Util.log("&9SimpleGUIs &bhas been fully enabled");
    }

    @Override
    public void onDisable() {
        Util.log("&9SimpleGUIs &bhas been disabled");
    }

    public static int loadGUIsFromConfig(){
        int counter = 0;
        GUI.unloadGUIs();
        ConfigurationSection section = instance.getConfig().getConfigurationSection("");
        for (String name : section.getKeys(false)){
            String guiTitle = section.getString(name+".gui-title");
            int rows = section.getInt(name+".rows");
            new GUI(name, guiTitle, rows);
            counter++;
        }
        return counter;
    }
}
