package com.rainchat.placeprotect.resourses.listeners;

import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {


    private final ClaimManager claimManager;

    public ConnectListener(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        claimManager.getPlayerData(event.getPlayer()).clear();
    }


}
