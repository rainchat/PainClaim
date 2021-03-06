package com.rainchat.placeprotect.resourses.listeners.flags.global;


import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityListener implements Listener {

    private final ClaimManager claimManager;

    public EntityListener(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {

        for (Block block : event.blockList()) {
            PaintClaim village = claimManager.getClaim(block.getLocation());
            if (village == null) {
                continue;
            }
            if (village.hasPermission("EXPLOSIONS", block.getLocation())) {
                event.blockList().remove(block);
                event.setCancelled(true);
                return;
            }
        }

    }

    @EventHandler
    public void onDamageAnimal(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Animals && event.getDamager() instanceof Player) {
            Entity entity = event.getEntity();
            PaintClaim entityClaim = claimManager.getClaim(entity.getLocation());
            Player player = (Player) event.getDamager();

            if (claimManager.loadPlayerData(player).isOverriding()) return;

            if (entityClaim != null) {
                if (entityClaim.hasMember(player)) {
                    return;
                }
                if (entityClaim.hasPermission("DAMAGE_ANIMALS", entity.getLocation())) {
                    event.setCancelled(true);
                }
            }

        }

    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player1 = (Player) event.getEntity();
            PaintClaim entityVillage = claimManager.getClaim(player1.getLocation());

            Player player2 = (Player) event.getDamager();
            PaintClaim otherVillage = claimManager.getClaim(player2.getLocation());

            if (entityVillage != null) {
                if (claimManager.loadPlayerData(player1).isOverriding()) return;
                if (entityVillage.hasPermission("PVP", player1.getLocation())) {
                    event.setCancelled(true);
                }
            }
            if (otherVillage != null) {
                if (claimManager.loadPlayerData(player2).isOverriding()) return;
                if (otherVillage.hasPermission("PVP", player2.getLocation())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {

        for (Block block : event.blockList()) {
            PaintClaim village = claimManager.getClaim(block.getLocation());
            if (village == null) {
                continue;
            }
            if (village.hasPermission("EXPLOSIONS", block.getLocation())) {
                event.setCancelled(true);
                return;
            }
        }

    }

    /*
    @EventHandler
    public void itemFramePlace(HangingPlaceEvent event) {
        Village village = villageManager.getVillage(event.getEntity().getLocation().getChunk());
        if (village == null) {
            return;
        }
        Player player = event.getPlayer();
        if (!village.hasMember(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void armourStandEvents(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        Village village = villageManager.getVillage(player.getLocation().getChunk());
        if (village == null) {
            return;
        }
        if (!village.hasMember(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        if (!(event.getItem().getType().equals(Material.ARMOR_STAND))) {
            return;
        }

        Player player = event.getPlayer();
        Village village = villageManager.getVillage(event.getClickedBlock().getChunk());
        if (village == null) {
            return;
        }
        if (!village.hasMember(player)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onItemFrameInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof ItemFrame)) {
            return;
        }

        Player player = event.getPlayer();
        Village village = villageManager.getVillage(event.getRightClicked().getLocation().getChunk());
        if (village == null) {
            return;
        }
        if (!village.hasMember(player)) {
            event.setCancelled(true);
        }

    }


    @EventHandler
    public void ArmorStandDestroy(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof ItemFrame) && !(event.getEntity() instanceof ArmorStand)) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        Village village = villageManager.getVillage(event.getEntity().getLocation().getChunk());

        if (village == null) {
            return;
        }
        if (village.hasMember(player)) {
            return;
        }

        event.setCancelled(true);
    }

     */


}
