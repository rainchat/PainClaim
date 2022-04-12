package com.rainchat.placeprotect.resourses.listeners;


import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.utils.claim.ClaimMode;
import com.rainchat.placeprotect.utils.claim.ClaimWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

public class CuboidEvent implements Listener {

    ClaimManager claimManager;


    public CuboidEvent(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @EventHandler
    public void onClickStick(PlayerInteractEvent event) {

        if (event.getItem() == null) return;
        if (!event.getItem().getType().equals(Material.FEATHER)) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        Action action = event.getAction();

        //Menu open
        if (player.isSneaking()) {
            //Bukkit.broadcastMessage("Вы открыли меню");
            return;
        }

        Location targetLocation = getTargetBlock(event.getPlayer(),96).getLocation();

        if (targetLocation.getBlock().getType() == Material.AIR) return;

        PaintPlayer paintPlayer = claimManager.loadPlayerData(player);

        //##############################
        //       Select Points
        //##############################
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
            if (paintPlayer.getClaimInteraction().getMode() == ClaimMode.EDIT) {
                ClaimWriter.resizeClaim(paintPlayer,paintPlayer.getClaimInteraction(), targetLocation);
                return;
            }
            paintPlayer.getClaimInteraction().setPos1(targetLocation);
            player.sendBlockChange(targetLocation, Bukkit.createBlockData(Material.PURPLE_TERRACOTTA));
        }

        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
            if (paintPlayer.getClaimInteraction().getMode() == ClaimMode.EDIT) {
                ClaimWriter.resizeClaim(paintPlayer,paintPlayer.getClaimInteraction(), targetLocation);
                return;
            }
            paintPlayer.getClaimInteraction().setPos2(targetLocation);
            player.sendBlockChange(targetLocation, Bukkit.createBlockData(Material.LIME_TERRACOTTA));
        }

        //##############################
        //         Edit Claim
        //##############################
        if (ClaimWriter.realizeEdit(paintPlayer, player, targetLocation)) {
            return;
        }

        //##############################
        //         Create Claim
        //##############################
        if (paintPlayer.getClaimInteraction().getPos1() != null && paintPlayer.getClaimInteraction().getPos2() != null) {
            ClaimWriter.createClaim(paintPlayer,player);
        }
    }


    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

}
