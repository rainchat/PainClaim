package com.rainchat.placeprotect.menu;


import com.rainchat.raingui.menus.ClickItem;
import com.rainchat.raingui.menus.PaginationMenu;
import com.rainchat.raingui.utils.general.Item;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModeMenu extends PaginationMenu {

    public Player player;

    public ModeMenu(Player player, Plugin inventoryAPI) {
        super(inventoryAPI, "Mode menu", 3);
        this.player = player;

    }


    public void addItems() {

        setItemSlots(new ArrayList<>(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35)));

        //Pages
        setItem(38, new ClickItem(new Item().name("back-page").material(Material.ARROW), (event) -> {
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1, 1);
            previousPage();
        }));

        setItem(40, new ClickItem(new Item().name("back").material(Material.BARRIER), (event) -> {
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1, 1);

        }));

        setItem(42, new ClickItem(new Item().name("next-page").material(Material.ARROW), (event) -> {
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1, 1);
            nextPage();
        }));

        //Fill items
        guiFill(new ClickItem(new Item().material(Material.GRAY_STAINED_GLASS_PANE),event -> {}));
    }


}
