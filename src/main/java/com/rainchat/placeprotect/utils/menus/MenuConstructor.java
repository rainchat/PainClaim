package com.rainchat.placeprotect.utils.menus;

import com.hakan.inventoryapi.InventoryAPI;
import com.hakan.inventoryapi.inventory.ClickableItem;
import com.hakan.inventoryapi.inventory.HInventory;
import com.hakan.inventoryapi.inventory.Pagination;
import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.api.placeholder.PlayerReplacements;
import com.rainchat.placeprotect.builder.PaginationBuilder;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MenuConstructor {

    public MenuSettings menuSettings;

    public HInventory hInventory;

    public Pagination pagination;

    public HashMap<String,String> parameter;

    public PaginationItem paginationItem;

    public Player player;


    public MenuConstructor(Player player, MenuSettings menuSettings, HashMap<String,String> parameter) {
        hInventory = InventoryAPI.getInstance(PlaceProtect.getInstance()).getInventoryCreator()
                .setTitle(menuSettings.title).setClosable(true)
                .setSize(menuSettings.size).setId("b")
                .create();

        this.parameter = parameter;
        this.player = player;
        this.pagination = hInventory.getPagination();
        this.menuSettings = menuSettings;

        if (menuSettings.slots != null) {
            this.pagination.setItemSlots(menuSettings.slots);
        } else {
            this.pagination.setItemSlots(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35));
        }


        if (menuSettings.feelItem != null) {
            hInventory.guiFill(menuSettings.feelItem.build(new PlayerReplacements(player)));
        }
        paginationItem = menuSettings.paginationItem;
        setupItems();

    }


    public void setupItems() {
        if (paginationItem != null) {
            PaginationBuilder.INSTANCE.getActions(this, paginationItem.type);
        }

        for (ClickItem clickItem : menuSettings.itemDataList) {

            ClickableItem item = ClickableItem.empty(clickItem.build(new PlayerReplacements(player)));
            item.setClick((event) ->
                    {
                        new Executor(clickItem.getCommands(), player, this).start();

                        if (event.isLeftClick()) {
                            new Executor(clickItem.getLeftClick(), player, this).start();
                        }
                        if (event.isRightClick()) {
                            new Executor(clickItem.getRightClick(), player, this).start();
                        }
                    }
            );

            hInventory.setItem(clickItem.getSlot(), item);
        }
    }


    public void openMenu() {
        hInventory.open(player);
    }

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
