package com.rainchat.placeprotect.listeners.flags.global;

import com.rainchat.placeprotect.PlaceProtect;
import com.rainchat.placeprotect.data.village.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BurnEvent implements Listener {

    private final ClaimManager claimManager;

    public BurnEvent(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @EventHandler
    public void onBlockIgnite(BlockSpreadEvent event) {
        if (event.getSource().getType() != Material.FIRE) return;

        PaintClaim entityVillage = claimManager.getClaim(event.getBlock().getLocation());
        if (entityVillage == null) {
            return;
        }

        if (entityVillage.hasPermission("FIRE_SPREAD", event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        PaintClaim entityVillage = claimManager.getClaim(event.getBlock().getLocation());
        if (entityVillage == null) {
            return;
        }

        if (entityVillage.hasPermission("FIRE_SPREAD", event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }


}
