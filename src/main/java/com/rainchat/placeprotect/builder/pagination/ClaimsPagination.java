package com.rainchat.placeprotect.builder.pagination;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.rainchat.placeprotect.api.placeholder.ClaimReplcements;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.utils.general.Chat;
import com.rainchat.placeprotect.utils.menus.Executor;
import com.rainchat.placeprotect.utils.menus.PaginationItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ClaimsPagination extends BasePagination {

    @Override
    public void setupItems() {

        Player player;

        if (getMenu().getParameter().get("target") != null) {
             player = Bukkit.getPlayer(getMenu().getParameter().get("target"));
        } else {
            player = null;
        }

        List<PaintClaim> claimList;
        if (player != null) {
            claimList = ClaimManager.INSTANCE.getClaims(player);
        } else {
            claimList =  new ArrayList<>(ClaimManager.INSTANCE.getClaims());
        }

        List<ClickableItem> clickableItems = new ArrayList<>();

        for (PaintClaim paintClaim: claimList) {
            PaginationItem clickItem = getMenu().paginationItem.clone();

            ClickableItem item = ClickableItem.empty(clickItem.build(new ClaimReplcements(paintClaim)));
            item.setClick(event -> {

                if (event.isLeftClick()) {
                    new Executor(Chat.translateRaw(getMenu().paginationItem.getLeftClick(), new ClaimReplcements(paintClaim)), getPlayer(), getMenu()).start();
                }
                if (event.isRightClick()) {
                    new Executor(Chat.translateRaw(getMenu().paginationItem.getRightClick(), new ClaimReplcements(paintClaim)), getPlayer(), getMenu()).start();
                }
                new Executor(Chat.translateRaw(getMenu().paginationItem.getCommands(), new ClaimReplcements(paintClaim)), getPlayer(), getMenu()).start();


            });

            clickableItems.add(item);
        }

        if (clickableItems.size() < 1) {
            clickableItems.add(ClickableItem.empty(new ItemStack(Material.AIR)));
        }

        getMenu().pagination.setItems(clickableItems);
    }
}
