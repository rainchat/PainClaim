package com.rainchat.placeprotect.utils.menus;


import org.bukkit.entity.Player;

public class SpecialActions {

    public PaginationItem paginationItem;
    public MenuConstructor menuConstructor;
    public Player player;


    public SpecialActions(PaginationItem paginationItem, MenuConstructor menuConstructor, Player player) {
        this.paginationItem = paginationItem;
        this.menuConstructor = menuConstructor;
        this.player = player;
    }


    public void action() {
        switch (paginationItem.getType()) {
            case "tag-category":
                //tagAllCategory();
                break;
            case "tag-storage":
                //tagStorage();
                break;
            case "tag-by-category":
                //tagByCatehory();
                break;
            case "online-players":
                break;
            default:
                System.out.println("unknown type (" + paginationItem.getType() + ")");

        }
    }

}
