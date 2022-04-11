package com.rainchat.placeprotect.resourses.commands;


import com.rainchat.placeprotect.api.placeholder.ClaimReplcements;
import com.rainchat.placeprotect.data.config.LanguageFile;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.managers.MenuManager;
import com.rainchat.placeprotect.utils.claim.ClaimMode;
import com.rainchat.placeprotect.utils.general.Chat;
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

        Chat.sendTranslation(player,true, LanguageFile.CLAIM_ADMIN_MODE.getMessage());

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
        Chat.sendTranslation(player,true, LanguageFile.CLAIM_ADMIN_ADD_BLOCKS.getMessage().
                replace("%target_name%", target.getName()).
                replace("%player_name%", player.getName()).
                replace("%blocks_add%", String.valueOf(blocks))
                );
    }

}
