package me.firelove.rowlingsrealm.owlery.listeners.inventory;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.firelove.rowlingsrealm.owlery.api.InventoryManager;
import me.firelove.rowlingsrealm.owlery.api.Methods;
import me.firelove.rowlingsrealm.owlery.api.Players;
import me.firelove.rowlingsrealm.owlery.api.anvilgui.AnvilGUI;
import me.firelove.rowlingsrealm.owlery.main.Owlery;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainMenuProvider implements InventoryProvider {

    public static HashMap<Player, OfflinePlayer> hash_send = new HashMap<>();

    @Override
    public void init(Player p, InventoryContents contents) {
        contents.fillRow(4, ClickableItem.empty(Methods.itemSubID(160, (short) 15, "§0")));
        String base64_inbox = Players.getInbox(p);
        ItemStack[] inbox_contents = null;
        try {
            inbox_contents = InventoryManager.itemStackArrayFromBase64(base64_inbox);
        } catch (IOException e) {
            System.out.print("error");
        }
        Inventory inv = Bukkit.createInventory(null, inbox_contents.length);
        inv.setContents(inbox_contents);
        for(ItemStack is : inbox_contents) {
            if(is != null) {
                contents.add(ClickableItem.of(is, e -> {
                    if (e.isLeftClick()) {
                        if(is.getType() == Material.PAPER && is.getItemMeta().getDisplayName().startsWith("§6Message from")) {
                            Inventories.MainMenu.close(p);
                            inv.removeItem(is);
                            try {
                                Players.setInbox(p, InventoryManager.itemStackArrayToBase64(inv.getContents()));
                            } catch (Exception exc) {
                                System.out.println("error");
                            }
                            p.sendMessage(Owlery.PREFIX+is.getItemMeta().getDisplayName());
                            p.sendMessage(" §6Message: §e"+is.getItemMeta().getLore().get(0));
                            return;
                        }
                        if (p.getInventory().firstEmpty() == -1) {
                            Inventories.MainMenu.close(p);
                            p.sendMessage(Owlery.PREFIX + "§cPlease empty some inventory space first!");
                        } else {
                            p.getInventory().addItem(is);
                            inv.removeItem(is);
                            try {
                                Players.setInbox(p, InventoryManager.itemStackArrayToBase64(inv.getContents()));
                            } catch (Exception exc) {
                                System.out.println("error");
                            }
                            Inventories.MainMenu.close(p);
                            p.sendMessage(Owlery.PREFIX + "§6The item you chose, has been send to your inventory!");
                        }
                    }
                }));
            }
        }

        contents.set(5,2, ClickableItem.of(Methods.itemID(267,  "§6Send an item to a player!"), e -> {
            if(e.isLeftClick()) {
                new AnvilGUI(Owlery.getMain(), p, "§6Player Name", new AnvilGUI.ClickHandler() {

                    @Override
                    public String onClick(Player clicker, String input) {
                        if (Players.offPlayerExists(Bukkit.getOfflinePlayer(input))) {
                            if(clicker.getName().equalsIgnoreCase(input)) {
                                p.sendMessage(Owlery.PREFIX+"§cYou can't send items to yourself!");
                                return null;
                            }
                            hash_send.put(clicker, Bukkit.getOfflinePlayer(input));
                            Inventories.ItemChooser.open(p);
                            return "nothing";
                        } else {
                            p.sendMessage(Owlery.PREFIX+"§cThe player "+input+" has never played on this server!");
                            return null;
                        }
                    }
                });
            }
        }));

        contents.set(5,6, ClickableItem.of(Methods.itemID(339,  "§6Send a message to a player!"), e -> {
            if(e.isLeftClick()) {
                new AnvilGUI(Owlery.getMain(), p, "§6Player Name", new AnvilGUI.ClickHandler() {

                    @Override
                    public String onClick(Player clicker, String input) {
                        if (Players.offPlayerExists(Bukkit.getOfflinePlayer(input))) {
                            if(clicker.getName().equalsIgnoreCase(input)) {
                                p.sendMessage(Owlery.PREFIX+"§cYou can't send a message to yourself!");
                                return null;
                            }
                            if(!clicker.getInventory().containsAtLeast(new ItemStack(Material.PAPER), 1)) {
                                p.sendMessage(Owlery.PREFIX+"§cYou need to have at least one paper to send a message!");
                                return null;
                            }
                            clicker.getInventory().remove(new ItemStack(Material.PAPER, 1));
                            hash_send.put(clicker, Bukkit.getOfflinePlayer(input));
                            new AnvilGUI(Owlery.getMain(), p, "§6Message", new AnvilGUI.ClickHandler() {

                                @Override
                                public String onClick(Player clicker_1, String input_1) {

                                    OfflinePlayer target = hash_send.get(p);
                                    Inventory inv_target = Bukkit.createInventory(null, 9*6);
                                    try {
                                        inv_target = Bukkit.createInventory(null, InventoryManager.itemStackArrayFromBase64(Players.getInbox(target.getUniqueId().toString())).length);
                                        inv_target.setContents(InventoryManager.itemStackArrayFromBase64(Players.getInbox(target.getUniqueId().toString())));
                                    } catch (Exception exc) {
                                        System.out.println("error");
                                    }
                                    if(inv_target.firstEmpty() == -1) {
                                        p.sendMessage("§cThe inbox of §c"+target.getName()+" §cis full!");
                                        return null;
                                    }
                                    List<String> message = new ArrayList<>();
                                    message.add("§e"+input_1);
                                    inv_target.addItem(Methods.itemSubIDLore(339, (short)0, "§6Message from §e"+p.getName(), message));
                                    Players.setInbox(target.getUniqueId().toString(), InventoryManager.itemStackArrayToBase64(inv_target.getContents()));
                                    p.sendMessage(Owlery.PREFIX+"§eYou have sent §6"+target.getName()+" §ea message!");
                                    p.sendMessage(" §6Your message: §e"+input_1);
                                    return null;
                                }
                            });
                            return "nothing";
                        } else {
                            p.sendMessage(Owlery.PREFIX+"§cThe player "+input+" has never played on this server!");
                            return null;
                        }
                    }
                });
            }
        }));


    }

    @Override
    public void update(Player p, InventoryContents contents) {
        return;
    }
}
