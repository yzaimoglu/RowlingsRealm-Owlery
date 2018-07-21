package me.firelove.rowlingsrealm.owlery.listeners.inventory;

import fr.minuskube.inv.SmartInventory;

public class Inventories {

    public static final SmartInventory MainMenu = SmartInventory.builder()
            .id("mainmenu")
            .provider(new MainMenuProvider())
            .size(6,9)
            .title("§6Owlery §8- §6Main Menu")
            .closeable(true)
            .build();

    public static final SmartInventory ItemChooser = SmartInventory.builder()
            .id("itemchooser")
            .provider(new ItemChooserProvider())
            .size(6,9)
            .title("§6Choose an item to send!")
            .closeable(true)
            .build();

}
