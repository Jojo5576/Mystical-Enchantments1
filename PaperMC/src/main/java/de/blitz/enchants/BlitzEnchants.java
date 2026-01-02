package de.blitz.enchants;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.blitz.enchants.listener.*;

public class BlitzEnchants extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new CombatListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new MovementListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
    }
}
