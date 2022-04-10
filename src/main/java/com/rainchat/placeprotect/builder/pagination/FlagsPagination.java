package com.rainchat.placeprotect.builder.pagination;

import com.hakan.inventoryapi.inventory.ClickableItem;
import com.rainchat.placeprotect.api.placeholder.ClaimReplcements;
import com.rainchat.placeprotect.api.placeholder.FlagReplacements;
import com.rainchat.placeprotect.data.config.ConfigFlags;
import com.rainchat.placeprotect.data.flag.FlagClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.managers.FlagManager;
import com.rainchat.placeprotect.utils.general.Chat;
import com.rainchat.placeprotect.utils.items.Item;
import com.rainchat.placeprotect.utils.menus.Executor;
import com.rainchat.placeprotect.utils.menus.PaginationItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlagsPagination extends BasePagination {



    @Override
    public void setupItems() {

        Player player = getPlayer();
        PaintClaim paintClaim = ClaimManager.INSTANCE.getClaim(UUID.fromString(getMenu().getParameter().get("claim")));

        List<ClickableItem> clickableItems = new ArrayList<>();

        for (FlagClaim flagClaim: FlagManager.INSTANCE.getFlags()) {
            PaginationItem clickItem = getMenu().paginationItem.clone();

            FlagReplacements flagReplacements = new FlagReplacements(flagClaim, ConfigFlags.FLAG_ITEM.get(flagClaim.getName()), paintClaim.hasPermission(flagClaim.getName()));
            ClickableItem item = ClickableItem.empty(permissionGlobal(flagClaim.getName(), clickItem, paintClaim.hasPermission(flagClaim.getName()), flagReplacements));
            item.setClick(event -> {
                if (!player.hasPermission("paintclaim.flag." + flagClaim.getName())) return;

                if (paintClaim.hasPermission(flagClaim.getName())) {
                    paintClaim.remove(flagClaim.getName());
                } else {
                    paintClaim.add(flagClaim.getName());
                }


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

    private ItemStack permissionGlobal(String villageGlobalPermission, PaginationItem paginationItem, boolean enabled, FlagReplacements flagReplacements) {
        Item item = new Item();
        if (enabled) {
            item.material(flagReplacements.getMaterial().toString());
            item.name(paginationItem.getName().replace("%Permission%", villageGlobalPermission));
            item.lore(paginationItem.getLore());
        } else {
            item.material(Material.GRAY_DYE.toString());
            item.name(paginationItem.getName().replace("%Permission%", villageGlobalPermission));
            item.lore(paginationItem.getLore());
        }
        return item.build(flagReplacements);
    }
}
