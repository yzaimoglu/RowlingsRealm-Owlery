package me.firelove.rowlingsrealm.owlery.api;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;

public class Entities {

    public static void startupMethod() {
        File file = new File("plugins/Owlery/", "owleries.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        int amount = config.getInt("Amount");
        System.out.println(String.valueOf(amount)+" Owleries are registered, some of them may already been deleted!");

    }

    public static void createFile() {
        File file = new File("plugins/Owlery/", "owleries.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("Amount", 0);

        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getAmount() {
        File file = new File("plugins/Owlery/", "owleries.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getInt("Amount");
    }

    public static int getID(int id) {
        File file = new File("plugins/Owlery/", "owleries.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getInt(String.valueOf(id)+".ID");
    }

    public static void setNewNPC(NPC npc) {
        int id = getAmount()+1;
        File file = new File("plugins/Owlery/", "owleries.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(String.valueOf(id)+".ID", npc.getId());
        config.set(String.valueOf(id)+".Name", npc.getName());
        config.set(String.valueOf(id)+".Location", npc.getStoredLocation());
        config.set(String.valueOf(id)+".UUID", npc.getUniqueId().toString());

        config.set("Amount", (config.getInt("Amount")+1));

        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeNPC(int id) {
        File file = new File("plugins/Owlery/", "owleries.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(String.valueOf(id), "REMOVED");

        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
