package me.firelove.rowlingsrealm.owlery.main;

import me.firelove.rowlingsrealm.owlery.api.Entities;
import me.firelove.rowlingsrealm.owlery.api.Players;
import me.firelove.rowlingsrealm.owlery.commands.OwleryCMD;
import me.firelove.rowlingsrealm.owlery.listeners.OwlClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Owlery extends JavaPlugin {

    public static String PREFIX = "§8[§6Owlery§8] §e";
    public static String NOPERM = Owlery.PREFIX+"§cInsufficient Permissions!";
    private static Owlery plugin;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            initialize();
        } catch (Exception e) {
           Bukkit.getConsoleSender().sendMessage(Owlery.PREFIX+"§cAn error occured, plugin is being disabled!");
        }
        Bukkit.getConsoleSender().sendMessage(Owlery.PREFIX+"§aPlugin has been successfully initialized!");
        Bukkit.getConsoleSender().sendMessage(Owlery.PREFIX+"§aEnabling Plugin;");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Owlery.PREFIX+"§cDisabling Plugin.");
    }

    private void initialize() {
        File file = new File("plugins/Owlery/", "owleries.yml");
        if(!file.exists()) {
            Entities.createFile();
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Entities.startupMethod();
            }
        },5*20);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Players.scheduler();
            }
        }, 0, 5*20);

        initCommands();
        initListeners();
    }

    private void initCommands() {
        getCommand("owlery").setExecutor(new OwleryCMD());
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new OwlClickEvent(), this);
    }
    public static Owlery getMain() {
        return plugin;
    }
}
