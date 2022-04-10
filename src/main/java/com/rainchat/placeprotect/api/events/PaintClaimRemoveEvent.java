package com.rainchat.placeprotect.api.events;

import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PaintClaimRemoveEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private final PaintClaim claim;
    private final Player remover;

    /**
     * Broadcasts this claim that was removed
     * @param claim The claim that was just removed
     * @param remover The player who remove this claim
     */
    public PaintClaimRemoveEvent(PaintClaim claim, Player remover) {
        this.claim = claim;
        this.remover = remover;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getRemover() {
        return remover;
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
