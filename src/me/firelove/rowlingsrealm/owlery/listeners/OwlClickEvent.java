package me.firelove.rowlingsrealm.owlery.listeners;

import me.firelove.rowlingsrealm.owlery.api.Entities;
import me.firelove.rowlingsrealm.owlery.listeners.inventory.Inventories;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OwlClickEvent implements Listener {

    @EventHandler
    public void on(NPCRightClickEvent e) {
        Player p = e.getClicker();
        NPC npc = e.getNPC();
        int amount = Entities.getAmount();
        while(amount != 0) {
            if (npc.getId() == Entities.getID(amount)) {
                Inventories.MainMenu.open(p);
            }
            amount--;
        }
    }


}
