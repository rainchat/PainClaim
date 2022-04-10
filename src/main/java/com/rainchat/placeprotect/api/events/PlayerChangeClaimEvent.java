package com.rainchat.placeprotect.api.events;

import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChangeClaimEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final PaintClaim fromClaim;
    private final PaintClaim toClaim;
    private boolean cancelled;

    public PlayerChangeClaimEvent(Player p, PaintClaim fromClaim, PaintClaim toClaim) {
        this.player = p;
        this.fromClaim = fromClaim;
        this.toClaim = toClaim;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public PaintClaim getFromClaim() {
        return fromClaim;
    }

    public PaintClaim getToClaim() {
        return toClaim;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}