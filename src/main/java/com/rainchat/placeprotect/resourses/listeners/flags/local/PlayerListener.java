package com.rainchat.placeprotect.resourses.listeners.flags.local;


import com.rainchat.placeprotect.data.enums.ClaimPermission;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    private final ClaimManager claimManager;

    public PlayerListener(ClaimManager ClaimManager) {
        this.claimManager = ClaimManager;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (claimManager.loadPlayerData(player).isOverriding()) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        PaintClaim currentClaim = claimManager.getClaim(block.getLocation());
        if (currentClaim == null) return;

        if (!currentClaim.isOwner(player.getUniqueId().toString()) && !currentClaim.hasMember(player)) {
            event.setCancelled(true);
            return;
        }

        Material material = block.getType();
        if (material == Material.FURNACE) {
            if (currentClaim.hasPermission(ClaimPermission.FURNACE_ACCESS, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (material == Material.BARREL) {
            if (currentClaim.hasPermission(ClaimPermission.BARREL_ACCESS, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (block.getState() instanceof ShulkerBox) {
            if (currentClaim.hasPermission(ClaimPermission.SHULKER_ACCESS, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (material == Material.CHEST || material == Material.TRAPPED_CHEST) {
            if (currentClaim.hasPermission(ClaimPermission.CHEST_ACCESS, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (material == Material.HOPPER || material == Material.DISPENSER || material == Material.DROPPER) {
            if (currentClaim.hasPermission(ClaimPermission.RESTONE_INVENTORY_ACCESS, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (material == Material.BREWING_STAND) {
            if (currentClaim.hasPermission(ClaimPermission.BREWING_ACCESS, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (material == Material.ANVIL || material == Material.CHIPPED_ANVIL || material == Material.DAMAGED_ANVIL) {
            if (currentClaim.hasPermission(ClaimPermission.ANVIL_ACCESS, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (material == Material.DRAGON_EGG) {
            if (currentClaim.hasPermission(ClaimPermission.DRAGON_EGG_TOUCH, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockClicked();

        if (claimManager.loadPlayerData(player).isOverriding()) return;

        PaintClaim currentClaim = claimManager.getClaim(block.getLocation());
        if (currentClaim == null) return;

        if (!currentClaim.isOwner(player.getUniqueId().toString()) && !currentClaim.hasMember(player)) {
            event.setCancelled(true);
            return;
        }

        Material material = event.getBucket();
        if (material == Material.WATER_BUCKET || material == Material.COD_BUCKET || material == Material.AXOLOTL_BUCKET || material == Material.PUFFERFISH_BUCKET || material == Material.TROPICAL_FISH_BUCKET || material == Material.SALMON_BUCKET) {
            if (currentClaim.hasPermission(ClaimPermission.WATER_PLACEMENT, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (material == Material.LAVA_BUCKET) {
            if (currentClaim.hasPermission(ClaimPermission.LAVA_PLACEMENT, player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();

        if (claimManager.loadPlayerData(player).isOverriding()) return;

        ArmorStand armorStand = event.getRightClicked();

        PaintClaim currentClaim = claimManager.getClaim(armorStand.getLocation());
        if (currentClaim == null) return;

        if (!currentClaim.isOwner(player.getUniqueId().toString()) && !currentClaim.hasMember(player)) {
            event.setCancelled(true);
            return;
        }

        if (currentClaim.hasPermission(ClaimPermission.ARMOR_STAND_ACCESS, player.getUniqueId())) {
            event.setCancelled(true);
        }
    }


}
