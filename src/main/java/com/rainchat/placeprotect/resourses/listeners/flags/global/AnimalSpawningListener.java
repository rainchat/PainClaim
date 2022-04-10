package com.rainchat.placeprotect.resourses.listeners.flags.global;

import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class AnimalSpawningListener implements Listener {

    private final ClaimManager claimManager;

    public AnimalSpawningListener(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }


    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        PaintClaim claim = claimManager.getClaim(event.getEntity().getLocation());
        if (claim == null) {
            return;
        }

        if (event.getEntity() instanceof Animals) {
            if (claim.hasPermission("ANIMALS_SPAWN", event.getEntity().getLocation())) {
                event.setCancelled(true);
            }
        }
    }

}
