package com.rainchat.placeprotect.resourses.commands;


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
        claimManager.getPlayerData(player).setOverriding();
        claimManager.getPlayerData(player).clear();
        claimManager.getPlayerData(player).getClaimInteraction().setMode(ClaimMode.CREATE_ADMIN);

        player.sendMessage("Вы установили режим админа:" + claimManager.getPlayerData(player).isOverriding());

    }

    @Subcommand("openMenu")
    @CommandPermission("paintclain.commands.adminlist")
    public void onAdminList(Player player) {
        MenuManager.getInstance().openMenu(player,"test");
    }

}
