package com.rainchat.placeprotect.builder.pagination;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.rainchat.placeprotect.api.placeholder.ClaimReplcements;
import com.rainchat.placeprotect.api.placeholder.FlagReplacements;
import com.rainchat.placeprotect.api.placeholder.MemberReplacements;
import com.rainchat.placeprotect.api.placeholder.PlayerReplacements;
import com.rainchat.placeprotect.data.config.ConfigFlags;
import com.rainchat.placeprotect.data.flag.FlagClaim;
import com.rainchat.placeprotect.data.paintclaim.ClaimMember;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.managers.FlagManager;
import com.rainchat.placeprotect.utils.general.Chat;
import com.rainchat.placeprotect.utils.menus.Executor;
import com.rainchat.placeprotect.utils.menus.PaginationItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClaimMembersPagination extends BasePagination {

    @Override
    public void setupItems() {

        PaintClaim paintClaim = ClaimManager.INSTANCE.getClaim(UUID.fromString(getMenu().getParameter().get("claim")));

        List<ClickableItem> clickableItems = new ArrayList<>();

        for (ClaimMember claimMember : paintClaim.getVillageMembers()) {
            PaginationItem clickItem = getMenu().paginationItem.clone();

            Player member = Bukkit.getPlayer(claimMember.getUniqueId());

            ClickableItem item = ClickableItem.empty(clickItem.build(new ClaimReplcements(paintClaim), new PlayerReplacements(member)));
            item.setClick(event -> {

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

}
