package com.rainchat.placeprotect.resourses.commands;


import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.managers.MenuManager;
import com.rainchat.placeprotect.utils.claim.ClaimMode;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;


@Command("aclaim")
public class AdminCommands {

    private final ClaimManager claimManager;

    public AdminCommands(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @Subcommand("adminmode")
    @CommandPermission("paintclain.commands.adminmode")
    public void onMode(Player player) {
        claimManager.loadPlayerData(player).setOverriding();
        claimManager.loadPlayerData(player).clear();
        claimManager.loadPlayerData(player).getClaimInteraction().setMode(ClaimMode.CREATE_ADMIN);

        player.sendMessage("Вы установили режим админа:" + claimManager.loadPlayerData(player).isOverriding());

    }

    @Subcommand("openMenu")
    @CommandPermission("paintclain.commands.adminlist")
    public void onAdminList(Player player) {
        MenuManager.getInstance().openMenu(player,"test");
    }


    @Subcommand("addBlocks")
    @CommandPermission("paintclain.commands.adminlist")
    public void onAddClaim(Player player, Player target, int blocks) {
        PaintPlayer paintPlayer = claimManager.loadPlayerData(target);
        PaintClaim paintClaim = claimManager.getClaim(target.getLocation());

        paintPlayer.setBonusBlocks(paintPlayer.getBonusBlocks() + blocks);
        paintPlayer.setAvailableBlocks(paintPlayer.getAvailableBlocks() + blocks);
    }

}
