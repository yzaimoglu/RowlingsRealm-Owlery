package me.firelove.rowlingsrealm.owlery.commands;

import me.firelove.rowlingsrealm.owlery.api.Entities;
import me.firelove.rowlingsrealm.owlery.api.Methods;
import me.firelove.rowlingsrealm.owlery.main.Owlery;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class OwleryCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("owlery.admin")) {
                if(args.length == 2) {
                    String arg0 = args[0];
                    if(arg0.equalsIgnoreCase("create")) {
                        NPC owl = CitizensAPI.getNPCRegistry().createNPC(EntityType.PARROT, args[1]);
                        owl.spawn(p.getLocation());
                        Entities.setNewNPC(owl);
                        p.sendMessage(Owlery.PREFIX+"§eThe NPC with the name "+args[1]+" §ehas been created!");
                    } else if(arg0.equalsIgnoreCase("destroy")) {
                        int id = 0;
                        try {
                            id = Integer.valueOf(args[1]);
                        } catch (Exception e) {
                            p.sendMessage(Owlery.PREFIX + "§cThe id has to be an integer!");
                            return false;
                        }
                        NPC owl = CitizensAPI.getNPCRegistry().getById(Entities.getID(id));
                        owl.despawn();
                        owl.destroy();
                        Entities.removeNPC(id);
                        p.sendMessage(Owlery.PREFIX+"§eThe NPC with the name "+args[1]+"§6 [id="+id+"] §ehas been destroyed!");
                    } else {
                        p.sendMessage(Owlery.PREFIX+"§6Usage: §e/Owlery <create/destroy> <Name/ID>");
                    }
                } else {
                    p.sendMessage(Owlery.PREFIX+"§6Usage: §e/Owlery <create/destroy> <Name/ID>");
                }
            } else {
                p.sendMessage(Owlery.NOPERM);
                Methods.playSound(p, Sound.BLOCK_ANVIL_BREAK);
            }
            Methods.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
        } else {
            ConsoleCommandSender p = Bukkit.getConsoleSender();
            p.sendMessage(Owlery.PREFIX+"§eThis command is only for players!");
        }


        return false;
    }
}
