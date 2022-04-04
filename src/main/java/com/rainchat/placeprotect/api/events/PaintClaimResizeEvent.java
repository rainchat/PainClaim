package com.rainchat.placeprotect.api.events;

import com.rainchat.placeprotect.data.village.PaintClaim;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PaintClaimResizeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private final PaintClaim claim;
    private final Player editor;

    private final Location newLoc1;
    private final Location newLoc2;

    /**
     * Broadcasts this new claim that was created
     * @param claim The claim that was just created
     * @param editor The player who edit this claim
     */
    public PaintClaimResizeEvent(PaintClaim claim, Player editor, Location loc1, Location loc2) {
        this.claim = claim;
        this.editor = editor;
        this.newLoc1 = loc1;
        this.newLoc2 = loc2;
    }

    public Location getNewLoc1() {
        return newLoc1;
    }

    public Location getNewLoc2() {
        return newLoc2;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getEditor() {
        return editor;
    }

    public PaintClaim getClaim() {
        return claim;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
