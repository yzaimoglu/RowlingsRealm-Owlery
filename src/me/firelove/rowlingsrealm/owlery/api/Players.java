package me.firelove.rowlingsrealm.owlery.api;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Players {

    public static void scheduler() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            File pfile = new File("plugins/Owlery/Players/", all.getUniqueId().toString()+".yml");
            if(!pfile.exists()) {
                Players.createPlayerFile(all);
            }
        }
    }

    public static void createPlayerFile(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9*6);

        File file = new File("plugins/Owlery/Players/", p.getUniqueId().toString()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(p.getUniqueId().toString()+".Current Name", p.getName());
        config.set(p.getUniqueId().toString()+".Inbox", InventoryManager.itemStackArrayToBase64(inv.getContents()));

        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getInbox(Player p) {
        File file = new File("plugins/Owlery/Players/", p.getUniqueId().toString()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getString(p.getUniqueId().toString()+".Inbox");
    }

    public static boolean offPlayerExists(OfflinePlayer p) {
        File file = new File("plugins/Owlery/Players/", p.getUniqueId().toString()+".yml");
        return file.exists();
    }

    public static String getInbox(String uuid) {
        File file = new File("plugins/Owlery/Players/", uuid+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getString(uuid+".Inbox");
    }

    public static void setInbox(Player p, String inbox) {
        File file = new File("plugins/Owlery/Players/", p.getUniqueId().toString()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(p.getUniqueId().toString()+".Inbox", inbox);

        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setInbox(String uuid, String inbox) {
        File file = new File("plugins/Owlery/Players/", uuid+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(uuid+".Inbox", inbox);

        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
