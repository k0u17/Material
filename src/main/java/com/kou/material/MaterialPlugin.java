package com.kou.material;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MaterialPlugin extends JavaPlugin {

    private static MaterialPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new FireballListener(), this);
        Bukkit.getPluginManager().registerEvents(new TNTListener(), this);
        Bukkit.getPluginManager().registerEvents(new TridentListener(), this);
        Bukkit.getPluginManager().registerEvents(new BouncyArrowListener(), this);
    }

    public static MaterialPlugin getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
    }
}

