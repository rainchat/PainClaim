package com.rainchat.placeprotect.builder.pagination;


import com.rainchat.placeprotect.utils.menus.MenuConstructor;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface Action {

    void setMenu(MenuConstructor menu);

    MenuConstructor getMenu();

    Player getPlayer();

    void setPlayer(Player player);

    void setupItems();

}
