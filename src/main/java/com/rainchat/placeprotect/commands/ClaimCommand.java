package com.rainchat.placeprotect.commands;


import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.data.village.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.menu.ClaimMenu;
import com.rainchat.placeprotect.utils.claim.ClaimMode;
import com.rainchat.placeprotect.utils.claim.ClaimWriter;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("claim")
public class ClaimCommand {

    private final ClaimManager claimManager;

    public ClaimCommand(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @Subcommand("list")
    @CommandPermission("paintclain.commands.list")
    public void onList(Player player) {
        ClaimMenu menu = new ClaimMenu(player, PlaceProtect.getInstance());
        menu.addItems();
        menu.refreshPages();

        menu.open(player);

    }

    @Subcommand("claim")
    @CommandPermission("paintclain.commands.claim")
    public void onClaim(Player player, int radius) {
        PaintPlayer paintPlayer = claimManager.getPlayerData(player);
        paintPlayer.getClaimInteraction().setMode(ClaimMode.CREATE);
        paintPlayer.getClaimInteraction().setPos1(player.getLocation().add(radius,0,radius));
        paintPlayer.getClaimInteraction().setPos2(player.getLocation().add(-radius,0,-radius));

        ClaimWriter.createClaim(paintPlayer, player);

    }


}
