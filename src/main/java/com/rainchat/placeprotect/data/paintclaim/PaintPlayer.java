package com.rainchat.placeprotect.data.paintclaim;

import com.rainchat.placeprotect.utils.claim.ClaimMode;
import com.rainchat.placeprotect.utils.visual.Visualization;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaintPlayer {
    private final UUID playerUUID;
    private PaintClaim paintClaim;

    private int availableBlocks;

    private int bonusBlocks;
    private int claimBlocks;

    private Visualization visualization;
    private boolean overriding;

    private final PlayerClaimInteraction claimInteraction;

    public PaintPlayer(Player player) {
        this.playerUUID = player.getUniqueId();
        this.visualization = new Visualization();
        this.claimInteraction = new PlayerClaimInteraction();
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPaintClaim(PaintClaim paintClaim) {
        this.paintClaim = paintClaim;
    }

    public void setAvailableBlocks(int availableBlocks) {
        this.availableBlocks = availableBlocks;
    }

    public int getBonusBlocks() {
        return bonusBlocks;
    }

    public void setBonusBlocks(int bonusBlocks) {
        this.bonusBlocks = bonusBlocks;
    }

    public int getClaimBlocks() {
        return claimBlocks;
    }

    public void setClaimBlocks(int claimBlocks) {
        this.claimBlocks = claimBlocks;
    }

    public void setOverriding(boolean overriding) {
        this.overriding = overriding;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public int getAvailableBlocks() {
        return availableBlocks;
    }

    public PaintClaim getCurrentClaim() {
        return paintClaim;
    }

    public PaintClaim getPaintClaim() {
        return paintClaim;
    }

    public PlayerClaimInteraction getClaimInteraction() {
        return claimInteraction;
    }

    public Visualization getVisualization() {
        return visualization;
    }

    public boolean isOverriding() {
        return overriding;
    }

    public void setVisualization(Visualization visualization) {
        this.visualization = visualization;
    }

    public void setCurrentClaim(PaintClaim paintClaim) {
        this.paintClaim = paintClaim;
    }

    public void setOverriding() {
        this.overriding = !overriding;
    }

    public void removeVisualization() {
        visualization = null;
    }

    public void clear() {
        getClaimInteraction().setPos1(null);
        getClaimInteraction().setPos2(null);
        if (overriding) {
            getClaimInteraction().setMode(ClaimMode.CREATE_ADMIN);
        } else {
            getClaimInteraction().setMode(ClaimMode.CREATE);
        }
        getClaimInteraction().setEditing(null);
    }

}