package com.rainchat.placeprotect.schedullers;

import com.rainchat.placeprotect.data.config.ConfigCliam;
import com.rainchat.placeprotect.data.paintclaim.PaintClaim;
import com.rainchat.placeprotect.data.paintclaim.PaintPlayer;
import com.rainchat.placeprotect.managers.ClaimManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClaimBlocksTask extends BukkitRunnable {

    private final ClaimManager claimManager;
    private int i = 0;

    public ClaimBlocksTask(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    @Override
    public void run() {
        if (i >= 60) {
            i = 0;
            for (Player player: Bukkit.getOnlinePlayers()) {
                PaintPlayer paintPlayer = claimManager.loadPlayerData(player);
                if (paintPlayer != null) {
                    paintPlayer.setBonusBlocks(paintPlayer.getBonusBlocks()+ ConfigCliam.ADD_BLOCK_PER_MINUTE);
                    claimManager.calculatePlayer(player,paintPlayer);
                }
            }
        }

        i++;
    }

}