package com.rainchat.placeprotect.data.paintclaim;


import com.rainchat.placeprotect.utils.claim.ClaimMode;
import com.rainchat.placeprotect.utils.visual.VisualizationType;
import org.bukkit.Location;


public class PlayerClaimInteraction {

    private Location pos1;
    private Location pos2;

    public ClaimMode mode;
    public PaintClaim editing;

    public PlayerClaimInteraction() {
        this.mode = ClaimMode.CREATE;
    }


    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public ClaimMode getMode() {
        return mode;
    }

    public VisualizationType getVisual() {
        switch (getMode()) {
            case CREATE:
                return VisualizationType.CLAIM;
            case EDIT:
                return VisualizationType.EDIT;
            case SUBCLAIM:
                return VisualizationType.CLAIM_SUB;
            case CREATE_ADMIN:
                return VisualizationType.ADMIN_CLAIM;
            default:
                return VisualizationType.CLAIM;
        }
    }

    public PaintClaim getEditing() {
        return editing;
    }

    public void setPos1(Location pos1) {
        if (this.pos1 != null) this.pos1.getBlock().getState().update();
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        if (this.pos2 != null) this.pos2.getBlock().getState().update();
        this.pos2 = pos2;
    }

    public void setMode(ClaimMode mode) {
        this.mode = mode;
    }

    public void setEditing(PaintClaim editing) {
        this.editing = editing;
    }
}
