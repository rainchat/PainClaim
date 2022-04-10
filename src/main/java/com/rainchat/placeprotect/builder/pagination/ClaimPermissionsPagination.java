package com.rainchat.placeprotect.builder.pagination;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.rainchat.placeprotect.api.placeholder.*;
import com.rainchat.placeprotect.data.enums.ClaimPermission;
import com.rainchat.placeprotect.data.paintclaim.ClaimMember;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.utils.general.Chat;
import com.rainchat.placeprotect.utils.items.Item;
import com.rainchat.placeprotect.utils.menus.Executor;
import com.rainchat.placeprotect.utils.menus.PaginationItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClaimPermissionsPagination extends BasePagination {

    @Override
    public void setupItems() {

        PaintClaim paintClaim = ClaimManager.INSTANCE.getClaim(UUID.fromString(getMenu().getParameter().get("claim")));
        ClaimMember claimMember = paintClaim.getMember(UUID.fromString(getMenu().getParameter().get("member")));


        List<ClickableItem> clickableItems = new ArrayList<>();

        for (ClaimPermission claimPermission : ClaimPermission.values()) {

            PaginationItem clickItem = getMenu().paginationItem.clone();

            ClickableItem item = ClickableItem.empty(permission(claimPermission, clickItem, claimMember.hasPermission(claimPermission),
            new PermissionReplacements(claimPermission,claimMember.hasPermission(claimPermission))));
            item.setClick(event -> {

                if (claimMember.hasPermission(claimPermission)) {
                    claimMember.remove(claimPermission);
                } else {
                    claimMember.add(claimPermission);
                }


                if (event.isLeftClick()) {
                    new Executor(Chat.translateRaw(getMenu().paginationItem.getLeftClick(), new ClaimReplcements(paintClaim), new MemberReplacements(claimMember)), getPlayer(), getMenu()).start();
                }
                if (event.isRightClick()) {
                    new Executor(Chat.translateRaw(getMenu().paginationItem.getRightClick(), new ClaimReplcements(paintClaim), new MemberReplacements(claimMember)), getPlayer(), getMenu()).start();
                }
                new Executor(Chat.translateRaw(getMenu().paginationItem.getCommands(), new ClaimReplcements(paintClaim), new MemberReplacements(claimMember)), getPlayer(), getMenu()).start();


            });

            clickableItems.add(item);
        }

        if (clickableItems.size() < 1) {
            clickableItems.add(ClickableItem.empty(new ItemStack(Material.AIR)));
        }

        getMenu().pagination.setItems(clickableItems);
    }

    private ItemStack permission(ClaimPermission villagePermission, PaginationItem paginationItem, boolean enabled, PermissionReplacements permissionReplacements) {
        Item item = new Item();
        if (enabled) {
            item.material(Material.LIME_DYE.toString());
            item.name(paginationItem.getName().replace("%Permission%", villagePermission.name()));
            item.lore(paginationItem.getLore());
        } else {
            item.material(Material.GRAY_DYE.toString());
            item.name(paginationItem.getName().replace("%Permission%", villagePermission.name()));
            item.lore(paginationItem.getLore());
        }
        return item.build(permissionReplacements);
    }

}