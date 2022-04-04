package com.rainchat.placeprotect.builder.pagination;


import com.rainchat.placeprotect.utils.menus.MenuConstructor;
import org.bukkit.entity.Player;

public abstract class BasePagination implements Action {

    private MenuConstructor menuConstructor;
    private Player player;

    @Override
    public MenuConstructor getMenu() {
        return menuConstructor;
    }

    @Override
    public void setMenu(MenuConstructor menu) {
        this.menuConstructor = menu;
    }

    abstract public void setupItems();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
