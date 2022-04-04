package com.rainchat.placeprotect.builder.pagination;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.rainchat.placeprotect.api.placeholder.PlayerReplacements;
import com.rainchat.placeprotect.utils.menus.PaginationItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OnlinePagination extends BasePagination {

    @Override
    public void setupItems() {

        List<ClickableItem> clickableItems = new ArrayList<>();

        for (Player player: Bukkit.getOnlinePlayers()) {
            PaginationItem clickItem = getMenu().paginationItem.clone();

            ClickableItem item = ClickableItem.empty(clickItem.build(new PlayerReplacements(player)));
            clickableItems.add(item);
        }

        getMenu().pagination.setItems(clickableItems);
    }
}
