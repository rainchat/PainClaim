package com.rainchat.placeprotect.utils.claim;

import com.rainchat.placeprotect.api.events.PaintClaimCreateEvent;
import com.rainchat.placeprotect.api.events.PaintClaimResizeEvent;
import com.rainchat.placeprotect.data.claim.Region;
import com.rainchat.placeprotect.data.config.ConfigCliam;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.data.paintclaim.PlayerClaimInteraction;
import com.rainchat.placeprotect.managers.ClaimManager;
import com.rainchat.placeprotect.utils.visual.Visualization;
import com.rainchat.placeprotect.utils.visual.VisualizationType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class ClaimWriter {

    private static ClaimManager claimManager;

    public static void setup(ClaimManager claimApi) {
        claimManager = claimApi;
    }

    public static void createClaim(PaintPlayer paintPlayer, Player player) {
        Region region = new Region(paintPlayer.getClaimInteraction().getPos1(), paintPlayer.getClaimInteraction().getPos2());

        PaintClaim paintClaim;
        if (paintPlayer.isOverriding()) {
            paintClaim = new PaintClaim("adminClaim",null, claimManager.generateUUID());
        } else {
            paintClaim = new PaintClaim(player.getName(),player.getUniqueId(), claimManager.generateUUID());
        }
        paintClaim.setRegion(region);

        Visualization visualization = Visualization.fromClaim(region,player.getLocation().getBlockY(), paintPlayer.getClaimInteraction().getVisual(), player.getLocation());
        paintPlayer.clear();


        //##############################
        //            Checks
        //##############################


        //Intersects with other regions
        if (claimManager.getClaims(player).size() > ConfigCliam.CLAIM_DEFAULT_CLAIM_LIMIT  && !paintPlayer.isOverriding()) {
            Bukkit.broadcastMessage("Превышен лимит регионов");
            return;
        }

        if (paintPlayer.getAvailableBlocks() < paintClaim.getRegion().getValueSize() && !paintPlayer.isOverriding()) {
            Bukkit.broadcastMessage("Недостаточно блоков для привата");
            paintPlayer.clear();
            return;
        }

        //Intersects with other regions
        if (isOverlaps(region,paintPlayer)) {
            Bukkit.broadcastMessage("Создаваемый регион пересикается с другим регионом");
            return;
        }

        //claim size
        if (!region.acceptableSize(ConfigCliam.CLAIM_MIN_SIZE,ConfigCliam.CLAIM_MAX_SIZE) && !paintPlayer.isOverriding()) {
            Bukkit.broadcastMessage("Размер вашего региона не соответствует требованиям");
            paintPlayer.clear();
            return;
        }

        //Create and apply region
        PaintClaimCreateEvent landEvent = new PaintClaimCreateEvent(paintClaim, player);
        Bukkit.getServer().getPluginManager().callEvent(landEvent);

        if (landEvent.isCancelled()) {
            return;
        }

        if (!paintPlayer.isOverriding()) {
            paintPlayer.setAvailableBlocks(paintPlayer.getAvailableBlocks()-paintClaim.getRegion().getValueSize());
        }

        claimManager.add(paintClaim);
        visualization.apply(paintPlayer.getPlayer());
        paintPlayer.setVisualization(visualization);


        Bukkit.broadcastMessage("Вы создали регион");
    }

    public static void createSubClaim() {

    }

    public static void resizeClaim(PaintPlayer paintPlayer, PlayerClaimInteraction claimInteraction, Location positionMovingCorner) {

        Region position = claimInteraction.getEditing().getRegion();
        Location start_location;
        if (claimInteraction.getPos1() != null) {
            start_location = claimInteraction.getPos1();
        } else {
            start_location = claimInteraction.getPos2();
        }


        ClaimCorner edittedCorner = position.getCorner(start_location);
        if (edittedCorner == null) {
            paintPlayer.getPlayer().sendMessage("We could not find the stiff corner!");
            paintPlayer.clear();
            return;
        }

        Vector positionStiff = position.getCorner(edittedCorner.opposite()); //Location that wont move, due to editing the opposite corner


        int max_x = Math.max(positionStiff.getBlockX(), positionMovingCorner.getBlockX());
        int max_z = Math.max(positionStiff.getBlockZ(), positionMovingCorner.getBlockZ());
        int min_x = Math.min(positionStiff.getBlockX(), positionMovingCorner.getBlockX());
        int min_z = Math.min(positionStiff.getBlockZ(), positionMovingCorner.getBlockZ());

        Location greater = new Location(claimInteraction.getEditing().getRegion().getWorld(), max_x, 0, max_z);
        Location lower = new Location(claimInteraction.getEditing().getRegion().getWorld(), min_x, 0, min_z);



        Region region = new Region(greater,lower);


        paintPlayer.getClaimInteraction().setPos1(null);
        paintPlayer.getClaimInteraction().setPos2(null);

        if (isOverlaps(region,paintPlayer,paintPlayer.getClaimInteraction().getEditing().getRegion())) {
            Bukkit.broadcastMessage("Создаваемый регион пересикается с другим регионом");
            paintPlayer.clear();
            return;
        }
        if (!region.acceptableSize(10,256)) {
            Bukkit.broadcastMessage("Размер вашего региона не соответствует требованиям");
            paintPlayer.clear();
            return;
        }

        Visualization visualization = Visualization.fromClaim(region,paintPlayer.getPlayer().getLocation().getBlockY(), VisualizationType.CLAIM, paintPlayer.getPlayer().getLocation());


        PaintClaimResizeEvent landEvent = new PaintClaimResizeEvent(paintPlayer.getClaimInteraction().getEditing(), paintPlayer.getPlayer(), greater,lower);
        Bukkit.getServer().getPluginManager().callEvent(landEvent);

        if (landEvent.isCancelled()) {
            return;
        }

        visualization.apply(paintPlayer.getPlayer());

        paintPlayer.setVisualization(visualization);
        paintPlayer.getClaimInteraction().getEditing().setRegion(region);
        paintPlayer.clear();
    }

    public static boolean isOverlaps(Region region, PaintPlayer paintPlayer,Region... ignore) {
        List<Region> regions = claimManager.regionOverlaps(region);

        for (Region region1: ignore) {
            regions.remove(region1);
        }

        if (regions.size() > 0) {
            Visualization visualization = Visualization.fromClaim(regions,paintPlayer.getPlayer().getLocation().getBlockY(), VisualizationType.OVERLAPS, paintPlayer.getPlayer().getLocation());

            visualization.apply(paintPlayer.getPlayer());

            paintPlayer.setVisualization(visualization);

            return true;
        }
        return false;
    }

    public static boolean realizeEdit(PaintPlayer paintPlayer, Player player, Location location) {
        PaintClaim paintClaim;

        if (paintPlayer.isOverriding()) {
            paintClaim = claimManager.getClaim(location);
        } else {
            paintClaim = claimManager.getClaim(paintPlayer.getPlayer(),location);
        }

        if (paintClaim == null) {
            return false;
        }

        Region position = paintClaim.getRegion();

        if (!position.isCorner(location)) {
            return false;
        }

        Visualization visualization = Visualization.fromClaim(paintClaim.getRegion(), player.getLocation().getBlockY(), VisualizationType.EDIT, player.getLocation());

        paintPlayer.getClaimInteraction().setMode(ClaimMode.EDIT);
        paintPlayer.getClaimInteraction().setEditing(paintClaim);

        visualization.apply(paintPlayer.getPlayer());

        paintPlayer.setVisualization(visualization);

        return true;
    }
}
