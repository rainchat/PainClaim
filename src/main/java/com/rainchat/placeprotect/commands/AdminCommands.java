package com.rainchat.placeprotect.commands;


import com.rainchat.placeprotect.data.village.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;

import com.rainchat.placeprotect.utils.claim.ClaimMode;
import com.rainchat.raingui.menus.PaginationMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Command("claim")
public class AdminCommands {

    private final ClaimManager claimManager;

    public AdminCommands(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @Subcommand("adminmode")
    @CommandPermission("paintclain.commands.adminmode")
    public void onMode(Player player) {
        claimManager.getPlayerData(player).setOverriding();
        claimManager.getPlayerData(player).clear();
        claimManager.getPlayerData(player).getClaimInteraction().setMode(ClaimMode.CREATE_ADMIN);

        player.sendMessage("Вы установили режим админа:" + claimManager.getPlayerData(player).isOverriding());

    }

    @Subcommand("adminlist")
    @CommandPermission("paintclain.commands.adminlist")
    public void onAdminList(Player player) {
        /*PaginationMenu menu = new PaginationMenu(InventoryAPI.getInstance(CubeCore.getPlugin()), "Test", InventoryType.CHEST, 4);
        menu.setItemSlots(new ArrayList<>(Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35)));


        List<ClickableItem> clickableItems = new ArrayList<>();

        for (PaintClaim paintClaim : claimManager.getClaims()) {

            ClickableItem item = ClickableItem.of(new Item().name("test").material(Material.GRASS_BLOCK).
                    lore(Arrays.asList(
                            "Имя - " + paintClaim.getName(),
                            "UUID - " + paintClaim.getId(),
                            "Админ регион - " + paintClaim.isAdminClaim(),
                            "Размер - " + paintClaim.getRegion().getSize())), inventoryClickEvent ->
            {
                claimManager.remove(paintClaim);
            });


            clickableItems.add(item);
        }
        menu.setItems(clickableItems);
        menu.guiFill(ClickableItem.empty(new Item().material(Material.BLACK_STAINED_GLASS_PANE)));

        menu.open(player);

         */
    }

}
