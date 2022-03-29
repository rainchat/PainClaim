package com.rainchat.placeprotect.listeners;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.rainchat.placeprotect.data.claim.Region;
import com.rainchat.placeprotect.data.village.PaintClaim;
import com.rainchat.placeprotect.data.village.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    private final ClaimManager claimManager;

    public MoveEvent(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo().getBlockX() == event.getFrom().getBlockX() &&
                event.getTo().getBlockY() == event.getFrom().getBlockY() &&
                event.getTo().getBlockZ() == event.getFrom().getBlockZ()) {
            return; // user didnt actually move a full block
        }

        Player player = event.getPlayer();
        PaintPlayer paintPlayer = claimManager.getPlayerData(player);

        PaintClaim paintClaim = claimManager.getClaim(event.getTo());

        Region newRegion = null;

        if (paintClaim != null) {
            newRegion = paintClaim.getRegion();
        }

        PaintClaim oldClaim  = paintPlayer.getCurrentClaim();

        Region oldRegion = null;

        if (oldClaim != null) {
            oldRegion = oldClaim.getRegion();
        }

        if (newRegion == oldRegion) {
            return; // user is still inside the same region as last time
        }

        paintPlayer.setCurrentClaim(paintClaim);

        if (newRegion == null) {
            player.sendTitle(
                    IridiumColorAPI.process("<GRADIENT:2C08BA>&lWilderness!</GRADIENT:028A97>&a"),
                    IridiumColorAPI.process("&7Fresh new land awaits you."),
                    10, 40, 10
            );
            return;
        }

        player.sendTitle(
                IridiumColorAPI.process("&a&lNew Region!"),
                        IridiumColorAPI.process("&7&7Welcome to &b" + paintClaim.getName()),
                10, 40, 10
        );




    }




}
