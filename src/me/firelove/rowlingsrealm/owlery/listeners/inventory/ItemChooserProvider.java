package me.firelove.rowlingsrealm.owlery.listeners.inventory;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.firelove.rowlingsrealm.owlery.api.InventoryManager;
import me.firelove.rowlingsrealm.owlery.api.Methods;
import me.firelove.rowlingsrealm.owlery.api.Players;
import me.firelove.rowlingsrealm.owlery.main.Owlery;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemChooserProvider implements InventoryProvider {

    @Override
    public void init(Player p, InventoryContents contents) {

        contents.fillRow(4, ClickableItem.empty(Methods.itemSubID(160, (short)15, "§0")));

        Inventory inv = p.getInventory();
        ItemStack[] inv_contents = inv.getContents();
        OfflinePlayer target = MainMenuProvider.hash_send.get(p);
        for(ItemStack is : inv_contents) {
            if(is != null) {
                contents.add(ClickableItem.of(is, e -> {
                    if(e.isLeftClick()) {
                        Inventory inv_target;
                        try {
                            inv_target = Bukkit.createInventory(null, InventoryManager.itemStackArrayFromBase64(Players.getInbox(target.getUniqueId().toString())).length);
                            inv_target.setContents(InventoryManager.itemStackArrayFromBase64(Players.getInbox(target.getUniqueId().toString())));
                            if(inv_target.firstEmpty() == -1) {
                                p.sendMessage("§cThe inbox of §c"+target.getName()+" §cis full!");
                                return;
                            }
                            p.getInventory().removeItem(is);
                            ItemMeta im = is.getItemMeta();
                            im.setDisplayName("§6Item sent by §e"+p.getName());
                            is.setItemMeta(im);
                            inv_target.addItem(is);
                            Players.setInbox(target.getUniqueId().toString(), InventoryManager.itemStackArrayToBase64(inv_target.getContents()));
                            p.sendMessage(Owlery.PREFIX+"§eYou have sent §6"+target.getName()+" §ean item!");
                            Inventories.ItemChooser.close(p);
                        } catch (Exception exc) {
                            System.out.println("error");
                        }
                    }

                }));
            }
        }

        contents.set(5,8, ClickableItem.of(Methods.itemMaterial(Material.BARRIER, "§cClose"), e-> {

            if(e.isLeftClick()) {
                Inventories.ItemChooser.close(p);
            }

        }));

    }

    @Override
    public void update(Player p, InventoryContents contents) {

    }
}
