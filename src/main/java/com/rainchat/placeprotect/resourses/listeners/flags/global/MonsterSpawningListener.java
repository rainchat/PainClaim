package com.rainchat.placeprotect.resourses.listeners.flags.global;

import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MonsterSpawningListener implements Listener {

    private final ClaimManager claimManager;

    public MonsterSpawningListener(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }


    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        PaintClaim claim = claimManager.getClaim(event.getEntity().getLocation());
        if (claim == null) {
            return;
        }

        if (event.getEntity() instanceof Monster) {
            if (claim.hasPermission("MONSTERS_SPAWN", event.getEntity().getLocation())) {
                event.setCancelled(true);
            }
        }
    }

}
