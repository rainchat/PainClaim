package com.rainchat.placeprotect.resourses.listeners;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.rainchat.placeprotect.api.placeholder.ClaimReplcements;
import com.rainchat.placeprotect.data.claim.Region;
import com.rainchat.placeprotect.data.config.LanguageFile;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.utils.general.Chat;
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
        PaintPlayer paintPlayer = claimManager.loadPlayerData(player);

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
            Chat.sendTranslation(player,false, LanguageFile.CLAIM_JOIN_WILDERNESS.getMessage());
            return;
        }

        Chat.sendTranslation(player,false, LanguageFile.CLAIM_JOIN_REGION.getMessage(), new ClaimReplcements(paintClaim));

    }




}
