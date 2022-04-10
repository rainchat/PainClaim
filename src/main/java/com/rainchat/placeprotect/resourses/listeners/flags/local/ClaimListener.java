package com.rainchat.placeprotect.resourses.listeners.flags.local;

import com.rainchat.placeprotect.data.enums.ClaimPermission;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ClaimListener implements Listener {

    private final ClaimManager claimManager;

    public ClaimListener(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }


    @EventHandler
    public void Teleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        PaintPlayer landPlayer = claimManager.loadPlayerData(player);

        if (event.getFrom().getChunk() != player.getLocation().getChunk()) {
            PaintClaim claim = claimManager.getClaim(player.getLocation());
            if (landPlayer.getCurrentClaim() != claim) {
                landPlayer.setCurrentClaim(claim);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        PaintPlayer landPlayer = claimManager.loadPlayerData(player);

        if (landPlayer.isOverriding()) return;

        PaintClaim currentClaim = claimManager.getClaim(block.getLocation());
        if (currentClaim == null) return;

        if (!currentClaim.isOwner(player.getUniqueId().toString()) && !currentClaim.hasMember(player)) {
            event.setCancelled(true);
            return;
        }

        if (currentClaim.hasPermission(ClaimPermission.BLOCK_BREAK, player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        PaintPlayer landPlayer = claimManager.loadPlayerData(player);

        if (landPlayer.isOverriding()) return;

        PaintClaim currentClaim = claimManager.getClaim(block.getLocation());
        if (currentClaim == null) return;

        if (!currentClaim.isOwner(player.getUniqueId().toString()) && !currentClaim.hasMember(player)) {
            event.setCancelled(true);
            return;
        }

        if (currentClaim.hasPermission(ClaimPermission.BLOCK_BREAK, player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

}
